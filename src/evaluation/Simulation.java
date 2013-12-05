/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

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
    private int[][][] state; // current state of the component
    private Coord zero; // virtual location of the zero index
    
    // update scheduling
    private ArrayList<Coord> scheduled; // components scheduled for updates
    private ArrayList<Integer> schedpower; // power at update scheduling
    
    private ArrayList<Coord> inputs;
    private ArrayList<Coord> outputs;
    
    /**
     * Construct a simulation for the given phenotype.
     * @param p 
     */
    public Simulation(RSPhenotype p) {
        // produce 3D state array
        zero = p.getMinBound();
        Coord size = p.getMaxBound().sub(zero);
        state = new int[size.x][size.y][size.z];
        
        // schedule components for first update
        scheduled = new ArrayList<Coord>();
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                for (int k = 0; k < size.z; k++) {
                    Coord loc = new Coord(i, j, k).add(zero);
                    if (Block.BlockID.isComponent(
                            p.getBlock(loc).id)) {
                        scheduled.add(loc);
                        schedpower.add(0);
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
     * Schedule element at coordinate c to be updated with power p next step.
     * @param c
     * @param p 
     */
    private void schedule(Coord c, int p) {
        
    }
    
    /**
     * Propagate change by notifying block at coord c of a neighbor change, 
     * producing a state recomputation!
     * @param c 
     */
    private void notify(Coord c) {
        
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
            vals[i] = state[loc.x][loc.y][loc.z];
        }
        
        return vals;
    }
}
