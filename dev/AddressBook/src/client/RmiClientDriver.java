package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Creates and runs an RmiClient
 * 
 * @author Randall Hunt
 * @version December 10, 2009
 */
public class RmiClientDriver {
    /**
     * Creates client and runs it.
     * 
     * @param args
     *            takes two command line arguments the host and the port
     */
    public static void main(String args[]) {
        if (args.length != 2) {
            printUsageAndExit();
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            RmiGuiClient client = new RmiGuiClient(host, port);
            client.execute();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            System.out.println("Unexpected remote exception: " + ex);
            ex.printStackTrace();
        }
    }

    public static void printUsageAndExit() {
        System.out.println("java client/RmiClientDriver ");
        System.out.println("<host> <port>");
    }

}