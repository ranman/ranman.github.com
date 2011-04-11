// 
// Wiring for the sensor test application.
// 
// Chris Blades
// Randall Hunt
// Benjamin Rudolph
//

#include "SensorMessage.h"

configuration MessageTestAppC {
}
implementation {
    components MessageTestC as App;
    components MainC;
    components new HamamatsuS10871TsrC() as TotalSolar;
    components new HamamatsuS1087ParC() as PhotoActive;
    components new SensirionSht11C() as TempHum;
    components new VoltageC() as Voltage;
    components SerialActiveMessageC;
    components new SerialAMSenderC(AM_SENSORMESSAGE) as Sender;
    components new TimerMilliC();

    App.Boot -> MainC.Boot;
    App.AMControl -> SerialActiveMessageC.SplitControl;
    App.AMPacket -> SerialActiveMessageC.AMPacket;
    App.Packet -> SerialActiveMessageC.Packet;
    App.AMSend -> Sender.AMSend;
    App.Timer -> TimerMilliC.Timer;
    App.ReadHum -> TempHum.Humidity;
    App.ReadTemp -> TempHum.Temperature;
    App.ReadSol -> TotalSolar;
    App.ReadPho -> PhotoActive;
    App.ReadVol -> Voltage;
}

