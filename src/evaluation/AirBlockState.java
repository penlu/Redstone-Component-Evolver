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
public class AirBlockState implements BlockState {
    private Block block;

    public AirBlockState(Block b) {
        block = b;
    }

    public Block getBlock() {
        return block;
    }

    public boolean update(BlockState[][][] world, Coord loc) {
        return false;
    }

    public boolean indirectPower() {
        return false;
    }

    public int weakPower(int dir) {
        return 0;
    }

    public int strongPower(int dir) {
        return 0;
    }
}
