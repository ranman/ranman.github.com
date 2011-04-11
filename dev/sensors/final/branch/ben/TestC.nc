#include "SensorMessage.h"

module TestC {
    uses interface AMSend;
    uses interface Timer<TMilli>;
    uses interface Boot;
    uses interface Leds;
    uses interface AMPacket;
} implementation {
    message_t message;
    uint8_t localMote;
    
    event void Boot.booted() {
        localMote = (int) call AMPacket.address();
        call Timer.startOneShot(1024);
    }

    event void Timer.fired() {
        if (localMote != 1) {
            SensorMessage* load = (SensorMessage*) call AMSend.getPayload(&message, sizeof(SensorMessage));
            load->time        = 24;
            load->temperature = 57;

            call AMSend.send(1, &message, sizeof(SensorMessage));
        }
    }

    event void AMSend.sendDone(message_t *msg, error_t error) {
        call Leds.led2Toggle();
    }
}
