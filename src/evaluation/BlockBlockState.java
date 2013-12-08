/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluation;

import evolver.Block;
import evolver.Coord;
import java.util.ArrayList;

/**
 *
 * @author Anne
 */
public class BlockBlockState implements BlockState {
    private Block block;
    
    public BlockBlockState(Block b) {
        block = b;
    }
    
    public Block block() {
        return block;
    }
    
    public ArrayList<Coord> update(World world, Coord loc) {
        return new ArrayList<Coord>();
    }
    
    public int weakPower(int dir) {
        return 0;
    }
    
    public int strongPower(int dir) {
        return 0;
    }
    
    public boolean connectable(int dir) {
        return false;
    }
}
