/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genome;

import evolver.population.Phenotype;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public interface Genome {
    public Phenotype toPhenotype();
    
    public Genome mutate();
    
    public Genome crossover(Genome g);
}
