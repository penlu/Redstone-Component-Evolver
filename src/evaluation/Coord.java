/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

/**
 * Coordinates for parts of components.  Also permits arithmetic and converts 
 * single-digit directions to concrete offsets.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class Coord implements Comparable<Coord> {
    int x;
    int y;
    int z;
    
    /**
     * Creates a new coord from the given x, y, z parameters.
     * @param x
     * @param y
     * @param z 
     */
    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Add a coordinate to this one, returning result.
     * @param a
     * @return 
     */
    public Coord add(Coord a) {
        return new Coord(x + a.x, y + a.y, z + a.z);
    }
    
    public Coord sub(Coord a) {
        return new Coord(x - a.x, y - a.y, z - a.z);
    }
    
    /**
     * Converts from numerical directions to offsets.
     * 0 - up
     * 1 - +x
     * 2 - +y
     * 3 - -x
     * 4 - -y
     * @param dir a numerical direction
     * @return the coordinate that the numerical direction represents
     */
    public static Coord coordFromDir(int dir) {
        switch(dir) {
            case 0:
                return new Coord(0, 1, 0);
            case 1:
                return new Coord(1, 0, 0);
            case 2:
                return new Coord(0, 0, 1);
            case 3:
                return new Coord(-1, 0, 0);
            case 4:
                return new Coord(0, 0, -1);
            default:
                return new Coord(0, 0, 0);
        }
    }
    
    public int compareTo(Coord c) {
        if (x < c.x) {
            return -4;
        } else if (x > c.x) {
            return 4;
        }
        if (y < c.y) {
            return -2;
        } else if (y > c.y) {
            return 2;
        }
        if (z < c.z) {
            return -1;
        } else if (z > c.z) {
            return 1;
        }
        
        return 0;
    }
}
