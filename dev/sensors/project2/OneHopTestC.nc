//
// Sensor-testing module.
//
// Chris Blades
// Randall Hunt
// Benjamin Rudolph
//

#include <Timer.h>
#include "SensorMessage.h"

//
// The length of a single read cycle, in which data is read from all sensors on
// the mote.
#define CYCLE_LENGTH 340
#define ROOT_ADDRESS 0

module OneHopTestC {
    uses interface Read<uint16_t> as ReadHum;   // A different Read interface
    uses interface Read<uint16_t> as ReadTemp;  // for each sensor.
    uses interface Read<uint16_t> as ReadSol;
    uses interface Read<uint16_t> as ReadPho;
    uses interface Read<uint16_t> as ReadVol;
    uses interface Boot;
    uses interface SplitControl as AMSerialControl;
    uses interface SplitControl as AMControl;
    uses interface Packet as SerialPacket;
    uses interface Packet as Packet;
    uses interface AMPacket as AMSerialPacket;
    uses interface AMPacket as AMPacket;
    uses interface AMSend as AMSendUSB;
    uses interface AMSend as AMSendOneHop;
    uses interface Receive;
    uses interface Timer<TMilli>;
    uses interface Leds;   
}
implementation {
    bool receiveLocked  = FALSE;         // Is message being sent?  If so, don't
                                        // touch it.
    bool messageLocked     = FALSE;

    message_t  baseStationMessage[2];   // The double buffer holding the sensor
    message_t  msg;                     // readings and time for basestation.
    message_t* remoteMessage = &msg;    // The double buffer holding the sensor
                                        // readings and time for node 1.

    int currentBMessage = 0;            // The buffer to write to (the one not
                                        // being sent).

    void startReads();
    void readHamamatsu();
    void readSensirion();

    //
    // This implements things we want to get done when the device starts.
    // Start AMControl so we can do networking and begin our read cycle.
    //
    event void Boot.booted() {
        if (call AMPacket.address() == 0)
            call Leds.led0Toggle();
        call AMControl.start();     // Start the radio.
        call AMSerialControl.start();
        call Timer.startPeriodic(CYCLE_LENGTH);
    }

    //
    // Begin reading from all sensors
    //
    void startReads() {
        readHamamatsu();
        readSensirion();
        call ReadVol.read();        // and read voltage all at once.
    }
    
    //
    // The Hamamatsu chip reads total solar radiation and photosynthetically
    // active radiation.  It appears taht Hama.  does some kind of mediation
    // on it's own, but for best performance we're going avoid parallel reads
    // to limit dropped packets.
    //
    void readHamamatsu() {
        call ReadHum.read();
    }

    //
    // The Sensirion chip reads temperature and humidity.
    // If Sensirion does any mediation, it's no good, so we're doing them in
    // sequence instead of in parallel.
    //
    void readSensirion() {
        call ReadSol.read();
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
    // This implements things we want to check after the radio is started.
    //
    // @param status Did the radio start successfully?
    //
    event void AMSerialControl.startDone(error_t status) {
        if (status != SUCCESS) {        // If it didn't start, start it.
            call AMSerialControl.start();
        }
    }

    //
    // This implements actions that happen when the humidity sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadHum.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            SensorMessage* sm;
            if (call AMPacket.address() == 0) {
                sm = (SensorMessage*)call SerialPacket.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            } else {
                sm = (SensorMessage*)call Packet.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            }
            sm->humidity = value;       // update the message

            call ReadTemp.read();       // read temperature,
        }
    }

    //
    // This implements actions that happen when the temperature sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadTemp.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            SensorMessage* sm;
            if (call AMPacket.address() == 0) {
                sm = (SensorMessage*)call SerialPacket.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            } else {
                sm = (SensorMessage*)call Packet.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            }
            sm->temperature = value;    // update the message
        }
    }

    //
    // This implements actions that happen when the TSR sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadSol.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            SensorMessage* sm;
            if (call AMPacket.address() == 0) {
                sm = (SensorMessage*)call SerialPacket.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            } else {
                sm = (SensorMessage*)call Packet.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            }
            sm->solarRadiation = value; // update the message

            call ReadPho.read();        // read PAR,
        }
    }

    //
    // This implements actions that happen when the PAR sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadPho.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            SensorMessage* sm;
            if (call AMPacket.address() == 0) {
                sm = (SensorMessage*)call SerialPacket.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            } else {
                sm = (SensorMessage*)call Packet.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            }
            sm->photoRadiation = value; // update the message
        }
    }

    //
    // This implements actions that happen when the voltage sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadVol.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            SensorMessage* sm;
            if (call AMPacket.address() == 0) {
                sm = (SensorMessage*)call SerialPacket.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            } else {
                sm = (SensorMessage*)call Packet.getPayload(
                                    &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            }
            sm->voltage = value;        // update the message
        }
    }
    
    //
    // Event fired when a packet has been sent over the network.
    // @param bufPtr location of the packet sent
    // @param error  the error status of the send operation
    //
    event void AMSendUSB.sendDone(message_t* bufPtr, error_t error) {
        SensorMessage *sm = (SensorMessage*)call Packet.getPayload(
                             &baseStationMessage[currentBMessage], 
                             sizeof(SensorMessage));
        // if this is the message we sent...
        if (bufPtr == &baseStationMessage[(currentBMessage + 1) % 2]) {
            //
            // reset all values in the packet to -1 so that we can detect
            // dropped packets
            sm = (SensorMessage*)call Packet.getPayload(
                                 &baseStationMessage[(currentBMessage + 1) % 2],
                                 sizeof(SensorMessage));
            
        } else if (bufPtr == remoteMessage) {
            sm = (SensorMessage*)call Packet.getPayload(
                                 &baseStationMessage[(currentBMessage + 1) % 2],
                                 sizeof(SensorMessage));
            call   Leds.led2Toggle();
            receiveLocked = FALSE;      // message is ready to be modified
        }
        sm->humidity        = -1;   // Reset to invalid sentinel values
        sm->temperature     = -1;   // so that missed values can be caught
        sm->solarRadiation  = -1;   
        sm->photoRadiation  = -1;
        sm->voltage         = -1;
    }

    event void AMSendOneHop.sendDone(message_t* bufPtr, error_t error) {
        // if this is the message we sent...
        if (bufPtr == &baseStationMessage[(currentBMessage + 1) % 2]) {
            //
            // reset all values in the packet to -1 so that we can detect
            // dropped packets
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(
                            &baseStationMessage[(currentBMessage + 1) %2],
                            sizeof(SensorMessage));
            
            sm->humidity        = -1;   // Reset to invalid sentinel values
            sm->temperature     = -1;   // so that missed values can be caught
            sm->solarRadiation  = -1;   
            sm->photoRadiation  = -1;
            sm->voltage         = -1;
        }
        messageLocked = FALSE;
    }

    event message_t* Receive.receive(message_t* bufPtr, void* payload,
                                     uint8_t len) {
        if (!receiveLocked) {
            message_t *temp;     
            call Leds.led0Toggle();
            if (call AMSendUSB.send(AM_BROADCAST_ADDR, bufPtr, 
                                       sizeof(SensorMessage)) == SUCCESS) {
                receiveLocked = TRUE;
                temp = remoteMessage;
                remoteMessage = bufPtr;
                return temp;
            }
        }
        return bufPtr;
    }

    //
    // When enuogh time has passed for all the sensors to have finshed reading
    // (the base time it takes for all readings to complete plus fudge factor),
    // send data.
    //
    event void Timer.fired() {
        if (call AMPacket.address() == 0) {
           SensorMessage* sm = (SensorMessage*)
                   call Packet.getPayload(&baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            call Leds.led1Toggle();
            sm->address = call AMSend.maxPayloadLength();
            

            // send packet
            call AMSendUSB.send(AM_BROADCAST_ADDR,
                                &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            
            currentBMessage = (currentBMessage + 1) % 2;
            // start here instead of in AMSendUSB.readDone() because sensors can
            // be gathering data in parallel of networking
            startReads();
        } else {
            if (!messageLocked) {
            // attach timestamp to packet
            SensorMessage* sm = (SensorMessage*)
                   call Packet.getPayload(&baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage));
            call Leds.led2Toggle();
            sm->address = call AMPacket.address();
            


            // send packet
            if (call AMSendOneHop.send(ROOT_ADDRESS,
                                &baseStationMessage[currentBMessage],
                                    sizeof(SensorMessage)) == SUCCESS) {
                messageLocked = TRUE;
            }
            currentBMessage = (currentBMessage + 1) % 2;
            
            // start here instead of in AMSendUSB.readDone() because sensors can
            // be gathering data in parallel of networking
            startReads();
            }
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
    // gets called when AMControl is stopped, which we'll never do.
    //
    // @param if AMControl was stopped successfully or not
    //
    event void AMSerialControl.stopDone(error_t status) {
        // DO NOTHING
    }
}

