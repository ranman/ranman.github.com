package edu.wcu.cs.team2.project03;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joseph Randall Hunt
 * @author Brian Lenau
 * @author Josh Davis
 * @version Oct 28, 2010
 */
public class SystemConfigurationTest {
    private SystemConfiguration systemConfiguration;

    /**
     * Before each Test, gives the test a clean slate to work with.
     */
    @Before
    public void setUp() {
        systemConfiguration = new SystemConfiguration();
    }

    /**
     * After each test, removes the changed/tested-upon object.
     */
    @After
    public void nuke() {
        systemConfiguration = null;
    }

    /**
     * Tests changing the user's email address.
     */
    @Test
    public void testSenderEmail() {
        assertEquals(systemConfiguration.getSenderEmailAddress(),
        null);

        systemConfiguration.setSenderEmailAddress("blenau@polaris");
        assertEquals(systemConfiguration.getSenderEmailAddress(),
        "blenau@polaris");

        systemConfiguration.setSenderEmailAddress("jhunt@polaris");
        assertEquals(systemConfiguration.getSenderEmailAddress(),
        "jhunt@polaris");

        systemConfiguration.setSenderEmailAddress(null);
        assertEquals(systemConfiguration.getSenderEmailAddress(),
        null);

        systemConfiguration.setSenderEmailAddress("jdavis@polaris");
        assertEquals(systemConfiguration.getSenderEmailAddress(),
        "jdavis@polaris");
    }

    /**
     * Tests changin the host server address.
     */
    @Test
    public void testSmtpHost() {
        assertEquals(systemConfiguration.getSmtpHost(), null);

        systemConfiguration.setSmtpHost("bleargh");
        assertEquals(systemConfiguration.getSmtpHost(), "bleargh");

        systemConfiguration.setSmtpHost("polaris");
        assertEquals(systemConfiguration.getSmtpHost(), "polaris");

        systemConfiguration.setSmtpHost("polaris.cs.wcu.edu");
        assertEquals(systemConfiguration.getSmtpHost(),
        "polaris.cs.wcu.edu");
    }
}
