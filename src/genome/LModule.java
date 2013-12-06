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
    public static enum LSymbol {
        ABSTRACT,
        BLOCK,
        STACK,
        DESTACK,
        INPUT,
        OUTPUT;
    }
    
    LSymbol symbol; // symbol
    Block payload; // block instruction this carries, if any
    int data; // integer data this carries
    
    
    
    
    
    public final Block block; // block to place
    public final int move;  // turtle movement direction after placing block
    
    public LModule(Block b, int m) {
        block = b;
        move = m;
    }
    
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
