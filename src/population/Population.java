/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package population;

import statistics.Statistics;

/**
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Population<S extends Statistics> {
    /**
     * Runs a generation of this population.  This is where the population does 
     * its evaluation, breeding, reaping... etc.
     */
    public void generation();
    
    /**
     * Get information about the current population.  Information is output as 
     * a statistics object.
     * @return 
     */
    public S getOutput();
}
