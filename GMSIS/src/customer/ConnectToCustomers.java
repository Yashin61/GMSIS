/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

/**
 *
 * @author Manoharan
 */

import Customer.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

    public class ConnectToCustomers {
    
    public static void createNewTable()
    {
        String url = "jdbc:sqlite:src/Customer/Database.db";
        
        String sql = "CREATE TABLE customers (\n" + "Number INT PRIMARY KEY NOT NULL, \n" + 
                "first_name text NOT NULL,\n" + "last_name text NOT NULL,\n" +
                "address text NOT NULL,\n" + "postcode text NOT NULL,\n"
                + "email text NOT NULL\n" + ");";
        
       
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            String insert= "INSERT INTO customers VALUES(\n" + "1  ,  ' N ', ' M ', ' A ', ' C ' ,' C ');";
            
             String insert2= "INSERT INTO customers VALUES(\n" + "2  ,  ' A ', ' M ', ' A ', ' C ' ,' C ');";
             String insert3= "INSERT INTO customers VALUES(\n" + "3  ,  ' N ', ' M ', ' A ', ' C ' ,' C ');";
             stmt.executeUpdate( insert);
            stmt.executeUpdate( insert2);
            stmt.executeUpdate( insert3);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
            
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        
        
            createNewTable();
        
        
        
       
    }
}
    

