/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

/**
 *
 * @author Eric Lu <penlume@gmail.com>
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CombinatorialFunctionTest combtest = new CombinatorialFunctionTest();
        assert(combtest.test());
        
        System.out.println("Tests passed!");
    }
    
}
