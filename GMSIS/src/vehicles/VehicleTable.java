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

    public void IDSearch(int ID){
        Connection con = connect();
       // Parts p=null;
       
        String sql = "SELECT * FROM Parts WHERE ID=?";
        try{
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1,ID);
       ResultSet info = stat.executeQuery();
        
               // p= new Parts(info.getString("Name"),info.getString("Description"),info.getString("Model"),info.getString("Make"),info.getInt("ID"),info.getString("Cost"),info.getInt("Quantity"));
              
         con.close();
        }
        catch(SQLException e)
        {
            
        }
        //return p;
    }
    
     public ArrayList<Customers_Parts_Edit> SearchCustomerparts(int ID){
        Connection con = connect();
        ArrayList<Customers_Parts_Edit> p=null;
       
        String sql = " SELECT * FROM Customer_Accounts WHERE ID = ?  ";
        try{
            System.out.println("4");
        PreparedStatement stat = con.prepareStatement(sql);
        System.out.println("5");
        stat.setInt(1,ID);
         System.out.println("1");
       ResultSet info = stat.executeQuery();
        System.out.println("2");
        while(info.next()){
            System.out.println(info.getString("Registration Number"));
        }
        p=getPartsUsingReg(info.getString("Registration Number"));
         System.out.println("3");
         con.close();
        }
        catch(SQLException e)
        {
            System.out.println("W");
        }
        return p;
    }
         public ArrayList<Customers_Parts_Edit> getPartsUsingReg(String ID){
        Connection con = connect();
        ArrayList<Customers_Parts_Edit> p=null;
       
        String sql = "SELECT FROM PartsUsed WHERE Registration Number ID=?";
        try{
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1,ID);
       ResultSet info = stat.executeQuery();
       System.out.println(info.getString(ID));
        while(info.next()){
            
            p.add(new Customers_Parts_Edit(info.getInt("Registration Number"),info.getInt("PartsID"),info.getInt("BookingID")));
        }
         con.close();
        }
        catch(SQLException e)
        {
            
        }
        return p;
    }
    
    public void searchByNameandPostcode(String name, String postcode){
        Connection con = connect();
        String sql = "SELECT ID, Firstname, Surname, Address, Postcode, Phone, Email FROM Customer_Accounts WHERE Surname=? AND Postcode=?";
        try{
            PreparedStatement stat = con.prepareStatement(sql);
            stat.setString(1,name);
            stat.setString(2, postcode);
            ResultSet info = stat.executeQuery();
            while(info.next()){
                System.out.println(info.getInt("ID") + " " + info.getString("Firstname") + "  " + info.getString("Surname"));
            }
         con.close();        
        }
        catch (SQLException e){
        
        }
    }
    
    public ObservableList<Parts_Table> getParts(TableView<Parts_Table> parts_table){
    
     Connection con = connect();
       
        String sql = "SELECT * FROM Parts";
        ObservableList<Parts_Table> p =FXCollections.observableArrayList();
        try{
        PreparedStatement stat = con.prepareStatement(sql);
        
       ResultSet info = stat.executeQuery();
      
        while(info.next()){
                   
            p.add(new Parts_Table(info.getInt("ID"),info.getString("Name"),info.getString("Model"),info.getString("Make"),info.getString("Description"),info.getInt("Quantity"),info.getString("Cost")));
        }
         con.close();
         parts_table.setItems(p);
        }
        catch(SQLException e)
        {
            
        }
        return p;
    }
}