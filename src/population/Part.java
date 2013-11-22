/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package population;

import evolver.Randomizer;

/**
 *
 * @author Access
 */
public enum Part {
    NOP, AIR, BLOCK, DUST,
    TORCH_X, TORCH_NX, TORCH_Y, TORCH_NY, TORCH_Z,
    REPEATER_X, REPEATER_NX, REPEATER_Y, REPEATER_NY,
    COMPARATOR_X, COMPARATOR_NX, COMPARATOR_Y, COMPARATOR_NY,
    PISTON_X, PISTON_NX, PISTON_Y, PISTON_NY, PISTON_Z, PISTON_NZ;

    /**
     * returns a random part
     * @return
     */
    public static Part getRandomPart() {
        return values()[(int)(Randomizer.getInt(values().length))];
    }
}
