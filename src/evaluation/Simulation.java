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
    private ArrayList<Coord> elements;
    private ArrayList<Coord> inputs;
    private ArrayList<Coord> outputs;
    
    /**
     * Construct a simulation for the given phenotype.
     * @param p 
     */
    public Simulation(RSPhenotype p) { // TODO preprocessing!
        // convert phenotype contents to 3D state array
        // find space bounds
        
        // get inputs
        inputs = p.getInputs();
        
        // get outputs
        outputs = p.getOutputs();
    }
    
    public int countInputs() {
        return inputs.size();
    }
    
    public void step(boolean[] inputs) {
        // TODO simulate one step with given inputs!
        // or previous inputs if the input here is null
    }
    
    public boolean[] outputs() {
        // TODO read off outputs!
        return new boolean[0];
    }
}
