import java.util.ArrayList;

/**
 * Cache Simulator class, maintains sets with an arraylist and
 * 
 * @author Joseph Randall Hunt
 * @version Apr 23, 2010
 */
public class SetAssociativeCache extends Cache {
    /** the number of sets */
    private final int            numSets;
    /** the size of blocks in bytes */
    private final int            blockSize;
    /** the size of sets in blocks */
    private final int            setSize;
    /** the size of the tag in bits */
    private final int            tagSize;
    /** the size of the offset in bytes */
    private final int            offsetSize;
    /** the size of the index in bits */
    private final int            indexSize;
    /** the collection of sets */
    private final ArrayList<Set> sets;

    /**
     * Make a new cache with all the variables set up
     * 
     * @param numSets
     * @param setSize
     * @param lineSize
     */
    public SetAssociativeCache(int numSets, int setSize, int lineSize) {
        /*
         * indexSize = log_2 numSets
         * offestSize = log_2 lineSize
         * tagSize = rest
         */
        this.blockSize = lineSize;
        this.setSize = setSize;
        this.numSets = numSets;
        if (numSets == 1) {
            this.indexSize = 1;
        } else {
            this.indexSize = (int) (Math.log(numSets) / Math.log(2));
        }
        this.offsetSize = (int) (Math.log(lineSize) / Math.log(2));
        this.tagSize = 32 - offsetSize - indexSize;
        this.sets = new ArrayList<Set>(numSets);
        for (int i = 0; i < numSets; i++) {
            sets.add(new Set(setSize));
        }
    }

    /**
     * Takes a binary string and parses it for the tag, index, offset
     * and tries
     * to access it.
     * 
     * @param address
     */
    @Override
    public void access(String address) {
        // Only count if address is aligned
        if (Integer.parseInt(address.substring(address.length() - 1,
        address.length())) % 4 != 0) {
            System.err.println("Not Aligned");
            return;
        }
        this.totalAccess++;
        boolean hit = false;

        // extend to 32 bits, could use flags
        int numZeroes = 33 - address.length();
        String realAddress =
        CacheUtils.prependZeroes(address, numZeroes);
        int tag =
        Integer.parseInt(realAddress.substring(0, tagSize), 2);
        int index;
        if (numSets == 1) {
            index = 0;
        } else {
            index =
            Integer.parseInt(realAddress.substring(tagSize, tagSize
            + indexSize), 2);
        }
        int offset =
        Integer.parseInt(realAddress.substring(tagSize + indexSize),
        2);
        int binAddr = Integer.parseInt(realAddress, 2);
        if (sets.get(index).access(tag)) {
            hit = true;
            hits++;
        } else {
            misses++;
        }

        // Print stats for this access
        System.out.print(Integer.toHexString(binAddr));
        System.out.print("\t\t" + Integer.toHexString(tag));
        System.out.print("\t\t" + index);
        System.out.print("\t\t" + offset);
        String hitOrMiss = (hit) ? "Hit" : "Miss";
        System.out.print("\t\t" + hitOrMiss);
        System.out.println();
    }

    /**
     * @return the numSets
     */
    public final int getNumSets() {
        return this.numSets;
    }

    /**
     * @return the blockSize
     */
    public final int getBlockSize() {
        return this.blockSize;
    }

    /**
     * @return the setSize
     */
    public final int getSetSize() {
        return this.setSize;
    }
}
