/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlgenerateprocedure;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minh Nhat
 */
public class GenerateProcedure {
    PrintWriter wr = null;
    Essential enssential = new Essential();
    public GenerateProcedure(){
        
    }
    public void init(){
        // cai dat dau file 
        initializeProcedureFile();
        // generate noi dung
        // generate tung bang
        List<String> lstTables = enssential.getTables();
        for(int i = 0; i <lstTables.size(); i++){
            wr.println("/*******************************************\n"
                    + "* Table: " + lstTables.get(i).toUpperCase()+"\n"
                    + "**********************************************/\n");
            GenereateInsert(lstTables.get(i));
            GenerateUpdate(lstTables.get(i));
            GenerateDelete(lstTables.get(i));
            wr.println();
            wr.println();
            
        }
        // cai dat cuoi file
        finishProcedureFile();
    }
    
    private void initializeProcedureFile() {
        try {
            wr = new PrintWriter(new FileWriter("StoredProcedure.sql"), true); // true = allowed append
            wr.println( "delimiter //");
            wr.println();
        } catch (IOException ex) {
            Logger.getLogger(GenerateProcedure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void GenereateInsert(String tableName){
       
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\ncreate procedure %s_Ins (", tableName.toUpperCase()));
        // list parameters
        List<ColumnDTO> lstCol = enssential.getColumns(tableName);
        for(int i = 0; i< lstCol.size(); i++){
            if(lstCol.get(i).getCHARACTER_MAXIMUM_LENGTH() == null){
                 builder.append(String.format("%s %s, ",lstCol.get(i).getCOLUMN_NAME(), lstCol.get(i).getDATA_TYPE()));
            }
            else
                builder.append(String.format("%s %s(%s), ",lstCol.get(i).getCOLUMN_NAME(), lstCol.get(i).getDATA_TYPE()
                ,lstCol.get(i).getCHARACTER_MAXIMUM_LENGTH()));
        }
        builder.deleteCharAt(builder.length()-1);
        builder.deleteCharAt(builder.length()-1);
        builder.append(" )");
        builder.append("\nbegin");
        builder.append( String.format("\ninsert into %s (",tableName));
        for(int i = 0; i<lstCol.size(); i++){
            builder.append(" " + lstCol.get(i).getCOLUMN_NAME() +",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append(" )");
        
        builder.append(" values (");
        for(int i = 0; i<lstCol.size(); i++){
            builder.append(" " + lstCol.get(i).getCOLUMN_NAME() +",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append(" );");
        builder.append("\nend//");
        
        wr.println();
        wr.println(builder.toString());
        
    }
    
    private void GenerateUpdate(String tableName){        
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("create procedure %s_Upd (", tableName.toUpperCase()));
        // list parameters
        List<ColumnDTO> lstCol = enssential.getColumns(tableName);
        for(int i = 0; i< lstCol.size(); i++){
            if(lstCol.get(i).getCHARACTER_MAXIMUM_LENGTH() == null){
                builder.append(String.format("%s %s, ",lstCol.get(i).getCOLUMN_NAME(), lstCol.get(i).getDATA_TYPE()));
            }
            else
                builder.append(String.format("%s %s(%s), ",lstCol.get(i).getCOLUMN_NAME(), lstCol.get(i).getDATA_TYPE()
                ,lstCol.get(i).getCHARACTER_MAXIMUM_LENGTH()));
        }

        builder.deleteCharAt(builder.length()-1);
        builder.deleteCharAt(builder.length()-1);
        builder.append(" )");
        builder.append("\nbegin");// dung
        builder.append(String.format("\nupdate %s as a \nset ",tableName));

        
        //a.CountOfUpdate = if(a.CountOfUpdate <> CountOfUpdate,CountOfUpdate,a.CountOfUpdate),
        for(int i = 1; i<lstCol.size(); i++){
            builder.append(String.format("\na.%s = if(a.%s <> %s, %s, a.%s),",lstCol.get(i).getCOLUMN_NAME(),lstCol.get(i).getCOLUMN_NAME(),
                    lstCol.get(i).getCOLUMN_NAME(),lstCol.get(i).getCOLUMN_NAME(),lstCol.get(i).getCOLUMN_NAME()));
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append(String.format("\nwhere a.%s = %s;",lstCol.get(0).getCOLUMN_NAME(),lstCol.get(0).getCOLUMN_NAME()));
        builder.append("\nend//");
        
        wr.println();
        wr.println();
        wr.println(builder.toString());
    }
    
    private void GenerateDelete(String tableName){
        
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("create procedure %s_Del (", tableName.toUpperCase()));

        // list parameters
        List<ColumnDTO> lstCol = enssential.getColumns(tableName);
        
            if(lstCol.get(0).getCHARACTER_MAXIMUM_LENGTH() == null){
                builder.append(String.format("%s %s, ",lstCol.get(0).getCOLUMN_NAME(), lstCol.get(0).getDATA_TYPE()));
            }
            else
                builder.append(String.format("%s %s(%s), ",lstCol.get(0).getCOLUMN_NAME(), lstCol.get(0).getDATA_TYPE()
                ,lstCol.get(0).getCHARACTER_MAXIMUM_LENGTH()));
        
        builder.deleteCharAt(builder.length()-1);
        builder.deleteCharAt(builder.length()-1);
        builder.append(" )");
        builder.append("\nbegin");// dung
        builder.append(String.format("\ndelete from %s ",tableName));
        builder.append(String.format("\nwhere %s = %s;",lstCol.get(0).getCOLUMN_NAME(),lstCol.get(0).getCOLUMN_NAME()));
        builder.append("\nend//");
        
        wr.println();
        wr.println();
        wr.println(builder.toString());
    }
    
    private void finishProcedureFile(){
        wr.println();
        wr.println("delimiter ;");
    }
    
}
