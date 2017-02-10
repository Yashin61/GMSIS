/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yhk30
 */
public class Database
{
    private static Connection connection;
    
    private Database() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        connection=DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
        System.out.print("Connected to database!");
    }
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(connection == null){
            new Database();
        }
        return connection;
    }
}
