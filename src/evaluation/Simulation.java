/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import evolver.Block;
import evolver.Coord;
import evolver.RSPhenotype;
import java.util.ArrayList;

/**
 * Simulates a RSPhenotype as it would transpire in the Minecraft world.
 * 
 * This implementation pre-processes to find dynamic elements for better update 
 * ticking efficiency.  Also finds inputs/outputs and provides methods that 
 * allow external interfacing to the simulated component.
 * 
 * Not a simulator, but a simulation.
 * @author Eric Lu <penlume@gmail.com>
 */
public class Simulation {
    private RSPhenotype phenotype; // definitions of blocks in component
    private BlockState[][][] state; // current state of blocks in component
    
    private ArrayList<Coord> scheduled; // components scheduled for updates
    
    private ArrayList<InputBlock> inputblocks;
    private ArrayList<Coord> outputs;
    
    /**
     * Construct a simulation for the given phenotype.
     * @param p 
     */
    public Simulation(RSPhenotype p) {
        // coordinate frame calculations
        Coord size = p.getSize();
        
        // initialize containers
        state = new BlockState[size.x][size.y][size.z];
        scheduled = new ArrayList<Coord>();
        
        // produce 3D state array and schedule components for first update
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                for (int k = 0; k < size.z; k++) {
                    Coord loc = new Coord(i, j, k);
                    
                    state[i][j][k] = p.getBlock(loc).initState();
                    
                    // schedule components
                    if (Block.BlockID.isSchedulable(
                            p.getBlock(loc).id)) {
                        scheduled.add(loc);
                    }
                }
            }
        }
        
        // store inputs
        ArrayList<Coord> inputs = p.getInputs();
        
        // make inputblocks that radiate weak power in all directions
        for (int i = 0; i < inputs.size(); i++) {
            Coord inloc = inputs.get(i);
            InputBlock in = new InputBlock();
            
            state[inloc.x][inloc.y][inloc.z] = in;
            inputblocks.add(in);
        }
        
        // store output locations
        outputs = p.getOutputs();
    }
    
    /**
     * Get number of inputs, for Evaluation to decide about how many inputs to 
     * give.
     * @return 
     */
    public int countInputs() {
        return inputblocks.size();
    }
    
    /**
     * Propagate an update to block coordinate c.
     * 
     * Basically conveniently decides whether immediate update or scheduling is 
     * appropriate.
     * @param c 
     */
    private void propagate(Coord c) {
        if (Block.BlockID.isSchedulable(phenotype.getBlock(c).id)) {
            scheduled.add(c);
            
            // remove duplicate update schedules
            for (int i = 0; i < scheduled.size() - 1; i++) {
                if (scheduled.get(i).equals(c)) {
                    scheduled.remove(i);
                }
            }
        } else {
            state[c.x][c.y][c.z].update(state, c);
        }
    }
    
    /**
     * Updates the block at the coordinate c.
     * 
     * Propagates updates if appropriate and schedules if appropriate.
     * @param c 
     */
    private void update(Coord c) {
        // index out of bounds check
        if (c.x < 0 || c.x > state.length
         || c.y < 0 || c.y > state[0].length
         || c.z < 0 || c.z > state[0][0].length) {
            return;
        }
        
        // propagate updates if necessary
        if (state[c.x][c.y][c.z].update(state, c)) {
            propagate(new Coord(c.x - 1, c.y    , c.z    ));
            propagate(new Coord(c.x + 1, c.y    , c.z    ));
            propagate(new Coord(c.x    , c.y - 1, c.z    ));
            propagate(new Coord(c.x    , c.y + 1, c.z    ));
            propagate(new Coord(c.x    , c.y    , c.z - 1));
            propagate(new Coord(c.x    , c.y    , c.z + 1));
        }
    }
    
    /**
     * Simulate one step with given inputs!
     * Or previous input if the input here is null.
     * @param inputs 
     */
    public void step(int[] inputs) {
        // update by scheduled updates
        for (Coord c : scheduled) {
            update(c);
        } // we're just going to assume the updates are not order-dependent...
        
        // set inputs on blocks defined in init
        for (int i = 0; i < inputblocks.size(); i++) {
            if (i < inputs.length) {
                inputblocks.get(i).setState(inputs[i]);
            }
        }
    }
    
    /**
     * Get outputs.
     * @return maximum output produced by each output block to an adjacent collector wire
     */
    public int[] outputs() {
        int[] vals = new int[outputs.size()];
        for (int i = 0; i < outputs.size(); i++) {
            Coord loc = outputs.get(i);
            
            // see if output is receiving power from neighboring squares
            int maxpow = 0; // prepare to get maximum received power
            for (int dir = 0; dir < 5; dir++) {
                Coord adj = loc.sub(new Coord(dir));
                BlockState neighbor = state[adj.x][adj.y][adj.z];
                int pow = Math.max(neighbor.weakPower(dir),
                                   neighbor.strongPower(dir));
                if (pow > maxpow) {
                    maxpow = pow;
                }
            }
            
            vals[i] = maxpow;
        }
        
        return vals;
    }
}
