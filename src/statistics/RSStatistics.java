/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics;

import evolver.RSPhenotype;
import genome.RSLGenome;

/**
 * Carries statistics for redstone evolver.
 * @author Eric Lu <penlume@gmail.com>
 */
public class RSStatistics implements Statistics {
    private final int num;
    private final double avg; // average fitness
    private final double std; // standard deviation of fitness
    private final RSLGenome gen; // best genome this generation
    private final RSPhenotype phn; // its genotype
    
    public RSStatistics(int num, double avg, double std, RSLGenome gen, RSPhenotype phn) {
        this.num = num;
        this.avg = avg;
        this.std = std;
        this.gen = gen;
        this.phn = phn;
    }
    
    public String getText(int verbosity) { // verbosity doesn't matter for this impl
        StringBuilder builder = new StringBuilder();
        builder.append("population size: " + num + "\n");
        builder.append("average fitness: " + avg + "\n");
        builder.append("std dev fitness: " + std + "\n");
        builder.append("most fit genome: \n");
        builder.append(gen.toString());
        builder.append("as a phenotype: \n");
        builder.append(phn.toString());
        
        return builder.toString();
    }
}
