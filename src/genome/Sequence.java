/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides manipulation utilities for a string of "elements".
 * @author Eric Lu <penlume@gmail.com>
 */
public class Sequence<E> {
    private ArrayList<E> elements; // elements of elements in this sequence
    
    /**
     * Create a new empty sequence.
     */
    public Sequence() {
        elements = new ArrayList<E>();
    }
    
    /**
     * Create a new sequence of specified length filled with specified contents.
     * Useful for generating new update masks.
     */
    public Sequence(int l, E c) {
        elements = new ArrayList<E>(l);
        for (int i = 0; i < l; i++) {
            elements.add(c);
        }
    }
    
    private Sequence(ArrayList<E> b) {
        elements = (ArrayList<E>)b.clone();
    }
    
    /**
     * Produces a copy of this sequence
     * @return 
     */
    public Sequence copy() {
        return new Sequence(elements);
    }
    
    public ArrayList<E> getElements() {
        return new ArrayList<E>(elements);
    }
    
    /**
     * Retrieves the subsequence of this sequence consisting of the elements with 
     * indices in the range [begin, end).
     * @param begin
     * @param end
     * @return
     */
    public Sequence subsequence(int begin, int end) {
        return new Sequence(new ArrayList<E>(elements.subList(begin, end)));
    }
    
    public boolean equals(Sequence s) {
        // length check
        if (s.getElements().size() != elements.size()) {
            return false;
        }
        
        // check every element for equality
        for (int i = 0; i < s.getElements().size(); i++) {
            if (s.getElements().get(i) != elements.get(i)) {
                return false;
            }
        }
        
        return true;
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
        for (int i = begin, l = elements.size() - s.getElements().size(); i < l; i++) {
            boolean match = true;
            // check if this location matches substring
            for (int j = 0; j < s.getElements().size(); j++) {
                if (!s.getElements().get(j).equals(elements.get(j + i))) {
                    match = false;
                    break;
                }
            }
            
            // found a match?
            if (match) return i;
        }
        
        return elements.size();
    }
    
    /**
     * Inserts a single base at the given index.
     * @param b
     * @param i
     * @return whether insertion was successful
     */
    public boolean insert(E b, int i) {
        if (i < 0 || i > elements.size()) {
            return false;
        }
        
        elements.add(i, b);
        return true;
    }
    
    /**
     * Inserts a specified subsequence at the given index.
     * @param s
     * @param i
     * @return whether the insertion was successful
     */
    public boolean insert(Sequence s, int i) {
        if (i < 0 || i > elements.size()) {
            return false;
        }
        
        // add elements
        elements.addAll(i, s.getElements());
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
        if (i + n > elements.size()) {
            n = elements.size() - i;
        }
        
        // retrieve removed elements
        Sequence s = new Sequence(new ArrayList<E>(elements.subList(i, i + n)));
        
        // remove elements
        for (int x = 0; x < n; x++) {
            elements.remove(i);
        }
        
        return s;
    }
}
