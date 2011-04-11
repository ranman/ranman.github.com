/**
 * A class to represent a block for a cache. Used by <code>Set</code>.
 * 
 * @author Joseph Randall Hunt
 * @version May 3, 2010
 */
public class Block {
    /** if tag is -1 it is invalid, otherwise it is taken */
    private final int tag;

    /**
     * Make a new block
     * 
     * @param tag
     */
    public Block(int tag) {
        this.tag = tag;
    }

    /**
     * returns the tag;
     * 
     * @return hit or miss
     */
    public int checkTag() {
        return tag;
    }
}
