package edu.wcu.cs.team2.project03;

import java.io.Serializable;

/**
 * This class provides a means to store important user information
 * that can be retrieved on system startup and saved when the system
 * closes.
 * 
 * @author Joseph Randall Hunt
 * @author Brian M. Lenau
 * @author Josh Davis
 * @version Oct 28, 2010
 */
public class SystemConfiguration implements Serializable {
    /**
     * String the hold the user's email address.
     */
    private String            senderEmailAddress;

    /**
     * String the hold teh email server's address
     */
    private String            smtpHost;

    /**
     * The generated version ID for this class
     */
    private static final long serialVersionUID = 8312031015427088109L;

    /**
     * Returns the user's email address.
     * @return the senderEmailAddress
     */
    public final String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    /**
     * Sets the user's email address to a new email.
     * @param senderEmailAddress
     *            the senderEmailAddress to set
     */
    public final void setSenderEmailAddress(String senderEmailAddress) {
        this.senderEmailAddress = senderEmailAddress;
    }

    /**
     * Returns the email server's address.
     * @return the smtpHost
     */
    public final String getSmtpHost() {
        return smtpHost;
    }

    /**
     * Sets the email server address to a new value.
     * @param smtpHost
     *            the smtpHost to set
     */
    public final void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SystemConfiguration)) {
            return false;
        }

        SystemConfiguration other = (SystemConfiguration) obj;

        if (senderEmailAddress == null) {
            if (other.getSenderEmailAddress() != null) {
                return false;
            }
        } else if (!senderEmailAddress.equals
                                      (other.getSenderEmailAddress())) {
            return false;
        }
        if (smtpHost == null) {
            if (other.getSmtpHost() != null) {
                return false;
            }
        } else if (!smtpHost.equals(other.getSmtpHost())) {
            return false;
        }
        return true;
    }
}
