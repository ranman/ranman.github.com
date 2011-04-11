package edu.wcu.cs.team2.project03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Class to contain the list of contacts. Contains methods to
 * manipulate the
 * list.
 * 
 * @author Joseph Randall Hunt
 * @author Brian Lenau
 * @author Joshua Davis
 * @version Nov 9, 2010
 */
public class ContactList extends Observable implements Serializable {
    /**
     * Field containing the ArrayList used for storing contact's
     * information.
     */
    private ArrayList<EmailContact> contacts;

    /**
     * Creates a new empty ArrayList for use in storing contacts.
     */
    public ContactList() {
        super();
        this.setChanged();
        contacts = new ArrayList<EmailContact>();
    }

    /**
     * Adds a new contact to the list.
     * 
     * @param contact
     *            contact to be added
     * @return whether or not add was succesful
     */
    public boolean add(EmailContact contact) {
        /**
         * checks to see if the contact you are adding is already in
         * the
         * list.
         */
        if (contacts.contains(contact)) {
            this.setChanged();
            return false;
        }
        contacts.add(contact);
        this.setChanged();
        return true;
    }

    /**
     * Removes a contact from the list.
     * 
     * @param the
     *            contact to be removed
     */
    public void remove(EmailContact contact) {
        contacts.remove(contact);
        this.setChanged();
    }

    /**
     * Puts a contact in a specific spot in the list.
     * 
     * @param index
     *            location to put the contact
     * @param contact
     *            contact to be placed
     */
    public void set(int index, EmailContact contact) {
        contacts.set(index, contact);
        this.setChanged();
    }

    /**
     * Returns a contact at a specific spot in the list.
     * 
     * @param index
     *            of the contact to be returned
     */
    public EmailContact get(int index) {
        return contacts.get(index);
    }

    /**
     * Returns the size of the contact list.
     */
    public int size() {
        return contacts.size();
    }

    /**
     * Removes a contact from the list.
     * 
     * @param index
     *            of the contact to be removed
     */
    public void remove(int index) {
        contacts.remove(index);
        this.setChanged();
    }
}
