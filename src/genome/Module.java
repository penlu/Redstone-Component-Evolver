/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

/**
 * Stores a base for the L-system genome.
 * 
 * LModule objects allow for equals comparisons with arbitrary behavior and 
 * parametrized 0-context L-systems.
 * 
 * This one stores a part, part data, and the direction to move after the 
 * part has been placed.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Module {
    @Override
    public boolean equals(Object o);
}
