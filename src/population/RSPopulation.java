/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package population;

import evaluation.RSEvaluation;
import evolver.RSPhenotype;
import genome.RSLGenome;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import statistics.RSStatistics;

/**
 * Maintains a population of candidates.
 * @author Eric Lu <penlume@gmail.com>
 */
public class RSPopulation implements Population<RSStatistics> {
    ArrayList<Candidate> population; // all of the candidates stored in this population
    private RSEvaluation eval; // the evaluation object used by this population
    
    /**
     * One candidate in the population.  Precomputes phenotypes and fitness.
     */
    private class Candidate {
        final RSLGenome genome;
        final RSPhenotype phenome;
        double fitness;
        int evaluations;
        
        Candidate (RSLGenome g) {
            genome = g;
            phenome = g.toPhenotype();
            fitness = eval.evaluate(phenome);
        }
        
        void reevaluate() {
            fitness = (evaluations * fitness + eval.evaluate(phenome)) / (++evaluations);
        }
    }
    
    /**
     * Randomly generate size genomes and stick them in a population together.
     * @param size 
     */
    public RSPopulation(int size, RSEvaluation e) {
        eval = e;
        population = new ArrayList<Candidate>(size);
        
        for (int i = 0; i < size; i++) {
            // produce new candidate
            Candidate c = new Candidate(new RSLGenome(10));
            
            // insert this to sort population in decreasing order of fitness
            boolean inserted = false;
            for (int ins = 0; ins < population.size(); ins++) {
                if (c.fitness >= population.get(ins).fitness) {
                    population.add(ins, c);
                    inserted = true;
                    break;
                }
            }
            // add at end if not added
            if (!inserted) {
                population.add(c);
            }
        }
    }
    
    public void generation() {
        // reevaluate like ten times per
        for (int i = 0; i < population.size(); i++) {
            for (int n = 0; n < 10; n++) {
                population.get(i).reevaluate();
            }
        }
        
        // resort
        Collections.sort(population, new Comparator<Candidate>() {
            public int compare(Candidate a, Candidate b) {
                return (int)Math.signum(b.fitness - a.fitness);
            }
        });
        
        // 25 random swaps?
        for (int i = 0; i < population.size() / 4; i++) {
            int a = (int)(Math.random() * population.size());
            int b = (int)(Math.random() * population.size());
            Collections.swap(population, a, b);
        }
        
        // take out three worst
        for (int i = 0; i < 3; i++) {
            population.remove(population.size() - 1);
        }
        
        // select from top half of remaining population
        ArrayList<RSLGenome> selections = new ArrayList<RSLGenome>();
        for (int i = 0; i < 3; i++) {
            selections.add(population.get((int)((Math.random() + 1) * population.size() / 2)).genome);
        }
        
        // add back with mutations
        for (int i = 0; i < selections.size(); i++) {
            RSLGenome mut = selections.get(i).copy();
            mut.mutate();
            Candidate c = new Candidate(mut);
            
            population.add(c);
        }
    }
    
    public RSStatistics getOutput() {
        ArrayList<Double> fitnesses = new ArrayList<Double>();
        for (int i = 0; i < population.size(); i++) {
            fitnesses.add(population.get(i).fitness);
        }
        
        // calculate average
        double sum = 0;
        for (int i = 0; i < fitnesses.size(); i++) {
            sum += fitnesses.get(i);
        }
        double average = sum / fitnesses.size();
        
        // calculate standard deviation
        double sumDevSqd = 0;
        for (int i = 0; i < fitnesses.size(); i++) {
            sumDevSqd += Math.pow(fitnesses.get(i) - average, 2);
        }
        double stdDev = Math.sqrt(sumDevSqd / fitnesses.size());
        
        return new RSStatistics(population.size(), average, stdDev, population.get(0).genome, population.get(0).phenome);
    }
}
