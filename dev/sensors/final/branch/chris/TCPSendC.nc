//
// Sensor-testing module.
//
// Chris Blades
// Randall Hunt
// Benjamin Rudolph
//

#include "TCPMessage.h"

#define MAX_RETRY 5           // maximum number of times to retry sending before
                              // signaling sendDone() with TCP_NOACK
                      
#define RETRANSMIT_TIME 1024  // time to wait for an ack before retransmitting
module TCPSendC {

    provides interface AMSend;
    provides interface Receive;

    uses interface Boot;

    uses interface Receive as AMReceive;
    
    uses interface SplitControl as AMControl;
    uses interface SplitControl as SerialControl;

    uses interface Packet as Packet;
    uses interface Packet as SerialPacket;

    uses interface AMPacket as AMPacket;
    uses interface AMPacket as SerialAMPacket;

    uses interface AMSend as Send;
    uses interface AMSend as SerialSend;
    
    uses interface Timer<TMilli>;


    uses interface Leds;
}
implementation {
    bool sendLocked = FALSE;  // a lock on sending
    error_t   sentError;
    message_t *message;
    message_t TCPMsg;
    
    am_addr_t sendAddress;    // store address passed in last Send() so that
                              // packet can be resent if needed

    uint8_t   numTries;       // number of times the current packet has been
                              // resent
                              
    // double buffering for Receive.receive
    message_t receiveMessage;
    message_t *receiveBuffer = &receiveMessage;
    //
    // This implements things we want to get done when the device starts.
    // Start AMControl so we can do networking and begin our read cycle.
    //
    event void Boot.booted() {
        call AMControl.start();     // Start the radio.
        call SerialControl.start();
    }

    event void Timer.fired() {
        if (sendLocked == TRUE) {
            call AMSend.send(sendAddress, &TCPMsg, sizeof(TCPMessage));
            numTries++;
            if (numTries > MAX_RETRY) {
                signal AMSend.sendDone(message, TCP_NOACK);
                sendLocked = FALSE;
                call Timer.stop();
            }
        }
    }


    ///////////////////////////////////
    // serial stuff
    /////////////////////////////////
    
    //
    // Signaled when packet gets done sending, signals sendDone on the 
    // sender above us
    //
    // @param msg   the messsage_t struct that was sent
    // @param error status of send
    event void SerialSend.sendDone(message_t *msg, error_t error) {
               call Leds.led1Toggle();
        sentError = error;
    }

    //
    // This implements things we want to check after the radio is started.
    //
    // @param status Did the radio start successfully?
    //
    event void SerialControl.startDone(error_t status) {
        if (status != SUCCESS) {        // If it didn't start, start it.
            call AMControl.start();
        }
    }


    //
    // gets called when AMControl is stopped, which we'll never do.
    //
    // @param if AMControl was stopped successfully or not
    //
    event void SerialControl.stopDone(error_t status) {
        // DO NOTHING
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
            if (TCPPayload == NULL) {
            }
           TCPPayload->ACK         = FALSE;
           TCPPayload->fromAddress = call AMPacket.address();

           if (len < TCPPayloadSize) {
                memcpy(TCPPayload->payload, call Send.getPayload(msg, len), 
                                                                len);
            } else {
                return !SUCCESS;
            }
            
            status = call Send.send(addr, &TCPMsg, sizeof(TCPMessage));
            if (status == SUCCESS) {
                sendLocked = TRUE;
                numTries = 1;
                sendAddress = addr;
                call Timer.startPeriodic(RETRANSMIT_TIME);
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
        if (error != SUCCESS) {
            signal AMSend.sendDone(message, FAIL);
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
    // This implements things to be done when we receive a message.
    //
    // @param  bufPtr  The message buffer handed to us by TinyOS
    // @param  payload The message content
    // @param  len     The size of the message
    // @return         The buffer we give to the OS to use.
    //
    event message_t* AMReceive.receive(message_t* bufPtr, void* payload,
                                                                uint8_t len) {
        message_t *temp;  // for double buffering

        TCPMessage *msg = (TCPMessage *) payload;
        TCPMessage *ackLoad = call Packet.getPayload(&TCPMsg, sizeof(TCPMessage));

        ackLoad->fromAddress = call AMPacket.address();
        call Leds.led0Toggle();   


        // if this is a message being acked...
        if (msg->ACK) {
            sendLocked = FALSE;
            signal AMSend.sendDone(message, SUCCESS);  // Unsure about this ...
        // if this is an ACK
        } else {
            call Timer.stop();
            
            ackLoad->ACK = TRUE;

            call Send.send(msg->fromAddress, &TCPMsg, sizeof(TCPMessage)); 

            return signal Receive.receive(bufPtr, 
                                        msg->payload, msg->payloadLength);
        }
        
        // double buffering implementation
        temp = receiveBuffer;
        receiveBuffer = bufPtr;
        
        return temp;
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

    //
    // if nothing is wired to our implementation of Receive, this proivdes
    // a default implementation of Receive.
    //
    default event message_t* Receive.receive(message_t* bufPtr, void* payload,
                                                                uint8_t len) {
        message_t *temp;
        temp = receiveBuffer;
        receiveBuffer = bufPtr;

        return temp;
    }
}

