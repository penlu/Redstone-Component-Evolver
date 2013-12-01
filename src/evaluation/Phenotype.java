/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
 * This implementation uses a TreeMap to store its contents!
 * NOTE may require profiling.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class Phenotype {
    private TreeMap<Coord, Block> contents; // all blocks in this device
    
    private ArrayList<Coord> inputs;
    private ArrayList<Coord> outputs;
    
    public Phenotype() {
        contents = new TreeMap<Coord, Block>();
    }
    
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
    public void setBlock(Coord c, Block b) {
        contents.put(c, b);
    }
    
    /**
     * Get block at specified location.
     * @param c
     * @return empty block full of AIR if no block currently specified there
     */
    public Block getBlock(Coord c) {
        Block b = contents.get(c);
        if (b == null) {
            b = new Block();
            b.id = BlockID.AIR;
            b.data = 0;
        }
        
        return b;
    }
    
    /**
     * Get list of locations of active elements.
     * 
     * This includes torches, repeaters, pistons, comparators...
     * @return list of locations of active elements
     */
    public ArrayList<Coord> getElements() {
        ArrayList<Coord> locs = new ArrayList<Coord>();
        for (Map.Entry<Coord, Block> entry : contents.entrySet()) {
            if (entry.getValue().id == BlockID.TORCH) {
                locs.add(entry.getKey());
            }
        }
        
        return locs;
    }
}
