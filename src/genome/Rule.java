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
        // find every match in sequence
        ArrayList<Integer> matches = new ArrayList<Integer>();
        for (int i = 0; i < s.bases.size() - lhs.bases.size(); i++) {
            
        }
    }
}
