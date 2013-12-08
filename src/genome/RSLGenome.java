/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.RSPhenotype;
import java.util.ArrayList;

/**
 * Contains methods for a genotype representation.
 * 
 * This implementation does an L-system genome, so it stores a starting axiom 
 * and a list of batches of rules to apply.
 * 
 * @author Eric Lu <penlume@gmail.com>
 */
public class RSLGenome implements Genome<RSLGenome, RSPhenotype> {
    ArrayList<Rule> rules; // list of rules
    ArrayList<EnderTurtle.AbstractModule> hierarchy; // abstraction hierarchy, decreasing order of abstraction
    
    public RSLGenome() {
        rules = new ArrayList<Rule>();
        hierarchy = new ArrayList<EnderTurtle.AbstractModule>();
        
        // add axiomatic symbol to hierarchy
        hierarchy.add(new EnderTurtle.AbstractModule(hierarchy));
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
        // TODO copy hierarchy into g
        
        g.rules = new ArrayList<Rule>(rules.size());
        for (int i = 0; i < rules.size(); i++) {
            g.rules.add(rules.get(i).copy());
        }
        
        return g;
    }
    
    /**
    * Translates this genome into a phenotype.
    * @return 
    */
    public RSPhenotype toPhenotype() {
        // translate axiom to final intertype
        Sequence<Module> inter = new Sequence<Module>(1, hierarchy.get(0)); // axiomatic symbol
        boolean change = true;
        while (change) {
            Sequence<Module> original = inter.copy();
            for (int i = 0; i < rules.size(); i++) {
                Rule rule = rules.get(i);
                rule.apply(inter);
            }
            change = !original.equals(inter);
        }
        
        // convert intertype sequence to phenotype
        return EnderTurtle.process(inter);
    }
    
    /**
     * Performs some mutation on this genome.
     * 
     * Mutations may be one of the following:
     *  - rule modification
     *  - rule substitution/factorization
     * 
     * Modification encompasses one of:
     *  - single base insertion/deletion
     *  - run insertion/deletion/duplication/reversal
     * 
     * @param g
     * @return
     */
    public void mutate() { // TODO
        // select rule to modify
        int rulenum = (int)(Math.random() * rules.size());
        
        // select a modification TODO
        
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
