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
    public static class AbstractModule implements Module, Comparable<AbstractModule> {
        public final ArrayList<AbstractModule> hierarchy; // list of abstract modules in order of decreasing abstraction

        public AbstractModule(ArrayList<AbstractModule> h) {
            hierarchy = h;
        }

        public boolean equals(Object o) {
            if (!(o instanceof AbstractModule)) {
                return false;
            }

            AbstractModule am = (AbstractModule)o;
            return this == am;
        }
        
        public int compareTo(AbstractModule am) {
            if (am.equals(this)) {
                return 0;
            }
            for (int i = 0; i < hierarchy.size(); i++) {
                if (hierarchy.get(i) == am) {
                    return -1; // am is more alpha on hierarchy; this item is less abstract; higher index -> more concrete
                }
                if (hierarchy.get(i) == this) {
                    return 1; // this is more alpha on hierarchy; lower index for this item -> more abstract
                }
            }
            return 1; // not on hierarchy: assumed infinitely unabstract
        }
        
        public String toString() {
            // find abstract position in hierarchy
            int pos = hierarchy.size();
            for (int i = 0; i < hierarchy.size(); i++) {
                if (hierarchy.get(i) == this) {
                    pos = i;
                    break;
                }
            }
            
            return "ABS(" + pos + ")";
        }
    }
    
    public static class BlockModule implements Module {
        public final Block block;
        public final int move;
        
        public BlockModule(Block b, int m) {
            block = b;
            move = m;
        }
        
        public boolean equals(Object o) {
            return false;
        }
        
        public String toString() {
            return "B(" + block.id + ", " + block.data + ", " + move + ")";
        }
    }
    
    public static class PushModule implements Module {
        public boolean equals(Object o) {
            return false;
        }
        
        public String toString() {
            return "PUSH";
        }
    }
    
    public static class PopModule implements Module {
        public boolean equals(Object o) {
            return false;
        }
        
        public String toString() {
            return "POP";
        }
    }
    
    public static class InputModule implements Module {
        public boolean equals(Object o) {
            return false;
        }
        
        public String toString() {
            return "IN";
        }
    }
    
    public static class OutputModule implements Module {
        public boolean equals(Object o) {
            return false;
        }
        
        public String toString() {
            return "OUT";
        }
    }
    
    /**
     * Returns a randomly chosen concrete module.
     * @return 
     */
    public static Module randomConcreteModule() {
        double choice = Math.random();
        if (choice < 0.5) {
            return new BlockModule(Block.randomBlock(), (int)(Math.random() * 6));
        } else if (choice < 0.7) {
            return new PushModule();
        } else if (choice < 0.9) {
            return new PopModule();
        } else if (choice < 0.95) {
            return new InputModule();
        } else {
            return new OutputModule();
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
            
            if (inst instanceof BlockModule) {
                BlockModule binst = (BlockModule)inst;
                parts.put(pos, binst.block);
                
                // clear inputs/outputs at location
                for (int j = 0; j < inputs.size(); j++) {
                    if (inputs.get(j).equals(pos)) {
                        inputs.remove(j);
                    }
                }
                for (int j = 0; j < outputs.size(); j++) {
                    if (outputs.get(j).equals(pos)) {
                        outputs.remove(j);
                    }
                }
                
                pos = pos.add(new Coord(binst.move));
            } else if (inst instanceof PushModule) {
                stack.add(pos);
            } else if (inst instanceof PopModule) {
                if (stack.size() > 0) {
                    pos = stack.remove(stack.size() - 1);
                }
            } else if (inst instanceof InputModule) {
                inputs.add(pos);
                for (int j = 0; j < inputs.size() - 1; j++) {
                    if (inputs.get(j).equals(pos)) {
                        inputs.remove(j);
                    }
                }
            } else if (inst instanceof OutputModule) {
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
            
            if (mount != null) {
                Coord mountpos = entry.getKey().add(mount);
                
                // check if mount missing
                if (parts.get(mountpos) == null || !parts.get(mountpos).canMount()) {
                    if (parts.get(entry.getKey()).id == Block.BlockID.AIR) {
                        // if air space, fill
                        parts.put(entry.getKey().add(mount), new Block(Block.BlockID.BLOCK, 0));
                    } else {
                        // delete this block
                        parts.put(entry.getKey(), new Block(Block.BlockID.AIR, 0));
                    }
                }
            }
        }
        
        return new RSPhenotype(parts, inputs, outputs);
    }
}
