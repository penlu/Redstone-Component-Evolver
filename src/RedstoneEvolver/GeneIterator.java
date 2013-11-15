/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RedstoneEvolver;

/**
 * Returns the instructions of a genome in sequence.  Transparently accounts for subroutines.
 * @author Access
 */
public class GeneIterator {
    private Instruction[] insts;
    private int loc;

    private GeneIterator curSubroutine; // holds the iterator for the current subroutine

    public GeneIterator(Instruction[] insts) {
        this.insts = insts;
        loc = 0;
    }

    /**
     * return the next instruction, accounting for subroutines of course
     * @return
     */
    public Instruction next() {
        Instruction curInst;
        if (loc < insts.length) {
            curInst = insts[loc];
        } else {
            return null;
        }

        if (!curInst.isSubroutine()) {
            loc++;
            return curInst;
        } else { // handle current subroutine
            if (curSubroutine == null) { // just starting a subroutine: get iterator
                curSubroutine = curInst.getSubroutine().getIterator();
            }

            Instruction subnext = curSubroutine.next();
            if (subnext != null) {
                return subnext; // pipe out contents of subroutine
            } else { // encountered empty/finished subroutine; move on
                curSubroutine = null;
                loc++;
                return this.next();
            }
        }
    }
}
