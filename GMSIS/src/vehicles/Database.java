// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database
{
    private static Connection connection;
    
    private Database() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        connection=DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
        System.out.print("Connected to database!");
    }
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        if(connection==null)
        {
            new Database();
        }
        return connection;
    }
}