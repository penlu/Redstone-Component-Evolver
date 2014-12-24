/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluation;

import evolver.CombinatorialFunction;
import evolver.RSPhenotype;
import java.util.ArrayList;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class RSEvaluation implements Evaluation<RSPhenotype> {
    CombinatorialFunction comb;
    
    public RSEvaluation(CombinatorialFunction c) {
        comb = c;
    }
    
    public double evaluate(RSPhenotype p) {
        // evolve for correct number of inputs and outputs
        double inputPenalty = -100 * Math.abs(p.getInputs().size() - comb.countInputs());
        double outputPenalty = -100 * Math.abs(p.getOutputs().size() - comb.countOutputs());
        
        // evaluate iff correct number of inputs and outputs
        if (inputPenalty == 0 && outputPenalty == 0) {
            // pick random inputs
            int[] inputs = new int[comb.countInputs()];
            ArrayList<Boolean> boolins = new ArrayList<Boolean>(comb.countInputs());
            for (int i = 0; i < inputs.length; i++) {
                if (Math.random() > 0.5) {
                    inputs[i] = 15;
                    boolins.add(true);
                } else {
                    inputs[i] = 0;
                    boolins.add(false);
                }
            }
            
            // calculate expected outputs
            ArrayList<Boolean> outputs = comb.compute(boolins);
            
            // make new simulation
            Simulation sim = new Simulation(p);
            
            // step ten times
            for (int s = 0; s < 10; s++) {
                sim.step(inputs);
            }
            
            // start comparing outputs to expected outputs; score a point per correct answer per step
            int points = 0;
            for (int s = 0; s < 20; s++) {
                // get outputs
                int[] out = sim.outputs();
                
                // check against expected outputs
                for (int o = 0; o < out.length; o++) {
                    if (outputs.get(o) == (out[o] > 0)) {
                        points++;
                    }
                }
            }
            
            return points;
        } else {
            return inputPenalty + outputPenalty;
        }
    }
}
