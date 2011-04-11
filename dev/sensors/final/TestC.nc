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
        call Timer.startPeriodic(1024);
    }

    event void Timer.fired() {
        if (call AMPacket.address() != 1 && !sendLocked) {
            sendLocked = TRUE;
            call AMSend.send(1, &message, sizeof(SensorMessage));
        }
    }

    event void AMSend.sendDone(message_t *msg, error_t error) {
        if(msg == &message) {
            sendLocked = FALSE;
        }
    }
}
