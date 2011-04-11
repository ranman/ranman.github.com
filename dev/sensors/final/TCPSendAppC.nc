// 
// Wiring for the sensor test application.
// 
// Chris Blades
// Randall Hunt
// Benjamin Rudolph
//

#include "TCPMessage.h"

configuration TCPSendAppC {
    provides interface AMSend;
}
implementation {
    components TCPSendC as App;
    components MainC;
    components ActiveMessageC;
    components new AMSenderC(AM_TCPMESSAGE) as Sender;
    components new AMReceiverC(AM_TCPMESSAGE);
    components LedsC;
    components new TimerMilliC();

    App.AMControl           -> ActiveMessageC.SplitControl;
    App.Packet              -> ActiveMessageC.Packet;
    App.AMPacket            -> ActiveMessageC.AMPacket;
    App.Send                -> Sender.AMSend;
    
    App.Receive             -> AMReceiverC.Receive;

    App.Boot                -> MainC.Boot;
    App.Leds                -> LedsC.Leds;
    App.Timer               -> TimerMilliC.Timer;
    AMSend                  =  App.AMSend;

}

