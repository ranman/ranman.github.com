import java.util.concurrent.ArrayBlockingQueue;

/**
 * A class to represent the set in a <code>SetAssociativeCache</code>.
 * 
 * @author Joseph Randall Hunt
 * @version Apr 23, 2010
 */
public class Set {
    /** The number of blocks in each set */
    private final int                       numBlocks;
    /** A queue is used to represent the blocks in the set */
    private final ArrayBlockingQueue<Block> blocks;

    /**
     * Constructs a new set with all blocks initialized to invalid
     * 
     * @param numBlocks
     */
    public Set(int numBlocks) {
        this.numBlocks = numBlocks;
        blocks = new ArrayBlockingQueue<Block>(numBlocks);
        for (int i = 0; i < numBlocks; i++) {
            blocks.add(new Block(-1));
        }
    }

    /**
     * Checks if a tag is correct and if it isn't adds it to the array
     * using LRU.
     * 
     * @param tag
     * @return hit or miss
     */
    public boolean access(int tag) {
        for (Block b : blocks) {
            if (b.checkTag() == tag) {
                blocks.remove(b);
                blocks.offer(b);
                return true;
            }
        }
        if (blocks.size() < numBlocks) {
            blocks.add(new Block(tag));
        } else {
            blocks.remove();
            blocks.add(new Block(tag));
        }
        return false;
    }
}
