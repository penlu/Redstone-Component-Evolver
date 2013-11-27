/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evaluation.Phenotype;
import java.util.ArrayList;

/**
 * Contains methods for a genotype representation.
 * 
 * This implementation does an L-system genome, so it stores a starting axiom 
 * and a list of batches of rules to apply.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class Genome {
    Sequence axiom; // starting axiom for intertype translation
    ArrayList<ArrayList<Rule>> batches; // list of batches of rules
    
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
     * Creates a copy of this genome.
     * @return 
     */
    public Genome copy() {
        Genome g = new Genome();
        g.axiom = axiom.copy();
        g.batches = new ArrayList<ArrayList<Rule>>(batches.size());
        for (int i = 0; i < batches.size(); i++) {
            g.batches.add(i, new ArrayList<Rule>(batches.get(i).size()));
            for (int j = 0; j < batches.get(i).size(); i++) {
                g.batches.get(i).add(j, batches.get(i).get(j).copy());
            }
        }
        
        return g;
    }
    
    /**
    * Translates this genome into a phenotype.
    * @param g
    * @return 
    */
    public Phenotype toPhenotype() {
        // TODO
        return new Phenotype();
    }
    
    /**
     * Performs some mutation on this genome.
     * @param g
     * @return
     */
    public Genome mutate() {
        // TODO
        return copy();
    }
    
    /**
     * Crosses two genomes over to produce some offspring.
     * 
     * Does not apply mutations.
     * @param f
     * @param g
     * @return
     */
    public Genome crossover(Genome g) {
        // TODO
        return copy();
    }
}
