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
    private boolean[] connections;

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
    
    /**
     * Recalculate wire configuration from connections.
     */
    private void reconnect(World world, Coord loc) {
        boolean[] cardinals = new boolean[4]; // list of connections
        for (int side = 0; side < 4; side++) {
            Coord ngbloc = loc.add(new Coord(side));
            
            // see if connectable item in this direction
            // connectable adjacent
            if (world.getBlock(ngbloc).connectable(side ^ 1)) {
                cardinals[side] = true;
            }
            
            // connectable above
            if (world.getBlock(loc.add(new Coord(0, 1, 0))).block().id == Block.BlockID.AIR
             && world.getBlock(ngbloc.add(new Coord(0, 1, 0))).connectable(-1)) {
                cardinals[side] = true;
            }
            
            // connectable below
            if (world.getBlock(ngbloc).block().id == Block.BlockID.AIR
             && world.getBlock(ngbloc.add(new Coord(0, -1, 0))).connectable(-1)) {
                cardinals[side] = true;
            }
        }
        
        connections[0] = true;
        connections[1] = false;
        for (int i = 0; i < 4; i++) {
            connections[2 + i] = cardinals[i];
        }
    }

    public ArrayList<Coord> update(World world, Coord loc) {
        // update and propagate current strengths
        int newlevel = Math.max(Math.max(world.indirectPowerInput(loc), neighborWireInputs(world, loc)) - 1, 0);
        
        // recalculate wire configuration from connections
        reconnect(world, loc);
        
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
    
    public int weakPower(int dir) {
        if (connections[dir]) { // power to below or sides
            return level;
        } else {
            return 0;
        }
    }
    
    public int strongPower(int dir) {
        // um... replicating strange code in Minecraft source; I don't trust this
        return weakPower(dir); // TODO testing!
    }
    
    public boolean connectable(int dir) {
        return true;
    }
}
