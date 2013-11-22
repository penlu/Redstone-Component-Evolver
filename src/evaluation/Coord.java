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
public class Coord {
    private int[] xyz;
    
    public Coord() {
        xyz = new int[3];
    }
    
    /**
     * Creates a new coord from the given x, y, z parameters.
     * @param x
     * @param y
     * @param z 
     */
    public Coord(int x, int y, int z) {
        xyz = new int[3];
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
    }
    
    /**
     * Creates coordinates from the array of parameters given.
     * @param arr 
     */
    public Coord(int[] arr) {
        xyz = new int[3];
        for (int i = 0; i < 3; i++) {
            xyz[i] = arr[i];
        }
    }
    
    /**
     * Gets the ith element of this coordinate.
     * @param i
     * @return 
     */
    public int e(int i) {
        return xyz[i];
    }
    
    public int x() {
        return xyz[0];
    }
    
    public int y() {
        return xyz[1];
    }
    
    public int z() {
        return xyz[2];
    }
    
    /**
     * Add a coordinate to this one, returning result.
     * @param a
     * @return 
     */
    public Coord add(Coord a) {
        int[] newXYZ = new int[3];
        for (int i = 0; i < 3; i++) {
            newXYZ[i] = xyz[i] + a.e(i);
        }
        
        return new Coord(newXYZ);
    }
    
    public Coord sub(Coord a) {
        int[] newXYZ = new int[3];
        for (int i = 0; i < 3; i++) {
            newXYZ[i] = xyz[i] - a.e(i);
        }
        
        return new Coord(newXYZ);
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
}
