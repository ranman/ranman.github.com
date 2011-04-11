//
// Sensor-testing module.
//
// Chris Blades
// Randall Hunt
// Benjamin Rudolph
//

#include "TCPMessage.h"

//
// The length of a single read cycle, in which data is read from all sensors on
// the mote.
#define CYCLE_LENGTH 340
#define ROOT_ADDRESS 0

module TCPSendC {
    provides interface AMSend;

    uses interface Boot;
    uses interface SplitControl as AMControl;
    uses interface Packet as Packet;
    uses interface AMPacket as AMPacket;
    uses interface AMSend as Send;
    uses interface Receive;
    uses interface Timer<TMilli>;

    uses interface Leds;
}
implementation {
    bool sendLocked = FALSE;  // a lock on sending
    error_t   sentError;
    message_t *message;
    message_t TCPMsg;

    //
    // This implements things we want to get done when the device starts.
    // Start AMControl so we can do networking and begin our read cycle.
    //
    event void Boot.booted() {
        call AMControl.start();     // Start the radio.
    }


    event void Timer.fired() {
        if (sendLocked == TRUE) {
            sendLocked = FALSE;
            call AMSend.send(AM_BROADCAST_ADDR, message, sizeof(TCPMessage));
        }
    }

    //////////////////////////////////
    // AMSend Implementation
    // /////////////////////////////

    //
    // cancels the current outstanding message
    //
    // @param msg the message_t struct that is outstanding
    //
    command error_t AMSend.cancel(message_t *msg) {
    }

    //
    // returns the payload originally sent with this implementation, minus any
    // extra data this implementation attatched.
    //
    // @param msg message_t struct to dig payload out of
    // @param len the length of the payload
    //
    command void *AMSend.getPayload(message_t *msg, uint8_t len) {
        return (call Send.getPayload(msg, len));
    }
    
    //
    // returns the maximum payload length that is allowed; i.e. the max payload
    // length of the underlying AMSend minus any overhead we introduce
    //
    command uint8_t AMSend.maxPayloadLength() {
        return (call Send.maxPayloadLength()) - TCPHeaderSize;
    }

    //
    // Sends a message
    //
    // @param addr address to send the message to
    // @param msg  message to send
    // @param len  length of the message to send
    command error_t AMSend.send(am_addr_t addr, message_t *msg, uint8_t len) {
        TCPMessage *TCPPayload; 
        error_t status;
        call Leds.led2Toggle();
        if (sendLocked) {
            return EBUSY;
        } else {
            message = msg;
            TCPPayload = (TCPMessage *)call Send.getPayload(&TCPMsg, 
                                                            sizeof(TCPMessage));
            TCPPayload->ACK         = FALSE;
            TCPPayload->fromAddress = call AMPacket.address();

            if (len < TCPPayloadSize) {
                memcpy(&TCPPayload->payload, call Send.getPayload(msg, len), 
                                                                len);
            } else {
                return !SUCCESS;
            }
            
            status = call Send.send(addr, &TCPMsg, len + TCPHeaderSize);
            call Leds.led2Toggle();
            if (status == SUCCESS) {
                sendLocked = TRUE;
                
                //call Timer.startPeriodic(300);
                return SUCCESS;
            } else if(status == EBUSY) {
                //call Leds.led1Toggle();
            } else if(status == FAIL) {
               // call Leds.led1Toggle();
            } else if(status == ESIZE) {
                //call Leds.led1Toggle();
            }
            return FAIL;
        }
    }

    //
    // Signaled when packet gets done sending, signals sendDone on the 
    // sender above us
    //
    // @param msg   the messsage_t struct that was sent
    // @param error status of send
    event void Send.sendDone(message_t *msg, error_t error) {
        sentError = error;
    }

    //
    // This implements things to be done when we receive a message.
    //
    // @param  bufPtr  The message buffer handed to us by TinyOS
    // @param  payload The message content
    // @param  len     The size of the message
    // @return         The buffer we give to the OS to use.
    //
    event message_t* Receive.receive(message_t* bufPtr, void* payload,
                                                                uint8_t len) {
        TCPMessage *msg = (TCPMessage *)payload;
        TCPMessage *ackPayload;

        if ((bool)msg->ACK) {
            call Leds.led1Toggle();
            sendLocked = FALSE;
            signal AMSend.sendDone(message, SUCCESS);
            return bufPtr;
        } else {
            msg->ACK = TRUE;
            ackPayload = call Send.getPayload(&TCPMsg, sizeof(TCPMessage));

            memcpy(ackPayload, msg, len);

            
            if (call Send.send(msg->fromAddress, 
                                    &TCPMsg, len) == SUCCESS) {
                call Leds.led1Toggle();
            }
            return bufPtr;
        }
    }

    

    //
    // This implements things we want to check after the radio is started.
    //
    // @param status Did the radio start successfully?
    //
    event void AMControl.startDone(error_t status) {
        if (status != SUCCESS) {        // If it didn't start, start it.
            call AMControl.start();
        }
    }


    //
    // gets called when AMControl is stopped, which we'll never do.
    //
    // @param if AMControl was stopped successfully or not
    //
    event void AMControl.stopDone(error_t status) {
        // DO NOTHING
    }

    //
    // if nothing is wired to our implementation of AMSend, this provides
    // a default implementation of sendDone.
    //
    default event void AMSend.sendDone(message_t *msg, error_t error) {
    }
}

