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
    /**
     * @return a BlockState that can be used to simulate this object
     */
    public static BlockState produceState(Block b) {
        switch (b.id) {
            case AIR:
                return new AirBlockState(b);
            case WIRE:
                return new WireBlockState(b);
            case TORCH:
                return new TorchBlockState(b);
            case BLOCK:
                return new BlockBlockState(b);
            default:
                return null;
        }
    }
    
    private RSPhenotype phenotype; // definitions of blocks in component
    private World world; // stores current states of blocks in component
    
    private ArrayList<Coord> scheduled; // components scheduled for updates
    
    private ArrayList<InputBlockState> inputblocks;
    private ArrayList<Coord> outputs;
    
    /**
     * Construct a simulation for the given phenotype.
     * @param p 
     */
    public Simulation(RSPhenotype p) {
        // coordinate frame calculations
        Coord size = p.getSize();
        
        // initialize containers
        BlockState[][][] state = new BlockState[size.x][size.y][size.z];
        
        scheduled = new ArrayList<Coord>();
        
        // produce 3D state array and schedule components for first update
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                for (int k = 0; k < size.z; k++) {
                    Coord loc = new Coord(i, j, k);
                    
                    state[i][j][k] = produceState(p.getBlock(loc));
                    
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
            InputBlockState in = new InputBlockState();
            
            state[inloc.x][inloc.y][inloc.z] = in;
            inputblocks.add(in);
        }
        
        // produce World object for this state array
        world = new World(state);
        
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
            world.getBlock(c).update(world, c);
        }
    }
    
    /**
     * Updates the block at the coordinate c.
     * 
     * Propagates updates if appropriate and schedules if appropriate.
     * @param c 
     */
    private void update(Coord c) {
        // propagate requested updates
        ArrayList<Coord> updates = world.getBlock(c).update(world, c);
        
        for (int i = 0; i < updates.size(); i++) {
            propagate(updates.get(i));
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
        if (inputs != null) {
            for (int i = 0; i < inputblocks.size(); i++) {
                if (i < inputs.length) {
                    inputblocks.get(i).setState(inputs[i]);
                }
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
            
            // see if output is receiving power from neighboring blocks
            int maxpow = 0; // prepare to get maximum received power
            for (int dir = 0; dir < 5; dir++) {
                Coord adj = loc.sub(new Coord(dir));
                BlockState neighbor = world.getBlock(adj);
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
