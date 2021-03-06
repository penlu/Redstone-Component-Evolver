/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evolver;

/**
 * Coordinates for parts of components.  Also permits arithmetic and converts 
 * single-digit directions to concrete offsets.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class Coord implements Comparable<Coord> {
    /**
     * Returns a coordinate with maximum elements from each given coordinate
     * @param a
     * @param b
     * @return 
     */
    public static Coord max(Coord a, Coord b) {
        return new Coord(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));
    }
    
    /**
     * Returns a coordinate with minimum elements from each given coordinate
     * @param a
     * @param b
     * @return 
     */
    public static Coord min(Coord a, Coord b) {
        return new Coord(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));
    }
    
    public final int x;
    public final int y;
    public final int z;
    
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
     * Copies a coord from another's elements
     */
    public Coord(Coord c) {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
    }
    
    /**
     * Converts from numerical directions to coordinates
     * 0 - -y
     * 1 - +y
     * 2 - -z
     * 3 - +z
     * 4 - -x
     * 5 - +x
     * @param dir a numerical direction
     * @return the coordinate that the numerical direction represents
     */
    public Coord(int dir) {
        switch(dir) {
            case 0:
                x = 0; y = -1; z = 0;
                break;
            case 1:
                x = 0; y = 1; z = 0;
                break;
            case 2:
                x = 0; y = 0; z = -1;
                break;
            case 3:
                x = 0; y = 0; z = 1;
                break;
            case 4:
                x = -1; y = 0; z = 0;
                break;
            case 5:
                x = 1; y = 0; z = 0;
                break;
            default:
                x = 0; y = 0; z = 0;
                break;
        }
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
    
    public boolean equals(Object c) {
        if (!(c instanceof Coord)) {
            return false;
        }
        
        return x == ((Coord)c).x && y == ((Coord)c).y && z == ((Coord)c).z;
    }
}
