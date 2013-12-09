/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evolver;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Stores a set of combinatorial binary functions.  Provides methods for 
 * evaluating these functions on lists of operands.
 * 
 * Functions are composed of alphabetical characters a-z and operations *, +, 
 * and !.  Characters a-z represent the operands.  When an ordered list of 
 * inputs is given, the inputs are made to correspond one-to-one in order to 
 * the characters in each expression.  For example if the expressions contain 
 * the characters cfip, the four inputs {T, F, T, T} would cause c -> T, f -> F, 
 * i -> T, p -> T when the expressions are being evaluated.
 * @author Eric Lu <penlume@gmail.com>
 */
public class CombinatorialFunction {
    private ArrayList<String> funcs;    // list of functions this object contains
    private SortedSet<Character> vars;  // ordered list of variables in this object's functions
    
    private static boolean checkFunc(String f) {
        int height = 0; // current stack height
        for (int c = 0; c < f.length(); c++) {
            char ch = f.charAt(c);
            
            // see how given instruction affects stack height
            if ('a' <= ch && ch <= 'z') {
                height++;
            } else if ('+' == ch || '*' == ch) {
                if (height < 2) {
                    // insufficient operands: bad expression
                    return false;
                }
                height--; // takes two, puts one
            } else if ('!' == ch) {
                if (height < 1) {
                    return false;
                }
            }
        }
        
        return height == 1; // should leave single value as final output
    }
    
    public CombinatorialFunction(ArrayList<String> funcs) {
        this.funcs = funcs;
        vars = new TreeSet<Character>(); // make variable list
        
        // check functions for correctness
        for (int f = 0; f < funcs.size(); f++) {
            if (!checkFunc(funcs.get(f))) {
                funcs.remove(f); // bad function: do not attempt to evaluate
            }
        }
        
        // populate variable list
        for (int f = 0; f < funcs.size(); f++) {
            for (int c = 0; c < funcs.get(f).length(); c++) {
                // compile list of letters contained
                char ch = funcs.get(f).charAt(c);
                if ('a' <= ch && ch <= 'z') {
                    vars.add(ch);
                }
            }
        }
    }
    
    /**
     * Computes all the combinatorial functions contained by this object on a 
     * given set of inputs, treating non-given inputs as false.
     * @param inputs
     * @return 
     */
    public ArrayList<Boolean> compute(ArrayList<Boolean> inputs) {
        ArrayList<Boolean> outputs = new ArrayList<Boolean>();
        for (int f = 0; f < funcs.size(); f++) {
            // execute computation of output f using stack for RPN
            String prog = funcs.get(f);
            Stack<Boolean> stack = new Stack<Boolean>();
            for (int i = 0; i < prog.length(); i++) {
                switch (prog.charAt(i)) {
                    case '*':
                        stack.push(stack.pop() && stack.pop());
                        break;
                    case '+':
                        stack.push(stack.pop() || stack.pop());
                        break;
                    case '!':
                        stack.push(!stack.pop());
                        break;
                    default:
                        // get input number for this char from character list
                        int in = vars.headSet(prog.charAt(i)).size();
                        if (in < 0 || in > inputs.size()) {
                            stack.push(false);
                        } else {
                            stack.push(inputs.get(in));
                        }
                        break;
                }
            }
            outputs.add(stack.pop());
        }
        
        return outputs;
    }
    
    public int countInputs() {
        return vars.size();
    }
    
    public int countOutputs() {
        return funcs.size();
    }
}
