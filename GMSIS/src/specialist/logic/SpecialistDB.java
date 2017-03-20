/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prashant
 */
public class SpecialistDB 
{
    private List<Object> rList = new ArrayList<Object>();
    private ArrayList SList = new ArrayList();
    private Object[][] rowData;
    private Object[] cols;
    private int rowsLength;
    private String[] SPCList;
    String Recordurl = "jdbc:sqlite:src/common/Records.db";
    
    public void addSPC(String name, String address, String phone, String email)
    {
        
        Connection conn = null;
        Statement stmt = null;
        
        try
        { 
            conn = DriverManager.getConnection(Recordurl);

            stmt = conn.createStatement();
            String sql = "INSERT INTO SPC (SPCname, SPCaddress, SPCphone, SPCemail) "
                    + "VALUES ("+"'"+name+"','"+address+"','"+phone+"','"+email+"');"; 

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "A new SPC has been added. \n");

            stmt.close();
            conn.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void addSPCBooking(String name, String dDate, String arrived, String rDate, String returned, int parts, String reg, int cust, String workOn, String type, double cost)
    {
        Connection conn = null;
        Statement stmt = null;
        
        try
        { 
            conn = DriverManager.getConnection(Recordurl);

            stmt = conn.createStatement();
            String sql = "INSERT INTO SPCBooking (SPCname, ExpectedDeliveryDate, Arrived, ExpectedReturnDate, Returned, PartID, RegistrationNumber, CustomerID, WorkOn, Type, Cost)"
                    + "VALUES ("+"'"+name+"','"+dDate+"','"+arrived+"','"+rDate+"','"+returned+"','"+parts+"','"+reg+"','"+cust+"','"+workOn+"','"+type+"','"+cost+"');"; 

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "A new SPC Booking has been added. \n");

            stmt.close();
            conn.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void editSPC(String id, String newData, int col)
    {      
        Connection connect = null;
        Statement stmt = null;
        
        try
        {
            if(id.equals(""))
            {
                System.out.println("error");
            } 
            else
            {    
                try
                {
                    Integer newid = Integer.parseInt(id);
                    String tableColumn ="";
                    if(col==1){ tableColumn = "SPCname";}
                    else if(col==2){ tableColumn =  "SPCaddress"; }
                    else if(col==3){ tableColumn = "SPCphone"; }
                    else if(col==4){ tableColumn = "SPCemail"; }
                    connect = DriverManager.getConnection(Recordurl);
                    stmt = connect.createStatement();
                    String sql = "UPDATE SPC SET "+ tableColumn + " = '" + newData+ "' "+ "WHERE SPCId = " + newid + " ;";

                    stmt.executeUpdate(sql);
                    stmt.close();
                    connect.close();
                }catch(NumberFormatException e){System.out.println("Error in converting the id");}
            }
        }catch(SQLException e)
        {
           Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void deleteSPC(Object id)
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            String sql = "DELETE FROM SPC WHERE SPCId = " + id + ";";

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "SPC has been deleted."); 

            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void deleteSPCBooking(Object id)
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            String sql = "DELETE FROM SPCBooking WHERE Id = " + id + ";";

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "SPCBooking has been deleted."); 

            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public String[] getSPC()
    {
        
        Connection connect = null;
        Statement stmt = null;      
        
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT SPCname FROM SPC");

            while(set.next()){
                SList.add(set.getString(1)); 
            }

            SPCList = (String[]) SList.toArray(new String[SList.size()]);

            stmt.close();
            set.close();
            connect.close();

            return SPCList;

          }catch(SQLException e)
          {
              Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);  
          } 
          return SPCList;
    }
    
    /*
    public static void main(String[] args) {
        SpecialistDB app = new SpecialistDB();
        System.out.print(Arrays.toString(app.getSPC()));
    }*/
}
