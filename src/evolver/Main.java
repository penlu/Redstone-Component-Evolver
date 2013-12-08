/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package evolver;

import evaluation.RSEvaluation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import population.RSPopulation;
import statistics.RSStatistics;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class Main {
    private static int gen = 0;
    
    private static void output(RSStatistics s) {
        String out = s.getText(0);
        
        File outfile = new File("outputs\\generation" + gen + ".txt");
        try {
            if (!outfile.createNewFile()) {
                System.out.println("could not create an output file!");
                System.exit(-1);
            }
            PrintWriter writer = new PrintWriter(new FileOutputStream(outfile));
            writer.print(out);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("could not create an output file!");
            System.exit(-1);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // load in config
        CombinatorialFunction functions = null;
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(new File("config.txt"))));
            String line;
            ArrayList<String> funcs = new ArrayList<String>();
            while ((line = read.readLine()) != null) {
                funcs.add(line);
            }
            read.close();
            
            functions = new CombinatorialFunction(funcs);
            
            // make output folder
            File dir = new File("outputs\\");
            if (!dir.mkdir()) {
                System.out.println("could not make output folder!");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("could not read config.txt!");
            System.exit(-1);
        }
        
        // make population
        RSPopulation pop = new RSPopulation(100, new RSEvaluation(functions));
        
        while (true) {
            pop.generation();
            gen++;
            output(pop.getOutput());
        }
    }
}
