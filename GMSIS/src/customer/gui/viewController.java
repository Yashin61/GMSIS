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
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Manoharan
 */
public class viewController implements Initializable
{
    private static allCustomers cust;
    
    @FXML
    private Label NameLabel;
            
    @FXML
    private Label none;
    
    @FXML
    private AnchorPane Pane;
    
    @FXML
    private TitledPane bookingsPane;

    @FXML
    private TitledPane vehiclesPane;

    @FXML
    private TitledPane partsPane;
    
    @FXML
    private Accordion mainFrame;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }
    
    @FXML
    public void setCustomer(allCustomers c, String type)
    {
        cust = c;
        ArrayList<String> regNo = new ArrayList<String>();
        NameLabel.setText(c.getFirstname() + " " + c.getSurname());
        if(type == "Vehicles")
        {
            viewVehicles2(c);
        }
        else if(type == "Bookings")
        {
            viewBookings2(c);
        }
        else
        {
            viewParts2(c);
        }
        
    }
   
    
    @FXML
    public void viewBookings2(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        ArrayList<String> reg = new ArrayList<String>();
        try
        {
            connection = db.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Vehicles WHERE CustomersID = '" + c.getID() + "' ");
            while(rs.next())
            {
                reg.add(rs.getString("RegistrationNumber"));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesnt work");
        }
        try
        {
            boolean answer = false;
            for(int i = 0; i < reg.size(); i++)
            {
                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Booking WHERE RegistrationNumber = '" + reg.get(i) + "' ");
                
                while(rs.next())
                {
                    answer = true;
                    TitledPane pane = new TitledPane(rs.getString("RegistrationNumber"), Pane);
                    pane.setContent(new Label("Booking ID: " + rs.getString("BookingID") + "\n" + "Type: " + rs.getString("BookingType") + "\n" + "Booking Date: " + rs.getString("BookingDate") + "\n" + "Time: " + rs.getString("BookingTime") + "\n" + "Bill: " + rs.getDouble("Bill")));
                    mainFrame.getPanes().add(pane);
                }
            }
            if(answer == false)
            {
                none.setVisible(true);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }          
    }
    
    @FXML
    public void viewVehicles2(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        try
        {
            connection = db.getConnection();
            boolean check = false;
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Vehicles WHERE CustomersID = '" + c.getID() + "' ");
            while(rs.next())
            {
                check = true;
                TitledPane pane = new TitledPane(rs.getString("RegistrationNumber"), Pane);
                String answer = rs.getString("WarrantyID");
                String warranty = "No";
                if(answer != null)
                {
                    warranty = "Yes";
                }
                pane.setContent(new Label("Vehicle Type: " + rs.getString("VehicleType") + "\n" + "Make: " + rs.getString("Make") + "\n" + "Model: " + rs.getString("Model") + "\n" + "Warratny: " + warranty));
                mainFrame.getPanes().add(pane);
            }
            if(check == false)
            {
                none.setVisible(true);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }

    }
    
    @FXML
    private void viewParts2(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        connection = db.getConnection();
        ArrayList<String> reg = new ArrayList<String>();
        try
        {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Vehicles WHERE CustomersID = '" + c.getID() + "' ");
            while(rs.next())
            {
                reg.add(rs.getString("RegistrationNumber"));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesnt work");
        }
        try
        {
            
            boolean answer = false;
            for(int i = 0; i < reg.size(); i++)
            {
                TitledPane pane = new TitledPane(reg.get(i), Pane);
                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM PartsUsed WHERE RegistrationNumber = '" + reg.get(i) + "' ");
                ArrayList<Integer> partsIDs = new ArrayList<Integer>();
                String output = "";
                while(rs.next())
                {
                    partsIDs.add(rs.getInt("PartsID")); 
                }
                for(int j = 0; j < partsIDs.size(); j++)
                {
                    rs = connection.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(j) + "' ");
                    
                    while(rs.next())
                    {
                        answer = true;
                        
                        output = output + "Parts ID: " + rs.getInt("ID") + "\n" + "Parts Name: " + rs.getString("Name") + "\n";
                        
                    }
                }
                pane.setContent(new Label(output));
                mainFrame.getPanes().add(pane);
            }
            if(answer == false)
            {
                none.setVisible(true);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Works");
        }
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
