/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author Manoharan
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommonDatabase 
{
    private static final CommonDatabase INSTANCE = new CommonDatabase();
    private Connection connection = null;
    
    public CommonDatabase()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed", e);
        }
    }
    
    public static CommonDatabase getInstance()
    {
        return INSTANCE;
    }
    
    public Connection getConnection()
    {
        return this.connection;
    }

}
    

