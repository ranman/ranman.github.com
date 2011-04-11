/*
 * Implementation of SensorRead.  Encapsulates reading data from all of the
 * sensors.
 *
 * Chris Blades
 * Randall Hunt
 * Benjamin Rudolph
 */
module SensorReadC {
    provides interface SensorRead;
    uses interface Read<uint16_t> as ReadHum;   // A different Read interface
    uses interface Read<uint16_t> as ReadTemp;  // for each sensor.
    uses interface Read<uint16_t> as ReadSol;
    uses interface Read<uint16_t> as ReadPho;
    uses interface Read<uint16_t> as ReadVol;
}
implementation {

    void readHamamatsu();
    void readSensirion();
    void checkDone();
    
    // a place to put the data we read
    SensorMessage *sm;

    // the overall status of all reads, i.e., success if all reads were 
    // successful
    bool overallStatus;

    // if individual read operations have completed
    bool tempDone = FALSE;
    bool humDone  = FALSE;
    bool parDone  = FALSE;
    bool tsrDone  = FALSE;
    bool volDone  = FALSE;

    //
    // Begin reading from all sensors
    //
    command void SensorRead.read(SensorMessage *buffer) {
        sm = buffer;
        readHamamatsu();
        readSensirion();
        call ReadVol.read();        // and read voltage all at once.
    }
    
    //
    // The Hamamatsu chip reads total solar radiation and photosynthetically
    // active radiation.  It appears that Hama.  does some kind of mediation
    // on it's own, but for best performance we're going avoid parallel reads
    // to limit dropped packets.
    //
    void readHamamatsu() {
        call ReadHum.read();
    }

    //
    // The Sensirion chip reads temperature and humidity.
    // If Sensirion does any mediation, it's no good, so we're doing them in
    // sequence instead of in parallel.
    //
    void readSensirion() {
        call ReadSol.read();
    }

    //
    // This implements actions that happen when the humidity sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadHum.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            sm -> humidity = value;
            call ReadTemp.read();       // read temperature,
        }
        overallStatus = overallStatus && status;
        humDone = TRUE;
        checkDone();
    }

    //
    // This implements actions that happen when the temperature sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadTemp.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            sm->temperature = value;    // update the message
        }
        overallStatus = overallStatus && status;
        tempDone = TRUE;
        checkDone();
    }

    //
    // This implements actions that happen when the TSR sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadSol.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            sm->solarRadiation = value; // update the message
            call ReadPho.read();        // read PAR,
        }
        overallStatus = overallStatus && status;
        tsrDone = TRUE;
        checkDone();
    }

    //
    // This implements actions that happen when the PAR sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadPho.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            sm->photoRadiation = value; // update the message
        }
        overallStatus = overallStatus && status;
        parDone = TRUE;
        checkDone();
    }

    //
    // This implements actions that happen when the voltage sensor is done
    // reading.
    //
    // @param status Did the sensor read successfully?
    // @param value  The value the sensor returned.
    //
    event void ReadVol.readDone(error_t status, uint16_t value) {
        if (status == SUCCESS) {
            sm->voltage = value;        // update the message
        }
        overallStatus = overallStatus && status;
        volDone = TRUE;
        checkDone();  
    }

    //
    // Checks to see if all reads have finished and if they have, signals 
    // readDone().
    //
    void checkDone() {
        if (volDone && tempDone && humDone && parDone && tsrDone) {
            signal SensorRead.readDone(overallStatus);
            // reset done flags
            tempDone = FALSE;
            humDone  = FALSE;
            parDone  = FALSE;
            tsrDone  = FALSE;
            volDone  = FALSE;
        }
    }
}
