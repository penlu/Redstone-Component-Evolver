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
public class InputBlockState implements BlockState {
    Block b; // block for getBlock
    int state; // if input is on or off

    public InputBlockState() {
        b = new Block(Block.BlockID.AIR, 0);
    }

    public Block block() {
        return b;
    }

    public void setState(int newstate) {
        state = newstate;
    }

    public ArrayList<Coord> update(World world, Coord loc) {
        ArrayList<Coord> neighbors = new ArrayList<Coord>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    Coord ngbloc = loc.add(new Coord(i, j, k));
                    if (!ngbloc.equals(loc)) {
                        neighbors.add(ngbloc);
                    }
                }
            }
        }
        
        return neighbors;
    }

    public int weakPower(int dir) {
        return state;
    }

    public int strongPower(int dir) {
        return 0;
    }
    
    public boolean connectable(int dir) {
        return true;
    }
}
