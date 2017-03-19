


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.gui;

import common.CommonDatabase;
import customer.logic.allCustomers;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
     
    @FXML
    private Label todayDate;
    
    @FXML
    private Label statusLabel;

    @FXML
    private Label realStatus;

    @FXML
    private Button settleButton;

    @FXML
    private Label instructions;

    @FXML
    private ListView<String> bookingsView = new ListView<String>();
    
    @FXML
    private ListView<String> allParts = new ListView<String>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }
    
    @FXML
    public void setCustomer(allCustomers c, String type)
    {
        cust = c;
        //ArrayList<String> regNo = new ArrayList<String>();
        //NameLabel.setText(c.getFirstname() + " " + c.getSurname());
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
    public void viewVehicles2(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection conn = null;
        try
        {
            conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery( "SELECT Customer_Accounts.ID, Vehicles.RegistrationNumber, Vehicles.Make, Vehicles.Model, Vehicles.Colour, Vehicles.VehicleType FROM Customer_Accounts INNER JOIN Vehicles ON Customer_Accounts.ID = Vehicles.CustomerID WHERE Customer_Accounts.ID = '" + c.getID() + "' ");
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
   
    
    
    /*@FXML
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
            ResultSet rs = conn.createStatement().executeQuery( "SELECT Vehicles.CustomerID, Vehicles.RegistrationNumber, Vehicles.WarrantyID, Booking.RegistrationNumber, Booking.BookingID, Booking.Bill, Booking.BookingType, Booking.BookingDate, Booking.BookingTime FROM Vehicles INNER JOIN Booking ON Vehicles.RegistrationNumber = Booking.RegistrationNumber WHERE Vehicles.CustomerID = '" + c.getID() + "' ");
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
    }*/
    
    
    @FXML
    public void viewBookings2(allCustomers c)
    {
        
        displayBookings(c);

        bookingsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
                statusLabel.setVisible(true);
                
                String[] words = newValue.split("\n");
                String st = words[7].substring(8);
                String id = words[3].substring(12);
                realStatus.setText(words[7].substring(8));
                realStatus.setVisible(true);
                System.out.println(st);
                        
                if(st.equals("OUTSTANDING"))
                {
                    instructions.setVisible(true);
                    settleButton.setVisible(true);
                    settleButton.setOnAction(new EventHandler<ActionEvent>() 
                    {
                        @Override
                        public void handle(ActionEvent event)
                        { 
                            settleBill(id);
                                        
                            printSettled();
                            instructions.setVisible(false);
                            settleButton.setVisible(false);
                            realStatus.setText("SETTLED");
                            ((Node)(event.getSource())).getScene().getWindow().hide(); 
                            //displayBookings(c);
                            //bookingsView.getSelectionModel().select(0);       
                         }
                     });
                    
                    
                }
                else
                {
                    instructions.setVisible(false);
                    settleButton.setVisible(false);
                }
                
            }
        });
    }
    
    public void displayBookings(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection conn = db.getConnection();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        String dateT = df.format(dateobj);
        ObservableList<String> data = FXCollections.observableArrayList();
        try
        {
            
            ResultSet rs = conn.createStatement().executeQuery( "SELECT Vehicles.CustomerID, Vehicles.RegistrationNumber, Vehicles.WarrantyID, Booking.RegistrationNumber, Booking.BookingID, Booking.Bill, Booking.BookingType, Booking.BookingDate, Booking.BookingTime FROM Vehicles INNER JOIN Booking ON Vehicles.RegistrationNumber = Booking.RegistrationNumber WHERE Vehicles.CustomerID = '" + c.getID() + "' ");
            
            
            if(rs.next())
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
                    
                    payment = checkWarranty(conn, rs.getInt("BookingID"));
                                        
                    data.add( "\nDate: " + status + "\n"+ "Vehicle: " + rs.getString("RegistrationNumber") + "\n" + "Booking ID: " + rs.getString("BookingID") + "\n" + "Booking Type: " + rs.getString("BookingType") + "\n"  + "Time: " + rs.getString("BookingTime") + "\n" + "Bill: " + rs.getDouble("Bill")  + "\nStatus: " + payment + "\n\n\n");
                    bookingsView.setItems(data); 
                    
                }
                while(rs.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println("first error");
        }
        close(conn);
    }
    
    
    public void printSettled()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PAYMENT COMPLETED");
        alert.setHeaderText("SETTLED");
        alert.setContentText("You have settled the bill");
        alert.showAndWait();
    }
    
    public String checkWarranty(Connection conn, int id) 
    {   
        String answer = "";
        //Connection conn = new CommonDatabase().getConnection();
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM BillsPaid WHERE BookingID = '" + id + "' ");
            if(rs != null)
            {
                return rs.getString("SettleBill");
            }
        }
        catch(SQLException e)
        {
            System.out.println("HELLO");
        }
        close(conn);
        return answer;
    }
    
    public void settleBill( String n) 
    {
        int id = Integer.parseInt(n);
        System.out.println(id);
        Connection conn = new CommonDatabase().getConnection();
        PreparedStatement statement = null;
        String sql = "UPDATE BillsPaid SET SettleBill = ? WHERE BookingID = ? ";
        try
        {
            statement = conn.prepareStatement(sql);
            String a = "SETTLED";
            statement.setString(1, a);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        close(conn);
    }
    
    @FXML
    public void viewParts2(allCustomers c)
    {
        System.out.println("HELLO");
        CommonDatabase db = new CommonDatabase();
        
        ArrayList<String> vehicles = new ArrayList<String>();
        ObservableList<String> data = FXCollections.observableArrayList();
        
        try
        {
            Connection conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Vehicles WHERE CustomerID = '" + c.getID() + "' ");
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
    
    @FXML
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
    
    @FXML
    public void close(Connection connection)
    {
        try
        {
            if(connection != null)
            {
                connection.close();
                System.out.println("CLOSED");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}


