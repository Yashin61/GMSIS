


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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Label noBookings;
    
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
                //String st = words[7].substring(8);
                String id = words[3].substring(12);
                //realStatus.setText(words[7].substring(8));
                String st ="";
                for(int a=0; a<words.length; a++)
                {
                    if(words[a].equals("Status: OUTSTANDING"))
                    {
                        st = "OUTSTANDING";
                        break;
                    }
                    else if(words[a].equals("Status: SETTLED"))
                    {
                        st = "SETTLED";
                        break;
                    }
                    else{}
                }
                realStatus.setText(st);
                realStatus.setVisible(true);
                //System.out.println(st);    
                
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
            
            ResultSet rs = conn.createStatement().executeQuery( "SELECT Vehicles.CustomerID, Vehicles.RegistrationNumber, Vehicles.WarrantyID, Booking.RegistrationNumber, "
                    + "Booking.BookingID, Booking.Bill, Booking.BookingType, Booking.BookingDate, Booking.BookingTime "
                    + "FROM Vehicles INNER JOIN Booking ON Vehicles.RegistrationNumber = Booking.RegistrationNumber WHERE Vehicles.CustomerID = '" + c.getID() + "' ");
            
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
                    
                    payment = checkWarranty(rs.getInt("BookingID"));
                    double[] totalBill = {0.00};                 
                    String spcVehicle = checkSPCVehicle(c.getID(), rs.getString("RegistrationNumber"), rs.getInt("BookingID"), totalBill);
                    String spcParts = checkSPCParts(c.getID(), rs.getString("RegistrationNumber"), rs.getInt("BookingID"), totalBill);
                    totalBill[0] = totalBill[0]+rs.getDouble("Bill");
                    data.add( "\nDate: " + status + "\n"+ "Vehicle: " + rs.getString("RegistrationNumber") + "\n" 
                            + "Booking ID: " + rs.getString("BookingID") + "\n" + "Booking Type: " + rs.getString("BookingType") 
                            + "\n"  + "Time: " + rs.getString("BookingTime") + "\n" + spcVehicle + spcParts +"Total Bill: "+totalBill[0] + "\nStatus: " + payment + "\n\n\n");
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
        catch (SQLException ex)
        {
            Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //checks if the vehicle had an spc repair
    private String checkSPCVehicle(int custId, String reg, int bookingID, double[] totalBill)
    {
        Connection connect = null;
        Statement stmt = null;
        String answer = "";
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SPCBooking WHERE BookingID = " + bookingID);
            if(rs != null)
            {
                answer = answer+"Special "+rs.getString("Type")+" on Vehicle: "+rs.getString("RegistrationNumber")+"\n"
                           +"+£"+rs.getString("Cost")+"\n";
                if(rs.getString("Returned").equals("No"))
                {
                    answer = answer + "SPECIAL REPAIRS ARE STILL UNDER PROGRESS \n";
                }
                totalBill[0] = totalBill[0]+rs.getDouble("Cost");
            }
            stmt.close();
            rs.close();
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }
    
    //checks if any of the parts of the vehicle has spc repair
    private String checkSPCParts(int custId, String reg, int bookingID, double[] totalBill)
    {
        Connection connect = null;
        Statement stmt = null;
        String answer = "";
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SPCBooking INNER JOIN Parts ON SPCBooking.PartID = Parts.ID WHERE BookingID = " + bookingID);
            if(rs != null)
            {
                answer = answer+"Special "+rs.getString("Type")+" on Part ID: "+rs.getString("PartID")+"\n"
                            +"Part Name: "+rs.getString("Name")+"\n"
                           +"+£"+rs.getString("Cost")+"\n";
                if(rs.getString("Returned").equals("No"))
                {
                    answer = answer + "SPECIAL REPAIRS ARE STILL UNDER PROGRESS \n";
                }
                totalBill[0] = totalBill[0]+rs.getDouble("Cost");
            }
            stmt.close();
            rs.close();
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }
    
    public void printSettled()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PAYMENT COMPLETED");
        alert.setHeaderText("SETTLED");
        alert.setContentText("You have settled the bill");
        alert.showAndWait();
    }
    
    public String checkWarranty( int id) 
    {   
        Connection connect = null;
        Statement stmt = null;
        String answer = "";
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BillsPaid WHERE BookingID = '" + id + "' ");
            if(rs != null)
            {
                return rs.getString("SettleBill");
            }
            
            stmt.close();
            rs.close();
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        Connection connect = null;
        Statement stmt = null;
        ArrayList<String> vehicles = new ArrayList<String>();
        ObservableList<String> data = FXCollections.observableArrayList();

        try
        {
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicles WHERE CustomerID = '" + c.getID() + "' ");
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
            }

            stmt.close();
            rs.close();
            connect.close();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList <Integer> partsIDs = new ArrayList<Integer>();
        for(int i=0; i< vehicles.size(); i++)
        {
            String answer = vehicles.get(i) + "\n";
            //boolean partsUsed = false;
            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PartsUsed WHERE RegistrationNumber = '" + vehicles.get(i) + "' ");
                if(!rs.isBeforeFirst())
                {
                    answer = answer + "NO PARTS USED FOR THIS VEHICLE";
                    //partsUsed = false;
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
                            //partsUsed = true;
                        }
                    }
                }
                stmt.close();
                rs.close();
                connect.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ArrayList<String> specialists = new ArrayList<String>();
            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT * FROM SPCBooking INNER JOIN Parts ON SPCBooking.PartID = Parts.ID WHERE SPCBooking.RegistrationNumber = '" + vehicles.get(i) + "' ");
                while(rs.next())
                {
                    specialists.add("\n Special "+rs.getString("Type")+" on Part ID: "+rs.getString("PartID")+"\n"
                            +"Part Name: "+rs.getString("Name")+"\n"
                            +"+£"+rs.getString("Cost"));
                }
                rs.close();

                stmt.close();
                connect.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();

                if(partsUsed)
                {
                    for(int a=0; a<partsIDs.size(); a++)
                    {
                        ResultSet rs = stmt.executeQuery("SELECT * FROM SPCBooking WHERE PartID = " + partsIDs.get(a));
                        if(rs != null)
                        {
                            specialists[a] = "\n Special "+rs.getString("Type")+": +£"+rs.getString("Cost");
                        }
                        else{specialists[a]="";}
                        rs.close();
                    }
                }
                stmt.close();

                connect.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();

                String blank="";
                for(int sp=0; sp<specialists.size(); sp++)
                {
                    blank = blank +specialists.get(sp);
                }

                for(int j=0; j<partsIDs.size(); j++)
                {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(j) + "' ");
                    if(rs != null)
                    {
                        answer = answer + "Parts ID: "+ partsIDs.get(j) + "\nName: " + rs.getString("Name") + "Cost: \n"+ rs.getString("Cost")+ "\n\n";
                    }
                    rs.close();
                }
                answer = answer+blank+ "\n\n";
                stmt.close();
                connect.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(viewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*
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


                String[] specialists = new String[partsIDs.size()];
                for(int a=0; a<partsIDs.size(); a++)
                {
                    rs = conn.createStatement().executeQuery("SELECT * FROM SPCBooking WHERE PartID = " + partsIDs.get(a));
                    if(rs != null)
                    {
                        specialists[a] = "\n Special "+rs.getString("Type")+": +£"+rs.getString("Cost");
                    }
                    else{specialists[a]="";}
                }

                for(int j=0; j<partsIDs.size(); j++)
                {
                    rs = conn.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(j) + "' ");
                    if(rs != null)
                    {
                        answer = answer + "Parts ID: "+ partsIDs.get(j) + "\nName: " + rs.getString("Name") + "Cost: \n"+ rs.getString("Cost") +specialists[j]+ "\n\n";
                    }
                }/*
                for(int j=0; j<partsIDs.size(); j++)
                {
                    rs = conn.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(j) + "' ");
                    if(rs != null)
                    {
                        answer = answer + "Parts ID: "+ partsIDs.get(j) + "\nName: " + rs.getString("Name") + "Cost: \n"+ rs.getString("Cost") + "\n\n";
                    }
                }*/
            data.add(answer);
        }
        allParts.setItems(data);
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


