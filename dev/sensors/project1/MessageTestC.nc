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
#define CYCLE_LENGTH 330

module MessageTestC {
    uses interface Read<uint16_t> as ReadHum;   // A different Read interface
    uses interface Read<uint16_t> as ReadTemp;  // for each sensor.
    uses interface Read<uint16_t> as ReadSol;
    uses interface Read<uint16_t> as ReadPho;
    uses interface Read<uint16_t> as ReadVol;
    uses interface Boot;
    uses interface SplitControl as AMControl;
    uses interface Packet;
    uses interface AMPacket;
    uses interface AMSend;
    uses interface Timer<TMilli>;
}
implementation {
    bool messageLocked = FALSE;     // Is message being sent?  If so, don't
                                    // touch it.

    message_t message;              // The message holding the sensor readings
                                    // and time.

    void startReads();
    void readHamamatsu();
    void readSensirion();

    //
    // This implements things we want to get done when the device starts.
    // Start AMControl so we can do networking and begin our read cycle.
    //
    event void Boot.booted() {
        call AMControl.start();     // Start the radio.
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
    // This implements actions that happen when the humidity sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadHum.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(&message, sizeof(SensorMessage));

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
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(&message, sizeof(SensorMessage));

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
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(&message, sizeof(SensorMessage));

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
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(&message, sizeof(SensorMessage));

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
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(&message, sizeof(SensorMessage));

            sm->voltage = value;        // update the message


        }
    }
    
    //
    // Event fired when a packet has been sent over the network.
    // @param bufPtr location of the packet sent
    // @param error  the error status of the send operation
    //
    event void AMSend.sendDone(message_t* bufPtr, error_t error) {
        // if this is the message we sent...
        if (bufPtr == &message) {
            //
            // reset all values in the packet to -1 so that we can detect
            // dropped packets
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(&message, sizeof(SensorMessage));

            messageLocked = FALSE;      // message is ready to be modified
            
            
            sm->humidity        = -1;   // Reset to invalid sentinel values
            sm->temperature     = -1;   // so that missed values can be caught
            sm->solarRadiation  = -1;   
            sm->photoRadiation  = -1;
            sm->voltage         = -1;
        }
    }

    //
    // When enuogh time has passed for all the sensors to have finshed reading
    // (the base time it takes for all readings to complete plus fudge factor),
    // send data.
    //
    event void Timer.fired() {
        if (!messageLocked) {
            // attach timestamp to packet
            SensorMessage* sm = (SensorMessage*)
                call Packet.getPayload(&message, sizeof(SensorMessage));
            
            sm->time = call Timer.getNow();
            
            // send packet
            call AMSend.send(AM_BROADCAST_ADDR, &message, sizeof(SensorMessage));
            
            messageLocked = TRUE;

            // start here instead of in AMSend.readDone() because sensors can be
            // gathering data in parallel of networking
            startReads();
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
}

