/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import java.util.ArrayList;

/**
 * Provides manipulation utilities for a string of integer "bases".
 * @author Eric Lu <penlume@gmail.com>
 */
public class Sequence {
    private ArrayList<Integer> bases; // list of bases in this sequence
    
    /**
     * Create a new empty sequence.
     */
    public Sequence() {
        bases = new ArrayList<Integer>();
    }
    
    /**
     * Create a sequence from a list of bases.
     * @param b 
     */
    private Sequence(ArrayList<Integer> b) {
        bases = (ArrayList<Integer>)b.clone();
    }
    
    public int length() {
        return bases.size();
    }
    
    /**
     * Read base at specified index.
     * @param i
     * @return 
     */
    public int read(int i) {
        return bases.get(i);
    }
    
    /**
     * Produces a copy of this sequence
     * @return 
     */
    public Sequence copy() {
        return new Sequence(bases);
    }
    
    /**
     * Retrieves the subsequence of this sequence consisting of the bases with 
     * indices in the range [begin, end).
     * @param begin
     * @param end
     * @return null if indexes out of range
     */
    public Sequence subsequence(int begin, int end) {
        if (begin < 0 || begin > this.length() || end < 0 || end > this.length())
            return null;
        
        // 
        ArrayList<Integer> subbases = new ArrayList<Integer>(end - begin);
        for (int i = begin; i < end; i++) {
            subbases.add(bases.get(i));
        }
        
        return new Sequence(subbases);
    }
    
    /**
     * Returns the index of the first character of the first complete match of 
     * the sequence s after the index given by begin.
     * @param s
     * @param begin
     * @return length of this sequence if no match found
     */
    public int find(Sequence s, int begin) {
        // naive substring match algorithm
        for (int i = begin, l = this.length() - s.length(); i < l; i++) {
            boolean match = true;
            // check if this location matches substring
            for (int j = 0; j < s.length(); j++) {
                if (s.read(j) != this.read(j + i)) {
                    match = false;
                    break;
                }
            }
            if (match) return i;
        }
        
        return this.length();
    }
    
    /**
     * Inserts a specified subsequence at the given index.
     * @param s
     * @param i
     * @return resulting subsequence or null if failure
     */
    public Sequence insert(Sequence s, int i) {
        if (i > this.length()) return null;
        
        
    }
}
