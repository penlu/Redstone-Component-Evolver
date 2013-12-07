/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import evolver.Block;
import evolver.Coord;

/**
 * Stores a bunch of block states!
 * @author Eric Lu <penlume@gmail.com>
 */
public class World {
    BlockState[][][] state;
    
    public World(BlockState[][][] state) {
        this.state = state;
    }
    
    public BlockState getBlock(Coord c) {
        if (c.x < 0 || c.y < 0 || c.z < 0 ||
            c.x >= state.length ||
            c.y >= state[0].length ||
            c.z >= state[0][0].length) {
            return new AirBlockState(new Block(Block.BlockID.AIR, 0));
        } else {
            return state[c.x][c.y][c.z];
        }
    }
}
