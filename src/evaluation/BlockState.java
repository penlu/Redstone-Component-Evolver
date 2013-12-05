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
public interface BlockState {
    /**
     * Get info of the block this activizes.
     * @return
     */
    public Block getBlock();
    
    /**
     * Update state of this block using world data and this block's location
     * @return did the state change?
     */
    public boolean update(BlockState[][][] world, Coord loc);
    
    /**
     * Is this block emitting indirect power?
     * 
     * (if the block is receiving weak/strong power, then yes)
     * @return 
     */
    public boolean indirectPower();
    
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
}
