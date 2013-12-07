/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import evolver.Block;
import evolver.Block.BlockID;
import evolver.Coord;
import java.util.ArrayList;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class WireBlockState implements BlockState {
    private Block block;
    private int level;

    public WireBlockState(Block b) {
        block = b;
    }

    public Block block() {
        return block;
    }
    
    private int neighborWireInputs(World world, Coord loc) {
        int maxInput = 0;
        for (int dir = 2; dir < 5; dir++) {
            Coord ngbloc = loc.add(new Coord(dir));
            
            // neighboring on same level
            if (world.getBlock(ngbloc).block().id == Block.BlockID.WIRE) {
                maxInput = Math.max(maxInput, 0); // TODO how to get wire info from wires?
            }
            
            // neighboring blocks below
            if (world.getBlock(ngbloc).block().id == Block.BlockID.AIR
             && world.getBlock(ngbloc.add(new Coord(0, -1, 0))).block().id == Block.BlockID.WIRE) {
                maxInput = Math.max(maxInput, 0);
            }
            
            // neighboring blocks above
            if (world.getBlock(loc.add(new Coord(0, 1, 0))).block().id == Block.BlockID.AIR
             && world.getBlock(ngbloc.add(new Coord(0, 1, 0))).block().id == Block.BlockID.WIRE) {
                maxInput = Math.max(maxInput, 0);
            }
        }
        
        return maxInput;
    }

    public ArrayList<Coord> update(World world, Coord loc) {
        // update and propagate current strengths
        int newlevel = Math.max(Math.max(world.highestWeakInput(loc), neighborWireInputs(world, loc)) - 1, 0);
        
        if (newlevel - 1 != level) {
            // change power level
            level = newlevel - 1;
            
            // update all neighbors!
            ArrayList<Coord> toUpdate = new ArrayList<Coord>();
            for (int dir = 0; dir < 6; dir++) {
                toUpdate.add(new Coord(dir));
            }
            return toUpdate;
        } else {
            return new ArrayList<Coord>();
        }
    }
    
    public boolean indirectPower() {
        return level > 0;
    }
    
    public int weakPower(int dir) {
        
    }
    
    public int strongPower(int dir) {
        
    }
}
