/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

/**
 * Defines the data structure to store the phenotype.
 * 
 * This is the keystone of the program; it defines the objects that the 
 * algorithm is meant to optimize over.  Genome and evaluation implementations 
 * depend on what the final phenotype is.
 * 
 * Data structure representing an arrangement of redstone components in the 
 * Minecraft environment.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Phenotype {
    /**
     * Defines enum variable specifying the type of block a certain part is.
     */
    public static enum BlockID {
        AIR,    // empty space
        WIRE,   // redstone wire
        TORCH,  // redstone torch
        BLOCK;  // normal block
    }
    
    /**
     * Data structure storing information about a single block.
     */
    public static class Block {
        BlockID id; // the type of part this is
        int data;   // extra data in bit-flags
                    // (direction and settings for most objects)
    }
    
    /**
     * Set block at specified location.
     * @param c
     * @param b 
     */
    void setBlock(Coord c, Block b);
    
    /**
     * Get block at specified location.
     * @param c
     * @return 
     */
    Block getBlock(Coord c);
}
