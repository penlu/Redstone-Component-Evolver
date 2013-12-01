/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.Phenotype;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Crossable<P extends Phenotype, G extends Genome<P>> {
    /**
     * Crosses two genomes over to produce some offspring.
     * 
     * Does not apply mutations to the result.
     * @param g
     * @return
     */
    public G crossover(G g);
}
