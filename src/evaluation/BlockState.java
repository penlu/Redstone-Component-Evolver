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
public interface BlockState {
    /**
     * Get info of the block this activizes.
     * @return
     */
    public Block block();
    
    /**
     * Update state of this block using world data and this block's location
     * @return any blocks that need updating as a consequence of this update
     */
    public ArrayList<Coord> update(World world, Coord loc);
    
    /**
     * Is this block emitting weak power in this direction?
     * 
     * (If this block is intrinsically powered, or else is receiving strong power)
     * @param dir
     * @return level of emitted weak power
     */
    public int weakPower(int dir);
    
    /**
     * Is this block emitting strong power in this direction?
     * 
     * (If this block is intrinsically strong-powered)
     * @param dir
     * @return level of emitted strong power?
     */
    public int strongPower(int dir);
    
    /**
     * Can this block be connected to from the given direction?
     * 
     * Exclusively for redstone wire connectivities.
     * 
     * A direction parameter of -1 indicates different altitudes.
     * @param dir
     * @return 
     */
    public boolean connectable(int dir);
}
