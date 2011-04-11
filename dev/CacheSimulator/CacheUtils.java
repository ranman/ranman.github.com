/**
 * @author Joseph Randall Hunt
 * @version May 3, 2010
 */
public class CacheUtils {
    /** Regex to find an address from stdin */
    public static final String ADDRESS_REGEX =
                                             "^\\s*(\\p{XDigit}+)\\s*$";

    /**
     * Prints out the current statistical information about a cache.
     * Called by <code>CacheDriver</code> at the end of a simulation.
     * 
     * @param cache
     *            the cache to print information about
     */
    public static void printCacheStats(Cache cache) {
        System.out.println("Stats:");
        System.out.println("\tTotal Hits: " + cache.hits);
        System.out.println("\tTotal Misses: " + cache.misses);
        System.out.println("\tTotal Accesses: " + cache.totalAccess);
        System.out.println("\tHit ratio: " + (double) (cache.hits)
        / (double) cache.totalAccess);
        System.out.println("\tMiss ratio: " + (double) (cache.misses)
        / (double) cache.totalAccess);
    }

    /**
     * Add 0s to a string
     * 
     * @param input
     *            the string to prepend
     * @param numZeroes
     *            the number of zeroes to prepend
     * @return the string with 0s prepended
     */
    public static String prependZeroes(String input, int numZeroes) {
        StringBuilder sb = new StringBuilder(input);
        for (int i = 1; i < numZeroes; i++)
            sb.insert(0, "0");
        return sb.toString();
    }

    /**
     * Returns if a number is a power of 2 or not.
     * 
     * @param n
     *            a number to check if it is a power of 2
     * @return if number is a power of two
     */
    public static boolean isPowerOfTwo(int n) {
        return (n & -n) == n;
    }

    /**
     * @param cache
     *            the cache to print the configuration of
     */
    @SuppressWarnings("boxing")
    public static void printCacheConfiguration(Cache cache) {
        if (cache instanceof SetAssociativeCache) {
            SetAssociativeCache c = (SetAssociativeCache) cache;
            System.out
            .printf(
            "%d %d-way set associative entries of line size %d bytes\n",
            c.getNumSets(), c.getSetSize(), c.getBlockSize());
        }
    }
}
