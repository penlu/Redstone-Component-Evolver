/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import evolver.Phenotype;

/**
 * Interface declaring methods for phenotype evaluators.
 * 
 * The inheritors define evaluation procedures to be applied to arbitrary 
 * phenotypes.
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Evaluation<P extends Phenotype> {
    /**
     * Evaluates 
     * @param p
     * @return 
     */
    public double evaluate(P p);
}
