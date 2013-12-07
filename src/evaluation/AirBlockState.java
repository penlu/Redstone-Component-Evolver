/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import evolver.Block;
import evolver.Coord;
import java.util.ArrayList;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class AirBlockState implements BlockState {
    private Block block;

    public AirBlockState(Block b) {
        block = b;
    }

    public Block block() {
        return block;
    }

    public ArrayList<Coord> update(World world, Coord loc) {
        return new ArrayList<Coord>();
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
