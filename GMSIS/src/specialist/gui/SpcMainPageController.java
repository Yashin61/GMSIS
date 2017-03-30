/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import com.jfoenix.controls.JFXCheckBox;
import static common.TemplateController.userType;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import specialist.logic.SpcBookings;
import specialist.logic.SpecialistDB;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class SpcMainPageController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField searchSPC;

    @FXML
    private JFXCheckBox custName;

    @FXML
    private JFXCheckBox vehiRegistration;
    
    @FXML
    private TableView<SpcBookings> dataTable;

    @FXML
    private TableColumn<SpcBookings, String> tableSpcName;

    @FXML
    private TableColumn<SpcBookings, String> tableCustomerName;

    @FXML
    private TableColumn<SpcBookings, String> tableRegistrationNo;

    @FXML
    private TableColumn<SpcBookings, String> tableWorkOn;
    
    @FXML
    private TableColumn<SpcBookings, String> tableReturned;

    @FXML
    private Label theSpcName;

    @FXML
    private Label theVehicleName;

    @FXML
    private Label theRegistrationNo;

    @FXML
    private Label theCustomerId;

    @FXML
    private Label theCustomerName;

    @FXML
    private Label theCustomerType;

    @FXML
    private Label theCustomerPhone;

    @FXML
    private Label theCustomerEmail;

    @FXML
    private Label theCustomerAddress;

    @FXML
    private Label theCustomerPostcode;

    @FXML
    private ListView<String> spcList;
    
    @FXML
    private ObservableList<SpcBookings> allSPCBooking;
    
    //not pushed to github so doing it again.

    //switch to the homepage
    @FXML
    private void homePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the customer page
    @FXML
    private void customerPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/customer/gui/CustomerRealPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    // switch to the vehicle page
    @FXML
    private void vehiclePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the bookings page
    @FXML
    private void bookingsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/diagrep/gui/BookingDetails.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the parts page
    @FXML
    private void partsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/parts/gui/PartsPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the specialists page
    @FXML
    private void specialistsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("spcMainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the 2nd specialists page - only accessable by system administraters
    @FXML
    private void spcAdmin(ActionEvent event) throws IOException
    {
        if(userType.equals("ADMIN"))
        {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("specialistGUI.fxml" ));
            rootPane.getChildren().setAll(pane);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("ADMIN ACCESS ONLY");
            alert.setContentText("");
            alert.showAndWait();
        }
    }
    
    //resets all the choices that has been made
    @FXML
    private void Reset(ActionEvent event) {
        spcList.getSelectionModel().clearSelection();
        custName.setSelected(false);
        vehiRegistration.setSelected(false);
        searchSPC.clear();
        showData();
    }
    
    //initailse / show the main spc booking table with all the data (unselect any selections)
    public void showData()
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
            stmt = connect.createStatement();
            allSPCBooking= FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking");
            while(set.next()){
                allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
        
        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
        tableReturned.setCellValueFactory(new PropertyValueFactory("SpcReturned"));
        dataTable.setItems(allSPCBooking);
    }
    
    //initialising the listview of the spc
    @FXML
    private void setSPCList()
    {
        SpecialistDB a = new SpecialistDB();
        String [] listOfSPC = a.getSPC();
        for(int i=0; i<10; i++)
        {
            String lbl = listOfSPC[i];
            spcList.getItems().add(lbl);
        }
    }
    
    //get the details of the spcBooking. like the customer (details) which is linked to the vehicle / parts
    @FXML
    private void setDetails(SpcBookings spc){
        if(spc != null)
        {
            theSpcName.setText(spc.getSpcBookingName());
            Connection connect = null;
            Statement stmt = null;
            //gets the basic information of the vehicle from the database
            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                ResultSet set = stmt.executeQuery("SELECT SPCBooking.RegistrationNumber, Vehicles.RegistrationNumber, Vehicles.Model, Vehicles.VehicleType "
                        + "FROM SPCBooking INNER JOIN Vehicles ON SPCBooking.RegistrationNumber = Vehicles.RegistrationNumber WHERE SPCBooking.ID = '" + spc.getSpcBookingId()+ "' ");
                while(set.next())
                {
                    theVehicleName.setText(set.getString("Model") + set.getString("VehicleType"));
                    theRegistrationNo.setText(set.getString("RegistrationNumber"));
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
            
            //gets the details of the customers linked to the bookings / parts / vehicle
            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                ResultSet set = stmt.executeQuery("SELECT SPCBooking.CustomerID, Customer_Accounts.ID, Customer_Accounts.Firstname, Customer_Accounts.Surname, Customer_Accounts.Address,"
                        + " Customer_Accounts.Postcode, Customer_Accounts.Phone, Customer_Accounts.Email, Customer_Accounts.Account "
                        + "FROM SPCBooking INNER JOIN Customer_Accounts ON SPCBooking.CustomerID = Customer_Accounts.ID WHERE SPCBooking.ID = '" + spc.getSpcBookingId()+ "' ");
                while(set.next())
                {
                    theCustomerId.setText(set.getString("ID"));
                    theCustomerName.setText(set.getString("Firstname")+" "+set.getString("Surname"));
                    theCustomerType.setText(set.getString("Account"));
                    theCustomerPhone.setText(set.getString("Phone"));
                    theCustomerEmail.setText(set.getString("Email"));
                    theCustomerAddress.setText(set.getString("Address"));
                    theCustomerPostcode.setText(set.getString("Postcode"));
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            theSpcName.setText("");
            theVehicleName.setText("");
            theRegistrationNo.setText("");
            theCustomerId.setText("");
            theCustomerName.setText("");
            theCustomerType.setText("");
            theCustomerPhone.setText("");
            theCustomerEmail.setText("");
            theCustomerAddress.setText("");
            theCustomerPostcode.setText("");
        }
    }
    
    //sets the spcBooking table according to the spc listed in the spc list view
    @FXML
    private void setTable(String name)
    {
        Connection connect = null;
        Statement stmt = null;
        
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
            stmt = connect.createStatement();
            allSPCBooking= FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '" + name +"';");
            while(set.next()){
                allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13)));  
            }
            stmt.close();
            set.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }

        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
        tableReturned.setCellValueFactory(new PropertyValueFactory("SpcReturned"));
        dataTable.setItems(allSPCBooking);
    }
    
    //view the list of parts and vehicles at SPC outstanding
    @FXML
    private void showOutstanding(ActionEvent event)
    {
        Connection connect = null;
        Statement stmt = null;
        String name = spcList.getSelectionModel().getSelectedItem();
        if(name!=null)
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '"+name+"' "
                        + "AND Returned = 'No';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE Returned = 'No';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        

        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
        tableReturned.setCellValueFactory(new PropertyValueFactory("SpcReturned"));
        dataTable.setItems(allSPCBooking);
    }
    
    //this function sets the booking table to only view the booking for parts / only parts that have been sent to SPC
    @FXML
    private void setTablePart(ActionEvent event)
    {
        Connection connect = null;
        Statement stmt = null;
        String name = spcList.getSelectionModel().getSelectedItem();
        if(name!=null)
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '"+name+"' "
                        + "AND WorkOn = 'Part';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE WorkOn = 'Part';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        

        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
        tableReturned.setCellValueFactory(new PropertyValueFactory("SpcReturned"));
        dataTable.setItems(allSPCBooking);
    }
    
    //this function sets the booking table to only view the booking for vehicle / only vehicle that have been sent to SPC
    @FXML
    private void setTableVehicle(ActionEvent event)
    {
        Connection connect = null;
        Statement stmt = null;
        String name = spcList.getSelectionModel().getSelectedItem();
        if(name!=null)
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '"+name+"' "
                        + "AND WorkOn = 'Vehicle';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE WorkOn = 'Vehicle';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        

        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
        tableReturned.setCellValueFactory(new PropertyValueFactory("SpcReturned"));
        dataTable.setItems(allSPCBooking);
    }

    //view the details for the specific vehicle / part listed as booking
    @FXML
    private void showPartVehicleDetails(ActionEvent event)
    {
        Connection connect = null;
        Statement stmt = null;
        SpcBookings spcBooking = dataTable.getSelectionModel().getSelectedItem();
        if(spcBooking != null)
        {
            if(spcBooking.getSpcWOn().equals("Vehicle"))
            {
                try
                {   
                    connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                    stmt = connect.createStatement();
                    ResultSet set = stmt.executeQuery("SELECT * FROM Vehicles WHERE RegistrationNumber ='"+spcBooking.getSpcRNumber()+ "';");
                    while(set.next()){
                        System.out.println("Hello");
                        String vehicle = "Vehicle type = "+set.getString("VehicleType");
                        String make = "Make = "+set.getString("Make")+"\n"
                                +"Model = "+set.getString("Model")+"\n"
                                +"Colour = "+set.getString("Colour")+"\n"
                                +"Year = "+set.getString("Year")+"\n";
                        String engSize = "Engine size = "+set.getString("EngineSize")+"\n"
                                +"Fuel type = "+set.getString("FuelType")+"\n"
                                +"Mileage = "+set.getString("Mileage")+"\n";
                        String mot = "MOT renewal date = "+set.getString("MOTRenewalDate")+"\n"
                                +"Last service date = "+set.getString("LastServiceDate")+"\n";
                        String dates = "Expected delivery date to SPC = "+spcBooking.getSpcDDate()+"\n"
                                +"Arrived at SPC ? = "+spcBooking.getSpcArrive()+"\n"
                                +"Expected return date from SPC = "+spcBooking.getSpcRDate()+"\n"
                                +"Returned from SPC ? = "+spcBooking.getSpcReturn()+"\n";
                        String repairType = "Type of specialist repais being done on "+spcBooking.getSpcWOn()+" = "+spcBooking.getSpcType();
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Vehicle Details");
                        alert.setHeaderText(vehicle);
                        alert.setContentText(make+engSize+mot+dates+repairType);
                        alert.showAndWait();
                    }
                    stmt.close();
                    set.close();
                    connect.close();
                }catch(SQLException e)
                {
                    Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            else
            {
                try
                {   
                    connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                    stmt = connect.createStatement();
                    ResultSet set = stmt.executeQuery("SELECT * FROM Parts WHERE ID ='"+spcBooking.getSpcPartId() + "';");
                    while(set.next()){
                        System.out.println("Bye");
                        String partId = "Part ID = "+set.getString("ID")+"\n";
                        String part = "Part ="+set.getString("Name")+"\n"
                                +"Model = "+set.getString("Model")+"\n"
                                +"Make = "+set.getString("Make")+"\n"
                                +"Description = "+set.getString("Description")+"\n";
                        String dates = "Expected delivery date to SPC = "+spcBooking.getSpcDDate()+"\n"
                                +"Arrived at SPC ? = "+spcBooking.getSpcArrive()+"\n"
                                +"Expected return date from SPC = "+spcBooking.getSpcRDate()+"\n"
                                +"Returned from SPC ? = "+spcBooking.getSpcReturn()+"\n";
                        String repairType = "Type of specialist repais being done on "+spcBooking.getSpcWOn()+" = "+spcBooking.getSpcType();
                        //JOptionPane.showMessageDialog(null,partId+part);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Part Details");
                        alert.setHeaderText(partId);
                        alert.setContentText(part+dates+repairType);
                        alert.showAndWait();
                    }
                    stmt.close();
                    set.close();
                    connect.close();
                }catch(SQLException e)
                {
                    Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        else
        {
            //JOptionPane.showMessageDialog(null,"Please select what you want to search");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please select what you want to details of");
            alert.showAndWait();
        }
    }
    
    // this function shows all the items which has been returned from the spc / all the spc repairs/bookings which has been completed
    @FXML
    private void showReturned(ActionEvent event)
    {
        Connection connect = null;
        Statement stmt = null;
        String name = spcList.getSelectionModel().getSelectedItem();
        if(name!=null)
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '"+name+"' "
                        + "AND Returned = 'Yes';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13)));  
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE Returned = 'Yes';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        

        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
        tableReturned.setCellValueFactory(new PropertyValueFactory("SpcReturned"));
        dataTable.setItems(allSPCBooking);
    }
    
    //switch to add spc booking page
    @FXML
    private void addSpcBookingPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/gui/spcAddBooking.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    //switch to edit spc booking page
    @FXML
    private void editSpcBookingPage(ActionEvent event) throws IOException, ParseException
    {
        SpcBookings spcBookings = dataTable.getSelectionModel().getSelectedItem();
        if(spcBookings == null)
        {
            //JOptionPane.showMessageDialog(null,"Please select a SPC");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please select a SPC Booking that you want to edit");
            alert.showAndWait();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spcEditBooking.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            SpcEditBookingController Econtroller = fxmlLoader.<SpcEditBookingController>getController();
            Econtroller.setAllFields(spcBookings);
            Stage stage = new Stage();
            stage.setTitle("Edit SPC");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            showData();
        } 
    }
    
    //delete an spc booking for part / vehicle
    @FXML
    private void deleteSpcBooking(ActionEvent event)
    {
        SpcBookings spcBooking = dataTable.getSelectionModel().getSelectedItem();
        if(spcBooking == null)
        {
            //JOptionPane.showMessageDialog(null,"Please select a SPC Booking");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please select a SPC Booking that you want to cancel / delete");
            alert.showAndWait();
            
        }
        else
        {
            //int row = dataTable.getSelectionModel().getSelectedIndex();
            int row = spcBooking.getSpcBookingId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Please Confirm");
            alert.setContentText("Are you sure you want to delete the SPC?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // if the user chooses to proceed
                SpecialistDB a = new SpecialistDB();
                a.deleteSPCBooking(row);
            } 
            else { /* the user closes the confirmation dialog*/}
            showData();
        }
    }
    
    //mark spc item as arrived to the spc
    @FXML
    private void spcItemArrived(ActionEvent event)
    {
        SpcBookings spcBooking = dataTable.getSelectionModel().getSelectedItem();
        if(spcBooking == null)
        {
            //JOptionPane.showMessageDialog(null,"Please select a SPC Booking");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please select a SPC Booking that you want to mark as arrived to SPC");
            alert.showAndWait();
            
        }
        else
        {
            //int row = dataTable.getSelectionModel().getSelectedIndex();
            int row = spcBooking.getSpcBookingId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Please Confirm");
            alert.setContentText("Has the item arrived to the SPC?");
            
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE); 
            alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton){
                // if the user chooses to click yes
                spcBooking.setSpcArrive("Yes");
            } 
            else if(result.get() == noButton)
            {
                // if the user chooses to click no
                spcBooking.setSpcArrive("No");
            }
            else { /* the user closes the confirmation dialog*/}
        }
    }
    
    //mark spc item as complete / returned
    @FXML
    private void spcItemReturned(ActionEvent event)
    {
        SpcBookings spcBooking = dataTable.getSelectionModel().getSelectedItem();
        if(spcBooking == null)
        {
            //JOptionPane.showMessageDialog(null,"Please select a SPC Booking");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please select a SPC Booking that you want to mark as completed");
            alert.showAndWait();
            
        }
        else
        {
            //int row = dataTable.getSelectionModel().getSelectedIndex();
            int row = spcBooking.getSpcBookingId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Please Confirm");
            alert.setContentText("Is the repairs on the item completed");
            
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE); 
            alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton){
                // if the user chooses to click yes
                spcBooking.setSpcReturn("Yes");
            } 
            else if(result.get() == noButton)
            {
                // if the user chooses to click no
                spcBooking.setSpcReturn("No");
            }
            else { /* the user closes the confirmation dialog*/}
        }
    }
    
    // if customer name checkbox is selected, disable search by registration checkbox
    @FXML
    private void customerName(ActionEvent event)
    {
        if(custName.isSelected())
        {
            vehiRegistration.setSelected(false);
        }
    }
    
    // if vehicle registration checkbox is selected, disable search by customer name
    @FXML
    private void vehicleReg(ActionEvent event)
    {
        if(vehiRegistration.isSelected())
        {
            custName.setSelected(false);
        }
    }
    
    //search for the spc / spcbooking by customer names or vehicle registration
    @FXML
    private void searchSPCBooking(ActionEvent event)
    {
        Connection connect = null;
        Statement stmt = null;
        
        if(custName.isSelected())
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking INNER JOIN Customer_Accounts ON SPCBooking.CustomerID = Customer_Accounts.ID"
                        + " WHERE Firstname like '%" + searchSPC.getText() + "%' OR Surname like '%" + searchSPC.getText() +"%'");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else if(vehiRegistration.isSelected())
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE RegistrationNumber like '%" + searchSPC.getText() + "%'");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12), set.getInt(13))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            //JOptionPane.showMessageDialog(null,"Please select what you want to search");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please select your method of searching - by customer name or registration number");
            alert.showAndWait();
            
        }
        
        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
        tableReturned.setCellValueFactory(new PropertyValueFactory("SpcReturned"));
        dataTable.setItems(allSPCBooking);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setSPCList();
        spcList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        spcList.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setTable(newValue));
        showData();
        setDetails(null);
        
        dataTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setDetails(newValue));
        custName.setSelected(false);
        vehiRegistration.setSelected(false);
    }   
}
