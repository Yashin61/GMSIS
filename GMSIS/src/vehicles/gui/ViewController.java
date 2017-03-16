// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import common.CommonDatabase;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import vehicles.Vehicle;

public class ViewController implements Initializable
{
    private static Vehicle veh;
    @FXML
    private AnchorPane Pane;
    private Accordion mainFrame;
    @FXML
    private Label none;
    private Label todayDate;
    @FXML
    private ListView<String> allParts;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                allParts.getSelectionModel().clearSelection();
            }
        });
    }    
    
    public void setVehicle(Vehicle v)
    {
//        System.out.println(v.getRegistrationNumber() + " 2");
        viewParts(v);
//        viewBookings(v);
    }
    
    private void viewParts(Vehicle v)
    {
//        System.out.println("It goes to the right method");
        CommonDatabase db = new CommonDatabase();
        ObservableList<String> data = FXCollections.observableArrayList();
        
        try
        {
            Connection conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM PartsUsed WHERE RegistrationNumber = '" + v.getRegistrationNumber() + "' ");
            if(!rs.isBeforeFirst())
            {
//                System.out.println("Goes through the if statement");
                allParts.setVisible(false);
                none.setVisible(true);
            }
            else
            {
//                System.out.println("Goes through the else statement");
                ArrayList<Integer> partsIDs = new ArrayList<Integer>();
                String answer = "";
                data.add("Registration Number: "+v.getRegistrationNumber());
                while(rs.next())
                {
                    partsIDs.add(rs.getInt("PartsID"));
                }
                removeDuplicates(partsIDs);
                for(int i=0; i<partsIDs.size(); i++)
                {
                    rs = conn.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(i) + "' ");
                    if(rs != null)
                    {
                        answer = answer +  "Parts ID: "+ rs.getInt("ID") + "\nName: " + rs.getString("Name") + "\nCost: £"+ rs.getString("Cost") + "\n\n"; 
                    }
                }
//                System.out.println(answer);
                data.add(answer);
                allParts.setItems(data);
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQL Error");
        }
    }
    
    private void viewBookings(Vehicle v)
    {
        CommonDatabase db = new CommonDatabase();
        Connection conn = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        String dateT = df.format(dateobj);
//        todayDate.setText("Today's Date: " + df.format(dateobj));
//        todayDate.setVisible(true);
        
        try
        {
            conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery( "SELECT Vehicles.CustomerID, Vehicles.RegistrationNumber, Vehicles.WarrantyID, Booking.RegistrationNumber, Booking.BookingID, Booking.Bill, Booking.BookingType, Booking.BookingDate, Booking.BookingTime FROM Vehicles INNER JOIN Booking ON Vehicles.RegistrationNumber = Booking.RegistrationNumber WHERE Vehicles.CustomerID = '" + v.getCustomerID1() + "' ");
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
//                    System.out.println(rs.getString("RegistrationNumber"));
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
//            System.out.println("HELLO");
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
//        System.out.println(array);
    }
}