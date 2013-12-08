/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics;

/**
 * Produced by a population; used to carry information about a population out 
 * of the population object.
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Statistics {
    /**
     * Convert contents of this statistics object into a string.
     * 
     * This enables file output, print to console, etc..
     * @param verbosity amount of information to include, if it matters
     * @return 
     */
    public String getText(int verbosity);
}
