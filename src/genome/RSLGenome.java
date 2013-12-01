/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evaluation.RSPhenotype;
import java.util.ArrayList;

/**
 * Contains methods for a genotype representation.
 * 
 * This implementation does an L-system genome, so it stores a starting axiom 
 * and a list of batches of rules to apply.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class RSLGenome implements Genome<RSPhenotype>,
                                  Crossable<RSPhenotype, RSLGenome> {
    Sequence axiom; // starting axiom for intertype translation
    ArrayList<ArrayList<Rule>> batches; // list of batches of rules
    
    public RSLGenome() {
        axiom = new Sequence();
        batches = new ArrayList<ArrayList<Rule>>();
    }
    
    /**
    * Randomly generates a new genome.
    * @param s a rough measure of the complexity of the genome to generate
    * @return 
    */
    public RSLGenome(int s) {
        // TODO
    }
    
    /**
     * Creates a copy of this genome.
     * @return deep copy of this genome
     */
    public RSLGenome copy() {
        RSLGenome g = new RSLGenome();
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
    * @return 
    */
    public RSPhenotype toPhenotype() {
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
        return new RSPhenotype(inter.bases);
    }
    
    private void modify(Sequence s) {
        // TODO!
    }
    
    /**
     * Performs some mutation on this genome.
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
    public void mutate() { // TODO
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
        } else {
            // TODO batch-level modification
            double op = Math.random();
            if (op < 0.2) {
                // perform a substitution
                
            } else if (op < 0.4) {
                // delete a rule
            } else if (op < 0.6) {
                // duplicate a rule
            } else if (op < 0.8) {
                // switch two rules
            } else {
                // move a rule to another batch
            }
        }
    }
    
    /**
     * Crosses two genomes over to produce some offspring.
     * 
     * Does not apply mutations to the result.
     * @param g
     * @return
     */
    public RSLGenome crossover(RSLGenome g) {
        // TODO
        return copy();
    }
}
