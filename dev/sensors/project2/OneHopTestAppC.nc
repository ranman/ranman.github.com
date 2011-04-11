// 
// Wiring for the sensor test application.
// 
// Chris Blades
// Randall Hunt
// Benjamin Rudolph
//

#include "SensorMessage.h"

configuration OneHopTestAppC {
}
implementation {
    components OneHopTestC as App;
    components MainC;
    components new HamamatsuS10871TsrC() as TotalSolar;
    components new HamamatsuS1087ParC() as PhotoActive;
    components new SensirionSht11C() as TempHum;
    components new VoltageC() as Voltage;
    components SerialActiveMessageC;
    components ActiveMessageC;
    components new AMSenderC(AM_SENSORMESSAGE) as OneHopSender;
    components new AMReceiverC(AM_SENSORMESSAGE);
    components new SerialAMSenderC(AM_SENSORMESSAGE) as Sender;
    components new TimerMilliC();
    components LedsC;

    App.AMSerialControl     -> SerialActiveMessageC.SplitControl;
    App.SerialPacket        -> SerialActiveMessageC.Packet;
    App.AMSerialPacket      -> SerialActiveMessageC.AMPacket;
    App.AMSendUSB           -> Sender.AMSend;
    
    App.AMControl           -> ActiveMessageC.SplitControl;
    App.Packet              -> ActiveMessageC.Packet;
    App.AMPacket            -> ActiveMessageC.AMPacket;
    App.AMSendOneHop        -> OneHopSender.AMSend;
    
    App.Receive             -> AMReceiverC.Receive;

    App.ReadHum             -> TempHum.Humidity;
    App.ReadTemp            -> TempHum.Temperature;
    App.ReadSol             -> TotalSolar;
    App.ReadPho             -> PhotoActive;
    App.ReadVol             -> Voltage;
    
    App.Leds                -> LedsC.Leds;
    App.Boot                -> MainC.Boot;
    App.Timer               -> TimerMilliC.Timer;
}

