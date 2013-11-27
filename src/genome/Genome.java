/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evaluation.Phenotype;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class Genome {
    /**
    * Randomly generates a new genome.
    * @param s a rough measure of the complexity of the genome to generate
    * @return 
    */
    public static Genome gen(int s) {
        // TODO
        return new Genome();
    }
    
    /**
    * Translates this genome into a phenotype.
    * @param g
    * @return 
    */
    public Phenotype toPhenotype();
    
    
    /**
     * Performs some mutation on this genome.
     * @param g
     * @return
     */
    public Genome mutate();
    
    /**
     * Crosses two genomes over to produce some offspring.
     * 
     * Does not apply mutations.
     * @param f
     * @param g
     * @return
     */
    public Genome crossover(Genome g);
}
