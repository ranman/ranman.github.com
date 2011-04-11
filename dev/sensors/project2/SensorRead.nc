/*
 * Defines an interface to abstract reading all of the sensors and stuffing
 * a SensorMessage struct with the data.
 *
 * Sensors Read:
 * Hamamatsu S10871 (Total Solar Radiation and 
 *                      Photosynthetically-Active Radiation)
 * SensirionSht11   (Temperature and Humidity)
 * VoltageC
 *
 * Chris Blades
 * Randall Hunt
 * Benjamin Rudolph
 */
interface SensorRead {
    /*
     * Begins reading data from the sensors.
     *
     * buffer - where to put the data
     */
    command void read(SensorMessage* buffer);
    
    /**
     * Gets signaled when all sensors have been read (successfully or not)
     * and will return:
     * SUCCESS - if and only if all sensors were read successfully
     * !SUCCESS - if one or more sensor reads fails
     */
    event void readDone(bool status);
}
