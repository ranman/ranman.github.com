package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class controls both the registry and the server. It has a method to
 * 
 * @author Randall Hunt
 * @version December 10, 2009
 * 
 */
public class RmiServer {
    /** The RMI registry */
    private Registry registry;

    /**
     * Starts a new <code>RmiServer</code> with security manager.
     * 
     * @param namingServicePort
     *            the port on which the server should run
     * @throws RemoteException
     */
    public RmiServer(int namingServicePort) throws RemoteException {
        if (System.getSecurityManager() == null) {
            System.out.print("Installing security manager... ");
            System.setSecurityManager(new SecurityManager());
            System.out.println("done.");
        }

        System.out.print("Creating registry... ");
        this.registry = LocateRegistry.createRegistry(namingServicePort);
        System.out.println("done.");
    }

    /**
     * This method adds an object to the register.
     * 
     * @param remoteObject
     *            the object to bind
     * @param name
     *            the name to give the object
     * @throws RemoteException
     */
    public void bindObjectToRegistry(Remote remoteObject, String name)
            throws RemoteException {
        System.out.printf(
                "Adding an instance of '%s' to registry as '%s'...\n",
                remoteObject.getClass().getName(), name);

        Remote serverSkeleton =
                UnicastRemoteObject.exportObject(remoteObject, 0);

        this.registry.rebind(name, serverSkeleton);
        System.out.printf("'%s' object bound as '%s' ...\n", remoteObject
                .getClass().getName(), name);
    }
}
