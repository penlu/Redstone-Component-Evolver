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
    
    private ArrayList<Coord> scheduled; // components scheduled for updates
    
    private ArrayList<Coord> inputs;
    private ArrayList<Coord> outputs;
    
    /**
     * Construct a simulation for the given phenotype.
     * @param p 
     */
    public Simulation(RSPhenotype p) { // TODO preprocessing!
        // produce 3D state array
        zero = p.getMinBound();
        Coord size = p.getMaxBound().sub(zero);
        state = new int[size.x][size.y][size.z];
        
        // schedule components for first update
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                for (int k = 0; k < size.z; k++) {
                    Coord loc = new Coord(i, j, k).add(zero);
                    if (Block.BlockID.isComponent(
                            p.getBlock(loc).id)) {
                        scheduled.add(loc);
                    }
                }
            }
        }
        
        // store inputs
        inputs = p.getInputs();
        
        // store outputs
        outputs = p.getOutputs();
    }
    
    public void updateComponents() {
        // TODO update scheduled components
    }
    
    public int countInputs() {
        return inputs.size();
    }
    
    public void step(boolean[] inputs) {
        // TODO simulate one step with given inputs!
        // or previous inputs if the input here is null
        
        // update scheduled components, propagating
        
        // set inputs, propagating
        
        // reschedule
    }
    
    public boolean[] outputs() {
        // TODO read off outputs!
        return new boolean[0];
    }
}
