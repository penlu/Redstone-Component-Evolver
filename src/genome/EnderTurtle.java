/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.Block;
import evolver.Coord;
import evolver.RSPhenotype;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class EnderTurtle {
    /**
     * Process a sequence, running it as a program to construct a new phenotype.
     * @param s sequence program to run
     * @return phenotype constructed from sequence
     */
    public static RSPhenotype process(Sequence<LBase> s) {
        TreeMap<Coord, Block> parts = new TreeMap<Coord, Block>();
        ArrayList<LBase> bases = s.getElements();
        
        Coord pos = new Coord(0, 0, 0);
        
        for (int i = 0; i < bases.size(); i++) {
            parts.put(pos, bases.get(i).block);
            pos = pos.add(new Coord(bases.get(i).move));
        }
        
        // TODO stack/destack? add input/output? pattern-matching these??
        
        // TODO somehow fix non-sensible blocks
        
        return new RSPhenotype(parts, new ArrayList<Coord>(), new ArrayList<Coord>());
    }
}
