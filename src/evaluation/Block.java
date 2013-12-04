
package evaluation;

/**
 * Data structure storing information about a single block.
 */
public class Block {
    public final BlockID id; // the type of part this is
    public final int data;   // extra data in bit-flags
                             // (direction and settings for most objects)
    
    /**
     * Defines enum variable specifying the type of block a certain part is.
     */
    public static enum BlockID {
        AIR,    // empty space
        WIRE,   // redstone wire
        TORCH,  // redstone torch
        BLOCK;  // normal block
        
        public static boolean isComponent(BlockID id) {
            if (id == TORCH) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Block(BlockID id, int data) {
        this.id = id;
        this.data = data;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Block)) {
            return false;
        }
        
        Block b = (Block)o;
        return b.id == id && b.data == data;
    }
}