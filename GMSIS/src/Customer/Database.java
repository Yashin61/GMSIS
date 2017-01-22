/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customer;

/**
 *
 * @author Manoharan
*/

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database 
{
    // Connect to a sample database
    
    public static void createNewDatabase(String filename)
    {
        String url = "jdbc:sqlite:src/Customer/" + "Database.db";
        
        try(Connection conn = DriverManager.getConnection(url))
        {
            if(conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is" + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());    
        }
        
    }
    
    public static void main(String[] args)
    {
        createNewDatabase("test.db");
    }
   
}
