/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlgenerateprocedure;

/**
 *
 * @author Minh Nhat
 */
public class MySqlGenerateProcedure {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GenerateProcedure generate = new GenerateProcedure();
        generate.init();
        
        System.out.println("Finished");
    }
    
}
