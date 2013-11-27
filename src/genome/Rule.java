/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

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
}
