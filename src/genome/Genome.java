/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.Phenotype;

/**
 * Interface declaring methods for genotype representations.
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Genome<G extends Genome<G, P>, P extends Phenotype> {
    /**
     * Creates a copy of this genome.
     * @return deep copy of this genome
     */
    public Genome<G, P> copy();
    
    /**
    * Translates this genome into a phenotype.
    * @return 
    */
    public P toPhenotype();
    
    /**
     * Performs some mutation on this genome.
     * @param g
     * @return
     */
    public void mutate();
    
    /**
     * Crosses two genomes over to produce some offspring.
     * 
     * Does not apply mutations to the result.
     * @param g
     * @return
     */
    public G crossover(G g);
}
