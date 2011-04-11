/**
 * Abstract class to define fields and behaviors common to all caches.
 * 
 * @author Joseph Randall Hunt
 * @version May 3, 2010
 */
public abstract class Cache {
    protected int totalAccess;
    /** the number of hits */
    protected int hits;
    /** the number of misses */
    protected int misses;

    /**
     * Tries to access an address in a cache
     * 
     * @param address
     *            the address to access
     */
    public abstract void access(String address);
}
