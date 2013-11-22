/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genome;

import population.Part;
import evolver.Randomizer;

/**
 * Single genetic instruction; specifies a part and direction to move after part placement.
 * @author Access
 */
public class Instruction {
    private Part part; // part to place
    private Coord dir; // direction to move after placement

    private boolean subjump; // is this instruction a subroutine jump?
    private LGenome subroutine; // reference to subroutine contained by this instruction

    public static Instruction getRandomInstruction() {
        return new Instruction(Part.getRandomPart(), Coord.getRandomCardinal());
    }

    public Instruction(Part part, Coord dir) { // TODO multiton
        subjump = false;
        this.part = part;
        this.dir = dir;
    }

    public Instruction(LGenome subroutine) { // construct an instruction holding a subroutine
        subjump = true;
        this.subroutine = subroutine;
    }

    public Part getPart() {
        return part;
    }

    public Coord getDir() {
        return dir;
    }

    public boolean isSubroutine() {
        return subjump;
    }

    public LGenome getSubroutine() {
        return subroutine;
    }

    /**
     * returns this instruction with one mutation applied (useful for point mutations)
     * @return
     */
    public Instruction mutate() {
        if (!subjump) {
            if (Randomizer.get() < 0.5) {
                return new Instruction(Part.getRandomPart(), dir);
            } else {
                return new Instruction(part, Coord.getRandomCardinal());
            }
        } else {
            return this;
        }
    }
}
