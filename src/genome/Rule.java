/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import java.util.ArrayList;

/**
 * Stores a transformation rule.
 * 
 * Transformation rules change single abstract modules into strings of abstract 
 * and concrete modules.  Result abstract modules are of strictly lower abstraction 
 * than the source abstract module.
 * @author Eric Lu <penlume@gmail.com>
 */
public class Rule {
    public final EnderTurtle.AbstractModule lhs;
    public final Sequence<Module> rhs;
    
    public Rule(EnderTurtle.AbstractModule lhs, Sequence<Module> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    public Rule copy() {
        return new Rule(lhs, rhs.copy());
    }
    
    /**
     * Applies this rule to a sequence, replacing affected abstract symbols with 
     * intended 
     * @param s sequence to modify
     */
    public void apply(Sequence<Module> s) {
        // ensure non-degenerate case
        if (lhs == null || s == null) {
            return;
        }
        
        // make list of matches
        ArrayList<Integer> matches = new ArrayList<Integer>();
        for (int i = 0; i < s.getElements().size(); i++) {
            if (s.getElements().get(i).equals(lhs)) {
                matches.add(i);
            }
        }
        
        // replace matches, going backwards so as not to disrupt indices
        for (int i = matches.size() - 1; i >= 0; i--) {
            s.remove(matches.get(i), 1);
            s.insert(rhs, matches.get(i));
        }
    }
    
    public String toString() {
        return lhs.toString() + " : " + rhs.toString();
    }
}
