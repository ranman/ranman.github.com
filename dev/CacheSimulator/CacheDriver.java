/**
 * Name: Joseph Randall Hunt
 * Program: Cache Simulator, CS350 Assignment 4
 * This is the entry-point program for my cache simulator.
 * It is easiest to run this program with: <code>
 * for filename in ./*.dat; do
 * echo $filename; java CacheDriver < $filename; done;
 * </code>
 */
import java.security.InvalidParameterException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Joseph Randall Hunt
 * @version May 3, 2010
 */
public class CacheDriver {
    /**
     * Reads from stdin information about a cache, creates it if the
     * information is valid, or fails with an error and unable to
     * complete execution error. Next it prints some configuration
     * information. Then it continues reading from stdin and passes
     * the addresses to the cache. Finally it prints some statistical
     * information about the cache simulation. This method is fairly
     * long but there is no real reason to abstract the sequential
     * operations to different methods.
     * 
     * @param args
     */
    public static void main(String args[]) {
        try {
            Pattern pattern;
            Matcher m;
            Cache cache;
            int numSets;
            int setSize;
            int lineSize;

            // Read in the configuration
            Scanner scanner = new Scanner(System.in);
            scanner.skip("Number of sets: ");
            numSets = scanner.nextInt();

            scanner.nextLine();
            scanner.skip("Set size: ");
            setSize = scanner.nextInt();

            scanner.nextLine();
            scanner.skip("Line size: ");
            lineSize = scanner.nextInt();

            // Check the input, powers of two, and within bounds
            if (!CacheUtils.isPowerOfTwo(numSets))
                throw new InvalidParameterException(
                "Number of sets not a power of two");
            if (!CacheUtils.isPowerOfTwo(setSize))
                throw new InvalidParameterException(
                "Set-Associativeity not a power of two");
            if (lineSize < 4)
                throw new InvalidParameterException(
                "Line size is less than 4");
            if (numSets > 8192)
                throw new InvalidParameterException(
                "Number of sets greater than 8192");
            if (setSize > 8)
                throw new InvalidParameterException(
                "Set-Associativeity greater than 8");

            // Create the cache
            cache =
            new SetAssociativeCache(numSets, setSize, lineSize);

            System.out.print("Cache Configuration:\n\t");
            CacheUtils.printCacheConfiguration(cache);

            System.out.print("Address\t\tTag\t\t");
            System.out.println("Index\t\tOffset\t\tResult");

            int address;
            String binary;

            pattern = Pattern.compile(CacheUtils.ADDRESS_REGEX);
            while (scanner.hasNextLine()) {
                m = pattern.matcher(scanner.nextLine());
                if (m.matches()) {
                    // Parse the int as a hexadecimal number
                    address = Integer.parseInt(m.group(1), 16);
                    binary = Integer.toBinaryString(address);
                    // Simulate the cache access
                    cache.access(binary);
                }
            }
            CacheUtils.printCacheStats(cache);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.out.println("Unable to complete execution");
        }
    }
}
