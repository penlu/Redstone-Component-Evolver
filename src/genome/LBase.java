/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evaluation.Block;

/**
 * Stores a base for the L-system genome.
 * 
 * LBase objects allow for equals comparisons with arbitrary behavior and 
 * parametrized 0-context L-systems.
 * 
 * This one stores a part, part data, and the direction to move after the 
 * part has been placed.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class LBase {
    public final Block block; // block to place
    public final int move;  // turtle movement after placing block
    
    public LBase(Block b, int m) {
        block = b;
        move = m;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LBase)) {
            return false;
        }
        
        LBase b = (LBase)o;
        
        // can add a don't-care condition here
        return b.block == block && b.move == move;
    }
}
