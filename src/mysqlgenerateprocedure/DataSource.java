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
import java.sql.Connection;
import java.sql.SQLException;


import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mchange.v2.c3p0.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Statement;

import javax.xml.ws.BindingProvider;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

//dung connecton poolling
public class DataSource {

    protected Connection connection = null;
    private static DataSource dataSource = null;
    private ComboPooledDataSource cpds = null;
    
    
    private String username = null;
    private String password = null;
    protected String databaseName = null;
    

    private DataSource() throws SQLException {
        try {
            System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
            System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
            System.setProperty("com.mchange.v2.c3p0.contextClassLoaderSource", "library");

            getConfigInfo();
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            //cpds.setJdbcUrl("jdbc:mysql://localhost:3306/nckh?useUnicode=true");&characterEncoding=UTF-8
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/" + databaseName+ "?characterEncoding=UTF-8");
            cpds.setUser(username);
            cpds.setPassword(password);

            // the settings below are optional -- c3p0 can work with defaults
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(50);
            cpds.setMaxStatements(180);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DataSource getInstance() throws SQLException {
        if (dataSource == null) {
            dataSource = new DataSource();
        }
        return dataSource;
    }

    public Connection getConnection() {
        Connection a = null;
        try {
            a = this.cpds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    private void getConfigInfo() {
        // TODO code application logic here
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("config.xml");

        try {

            org.jdom.Document document = (org.jdom.Document) builder.build(xmlFile);
            Element node = document.getRootElement();

            databaseName = node.getChildText("databaseName");
            username = node.getChildText("username");
            password = node.getChildText("password");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }
    
    public String getDatabaseName(){
        return this.databaseName;
    }
}
