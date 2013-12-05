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
    private Coord zero; // virtual location of the zero index
    // TODO this zero bullshit seems unnecessary. fix phenotype so it stops being a thing.
    
    private ArrayList<Coord> scheduled; // components scheduled for updates
                                        // make no mistake this stores their indices!
    
    private ArrayList<Coord> inputs;
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
                    Coord loc = new Coord(i, j, k).add(zero);
                    
                    state[i][j][k] = p.getBlock(loc).initState();
                    
                    // schedule components
                    if (Block.BlockID.isSchedulable(
                            p.getBlock(loc).id)) {
                        scheduled.add(new Coord(i, j, k));
                    }
                }
            }
        }
        
        // store inputs
        inputs = p.getInputs();
        
        // store outputs
        outputs = p.getOutputs();
    }
    
    public int countInputs() {
        return inputs.size();
    }
    
    /**
     * Simulate one step with given inputs!
     * Or previous input if the input here is null.
     * @param inputs 
     */
    public void step(boolean[] inputs) {
        // TODO
        
        
        
        // update by scheduled updates
        
        
        // update scheduled components, propagating
        // we're just going to assume the updates are not order-dependent...
        // propagate into a goddamn reschedule
        
        // set inputs, propagating
        // when you propagate onto a component, schedule an update
    }
    
    public int[] outputs() {
        int[] vals = new int[outputs.size()];
        for (int i = 0; i < outputs.size(); i++) {
            Coord loc = outputs.get(i);
            vals[i] = state[loc.x][loc.y][loc.z].weakPower(0); // TODO what dir?
        }
        
        return vals;
    }
}
