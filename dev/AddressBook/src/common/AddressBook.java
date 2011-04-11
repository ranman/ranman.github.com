package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface describes the methods available to both the client and server
 * side AddressBook. It is implemented by a concrete class on the server side.
 * 
 * @author Randall Hunt
 * @version December 10, 2009
 */
public interface AddressBook extends Remote {
    /**
     * Adds a contact to the AddressBook.
     * 
     * @param contact
     * @throws RemoteException
     */
    public void addContact(Contact contact) throws RemoteException;

    /**
     * Checks to see if a contact is in the AddressBook.
     * 
     * @param firstName
     *            the contacts first name
     * @param lastName
     *            the contacts last name
     * @return the <code>Contact</code> or null if none found.
     * @throws RemoteException
     */
    public Contact lookupContact(String firstName, String lastName)
            throws RemoteException;

    /**
     * Removes a Contact from the AddressBook.
     * 
     * @param firstName
     * @param lastName
     * @return the Contact removed
     * @throws RemoteException
     */
    public Contact removeContact(String firstName, String lastName)
            throws RemoteException;

    /**
     * Returns a list of Contact objects in the AddressBook.
     * 
     * @return a list of all the Contact objects in the AddressBook
     * @throws RemoteException
     */
    public List<Contact> getContactList() throws RemoteException;

}
