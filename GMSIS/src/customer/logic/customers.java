/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.logic;

import common.CommonDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author Nandhini
 */
public class customers 
{
    //private int id;
    private String firstname;
    private String surname;
    private String address;
    private String postcode;
    private String phone;
    private String email;
    private String account;
    
    
    public customers(String fn, String sn, String a, String p, String pn, String e, String at)
    {
        //id = identifier;
        firstname = fn;
        surname = sn;
        address = a;
        postcode = p;
        phone = pn;
        email = e;
        account = at;
    }
    
    
    public String getFirstname()
    {
        return firstname;
    }
    
    public String getSurname()
    {
        return surname;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public String getPostcode()
    {
        return postcode;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String getAccount()
    {
        return account;
    }
    
    
    // ADD CUSTOMER METHOD
    public void addCustomer(String sql)
    {
        PreparedStatement statement = null;
        Connection connection = null;
        CommonDatabase db = new CommonDatabase();
        connection = db.getConnection();
        try
        {
            statement = connection.prepareStatement(sql);
            statement.setString(2, getFirstname());         
            statement.setString(3, getSurname());
            statement.setString(4, getAddress());
            statement.setString(5, getPostcode());
            statement.setString(6, getPhone());
            statement.setString(7, getEmail());
            //statement.setString(8, getAccount());
            statement.execute();   
        }
        catch(SQLException ex)
        {
            ex.getMessage();
        }
        close(connection);
    }
    
    public void editCustomer(String sql, int n)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        CommonDatabase db = new CommonDatabase();
        connection = db.getConnection();
        try
        {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, n);
            statement.setString(2, getFirstname());         
            statement.setString(3, getSurname());
            statement.setString(4, getAddress());
            statement.setString(5, getPostcode());
            statement.setString(6, getPhone());
            statement.setString(7, getEmail());
            statement.execute(sql);   
            System.out.println("DONE");
        }
        catch(SQLException e)
        {
            System.out.println("ERROR");
        }
                
    }
       
    
     //Common close method to close the connection
    public void close(Connection connection)
    {
        try
        {
            if(connection != null)
            {
                connection.close();
                System.out.println("CLOSED");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
