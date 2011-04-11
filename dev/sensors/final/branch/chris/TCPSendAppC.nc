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
    provides interface Receive;
}
implementation {
    components TCPSendC as App;
    components MainC;

    components SerialActiveMessageC as SerialActiveMessageC;
    components ActiveMessageC as ActiveMessageC;

    components new SerialAMSenderC(AM_TCPMESSAGE) as SerialSender;
    components new AMSenderC(AM_TCPMESSAGE) as Sender;

    components new AMReceiverC(AM_TCPMESSAGE) as Receiver;
    components LedsC;
    components new TimerMilliC();

    App.SerialControl             -> SerialActiveMessageC.SplitControl;
    App.SerialPacket              -> SerialActiveMessageC.Packet;
    App.SerialAMPacket            -> SerialActiveMessageC.AMPacket;
    App.SerialSend                -> SerialSender.AMSend;
    

    App.AMControl           -> ActiveMessageC.SplitControl;
    App.Packet              -> ActiveMessageC.Packet;
    App.AMPacket            -> ActiveMessageC.AMPacket;
    App.Send                -> Sender.AMSend;

    App.AMReceive             -> Receiver.Receive;

    App.Boot                -> MainC.Boot;
    App.Leds                -> LedsC.Leds;
    App.Timer               -> TimerMilliC.Timer;
    AMSend                  =  App.AMSend;
    Receive                 =  App.Receive;
}

