

package customer.gui;

import common.CommonDatabase;
import customer.logic.allCustomers;
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
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Nandhini Manoharan
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
    private Label noBookings;
    
    @FXML
    private ListView<String> bookingsView = new ListView<String>();
    
    @FXML
    private ListView<String> allParts = new ListView<String>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }
    
    
    // method to check if view bookings, view vehicles, or view parts
    @FXML
    public void setCustomer(allCustomers c, String type)
    {
        cust = c;
        NameLabel.setText(c.getFirstname() +  " " + c.getSurname());
        
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
   
    
    // method to view all the vehicles owned by a particular customer
    @FXML
    public void viewVehicles2(allCustomers c)
    {
        CommonDatabase db = new CommonDatabase();
        Connection conn = null;
        NameLabel.setText(c.getFirstname() +  " " + c.getSurname());
        NameLabel.setVisible(true);
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
   
    // method to view all the bookings for a customer account
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
                
                String st = words[words.length-1].substring(8);
                String id = words[3].substring(12);
                realStatus.setText(words[words.length-1].substring(8));
                realStatus.setVisible(true);
                        
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
    
    // method to display all the bookings in a list view
    // Code Reference/Credit: http://docs.oracle.com/javafx/2/ui_controls/list-view.htm
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
            
            ResultSet rs = conn.createStatement().executeQuery( "SELECT * FROM Booking WHERE CustomerID = '" + c.getID() + "' ");

            if(rs.next())
            {
                do
                {
                    String payment;
                    double totalBill = rs.getDouble("Bill");
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
                    String answer ="\nDate: " + status + "\n"+ "Vehicle: " + rs.getString("RegistrationNumber") + "\n" + "Booking ID: " + rs.getString("BookingID") + "\n" + "Booking Type: " + rs.getString("BookingType") + "\n"  + "Time: " + rs.getString("BookingTime") + "\n";
                    answer = answer + checkSPCVehicle(conn, rs.getInt("BookingID"), totalBill) + "\nStatus: " + payment;                    
                    data.add(answer);
                    bookingsView.setItems(data); 
                    
                }
                while(rs.next());
            }
            else
            {
                noBookings.setVisible(true);
                bookingsView.setOpacity(0.5);
            }
        }
        catch(SQLException e)
        {
            System.out.println("first error");
        }
        close(conn);
    }
  
    // method to check there are any SPC bookings 
    private String checkSPCVehicle(Connection conn, int ID, double bill)
    {
        double totalCost = bill;
        String answer = "";
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SPCBooking WHERE BookingID = '" + ID + "' ");
            if(rs.next())
            {
                do
                {
                    boolean check = true;
                    if(rs.getString("Returned").equals("No"))
                    {
                        check = false;
                    }
                    if(check)
                    {
                        answer = answer + "\nSPC " + rs.getString("Type") + " on " + rs.getString("WorkOn") + " ";
                        if(rs.getString("WorkOn").equals("Part"))
                        {
                            answer = answer + String.valueOf(rs.getInt("PartID"));
                        }
                    }
                    else
                    {
                        answer = answer + "\nSPC " + rs.getString("Type") + " on " + rs.getString("WorkOn") + " ";
                        if(rs.getString("WorkOn").equals("Part"))
                        {
                            answer = answer + String.valueOf(rs.getInt("PartID")) + " (UNDER PROGRESS)";
                        }
                        else
                        {
                            answer = answer + " (UNDER PROGRESS)";
                        }
                    }
                    totalCost = totalCost + rs.getDouble("Cost");
                }
                while(rs.next());
            }
            else
            {
                answer = answer;
            }
            
            answer = answer + "\nBill: " + totalCost;
        }
        catch(SQLException e)
        {
            System.out.println("SQL error");
        }
        //close(conn);
        return answer;
    }

    // message to print if the payment has been completed
    public void printSettled()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PAYMENT COMPLETED");
        alert.setHeaderText("SETTLED");
        alert.setContentText("You have settled the bill");
        alert.showAndWait();
    }
    
    // method to check if a vehicle has warranty or not
    public String checkWarranty(Connection conn, int id) 
    {   
        String answer = "";
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
    
    // method to settle the bill for a booking
    public void settleBill( String n) 
    {
        int id = Integer.parseInt(n);
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
    
    // method to view all the parts for all the vehicles owned by a customer
    @FXML
    private void viewParts2(allCustomers c)
    {
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
                for(int i=0; i< vehicles.size(); i++)
                {
                    String answer = vehicles.get(i) + "\n\n";
                    ArrayList <Integer> partsIDs = new ArrayList<Integer>();
                    rs = conn.createStatement().executeQuery("SELECT * FROM PartsUsed WHERE RegistrationNumber = '" + vehicles.get(i) + "' ");
                    if(!rs.isBeforeFirst())
                    {
                        //answer = answer + "NO PARTS USED FOR THIS VEHICLE";
                    }
                    if(rs != null)
                    {

                        while(rs.next())
                        {
                            if(!partsIDs.contains(rs.getString("PartsID")))
                            {
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
                            answer = answer + "Parts ID: " + rs.getString("ID") + "\n" + rs.getString("Name") + "\nCost: " + rs.getString("Cost")+"\n\n";
                            
                        }
                    }
                    answer = answer + checkSPCAllParts(conn, vehicles.get(i));
                    
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
    
    // method to check any parts sent to SPC or any vehicle sent to SPC
    public String checkSPCAllParts(Connection conn, String reg)
    {
        String answer = "";
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SPCBooking WHERE RegistrationNumber = '" + reg + "' ");
            if(rs.next())
            {
                do
                {
                    if(rs.getString("WorkOn").equals("Part"))
                    {
                        int id = rs.getInt("PartID");
                        double cost = rs.getDouble("Cost");
                        answer = answer + "SPC " + rs.getString("Type") + " on ";
                        rs = conn.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + id + "' ");
                        if(rs != null)
                        {
                            answer = answer + rs.getString("Name") + "(" + id + ")" + "\n+" + cost + "\n";
                        }
                    }
                }
                while(rs.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println("");
        }
        return answer;
    }
    
    
    // method to remove any duplicated
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
    }
    
    // method to close the database connection
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


