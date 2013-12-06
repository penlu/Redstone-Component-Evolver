/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class AbstractModule implements LModule {
    public final int level; // abstraction level of this module
    
    public AbstractModule(int level) {
        this.level = level;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof AbstractModule)) {
            return false;
        }
        
        AbstractModule am = (AbstractModule)o;
        return level == am.level;
    }
}
