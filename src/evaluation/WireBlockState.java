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

    public Block getBlock() {
        return block;
    }
    
    /**
     * 
     * @param world
     * @param loc
     * @return a list of locations of wires that conduct to this one
     */
    private ArrayList<Coord> getConductingWires(BlockState[][][] world, Coord loc) {
        ArrayList<Coord> wires = new ArrayList<Coord>();
        
        int[] cardinals = {0, 1, 4, 5}; // cardinal directions
        for (int i = 0; i < 4; i++) {
            Coord side = new Coord(cardinals[i]);
            if (world[side.x][side.y][side.z].getBlock().id == BlockID.WIRE) {
                wires.add(side);
            } else if (world[side.x][side.y][side.z].getBlock().id == BlockID.AIR
                    && world[side.x][side.y - 1][side.z].getBlock().id == BlockID.WIRE) {
                wires.add(side.add(new Coord(0, -1, 0))); // add below
            } else if (world[loc.x][loc.y + 1][loc.z].getBlock().id == BlockID.AIR
                    && world[side.x][side.y + 1][side.z].getBlock().id == BlockID.WIRE) {
                wires.add(side.add(new Coord(0, 1, 0))); // add above
            }
        }
        
        return wires;
    }

    // get maximum incoming current
    private int getMaxCurrent(BlockState[][][] world, Coord loc) {
        int maxcurrent = 0;
        for (int i = 0; i < 6; i++) {
            // maximize weak/strong incoming power on all sides
            Coord dir = loc.add(new Coord(i));
            BlockState adj = world[dir.x][dir.y][dir.z];
            int rev = i ^ 1; // reverse direction

            // TODO disregard wires in this step
            int power = Math.max(adj.weakPower(rev), adj.strongPower(rev));
            if (power > maxcurrent) {
                maxcurrent = power;
            }
        }

        // also check all neighboring wires
        ArrayList<Coord> wires = getConductingWires(world, loc);
        for (int i = 0; i < wires.size(); i++) {
            // all accessible wires DO conduct
            
            maxcurrent = Math.max(maxcurrent, 1); // TODO don't disregard wires
        }
    }

    public boolean update(BlockState[][][] world, Coord loc) {

    }
    
    public boolean indirectPower() {
        return level > 0;
    }
    
    public int weakPower(int dir) {
        
    }
}
