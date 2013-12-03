/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evolver;

import evaluation.Coord;
import evaluation.Block;
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
public class RSPhenotype implements Phenotype {
    private Block[][][] contents; // all blocks in this device
    
    private ArrayList<Coord> inputs;
    private ArrayList<Coord> outputs;

    /**
     * Process a sequence, running it as a program to construct a new phenotype.
     * @param s sequence program to run
     * @return phenotype constructed from sequence
     */
    public RSPhenotype(TreeMap<Coord, Block> partlist) {
        // TODO! processssss
        
        TreeMap<Coord, Block> partlist = new TreeMap<Coord, Block>();
        
        inputs = new ArrayList<Coord>();
        outputs = new ArrayList<Coord>();
        
        
        
        elements = new ArrayList<Coord>();
        for (Map.Entry<Coord, Block> entry : contents.entrySet()) {
            if (entry.getValue().id == BlockID.TORCH) {
                elements.add(entry.getKey());
            }
        }
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
    
    public Coord getMinBound() {
        // TODO
        return new Coord(0, 0, 0);
    }
    
    public Coord getMaxBound() {
        // TODO
        return new Coord(0, 0, 0);
    }
    
    /**
     * Gets the volume of this redstone component.
     * 
     * Volume is defined as the number of voxels within one unit (including 
     * diagonals) of a non-air block in this component.
     * @return 
     */
    public int getVolume() {
        // TODO
        return 0;
    }
    
    /**
     * Get block at specified location.
     * @param c
     * @return empty block full of AIR if no block currently specified there
     */
    public Block getBlock(Coord c) {
        Block b = contents.get(c);
        if (b == null) {
            b = new Block(BlockID.AIR, 0);
        }
        
        return b;
    }
    
    public ArrayList<Coord> getInputs() {
        return inputs;
    }
    
    public ArrayList<Coord> getOutputs() {
        return outputs;
    }
}
