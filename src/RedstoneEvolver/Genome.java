/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RedstoneEvolver;

import java.util.*;

/**
 * Stores the genome of a component: a program that produces the component,
 * when it is run.
 *
 * @author Access
 */
public class Genome {
    private static enum Mutation {
        POINT, INSERTION, DELETION, DUPLICATION, SUBSTITUTION, FACTORIZATION;

        static Mutation getRandomMutation() {
            return values()[Randomizer.getInt(values().length)];
        }
    }

    private ArrayList<Instruction> insts; // list of instructions

    private ArrayList<Genome> subroutines; // list of subroutines referenced by this genome

    /**
     * Generates a random genome of length n
     * @param n
     */
    public Genome(int n) {
        insts = new ArrayList<Instruction>(n);
        for (int i = 0; i < n; i++) {
            insts.set(i, Instruction.getRandomInstruction());
        }

        subroutines = new ArrayList<Genome>();
    }

    /**
     * returns length of this genome in instructions (does not account for subroutines)
     * @return
     */
    public int length() {
        return insts.size();
    }

    /**
     * counts number of instructions under this genome: not including repeated subroutine calls
     */
    public int instCount() {
        int total = length();
        for (Genome subroutine : subroutines) {
            total += subroutine.instCount();
        }

        return total;
    }

    /**
     * recursively counts genome length, including subroutines
     * @return
     */
    public int totalLength() {
        int l = this.length();
        for (int i = 0; i < insts.length; i++) {
            if (insts.get(i).isSubroutine()) {
                l--;
                l += insts.get(i).getSubroutine().totalLength();
            }
        }

        return l;
    }

    public Instruction[] getInstructions() {
        Instruction[] instructions = new Instruction[insts.size()];
        for (int i = 0; i < insts.size(); i++) {
            instructions[i] = insts.get(i);
        }

        return instructions;
    }

    /**
     * probably good for crossover reproduction
     * @return
     */
    public Genome[] getSubroutines() {
        Genome[] subroutineList = new Genome[subroutines.size()];
        for (int i = 0; i < subroutines.size(); i++) {
            subroutineList[i] = subroutines.get(i);
        }

        return subroutineList;
    }

    public GeneIterator getIterator() {
        return new GeneIterator(insts);
    }

    /**
     * Insert a series of instructions at the specified location, shifting contents up to fit
     * @param loc
     * @param insert
     */
    private void insert(int loc, Instruction[] insert) {
        
    }

    /**
     * Remove a series of instructions beginning with the one at the specified location
     * @param loc
     * @param num
     * @return
     */
    private Instruction[] remove(int loc, int num) {

    }

    /**
     * Retrieves the series of instructions beginning at a specified location
     * @param loc
     * @param num
     * @return
     */
    private Instruction[] substring(int loc, int num) {

    }

    /**
     * Returns this genome after a mutation has been applied.
     */
    public void mutate() { // TODO mutation algorithm
        // pick random loc to mutate
        double[] subweights = new double[subroutines.size() + 1];
        subweights[0] = this.length();
        for (int i = 0; i < subroutines.size(); i++) {
            subweights[i + 1] = subroutines.get(i).length();
        }

        int selected = Randomizer.pick(subweights);
        if (selected == 0) { // mutate this genome
            // pick random mutation type: point, insert, delete, duplicate, substitute, factorize
            Mutation mut = Mutation.getRandomMutation();
            switch (mut) {
                case POINT:
                    // pick random point in this genome; replace it with mutated
                    int pos = Randomizer.getInt(insts.size());
                    insts.set(pos, insts.get(pos).mutate());
                    break;
                case INSERTION:

                    break;
                case DELETION:

                    break;
                case DUPLICATION:

                    break;
                case SUBSTITUTION:
                    // needs to produce subroutines
                    break;
                case FACTORIZATION:
                    // needs to garbage collect subroutines (pending better data structures)
                    break;
            }

        } else { // delegate to sub-genome
            subroutines.get(selected - 1).mutate();
        }
    }

    // TODO implement quick and easy instruction insert/removal/substring operations

    public Genome replicate() {

    }

    public Genome crossover(Genome partner) {

    }
}
