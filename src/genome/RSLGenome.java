/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.RSPhenotype;
import genome.EnderTurtle.AbstractModule;
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
    ArrayList<AbstractModule> hierarchy; // abstraction hierarchy, decreasing order of abstraction
    
    public static int factorial(int k) {
        int res = 1;
        for (int i = 1; i < k; i++) {
            res *= i;
        }
        
        return res * k;
    }
    
    public static int poisson(double lambda, double rand) {
        int count = 0;
        double factor = Math.exp(-lambda);
        do {
            rand -= Math.pow(lambda, count) / factorial(count) * factor;
        } while (rand > 0);
        
        return count;
    }
    
    public RSLGenome() {
        rules = new ArrayList<Rule>();
        hierarchy = new ArrayList<AbstractModule>();
        
        // add axiomatic symbol to hierarchy
        hierarchy.add(new AbstractModule(hierarchy));
    }
    
    /**
    * Randomly generates a new genome.
    * @param s a rough measure of the complexity of the genome to generate
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
        
        // copy hierarchy into g
        g.hierarchy = new ArrayList<AbstractModule>();
        for (int i = 0; i < hierarchy.size(); i++) {
            hierarchy.add(new AbstractModule(g.hierarchy));
        }
        
        // copy rules into g
        g.rules = new ArrayList<Rule>(rules.size());
        for (int i = 0; i < rules.size(); i++) {
            Rule copy = rules.get(i).copy();
            g.rules.add(copy);
            
            // TODO repair abstract symbols in copied rules to new hierarchy
            
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
     *  - run insertion/deletion/duplication
     */
    public void mutate() {
        if (Math.random() < 0.8) {
            // select rule to modify
            Rule rule = rules.get((int)(Math.random() * rules.size()));
            mutateRule(rule);
        } else {
            doSubFac();
        }
    }

    private void doSubFac() {
        // select substitution/factorization
        int subfac = (int)(Math.random() * 2);
        
        // select which rule to substitute/factorize
        int rulen = (int)(Math.random() * rules.size() - 1) + subfac;
        
        if (subfac == 0) { // factorization
            // select random contiguous sequence
            int modpos = (int)(Math.random() * rules.get(rulen).rhs.getElements().size());
            int modsize = poisson(3, Math.random() * 0.6 + 0.4);
            
            // produce new abstract module for new rule lhs
            AbstractModule abs = new AbstractModule(hierarchy);
            
            // insert new abstract module after this lhs in hierarchy
            for (int i = 0; i < hierarchy.size(); i++) {
                if (hierarchy.get(i) == rules.get(rulen).lhs) {
                    hierarchy.add(i + 1, abs);
                    break;
                }
            }
            
            // pull sequence out to form new rule
            Sequence<Module> newrule = rules.get(rulen).rhs.remove(modpos, modsize);
            
            // replace with new abstract module
            rules.get(rulen).rhs.insert(abs, modpos);
            
            // also find all equal sequences
            int match = 0;
            ArrayList<Integer> matches = new ArrayList<Integer>();
            while (match < rules.get(rulen).rhs.getElements().size()) {
                match = rules.get(rulen).rhs.match(newrule, match);
                matches.add(match);
            }
            
            // pull out and replace equal sequences
            for (int i = matches.size() - 1; i >= 0; i--) { // backwards to avoid disrupting indices
                rules.get(rulen).rhs.remove(i, modsize);
                rules.get(rulen).rhs.insert(abs, i);
            }
            
            // store newly formed rule
            rules.add(new Rule(abs, newrule));
        } else { // substitution
            // find abstract symbol preceding this lhs, removing this lhs from hierarchy
            AbstractModule prec = null;
            for (int i = 0; i < hierarchy.size(); i++) {
                if (hierarchy.get(i) == rules.get(rulen).lhs) {
                    prec = hierarchy.get(i - 1);
                    hierarchy.remove(i);
                    break;
                }
            }
            
            // find rule with prec abstract symbol so preceding this rule
            Rule target = null;
            for (int i = 0; i < rules.size(); i++) {
                if (rules.get(i).lhs == prec) {
                    target = rules.get(i);
                    break;
                }
            }
            
            // apply this rule to rhs of previous rule
            rules.get(rulen).apply(target.rhs);
        }
    }

    private void mutateRule(Rule rule) {
        // select size of modification TODO don't permit edge runoff
        int modsize = poisson(1, Math.random() / 2 + 0.5);
        
        // select type of modification
        int modchoice = (int)(Math.random() * 3);
        
        switch (modchoice) {
            case 0: // insertion
                // location: random quantity leq length
                int insertloc = (int)(Math.random() * rule.rhs.getElements().size() + 1);
                
                // make list of insertable symbols
                ArrayList<Module> syms = new ArrayList<Module>(); // concrete symbols and symbols less abstract than LHS
                syms.add(null);
                boolean encountered = false;
                for (int i = 0; i < hierarchy.size(); i++) {
                    if (encountered) {
                        syms.add(hierarchy.get(i));
                    } else if (hierarchy.get(i) == rule.lhs) {
                        encountered = true;
                    }
                }
                
                // generate random sequence
                Sequence<Module> seq = new Sequence<Module>();
                for (int i = 0; i < modsize; i++) {
                    Module mod = syms.get((int)(Math.random() * syms.size()));
                    if (mod == null) {
                        seq.insert(EnderTurtle.randomConcreteModule(), seq.getElements().size());
                    } else {
                        seq.insert(syms.get(i), seq.getElements().size());
                    }
                }
                
                rule.rhs.insert(seq, insertloc);
                break;
            case 1: // deletion
                int deleteloc = (int)(Math.random() * rule.rhs.getElements().size());
                rule.rhs.remove(deleteloc, modsize);
                break;
            case 2: // duplication
                int duploc = (int)(Math.random() * rule.rhs.getElements().size());
                rule.rhs.insert(rule.rhs.subsequence(duploc, modsize), duploc);
                break;
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
