#include "SensorMessage.h"

configuration TestAppC {
} implementation {
    components TestC as App;
    components TCPSendAppC;
    components ActiveMessageC;
    components MainC;
    components new TimerMilliC();
    components LedsC;

    App.AMPacket            -> ActiveMessageC.AMPacket;
    App.Leds   -> LedsC.Leds;
    App.Timer  -> TimerMilliC.Timer;
    App.Boot   -> MainC.Boot;
    App.AMSend -> TCPSendAppC.AMSend;
}
