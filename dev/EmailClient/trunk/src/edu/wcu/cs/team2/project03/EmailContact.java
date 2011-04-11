package edu.wcu.cs.team2.project03;

import java.io.Serializable;

/**
 * A class that hold the information of a contact within the email
 * client.
 * This class will hold a contact's name, postal address, phone
 * number, and email address.
 * PLAN:
 * 1) Fields to hold the information about the contact
 * 2) Methods to access and change the information in the fields.
 * 
 * @author Joseph Randall Hunt
 * @author Brian M. Lenau
 * @author Josh Davis
 * @version Oct 28, 2010
 */
public class EmailContact implements Serializable {
    /**
     * Field to assign a serial ID to the class
     */
    static final long serialVersionUID = -7137408062476453548L;
   /**
    *  A String that holds the contacts name.
    */
    private String    contactName;

    /**
     * A String to hold the contact's postal address.
     */
    private String    postalAddress;

    /**
     * A String to hold the contact's phone number.
     */
    private String    phoneNumber;

    /**
     * A String to hold the contact's email address.
     */
    private String    emailAddress;

    /**
     * Create an EmailContact with the basic fields.
     * 
     * @param contactName
     * @param postalAddress
     * @param phoneNumber
     * @param emailAddress
     */
    public EmailContact(String contactName, String postalAddress,
    String phoneNumber, String emailAddress) {
        this.contactName = contactName;
        this.postalAddress = postalAddress;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * Returns the contact's name
     *
     * @return the contactName
     */
    public final String getContactName() {
        return contactName;
    }

    /**
     * Changes the contact's name to a given name
     *
     * @param contactName the contactName to set
     */
    public final void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns the contact's postal address
     *
     * @return the postalAddress
     */
    public final String getPostalAddress() {
        return postalAddress;
    }

    /**
     * Changes the contact's postal address to the given postal
     * address
     *
     * @param postalAddress the postalAddress to set
     */
    public final void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * Returns the contact's phone number
     *
     * @return the phoneNumber
     */
    public final String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Changes the contact's phone number to the given phone number
     *
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the contact's email address
     *
     * @return the emailAddress
     */
    public final String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Changes the contact's email address to the given email address
     *
     * @param emailAddress
     *            the emailAddress to set
     */
    public final void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
        prime * result
        + ((contactName == null) ? 0 : contactName.hashCode());
        result =
        prime * result
        + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result =
        prime * result
        + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result =
        prime * result
        + ((postalAddress == null) ? 0 : postalAddress.hashCode());
        return result;
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
        if (!(obj instanceof EmailContact)) {
            return false;
        }
        EmailContact other = (EmailContact) obj;
        if (contactName == null) {
            if (other.contactName != null) {
                return false;
            }
        } else if (!contactName.equals(other.contactName)) {
            return false;
        }
        if (emailAddress == null) {
            if (other.emailAddress != null) {
                return false;
            }
        } else if (!emailAddress.equals(other.emailAddress)) {
            return false;
        }
        if (phoneNumber == null) {
            if (other.phoneNumber != null) {
                return false;
            }
        } else if (!phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        if (postalAddress == null) {
            if (other.postalAddress != null) {
                return false;
            }
        } else if (!postalAddress.equals(other.postalAddress)) {
            return false;
        }
        return true;
    }
}
