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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author prashant
 */
public class SpecialistDB 
{
    private ArrayList SList = new ArrayList();
    private Object[][] rowData;
    private Object[] cols;
    private int rowsLength;
    private String[] SPCList;
    String Recordurl = "jdbc:sqlite:src/common/Records.db";
    
    //adds spc to the database
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
            //JOptionPane.showMessageDialog(null, "A new SPC has been added. \n");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding SPC");
            alert.setHeaderText("Success");
            alert.setContentText("A new SPC has been added.");

            stmt.close();
            conn.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //adds details about an spc booking to the database
    public void addSPCBooking(String name, String dDate, String arrived, String rDate, String returned, int parts, String reg, int cust, String workOn, String type, double cost, int bookID)
    {
        Connection conn = null;
        Statement stmt = null;
        
        try
        { 
            conn = DriverManager.getConnection(Recordurl);

            stmt = conn.createStatement();
            String sql = "INSERT INTO SPCBooking (SPCname, ExpectedDeliveryDate, Arrived, ExpectedReturnDate, Returned, PartID, RegistrationNumber, CustomerID, WorkOn, Type, Cost, BookingID)"
                    + "VALUES ("+"'"+name+"','"+dDate+"','"+arrived+"','"+rDate+"','"+returned+"','"+parts+"','"+reg+"','"+cust+"','"+workOn+"','"+type+"','"+cost+"','"+bookID+"');"; 

            stmt.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null, "A new SPC Booking has been added. \n");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding SPC Booking");
            alert.setHeaderText("Success");
            alert.setContentText("A new SPC Booking has been added.");

            stmt.close();
            conn.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //used to edit the details of the spc stored in the database
    public void editSPC(String id, String name, String address, String phone, String email)
    {      
        Connection connect = null;
        Statement stmt = null;
        
        try
        {
            if(id == null)
            {
                //System.out.println("error");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("SPC has not been selected");
            } 
            else
            {    
                Integer newid = Integer.parseInt(id);
                connect = DriverManager.getConnection(Recordurl);
                stmt = connect.createStatement();
                String sql = "UPDATE SPC SET SPCname = '"+name+"', SPCaddress = '"+address
                +"', SPCphone = '"+phone+"', SPCemail = '"+email+"' WHERE SPCId = " + id + " ;";

                stmt.executeUpdate(sql);
                stmt.close();
                connect.close();
            }
        }catch(SQLException e)
        {
           Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //used to edit the details of an spc booking stored in the database
    public void editSPCBooking(String id, String name, String dDate, String arrived, String rDate, String returned, int parts, String reg, int cust, String workOn, String type, double cost, int bookId)
    {
        Connection connect = null;
        Statement stmt = null;
        
        try
        {
            if(id == null)
            {
                //System.out.println("error");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("SPC Booking has not been selected");
                alert.showAndWait();
            } 
            else
            {    
                connect = DriverManager.getConnection(Recordurl);
                stmt = connect.createStatement();

                String sql = "UPDATE SPCBooking SET SPCname = '"+name+"', ExpectedDeliveryDate = '"+dDate+"', Arrived = '"+arrived+"',"
                        + " ExpectedReturnDate = '"+rDate+"', Returned = '"+returned+"', "
                        + "PartID = "+parts+", RegistrationNumber = '"+reg+"', CustomerID = "+cust+", WorkOn = '"+workOn+"',"
                        + " Type = '"+type+"', Cost = "+cost+", BookingID = "+bookId+""
                        + " WHERE Id = "+id+" ;";

                stmt.executeUpdate(sql);
                stmt.close();
                connect.close();
            }
        }catch(SQLException e)
        {
           Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //delete an spc from the stored list in the database
    public void deleteSPC(int id)
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            String sql = "DELETE FROM SPC WHERE SPCId = " + id + ";";

            stmt.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null, "SPC has been deleted."); 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleting SPC");
            alert.setHeaderText("Success");
            alert.setContentText("SPC has been deleted.");
            
            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //deletes the spc booking that was made
    public void deleteSPCBooking(int id)
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            String sql = "DELETE FROM SPCBooking WHERE Id = " + id + ";";

            stmt.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null, "SPCBooking has been deleted."); 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleting SPC Booking");
            alert.setHeaderText("Success");
            alert.setContentText("SPC Booking has been deleted.");
            
            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //deletes all the spc bookings when the spc is deleted from the record
    public void deleteSPCBooking2(String spcName)
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            String sql = "DELETE FROM SPCBooking WHERE SPCname = '" + spcName + "';";

            stmt.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null, "SPCBooking has been deleted."); 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleting SPC Booking");
            alert.setHeaderText("Success");
            alert.setContentText("SPC Bookings has been deleted along with the SPC.");
            
            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //gets the list of spc names stored in the database
    public String[] getSPC()
    {
        
        Connection connect = null;
        Statement stmt = null;      
        
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM SPC");

            while(set.next()){
                SList.add(set.getString("SPCname")); 
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
    public static void main(String [] param)
    {
        SpecialistDB a = new SpecialistDB();
        System.out.println(Arrays.toString(a.getSPC()));
    }
    */
    
}
