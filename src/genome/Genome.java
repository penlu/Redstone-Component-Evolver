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
    
    public Genome() {
        axiom = new Sequence();
        batches = new ArrayList<ArrayList<Rule>>();
    }
    
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
     * @return deep copy of this genome
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
        // translate axiom to final intertype
        Sequence inter = axiom.copy();
        for (int i = 0; i < batches.size(); i++) {
            ArrayList<Rule> batch = batches.get(i);
            Sequence mask = new Sequence(inter.bases.size(), 0);
            for (int j = 0; j < batch.size(); j++) {
                batch.get(j).apply(inter, mask);
            }
        }
        
        // convert intertype sequence to phenotype
        return EnderTurtle.process(inter);
    }
    
    private void modify(Rule r) {
        
    }
    
    /**
     * Performs some mutation on this genome.  One of the following:
     *  - axiom modification
     *  - rule modification
     * 
     *  - rule substitution/factorization
     * 
     *  - rule deletion
     *  - rule duplication
     *  - rule order changing
     *  - rule batch changing
     * 
     *  - batch splitting
     *  - batch merging
     * 
     * Modification encompasses one of:
     *  - single base insertion/deletion
     *  - run insertion/deletion/duplication/reversal
     * 
     * @param g
     * @return
     */
    public Genome mutate() { // TODO
        // weighted mutation probabilities!
        double rand = Math.random();
        if (rand < 0.85) {
            // pick a rule/axiom to modify
            ArrayList<Rule> batch = batches.get((int)(Math.random() * batches.size()));
            Rule r = batch.get((int)(Math.random() * batch.size()));
            
            modify(r);
        } else {
            // do a batch-level modification
        }
        
        return copy();
    }
    
    /**
     * Crosses two genomes over to produce some offspring.
     * 
     * Does not apply mutations to the result.
     * @param f
     * @param g
     * @return
     */
    public Genome crossover(Genome g) {
        // TODO
        return copy();
    }
}
