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
    
    /**
     * Get strong power remnant "leaking" out of a block.
     * @param c
     * @return 
     */
    public int blockLeakage(Coord c) {
        int leakage = 0;
        for (int dir = 0; dir < 6; dir++) {
            Coord inputLoc = c.add(new Coord(dir));
            // power from strong outputs
            leakage = Math.max(leakage, getBlock(inputLoc).strongPower(dir ^ 1));
        }
        
        return leakage;
    }
    
    /**
     * Get weak power inputs to a location (the kind that will power a wire)
     * @param c
     * @return level of input
     */
    public int highestWeakInput(Coord c) {
        int maxWeakInput = 0;
        for (int dir = 0; dir < 6; dir++) {
            Coord inputLoc = c.add(new Coord(dir));
            
            if (getBlock(inputLoc).block().id == Block.BlockID.BLOCK) {
                // power from leakage from strong input to block
                maxWeakInput = Math.max(maxWeakInput, blockLeakage(inputLoc));
            } else {
                // power from weak output
                maxWeakInput = Math.max(maxWeakInput, getBlock(inputLoc).weakPower(dir ^ 1));
            }
        }
        
        return maxWeakInput;
    }
}
