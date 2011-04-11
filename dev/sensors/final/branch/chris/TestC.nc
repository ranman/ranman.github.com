#include "SensorMessage.h"

module TestC {
    uses interface AMSend;
    uses interface Timer<TMilli>;
    uses interface Boot;
    uses interface Leds;
    uses interface AMPacket;

} implementation {
    message_t message;
    
    bool sendLocked = FALSE;

    event void Boot.booted() {
        SensorMessage *sensor = 
        (SensorMessage *)call AMSend.getPayload(&message, sizeof(SensorMessage));
        sensor->time = 0x22000022;
        sensor->humidity    = 0x11;
        sensor->voltage     = 0xFFFF;
        call Timer.startPeriodic(4000);
    }

    event void Timer.fired() {
        if (call AMPacket.address() != 1 && !sendLocked) {
            sendLocked = TRUE;
            call AMSend.send(1, &message, sizeof(SensorMessage));
        }
    }

    event void AMSend.sendDone(message_t *msg, error_t error) {
        if(msg == &message) {
            call Leds.led1Toggle();
            sendLocked = FALSE;
        }
    }
}
