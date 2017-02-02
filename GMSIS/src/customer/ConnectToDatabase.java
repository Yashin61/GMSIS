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


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class ConnectToDatabase 
{    
    private Connection connect()
    {
        String url = "jdbc:sqlite:src/common/Records.db";
        Connection conn = null;
        
        try 
        {
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void selectAll()
    {
        String sql = "SELECT ID, Firstname, Surname, Postcode FROM Customer_Accounts";
        
        try(Connection conn = this.connect();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql))
            {
                
                while(rs.next())
                {
                    System.out.println(rs.getInt("ID") + "\t" + rs.getString("Firstname")  + 
                            rs.getString("Surname") 
                     + rs.getString("Postcode")); 
                }
                
            }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    public static void main(String[] args) throws Exception
    {
        ConnectToDatabase obj = new ConnectToDatabase();
        obj.connect();
        obj.selectAll();
    }
    
}
