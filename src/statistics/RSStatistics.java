/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics;

import genome.RSLGenome;

/**
 * Carries statistics for redstone evolver.
 * @author Eric Lu <penlume@gmail.com>
 */
public class RSStatistics implements Statistics {
    private double avg; // average fitness
    private double std; // standard deviation of fitness
    private RSLGenome gen; // best genome this generation
    
    public RSStatistics(double avg, double std, RSLGenome gen) {
        this.avg = avg;
        this.std = std;
        this.gen = gen;
    }
    
    public String getText(int verbosity) { // verbosity doesn't matter
        StringBuilder builder = new StringBuilder();
        builder.append("average fitness: " + avg + "\n");
        builder.append("std dev fitness: " + std + "\n");
        builder.append("best genome: \n");
        // TODO append best genome
        
        return builder.toString();
    }
}
