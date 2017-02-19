/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.gui;

import common.CommonDatabase;
import customer.logic.allCustomers;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

/**
 *
 * @author Manoharan
 */
public class viewController implements Initializable
{
    private static allCustomers cust;
    
    @FXML
    private TitledPane bookingsPane;

    @FXML
    private TitledPane vehiclesPane;

    @FXML
    private TitledPane partsPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }
    
    @FXML
    public void setCustomer(allCustomers c)
    {
        cust = c;
        ArrayList<String> regNo = new ArrayList<String>();
        viewVehicles(c, regNo);
        viewBookings(regNo);
        viewParts(regNo);
        //System.out.println(regNo.toString());
    }
    
    @FXML
    public void viewVehicles(allCustomers c, ArrayList<String> reg)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        String answer = "";
        
        try
        {
            connection = db.getConnection();
            
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Vehicles WHERE Customers_ID = '" + c.getID() + "' ");
            
            while(rs.next())
            {
                reg.add(rs.getString("Registration Number"));
                answer = answer + rs.getString("Registration Number") + ":   " + rs.getString("Vehicle_Type") + "\n";
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
        
        vehiclesPane.setContent(new Label(answer));
        System.out.println("DONE");
    }
  
    
    @FXML 
    private void viewBookings(ArrayList<String> reg)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        String answer = "";
        
        try
        {
            connection = db.getConnection();
            
            for(int i = 0; i < reg.size(); i++)
            {
                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Booking WHERE RegistrationNumber = '" + reg.get(i) + "' ");
                
                while(rs.next())
                {
                    answer = answer + rs.getString("RegistrationNumber") + ":  " + rs.getString("BookingDate") + "\n";
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
        
        bookingsPane.setContent(new Label(answer));
        System.out.println("DONE");
        
    }
    
    @FXML
    private void viewParts(ArrayList<String> reg)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        
        String answer = "";
        
        try
        {
            connection = db.getConnection();
            
            for(int i = 0; i < reg.size(); i++)
            {
                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM PartsUsed WHERE RegistrationNumber = '" + reg.get(i) + "' ");
                ArrayList<Integer> partsIDs = new ArrayList<Integer>();
                answer = answer + reg.get(i) + ": \n"; 
                while(rs.next())
                {
                    partsIDs.add(rs.getInt("PartsID")); 
                }
                
                if(partsIDs.isEmpty())
                {
                    answer = answer + "NO PARTS USED\n\n";
                }
                else
                {

                    for(int j = 0; j < partsIDs.size(); j++)
                    {
                        ResultSet rs2 = connection.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(j) + "' ");

                        while(rs2.next())
                        {
                            answer = answer  + "ID: " + rs2.getInt("ID") + " - " + rs2.getString("Name") + "\n";
                        }
                        //answer = answer + "\n";
                    }
                }
                
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
        
        partsPane.setContent(new Label(answer));
        System.out.println("DONE");
    
    }
}
