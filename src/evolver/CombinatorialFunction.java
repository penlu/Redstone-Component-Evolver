/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evolver;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.Stack;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class CombinatorialFunction {
    private ArrayList<String> funcs;
    private SortedSet<Character> vars;
    
    public CombinatorialFunction(ArrayList<String> funcs) {
        this.funcs = funcs;
        
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
                        // get input number from character list
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
