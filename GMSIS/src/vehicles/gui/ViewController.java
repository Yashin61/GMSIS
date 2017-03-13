// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import common.CommonDatabase;
import customer.logic.allCustomers;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class ViewController implements Initializable
{
    private static allCustomers cust;
    
    @FXML
    private AnchorPane Pane;
    @FXML
    private Accordion mainFrame;
    @FXML
    private Label NameLabel;
    @FXML
    private Label none;
    @FXML
    private Label todayDate;
    private ListView<?> allParts;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {}    
    
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
    
    public void viewVehicles2(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection conn = null;
        try
        {
            conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery( "SELECT Customer_Accounts.ID, Vehicles.RegistrationNumber, Vehicles.Make, Vehicles.Model, Vehicles.Colour, Vehicles.VehicleType FROM Customer_Accounts INNER JOIN Vehicles ON Customer_Accounts.ID = Vehicles.CustomersID WHERE Customer_Accounts.ID = '" + c.getID() + "' ");
            if(!rs.next())
            {
                none.setVisible(true);
            }
            
            if(rs != null)
            {
                do
                {
                    System.out.println(rs.getString("RegistrationNumber"));
                    TitledPane pane = new TitledPane(rs.getString("RegistrationNumber"), Pane);
                    pane.setContent(new Label("Vehicle Type: " + rs.getString("VehicleType") + "\n" + "Make: " + rs.getString("Make") + "\n" + "Model: " + rs.getString("Model") + "\n" + "Colour: " + rs.getString("Colour")));
                    mainFrame.getPanes().add(pane); 
                }
                while(rs.next());
            }
        }
        catch(SQLException e)
        {
            e.getMessage();
            System.out.println("HELLO");
        }
    }
    
    public void viewBookings2(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection conn = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        String dateT = df.format(dateobj);
        todayDate.setText("Today's Date: " + df.format(dateobj));
        todayDate.setVisible(true);
        try
        {
            conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery( "SELECT Vehicles.CustomersID, Vehicles.RegistrationNumber, Vehicles.WarrantyID, Booking.RegistrationNumber, Booking.BookingID, Booking.Bill, Booking.BookingType, Booking.BookingDate, Booking.BookingTime FROM Vehicles INNER JOIN Booking ON Vehicles.RegistrationNumber = Booking.RegistrationNumber WHERE Vehicles.CustomersID = '" + c.getID() + "' ");
            if(!rs.next())
            {
                none.setVisible(true);
            }
            
            if(rs != null)
            {
                do
                {
                    String payment;
                    String status =rs.getString("BookingDate") + " - " +  "PAST";
                    DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy"); 
                    String d = rs.getString("BookingDate").replace("-", "/");
                    Date dateBooking = new Date();
                    try
                    {
                        dateBooking = df.parse(d);
                    }
                    catch(ParseException e)
                    {
                        System.out.println("DATE ERROR");
                    }
                    if(dateobj.before(dateBooking))
                    {
                        status = rs.getString("BookingDate") + " - " +  "FUTURE";
                    }
                    System.out.println(rs.getString("RegistrationNumber"));
                    TitledPane pane = new TitledPane(status, Pane);
                    if(rs.getInt("WarrantyID") != 0)
                    {
                        payment = "OUTSTANDING";
                    }
                    else
                    {
                        payment = "SETTLED";
                    }
                    pane.setContent(new Label("Vehicle: " + rs.getString("RegistrationNumber") + "\n" + "Booking ID: " + rs.getString("BookingID") + "\n" + "Booking Type: " + rs.getString("BookingType") + "\n"  + "Time: " + rs.getString("BookingTime") + "\n" + "Bill: " + rs.getDouble("Bill") + "\n" + "Payment Status: "  + payment));
                    mainFrame.getPanes().add(pane); 
                }
                while(rs.next());
            }
        }
        catch(SQLException e)
        {
            e.getMessage();
            System.out.println("HELLO");
        }
    }
    
    
   
    
    public void viewParts2(allCustomers c)
    {
        System.out.println("HELLO");
        CommonDatabase db = new CommonDatabase();
        
        ArrayList<String> vehicles = new ArrayList<String>();
        ObservableList<String> data = FXCollections.observableArrayList();
        
        try
        {
            Connection conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Vehicles WHERE CustomersID = '" + c.getID() + "' ");
            if(!rs.isBeforeFirst())
            {
                allParts.setVisible(false);
                none.setVisible(true);
            }
            else
            {
                while(rs.next())
                {
                    vehicles.add(rs.getString("RegistrationNumber"));
                }
                System.out.println(vehicles);
                for(int i=0; i< vehicles.size(); i++)
                {
                    String answer = vehicles.get(i) + "\n";
                    ArrayList <Integer> partsIDs = new ArrayList<Integer>();
                    rs = conn.createStatement().executeQuery("SELECT * FROM PartsUsed WHERE RegistrationNumber = '" + vehicles.get(i) + "' ");
                    if(!rs.isBeforeFirst())
                    {
                        answer = answer + "NO PARTS USED FOR THIS VEHICLE";
                    }
                    if(rs != null)
                    {

                        while(rs.next())
                        {
                            if(!partsIDs.contains(rs.getString("PartsID")))
                            {
                                System.out.println(rs.getString("PartsID"));
                                partsIDs.add(rs.getInt("PartsID"));
                                removeDuplicates(partsIDs);
                            }
                        }
                    }
                    for(int j=0; j<partsIDs.size(); j++)
                    {
                        rs = conn.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(j) + "' ");
                        if(rs != null)
                        {
                            answer = answer + "Parts ID: "+ partsIDs.get(j) + "\nName: " + rs.getString("Name") + "Cost: \n"+ rs.getString("Cost") + "\n\n";
                        }
                    }
                    data.add(answer);
                }
                allParts.setItems(data);
            }
           
            
        }
        catch(SQLException e)
        {
            System.out.println("SQL Error");
        }
    }
    
    private void removeDuplicates(ArrayList<Integer> array)
    {
        for(int i=0; i<array.size(); i++)
        {
            for(int j = i+1; j<array.size(); j++)
            {
                if(array.get(j) == array.get(i))
                {
                    array.remove(j);
                    j--;
                           
                }
            }
        }
        System.out.println(array);
    }
}