package edu.wcu.cs.team2.project03;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A class that tests the implementation of the DataStore class.
 * This class extensively tests the methods of the DataStore class.
 * It tests the functions of accessing, retrieving, and storing the
 * configuration and contact data.
 * 
 * @author Joseph Randall Hunt
 * @author Josh Davis
 * @author Brian M. Lenau
 * @version Nov 4, 2010
 */
public class DataStoreTest {
    /**
     * A field of the tested class, Datastore, to contain an object
     * to test.
     */
    private DataStore dataStore;

    /**
     * Before each test gives the test a clean slate to work with.
     */
    @Before
    public void setUp() {
        dataStore = DataStore.getDataStore();

    }

    /**
     * After each test, removes the changed/tested-upon object.
     */
    @After
    public void nuke() {
        dataStore = null;
    }

    /**
     * Tests changing the different fields of a contact
     */
    @Test
    public void testEmailContacts() {
        EmailContact contact1 = new EmailContact("A", "A", "A", "A");
        EmailContact contact2 = new EmailContact("B", "B", "B", "B");
        EmailContact contact3 = new EmailContact("C", "C", "C", "C");
        EmailContact contact4 = new EmailContact("D", "D", "D", "D");

        dataStore.getContacts().add(contact1);
        dataStore.getContacts().add(contact2);
        dataStore.getContacts().add(contact3);
        dataStore.getContacts().add(contact4);

        assertEquals(dataStore.getContacts().get(0), contact1);
        assertEquals(dataStore.getContacts().get(1), contact2);
        assertEquals(dataStore.getContacts().get(2), contact3);
        assertEquals(dataStore.getContacts().get(3), contact4);

        dataStore.getContacts().remove(0);

        assertEquals(dataStore.getContacts().get(0), contact2);
        assertEquals(dataStore.getContacts().get(1), contact3);
        assertEquals(dataStore.getContacts().get(2), contact4);

        dataStore.getContacts().remove(0);

        assertEquals(dataStore.getContacts().get(0), contact3);
        assertEquals(dataStore.getContacts().get(1), contact4);

        dataStore.getContacts().remove(0);

        assertEquals(dataStore.getContacts().get(0), contact4);
    }

    /**
     * Tests the system configurations of the DataStore.
     */
    @Test
    public void testSystemConfiguration() {
        SystemConfiguration config1 = new SystemConfiguration();
        SystemConfiguration config2 = new SystemConfiguration();
        SystemConfiguration config3 = new SystemConfiguration();
        SystemConfiguration config4 = new SystemConfiguration();

        config1.setSenderEmailAddress("A");
        config1.setSmtpHost("A");
        config2.setSenderEmailAddress("B");
        config2.setSmtpHost("B");
        config3.setSenderEmailAddress("C");
        config3.setSmtpHost("C");
        config4.setSenderEmailAddress("D");
        config4.setSmtpHost("D");

        dataStore.getSystemConfig().setSenderEmailAddress("A");
        dataStore.getSystemConfig().setSmtpHost("A");
        assertEquals(dataStore.getSystemConfig(), config1);
    }
}
