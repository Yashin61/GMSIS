
package Parts;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.sqlite.JDBC;
import Parts.gui.*;

public class ConnectionToParts {
 private Connection connect()
    {
       // String url = "jdbc:sqlite:Records.db";
        Connection conn = null;
       
        
        try 
        {
           conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rohim\\Documents\\rohim\\year 2\\NBProject\\SE8\\GMSIS\\src\\common\\Records(2).db");
            //conn = DriverManager.getConnection("jdbc:sqlite:Records1(2).db");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    public void add(int ID, int Plus_Qty){
        int qty=0;
        String sql = "SELECT ID,Name,Description,Quantity,Cost FROM Parts2";
        Connection con=connect();
            
     
        try {
             //con = this.connect();
            Statement stats = con.createStatement();
             ResultSet rs = stats.executeQuery(sql);
            qty=rs.getInt("Quantity");
            //stats.setString(2,"rohim");
          // stats.execute();#qty=qty+Plus_Qty;
          if(qty<10){
              qty=qty+Plus_Qty;
      String addData= "UPDATE Parts2 SET Quantity=? WHERE ID=?";
      System.out.println(qty);
      System.out.println(Plus_Qty);
      PreparedStatement addD = con.prepareStatement(addData);
      addD.setInt(1, qty);
      addD.setInt(2, ID);
      addD.execute();
      System.out.println("complete");
          }
          con.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       /* qty=qty+Plus_Qty;
      String addData= "UPDATE Customers SET Quantity='?' WHERE ID='?'";
      PreparedStatement addD = con.prepareStatement(addData);*/
      
    }
    public void selectAll()
    {
        
       String sql = "";//SELECT ID, Name FROM Hi";//prepared statement
        
        
        try(Connection conn = this.connect();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql))
            {
                // loop through the result set
                while(rs.next())
                {
                    System.out.println(rs.getInt("ID") + "\t" + rs.getString("name"));
                }
                
            }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

     
    public static void main(String[] args) throws Exception
    {
        ConnectionToParts obj = new ConnectionToParts();
        obj.connect();
        //obj.add(8, "er");
        obj.selectAll();
    }
    
       
}
