/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

/**
 * Simulates a Phenotype as it would transpire in the Minecraft world.
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
     * Construct a simulation for the given phenotype.
     * @param p 
     */
    public Simulation(Phenotype p) {
        // TODO preprocessing!!
    }
    
    public int countInputs() {
        // TODO return input count!
        return 0;
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
