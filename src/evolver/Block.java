
package evolver;

import evaluation.BlockState;

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
        
        public static boolean isSchedulable(BlockID id) {
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
    
    /**
     * For blocks that must be mounted on another block.
     * @return relative location of required mount, or null if none
     */
    public Coord needsMount() {
        if (id == BlockID.WIRE) {
            return new Coord(0, -1, 0);
        } else if (id == BlockID.TORCH) {
            // torches: data stores the direction the torch is pointing...
            if (data == 1) { // pointing +x
                return new Coord(-1, 0, 0);
            } else if (data == 2) { // pointing +z
                return new Coord(0, 0, -1);
            } else if (data == 3) { // pointing -x
                return new Coord(1, 0, 0);
            } else if (data == 4) { // pointing -z
                return new Coord(0, 0, 1);
            } else if (data == 5) { // pointing +y up
                return new Coord(0, -1, 0);
            } else {
                return new Coord(0, 0, 0);
            }
        } else {
            return null;
        }
    }
    
    public boolean canMount() {
        if (id == BlockID.BLOCK) {
            return true;
        } else {
            return false;
        }
    }
}