package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import common.AddressBook;
import common.Contact;

/**
 * This class is the implementation of the logic described in AddressBook.
 * 
 * @author Randall Hunt
 * @see AddressBook
 */
public class AddressBookImpl implements AddressBook {
    /** A list of all contacts currently in use */
    private List<Contact> contacts = new ArrayList<Contact>();

    @Override
    public void addContact(Contact contact) throws RemoteException {
        this.contacts.add(contact);
    }

    @Override
    public List<Contact> getContactList() throws RemoteException {
        return this.contacts;
    }

    @Override
    public Contact lookupContact(String firstName, String lastName)
            throws RemoteException {
        for (Contact contact : this.contacts) {
            if (contact.getName().equals(firstName + " " + lastName))
                return contact;
        }
        return null;
    }

    @Override
    public Contact removeContact(String firstName, String lastName)
            throws RemoteException {
        for (Contact contact : this.contacts) {
            if (contact.getName().equals(firstName + " " + lastName)) {
                this.contacts.remove(contact);
                return contact;
            }
        }
        return null;
    }
}
