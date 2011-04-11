package edu.wcu.cs.team2.project03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A class that is used to store and retrieve data from the disk
 * provide access to SystemConfiguration and a EmailContact objects.
 * This class contains the logic and means to access data used in
 * contacts and configurations of the system.
 * 
 * @author Brian M. Lenau
 * @author Joseph Randall Hunt
 * @author Josh Davis
 */
public class DataStore {
    /**
     * Single instance of DataStore
     */
    private volatile static DataStore dataStore;

    /**
     * The system configuration object
     */
    private SystemConfiguration       systemConfig;
    /**
     * A collection of all contacts
     */
    private ArrayList<EmailContact>   contacts;

    /**
     * Reads the configuration and contact files from disk and
     * creates a new DataStore object.
     */
    private DataStore() {
        try {
            readStateFromDisk();
        } catch (ClassCastException e) {
            systemConfig = new SystemConfiguration();
            contacts = new ArrayList<EmailContact>();
        } catch (IOException e) {
            systemConfig = new SystemConfiguration();
            contacts = new ArrayList<EmailContact>();
        } catch (ClassNotFoundException e) {
            System.err.println("Possible problem with setup, "
            + "try removing: .dat files from running directory");
            e.printStackTrace();
        }
    }

    /**
     * Completely clears the Datastore. Sets systemConfig and the
     * contacts list to empty versions.
     */
    public void resetDataStore() {
        systemConfig = new SystemConfiguration();
        contacts = new ArrayList<EmailContact>();
    }

    /**
     * Gets the instance of the DataStore or creates one if it does
     * not already exist.
     * 
     * @return the single instance of DataStore
     */
    public static DataStore getDataStore() {
        if (dataStore == null) {
            synchronized (DataStore.class) {
                if (dataStore == null) {
                    dataStore = new DataStore();
                }
            }
        }
        return dataStore;
    }

    /**
     * Saves the configuration and contact data to disk for later use.
     * 
     * @throws IOException
     */
    public void saveStateToDisk() throws IOException {
        OutputStream out =
        new BufferedOutputStream(new FileOutputStream("config.dat"));
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(systemConfig);
        oos.flush();

        out =
        new BufferedOutputStream(new FileOutputStream("contacts.dat"));
        oos = new ObjectOutputStream(out);
        oos.writeObject(contacts);
        oos.flush();
    }

    /**
     * Reads the configuration and contact data from disk to be used
     * in the Email client.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public void readStateFromDisk() throws IOException,
    ClassNotFoundException {
        InputStream in =
        new BufferedInputStream(new FileInputStream("config.dat"));

        ObjectInputStream ois = new ObjectInputStream(in);
        this.systemConfig = (SystemConfiguration) ois.readObject();

        in =
        new BufferedInputStream(new FileInputStream("contacts.dat"));
        // Guarantee stream is updated
        ois = new ObjectInputStream(in);
        this.contacts = (ArrayList<EmailContact>) ois.readObject();
    }

    /**
     * Returns the SystemConfiguration object to be used.
     * 
     * @return the systemConfig
     */
    public final SystemConfiguration getSystemConfig() {
        return systemConfig;
    }

    /**
     * Returns the collection of EmailContacts in the form of a
     * Vector.
     * 
     * @return the contacts
     */
    public final ArrayList<EmailContact> getContacts() {
        return contacts;
    }
}
