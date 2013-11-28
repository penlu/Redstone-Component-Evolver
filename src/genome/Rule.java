/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import java.util.ArrayList;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class Rule {
    Sequence lhs;
    Sequence rhs;
    
    public Rule(Sequence lhs, Sequence rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    public Rule copy() {
        return new Rule(lhs.copy(), rhs.copy());
    }
    
    /**
     * Applies this rule to a sequence, modifying the sequence and an update mask  
     * indicating which areas have already been modified and should thus be avoided in 
     * future iterations.
     * @param s sequence to modify
     * @param mask edit forbiddence mask
     */
    public void apply(Sequence s, Sequence mask) {
        // ensure non-degenerate case
        if (lhs.bases.isEmpty()) {
            return;
        }
        
        // start matching
        ArrayList<Integer> matches = new ArrayList<Integer>();
        int begin = s.match(lhs, 0);
        while (begin < s.bases.size()) { // should probably get matches in Sequence itself
            matches.add(begin);
            begin = s.match(lhs, begin);
        }
        
        // check masking
        for (int i = 0; i < matches.size(); i++) {
            for (int j = 0; j < lhs.bases.size(); j++) {
                // remove this match if overlapping nonzero mask value
                if (mask.bases.get(matches.get(i) + j) != 0) {
                    matches.remove(i);
                    i--; // back up to not skip values
                    break;
                }
            }
        }
        
        // remove overlapping matches
        // go backwards so continuous overlaps don't delete all values
        for (int i = matches.size() - 2; i >= 0; i--) {
            if (matches.get(i) + lhs.bases.size() > matches.get(i + 1)) {
                matches.remove(i);
                i++; // back up to not skip values
                break;
            }
        }
        
        // apply at remaining locations; modify mask
        for (int i = matches.size() - 1; i >= 0; i--) { // we go backwards to avoid index fuckery
            
        }
    }
}
