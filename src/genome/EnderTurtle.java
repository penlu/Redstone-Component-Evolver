/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.Block;
import evolver.Coord;
import evolver.RSPhenotype;
import java.util.ArrayList;
import java.util.Map;
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
    public static RSPhenotype process(Sequence<LModule> s) {
        TreeMap<Coord, Block> parts = new TreeMap<Coord, Block>();
        ArrayList<LModule> bases = s.getElements();
        
        Coord pos = new Coord(0, 0, 0);
        
        // follow the given instructions
        for (int i = 0; i < bases.size(); i++) {
            parts.put(pos, bases.get(i).block);
            pos = pos.add(new Coord(bases.get(i).move));
        }
        
        // TODO input, output, stack, destack...?? modify Rule and Sequence!
        
        // fix blocks that need to be attached to something
        for (Map.Entry<Coord, Block> entry : parts.entrySet()) {
            Coord mount = entry.getValue().needsMount();
            
            // check if mount missing
            Coord mountpos = entry.getKey().add(mount);
            if (mount != null && !parts.get(mountpos).canMount()) {
                if (parts.get(entry.getKey()).id == Block.BlockID.AIR) {
                    // if air space, fill
                    parts.put(mountpos, new Block(Block.BlockID.BLOCK, 0));
                } else {
                    // delete this block
                    parts.put(entry.getKey(), new Block(Block.BlockID.AIR, 0));
                }
            }
        }
        
        return new RSPhenotype(parts, new ArrayList<Coord>(), new ArrayList<Coord>());
    }
}
