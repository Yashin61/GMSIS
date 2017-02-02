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


public class ConnectToCustomers 
{
    private Connection connect()
    {
        String url = "jdbc:sqlite:src/customer/Records.db";
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
        String sql = "SELECT ID, Firstname, Surname, Phone FROM Customer_Accounts";
        
        try(Connection conn = this.connect();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql))
            {
                // loop through the result set
                while(rs.next())
                {
                    System.out.println(rs.getInt("ID") + "\t" + rs.getString("Firstname")  + 
                            rs.getString("Surname") 
                     + rs.getString("Phone")); 
                }
                
            }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

     
    public static void main(String[] args) throws Exception
    {
        ConnectToCustomers obj = new ConnectToCustomers();
        
        obj.connect();
        obj.selectAll();
    }
    
    
}
