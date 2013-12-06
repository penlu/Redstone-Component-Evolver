/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.Block;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class BlockModule extends ConcreteModule {
    public final Block block;
    public final int move;
    
    public BlockModule(Block b, int m) {
        block = b;
        move = m;
    }
}
