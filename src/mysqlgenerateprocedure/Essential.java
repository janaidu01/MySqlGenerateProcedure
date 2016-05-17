/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlgenerateprocedure;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minh Nhat
 */

/*
*  - Result:
    1. Get all tables in database. 
    2. Get all columns of table. 
    
*/
public class Essential{
    /*
    * Properties
    */
    private Connection connection = null;
    /*
    * Constructor
    */
    public Essential(){
        
    }
    
    /*
    * Method
    */
    
    // GetTableNames
    public List<String> getTables() {
        try {
            connection = DataSource.getInstance().getConnection();
            String databaseName = DataSource.getInstance().getDatabaseName();
            List<String> rsTables = new ArrayList<String>();
            
            String sqlQuery = String.format("SELECT Table_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema='%s';",databaseName);
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();
            
            while(result.next()){
                rsTables.add(result.getString(1));
            }
            
            return rsTables;
        } catch (SQLException ex) {
            Logger.getLogger(Essential.class.getName()).log(Level.SEVERE, null, ex);
        }  
        finally{
            if(connection!= null)
                try {
                    connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Essential.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    } 
    
    // get columns in table
    public List<ColumnDTO> getColumns(String tableName){
        List<ColumnDTO> lstCol = new ArrayList<ColumnDTO>();
        ColumnDTO col = null;
        try {
            connection = DataSource.getInstance().getConnection();
            String sqlQuery = "SELECT COLUMN_NAME, DATA_TYPE,CHARACTER_MAXIMUM_LENGTH\n" +
                                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                                "WHERE TABLE_Name = '" + tableName + "'";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()){
                col = new ColumnDTO();
                col.setCOLUMN_NAME(rs.getString(1));
                col.setDATA_TYPE(rs.getString(2));
                col.setCHARACTER_MAXIMUM_LENGTH(rs.getString(3));
                
                lstCol.add(col);
            }
            
            return lstCol;
            
        } catch (SQLException ex) {
            Logger.getLogger(Essential.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(connection != null)
                try {
                    connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Essential.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
