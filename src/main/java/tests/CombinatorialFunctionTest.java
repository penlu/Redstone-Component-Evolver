/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import evolver.CombinatorialFunction;
import java.util.ArrayList;

/**
 * Test the combinatorial function system.
 * @author Eric Lu <penlume@gmail.com>
 */
public class CombinatorialFunctionTest {
    CombinatorialFunction funcs;
    
    public CombinatorialFunctionTest() {
        ArrayList<String> tests = new ArrayList<String>();
        
        tests.add("+"); // incorrectly formatted
        tests.add("a"); // returns input
        tests.add("ac+"); // does logical OR
        tests.add("ac+ac*!*"); // does logical XOR
        tests.add("ac+ac**!*"); // incorrectly formatted
        
        funcs = new CombinatorialFunction(tests);
    }
    
    public boolean test() {
        // expected three tests to parse correctly
        if (funcs.countOutputs() != 3) {
            return false;
        }
        
        // expected two inputs
        if (funcs.countInputs() != 2) {
            return false;
        }
        
        // test evaluation
        for (int i = 0; i < 10; i++) {
            // generate new set of inputs
            ArrayList<Boolean> inputs = new ArrayList<Boolean>();
            for (int in = 0; in < 2; in++) {
                inputs.add(Math.random() < 0.5);
            }
            
            // test these inputs
            ArrayList<Boolean> outputs = funcs.compute(inputs);
            
            if (outputs.get(0) != inputs.get(0)) {
                return false;
            }
            if (outputs.get(1) != (inputs.get(0) || inputs.get(1))) {
                return false;
            }
            if (outputs.get(2) != (inputs.get(0) ^ inputs.get(1))) {
                return false;
            }
        }
        
        // evaluation on too few variables
        ArrayList<Boolean> input = new ArrayList<Boolean>();
        input.add(true);
        ArrayList<Boolean> outputs = funcs.compute(input);
        if (outputs.get(0) != true) {
            return false;
        }
        if (outputs.get(1) != true) {
            return false;
        }
        if (outputs.get(2) != true) {
            return false;
        }
        
        // evaluation on too many variables
        input = new ArrayList<Boolean>();
        input.add(true); input.add(false); input.add(true);
        outputs = funcs.compute(input);
        if (outputs.get(0) != true) {
            return false;
        }
        if (outputs.get(1) != true) {
            return false;
        }
        if (outputs.get(2) != true) {
            return false;
        }
        
        return true;
    }
}
