package common;

import java.io.Serializable;

/**
 * This class represents a Contact in the AddressBook with a first and last
 * name. It is serializable which means it can be saved or perpetuated from one
 * JVM session to another.
 * 
 * @author Randall
 * @version December 10, 2009
 */
public class Contact implements Serializable {
    /** Generated serialVersionUID */
    private static final long serialVersionUID = -1429051844048855997L;
    /** The Contact's first name */
    private String            firstName;
    /** The Contact's last name */
    private String            lastName;

    /**
     * This constructor makes a new Contact with the a first and last name.
     * 
     * @param firstName
     *            the first name of the contact
     * @param lastName
     *            the last name of the contact
     */
    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return the firstName and lastName separated by a space
     */
    public String getName() {
        return this.firstName + " " + this.lastName;
    }

}
