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
    ArrayList<Integer> bases; // list of bases in this sequence
    
    /**
     * Create a new empty sequence.
     */
    public Sequence() {
        bases = new ArrayList<Integer>();
    }
    
    /**
     * Produces a copy of this sequence
     * @return 
     */
    public Sequence copy() {
        Sequence s = new Sequence();
        s.bases = (ArrayList<Integer>)bases.clone();
        return s;
    }
    
    /**
     * Retrieves the subsequence of this sequence consisting of the bases with 
     * indices in the range [begin, end).
     * @param begin
     * @param end
     * @return
     */
    public Sequence subsequence(int begin, int end) {
        Sequence s = new Sequence();
        s.bases = (ArrayList<Integer>)bases.subList(begin, end);
        return s;
    }
    
    /**
     * Returns the index of the first character of the first complete match of 
     * the sequence s after the index given by begin.
     * @param s
     * @param begin
     * @return length of this sequence if no match found
     */
    public int match(Sequence s, int begin) {
        // naive substring match algorithm
        for (int i = begin, l = bases.size() - s.bases.size(); i < l; i++) {
            boolean match = true;
            // check if this location matches substring
            for (int j = 0; j < s.bases.size(); j++) {
                if (s.bases.get(j) != bases.get(j + i)) {
                    match = false;
                    break;
                }
            }
            
            // found a match?
            if (match) return i;
        }
        
        return bases.size();
    }
    
    /**
     * Inserts a single base at the given index.
     * @param b
     * @param i
     * @return whether insertion was successful
     */
    public boolean insert(int b, int i) {
        if (i < 0 || i > bases.size()) {
            return false;
        }
        
        bases.add(i, b);
        return true;
    }
    
    /**
     * Inserts a specified subsequence at the given index.
     * @param s
     * @param i
     * @return whether the insertion was successful
     */
    public boolean insert(Sequence s, int i) {
        if (i < 0 || i > bases.size()) {
            return false;
        }
        
        // add bases
        bases.addAll(i, s.bases);
        return true;
    }
    
    /**
     * Removes a subsequence of specified length beginning at the specified index.
     * @param i
     * @param n
     * @return the removed subsequence, or as much of it as was removed
     */
    public Sequence remove(int i, int n) {
        // bounds check
        if (n < 0) {
            i += n;
            n = -n;
        }
        if (i < 0) {
            n += i;
            i = 0;
        }
        if (i + n > bases.size()) {
            n = bases.size() - i;
        }
        
        // retrieve removed elements
        Sequence s = new Sequence();
        s.bases = (ArrayList<Integer>)bases.subList(i, i + n);
        
        // remove elements
        for (int x = 0; x < n; x++) {
            bases.remove(i);
        }
        
        
        return s;
    }
}
