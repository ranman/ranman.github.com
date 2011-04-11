//
// SensorMessage type defined here.
//
// Chris Blades
// Randall Hunt
// Benjamin Rudolph
//

#ifndef SENSOR_MESSAGE_H
#define SENSOR_MESSAGE_H

typedef nx_struct SensorMessage{
    nx_uint32_t time;
    nx_uint16_t humidity;           // Different sensor readings to be stored.
    nx_uint16_t temperature;
    nx_uint16_t solarRadiation;
    nx_uint16_t photoRadiation;
    nx_uint16_t voltage;
    
} SensorMessage;

enum {
    AM_SENSORMESSAGE = 6,
};


#endif
