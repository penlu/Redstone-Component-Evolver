/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evolver;

import java.util.ArrayList;
import java.util.Map;

/**
 * Defines the data structure to store the phenotype.
 * 
 * This is the keystone of the program; it defines the objects that the 
 * algorithm is meant to optimize over.  Genome and evaluation implementations 
 * depend on what the final phenotype is.
 * 
 * Data structure representing an arrangement of redstone components in the 
 * Minecraft environment.  Block placements assumed to be sane.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class RSPhenotype implements Phenotype {
    private Block[][][] contents; // all blocks in this device
    private Coord zero; // virtual coordinates of zero index in contents array
    
    private ArrayList<Coord> inputs;
    private ArrayList<Coord> outputs;

    /**
     * Process a sequence, running it as a program to construct a new phenotype.
     * @param s sequence program to run
     * @return phenotype constructed from sequence
     */
    public RSPhenotype(Map<Coord, Block> partlist,
                       ArrayList<Coord> inputs, ArrayList<Coord> outputs) {
        
        // find part position bounds
        Coord min = new Coord(partlist.entrySet().iterator().next().getKey());
        Coord max = new Coord(partlist.entrySet().iterator().next().getKey());
        for (Map.Entry<Coord, Block> entry : partlist.entrySet()) {
            // track min coord
            if (entry.getKey().x < min.x) {
                min = new Coord(entry.getKey().x, min.y, min.z);
            }
            if (entry.getKey().y < min.y) {
                min = new Coord(min.x, entry.getKey().y, min.z);
            }
            if (entry.getKey().z < min.z) {
                min = new Coord(min.x, min.y, entry.getKey().z);
            }
            
            // track max coord
            if (entry.getKey().x > max.x) {
                max = new Coord(entry.getKey().x, max.y, max.z);
            }
            if (entry.getKey().y > max.y) {
                max = new Coord(max.x, entry.getKey().y, max.z);
            }
            if (entry.getKey().z > max.z) {
                max = new Coord(max.x, max.y, entry.getKey().z);
            }
        }
        
        // copy parts into array
        contents = new Block[max.x - min.x][max.y - min.y][max.z - min.z];
        for (Map.Entry<Coord, Block> entry : partlist.entrySet()) {
            Coord index = entry.getKey().sub(min);
            contents[index.x][index.y][index.z] = entry.getValue();
        }
        
        // store coordinates represented by zero index
        zero = min;
        
        // TODO try shifting ALL of the coordinates!
        
        this.inputs = inputs;
        this.outputs = outputs;
    }
    
    public Coord getMinBound() {
        return zero;
    }
    
    public Coord getMaxBound() {
        return zero.add(new Coord(contents.length,
                                  contents[0].length,
                                  contents[0][0].length));
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
        Coord index = c.sub(zero);
        
        Block b;
        if (index.x < 0 || index.x > contents.length
         || index.y < 0 || index.y > contents.length
         || index.z < 0 || index.z > contents.length) {
            b = new Block(Block.BlockID.AIR, 0);
        } else {
            b = contents[index.x][index.y][index.z];
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
