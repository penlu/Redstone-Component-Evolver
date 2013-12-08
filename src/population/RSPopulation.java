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
        final double fitness;
        
        Candidate (RSLGenome g) {
            genome = g;
            phenome = g.toPhenotype();
            fitness = eval.evaluate(phenome);
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
        // take out three worst
        for (int i = 0; i < 3; i++) {
            population.remove(population.size() - 1);
        }
        
        // select from remaining population
        ArrayList<RSLGenome> selections = new ArrayList<RSLGenome>();
        for (int i = 0; i < 3; i++) {
            selections.add(population.get((int)(Math.random()*population.size())).genome);
        }
        
        // add back with mutations
        for (int i = 0; i < selections.size(); i++) {
            RSLGenome mut = selections.get(i).copy();
            mut.mutate();
            Candidate c = new Candidate(mut);
            
            // add at proper location to maintain sorted
            boolean inserted = false;
            if (c.fitness >= population.get(i).fitness) {
                population.add(i, c);
                inserted = true;
                break;
            }
            // add at end if not added
            if (!inserted) {
                population.add(c);
            }
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
        
        return new RSStatistics(average, stdDev, population.get(0).genome);
    }
}
