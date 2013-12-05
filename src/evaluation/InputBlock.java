/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import evolver.Block;
import evolver.Coord;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class InputBlock implements BlockState {
    Block b; // block for getBlock
    int state; // if input is on or off
    
    public InputBlock() {
        b = new Block(Block.BlockID.AIR, 0);
    }
    
    public Block getBlock() {
        return b;
    }
    
    public void setState(int newstate) {
        state = newstate;
    }
    
    public boolean update(BlockState[][][] world, Coord loc) {
        return true;
    }
    
    public boolean indirectPower() {
        return state > 0;
    }
    
    public int weakPower(int dir) {
        return state;
    }
    
    public int strongPower(int dir) {
        return 0;
    }
}
