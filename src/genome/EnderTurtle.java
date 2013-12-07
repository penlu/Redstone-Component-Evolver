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
    * Stores a base for the L-system genome.
    * 
    * LModule objects allow for equals comparisons with arbitrary behavior and 
    * parametrized 0-context L-systems.
    * 
    * This one stores a part, part data, and the direction to move after the 
    * part has been placed.
    */
    public static interface Module {
        @Override
        public boolean equals(Object o);
    }
    
    public static class ABSTRACT implements Module {
        public final int level; // abstraction level of this module

        public ABSTRACT(int level) {
            this.level = level;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ABSTRACT)) {
                return false;
            }

            ABSTRACT am = (ABSTRACT)o;
            return level == am.level;
        }
    }
    
    public static class BLOCK implements Module {
        public final Block block;
        public final int move;
        
        public BLOCK(Block b, int m) {
            block = b;
            move = m;
        }
        
        public boolean equals(Object o) {
            return false;
        }
    }
    
    public static class PUSH implements Module {
        public boolean equals(Object o) {
            return false;
        }
    }
    
    public static class POP implements Module {
        public boolean equals(Object o) {
            return false;
        }
    }
    
    public static class INPUT implements Module {
        public boolean equals(Object o) {
            return false;
        }
    }
    
    public static class OUTPUT implements Module {
        public boolean equals(Object o) {
            return false;
        }
    }

    /**
     * Process a sequence, running it as a program to construct a new phenotype.
     * @param s sequence program to run
     * @return phenotype constructed from sequence
     */
    public static RSPhenotype process(Sequence<Module> s) {
        ArrayList<Module> bases = s.getElements();
        
        TreeMap<Coord, Block> parts = new TreeMap<Coord, Block>();
        ArrayList<Coord> inputs = new ArrayList<Coord>();
        ArrayList<Coord> outputs = new ArrayList<Coord>();
        
        Coord pos = new Coord(0, 0, 0);
        ArrayList<Coord> stack = new ArrayList<Coord>();
        
        // follow the given instructions
        for (int i = 0; i < bases.size(); i++) {
            Module inst = bases.get(i);
            
            if (inst instanceof BLOCK) {
                BLOCK binst = (BLOCK)inst;
                parts.put(pos, binst.block);
                
                // clear inputs/outputs at location
                for (int j = 0; j < inputs.size(); j++) {
                    if (inputs.get(j).equals(pos)) {
                        inputs.remove(j);
                    }
                    if (outputs.get(j).equals(pos)) {
                        outputs.remove(j);
                    }
                }
                
                pos = pos.add(new Coord(binst.move));
            } else if (inst instanceof PUSH) {
                stack.add(pos);
            } else if (inst instanceof POP) {
                if (stack.size() > 0) {
                    pos = stack.remove(stack.size() - 1);
                }
            } else if (inst instanceof INPUT) {
                inputs.add(pos);
                for (int j = 0; j < inputs.size() - 1; j++) {
                    if (inputs.get(j).equals(pos)) {
                        inputs.remove(j);
                    }
                }
            } else if (inst instanceof OUTPUT) {
                outputs.add(pos);
                for (int j = 0; j < outputs.size() - 1; j++) {
                    if (outputs.get(j).equals(pos)) {
                        outputs.remove(j);
                    }
                }
            }
        }
        
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
        
        return new RSPhenotype(parts, inputs, outputs);
    }
}
