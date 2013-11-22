/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package evolver;

import java.util.*;

/**
 * Your one-stop source for random numbers.
 * @author Access
 */
public class Randomizer {
    private static final Random rand = new Random(0);

    public static double get() {
        return rand.nextDouble();
    }

    /**
     * Picks an object out of a list of objects with certain weights (weights do not need to sum to 1)
     * @param weights
     * @return index of selected objects
     */
    public static int pick(double[] weights) {
        double total = 0;
        int i;
        for (i = 0; i < weights.length; i++) {
            total += weights[i];
        }

        double selection = get();
        for (i = 0; selection > 0 && i < weights.length; i++) {
            selection -= weights[i] / total;
        }

        return i;
    }

    /**
     * Returns a randomly selected integer less than or equal to top.
     * @param top
     * @return
     */
    public static int getInt(int top) {
        return (int)(top * get());
    }

    // TODO parallelize for speed

    // TODO faster generation of single bits than doubles
}
