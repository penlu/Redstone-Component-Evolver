/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import evolver.Block;
import evolver.Coord;
import java.util.ArrayList;

// TODO correct powering
/**
 * Implements redstone torch behavior.
 * @author Eric Lu <penlume@gmail.com>
 */
public class TorchBlockState implements BlockState {
    private Block block;
    private boolean lit;
    
    public TorchBlockState(Block b) {
        block = b;
    }
    
    public Block block() {
        return block;
    }
    
    public ArrayList<Coord> update(World world, Coord loc) {
        Coord anchorloc = loc.sub(new Coord(block.data));
        boolean newlight = world.isPowered(anchorloc);
        if (newlight != lit) {
            lit = newlight;
            ArrayList<Coord> updates = new ArrayList<Coord>();
            for (int i = 0; i < 6; i++) {
                updates.add(new Coord(i));
            }
            
            return updates;
        }
        
        return new ArrayList<Coord>();
    }
    
    public int weakPower(int dir) {
        return dir != block.data ? 15 : 0;
    }
    
    public int strongPower(int dir) {
        return dir == 1 ? 15 : 0;
    }
    
    public boolean connectable(int dir) {
        return dir != block.data && dir != -1;
    }
}
