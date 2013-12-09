/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package evolver;

import evaluation.RSEvaluation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        /*String out = s.getText(0);
        
        File outfile = new File("outputs\\generation" + gen + ".txt");
        try {
            if (!outfile.mkdirs() || !outfile.createNewFile()) {
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
        }*/ // TODO: outputs to file!
        System.out.println(s.getText(0));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // load functions for circuit evaluation
        ArrayList<String> funcs = new ArrayList<String>();
        try {
            // get path of config.txt file
            Path workingDir = Paths.get("").toAbsolutePath();
            Path config = Paths.get(workingDir.toString() + "\\src\\evolver\\config.txt").toAbsolutePath();
            
            // open config.txt file
            BufferedReader read = Files.newBufferedReader(config, Charset.forName("UTF-8"));
            
            // read line by line
            String line;
            while ((line = read.readLine()) != null) {
                funcs.add(line);
            }
            read.close();
        } catch (IOException e) {
            System.out.println("Could not read config.txt");
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        
        CombinatorialFunction functions = new CombinatorialFunction(funcs);
        
        // make population
        RSPopulation pop = new RSPopulation(100, new RSEvaluation(functions));
        
        // TODO: automatically stop on convergence?
        while (true) {
            pop.generation();
            gen++;
            output(pop.getOutput());
        }
    }
}
