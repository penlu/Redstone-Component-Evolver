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
    
    private void modify(Sequence s) {
        
    }
    
    /**
     * Performs one mutation cycle on this genome.
     * A set of mutations is applied with specified probability for each 
     * mutation.
     * Physically, one mutation cycle represents the state of the genome after 
     * some fixed amount of time for which the probability of certain mutations 
     * occurring is specified.
     * 
     * Mutations may be one of the following:
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
        if (Math.random() < 0.9) {
            // select a rule / modify the axiom
            
            // aggregating rules
            ArrayList<Rule> rules = new ArrayList<Rule>();
            // super hacky backtrack solution
            ArrayList<Integer> batchnums = new ArrayList<Integer>();
            ArrayList<Integer> batchindex = new ArrayList<Integer>();
            for (int i = 0; i < batches.size(); i++) {
                for (int j = 0; j < batches.get(i).size(); j++) {
                    rules.add(batches.get(i).get(j));
                    batchnums.add(i);
                    batchindex.add(j);
                }
            }
            
            int sel = (int)(Math.random() * (rules.size() + 1));
            if (sel == rules.size()) {
                // modify axiom
                modify(axiom);
            } else {
                Rule r = rules.get(sel);
                // modify indexed rule
                if (Math.random() < 0.3) {
                    // modify lhs
                    modify(r.lhs);
                    if (r.lhs.bases.isEmpty()) { // remove rule!
                        // hacky solution
                        batches.get(batchnums.get(sel)).remove((int)batchindex.get(sel));
                    }
                } else {
                    // modify rhs
                    modify(r.rhs);
                }
            }
        }
        if (Math.random() < 0.1) {
            // TODO batch-level modification
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
