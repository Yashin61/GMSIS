// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.sqlite.JDBC;
import parts.gui.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class VehicleTable {
 public Connection connect()
    {
       // String url = "jdbc:sqlite:Records.db";
        Connection conn = null;
       
        
        try 
        {
           conn = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:Records.db");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    
}