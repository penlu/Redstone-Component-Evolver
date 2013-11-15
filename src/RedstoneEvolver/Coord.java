/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RedstoneEvolver;

/**
 *
 * @author Access
 */
public class Coord {
    public static final Coord[] cardinals
            = {new Coord(1, 0, 0), new Coord(-1, 0, 0),
            new Coord(0, 1, 0), new Coord(0, -1, 0),
            new Coord(0, 0, 1), new Coord(0, 0, -1)};
    
    public static Coord getRandomCardinal() {
        return cardinals[(int)(Randomizer.getInt(cardinals.length))];
    }

    private int x;
    private int y;
    private int z;

    public Coord(int x, int y, int z) { // TODO efficiency by caching; multiton
        this.x = x; this.y = y; this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
