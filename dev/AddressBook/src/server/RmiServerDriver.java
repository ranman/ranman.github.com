package server;

import java.rmi.RemoteException;

import common.AddressBook;

/**
 * The driver application for the RmiServer.
 * 
 * @author Randall Hunt
 * @version December 10, 2009
 */
public class RmiServerDriver {
    /**
     * This method serves as the entry point to a command-line version of a
     * multiuser chat client.
     * 
     * @param args
     *            The command line arguments to this program. There should be no
     *            more than one which is the port number the server should
     *            listen on.
     */
    public static void main(String[] args) {
        int port = 24482; // DEFAULT PORT
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try {
            System.out.println("RmiServer program started...");

            RmiServer server = new RmiServer(port);
            AddressBook addressBook = new AddressBookImpl();
            Object lock = new Object();

            server.bindObjectToRegistry(addressBook, "addressBook");

            synchronized (lock) {
                lock.wait(); // Wait forever -- let other threads handle
                // work
            }
        } catch (RemoteException ex) {
            System.out.println("Unexpected exception: " + ex);
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            // This should never happen
            ex.printStackTrace();
        }
    }

    /**
     * Prints the method usage.
     */
    public static void printUsageAndExit() {
        System.out
                .println("usage: java -Djava.security.policy=server/server.policy server/RmiServerDriver ");
        System.out.println("<port>");
    }
}
