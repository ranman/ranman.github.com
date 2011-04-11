package edu.wcu.cs.team2.project03;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A class that tests the implementation of the EmailContact class.
 * This class extensively tests the methods of the EmailContact class.
 * It tests the functions of adding, removing, and editting contacts.
 * 
 * @author Joseph Randall Hunt
 * @author Josh Davis
 * @author Brian M. Lenau
 * @version Oct 28, 2010
 */
public class EmailContactTest {
    private EmailContact emailContact;

    /**
     * Before each test gives the test a clean slate to work with.
     */
    @Before
    public void setUp() {
        emailContact = new EmailContact("", "", "", "");
    }

    /**
     * After each test, removes the changed/tested-upon object.
     */
    @After
    public void nuke() {
        emailContact = null;
    }

    /**
     * Tests changing a contact's name.
     */
    @Test
    public void testContactName() {

        assertEquals(emailContact.getContactName(), "");

        emailContact.setContactName("Brian");
        assertEquals(emailContact.getContactName(), "Brian");

        emailContact.setContactName("Brian M. Lenau");
        assertEquals(emailContact.getContactName(), "Brian M. Lenau");

        emailContact.setContactName("Randall");
        assertEquals(emailContact.getContactName(), "Randall");

        emailContact.setContactName("J. Randall Hunt");
        assertEquals(emailContact.getContactName(), "J. Randall Hunt");

        emailContact.setContactName("Josh");
        assertEquals(emailContact.getContactName(), "Josh");

        emailContact.setContactName("Josh Davis");
        assertEquals(emailContact.getContactName(), "Josh Davis");
    }

    /**
     * Tests changing a contact's postal address.
     */
    @Test
    public void testPostalAddress() {

        assertEquals(emailContact.getPostalAddress(), "");

        emailContact.setPostalAddress("blenau@polaris");
        assertEquals(emailContact.getPostalAddress(),
        "blenau@polaris");

        emailContact.setPostalAddress("jhunt@polaris");
        assertEquals(emailContact.getPostalAddress(), "jhunt@polaris");

        emailContact.setPostalAddress("jdavis@polaris");
        assertEquals(emailContact.getPostalAddress(),
        "jdavis@polaris");
    }

    /**
     * Tests changing a contact's phone number.
     */
    @Test
    public void testPhoneNumber() {

        assertEquals(emailContact.getPhoneNumber(), "");

        emailContact.setPhoneNumber("8283427246");
        assertEquals(emailContact.getPhoneNumber(), "8283427246");

        emailContact.setPhoneNumber("828-342-7246");
        assertEquals(emailContact.getPhoneNumber(), "828-342-7246");

        emailContact.setPhoneNumber("XXX-XXX-XXXX");
        assertEquals(emailContact.getPhoneNumber(), "XXX-XXX-XXXX");
    }

    /**
     * Tests changing a contact's email address.
     */
    @Test
    public void testEmailAddress() {

        assertEquals(emailContact.getEmailAddress(), "");

        emailContact.setEmailAddress("blenau@polaris");
        assertEquals(emailContact.getEmailAddress(), "blenau@polaris");

        emailContact.setEmailAddress("jhunt@polaris");
        assertEquals(emailContact.getEmailAddress(), "jhunt@polaris");

        emailContact.setEmailAddress("jdavis@polaris");
        assertEquals(emailContact.getEmailAddress(), "jdavis@polaris");
    }
}
