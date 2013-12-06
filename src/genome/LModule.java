/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.Block;
import java.util.ArrayList;

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
public class LModule {
    public final boolean abs; // is this instruction abstract?
    
    public final Block block; // block this defines, if any
    public final int action; // generic integer information
                             // if abstract, is abstraction level
                             // if block is null, defines a stack/destack/
                             // set input/set output; else represents move 
                             // after placing block
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LModule)) {
            return false;
        }
        
        LModule b = (LModule)o;
        
        // can add a don't-care condition here
        return (b.block == block || block == null)
            && (b.move == move || move == -1);
    }
}
