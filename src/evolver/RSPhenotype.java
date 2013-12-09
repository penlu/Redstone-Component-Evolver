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
    
    private ArrayList<Coord> inputs; // NOTE no way to enforce accessibility
    private ArrayList<Coord> outputs;

    /**
     * Phenotype constructed from list of parts, input, and output
     * @param partlist
     * @param inputs
     * @param outputs 
     */
    public RSPhenotype(Map<Coord, Block> partlist,
                       ArrayList<Coord> inputs, ArrayList<Coord> outputs) {
        
        // find part position bounds
        Coord min;
        Coord max;
        if (!partlist.isEmpty()) {
            min = new Coord(partlist.entrySet().iterator().next().getKey());
            max = new Coord(partlist.entrySet().iterator().next().getKey());
        } else {
            min = new Coord(0, 0, 0);
            max = new Coord(-1, -1, -1);
        }
        for (Map.Entry<Coord, Block> entry : partlist.entrySet()) {
            // track min coord
            min = Coord.min(min, entry.getKey());
            
            // track max coord
            max = Coord.max(max, entry.getKey());
        }
        
        // also include inputs and outputs in coord tracking
        for (Coord in : inputs) {
            min = Coord.min(min, in);
            max = Coord.max(max, in);
        }
        for (Coord out : outputs) {
            min = Coord.min(min, out);
            max = Coord.max(max, out);
        }
        
        // copy parts into array
        contents = new Block[max.x - min.x + 1][max.y - min.y + 1][max.z - min.z + 1];
        for (Map.Entry<Coord, Block> entry : partlist.entrySet()) {
            Coord index = entry.getKey().sub(min);
            contents[index.x][index.y][index.z] = entry.getValue();
        }
        
        // shift input and output coords to match indices
        this.inputs = new ArrayList<Coord>();
        this.outputs = new ArrayList<Coord>();
        for (int i = 0; i < inputs.size(); i++) {
            this.inputs.add(inputs.get(i).sub(min));
        }
        for (int i = 0; i < outputs.size(); i++) {
            this.outputs.add(outputs.get(i).sub(min));
        }
    }
    
    public Coord getSize() {
        return new Coord(contents.length,
                         contents[0].length,
                         contents[0][0].length);
    }
    
    /**
     * Get block at specified location.
     * @param c
     * @return empty block full of AIR if no block currently specified there
     */
    public Block getBlock(Coord c) {
        Block b;
        if (c.x < 0 || c.x > contents.length
         || c.y < 0 || c.y > contents[0].length
         || c.z < 0 || c.z > contents[0][0].length) {
            b = new Block(Block.BlockID.AIR, 0);
        } else {
            b = contents[c.x][c.y][c.z];
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
