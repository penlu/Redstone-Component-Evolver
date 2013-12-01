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
    
    private ArrayList<Coord> elements;
    private ArrayList<Coord> inputs;
    private ArrayList<Coord> outputs;
    
    /**
     * Blank phenotype (why)
     */
    public Phenotype() {
        contents = new TreeMap<Coord, Block>();
        elements = new ArrayList<Coord>();
        inputs = new ArrayList<Coord>();
        outputs = new ArrayList<Coord>();
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
    
    public Phenotype(ArrayList<Integer> bases) {
        
    }
    
    /**
     * Set block at specified location.
     * @param c
     * @param b 
     */
    private void setBlock(Coord c, Block b) {
        contents.put(c, b);
        
        // clear inputs/outputs
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).equals(c)) {
                inputs.remove(i);
                i--; // don't skip
            } else if (outputs.get(i).equals(c)) {
                outputs.remove(i);
                i--; // don't skip
            }
        }
    }
    
    public ArrayList<Coord> getInputs() {
        return inputs;
    }
    
    public ArrayList<Coord> getOutputs() {
        return outputs;
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
    private ArrayList<Coord> getElements() {
        ArrayList<Coord> locs = new ArrayList<Coord>();
        for (Map.Entry<Coord, Block> entry : contents.entrySet()) {
            if (entry.getValue().id == BlockID.TORCH) {
                locs.add(entry.getKey());
            }
        }
        
        return locs;
    }
}
