/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
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

    @FXML
    private void home(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void spcAdmin(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("specialistGUI.fxml" ));
        rootPane.getChildren().setAll(pane);
    }
   
    
    //resets all the choices that has been made
    @FXML
    private void Reset(ActionEvent event) {
        custName.setSelected(false);
        vehiRegistration.setSelected(false);
        setSPCList();
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
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allSPCBooking= FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking");
            while(set.next()){
                allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
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
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
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
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allSPCBooking= FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '" + name +"';");
            while(set.next()){
                allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '"+name+"' "
                        + "AND WorkOn = 'Part';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE WorkOn = 'Part';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE SPCname = '"+name+"' "
                        + "AND WorkOn = 'Vehicle';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE WorkOn = 'Vehicle';");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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
                    connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                    stmt = connect.createStatement();
                    ResultSet set = stmt.executeQuery("SELECT * FROM Vehicles WHERE RegistrationNumber ='"+spcBooking.getSpcRNumber()+ "';");
                    while(set.next()){
                        System.out.println("Hello");
                        String vehicle = "Vehicle type = "+set.getString("VehicleType")+"\n";
                        String make = "Make = "+set.getString("Make")+"\n"
                                +"Model = "+set.getString("Model")+"\n"
                                +"Colour = "+set.getString("Colour")+"\n"
                                +"Year = "+set.getString("Year")+"\n";
                        String engSize = "Engine size = "+set.getString("EngineSize")+"\n"
                                +"Fuel type = "+set.getString("FuelType")+"\n"
                                +"Mileage = "+set.getString("Mileage")+"\n";
                        String mot = "MOT renewal date = "+set.getString("MOTRenewalDate")+"\n"
                                +"Last service date = "+set.getString("LastServiceDate")+"\n";
                        JOptionPane.showMessageDialog(null,vehicle+make+engSize+mot);
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
                    connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                    stmt = connect.createStatement();
                    ResultSet set = stmt.executeQuery("SELECT * FROM Parts WHERE ID ='"+spcBooking.getSpcPartId() + "';");
                    while(set.next()){
                        System.out.println("Bye");
                        String partId = "Part ID = "+set.getString("ID")+"\n";
                        String part = "Part ="+set.getString("Name")+"\n"
                                +"Model = "+set.getString("Model")+"\n"
                                +"Make = "+set.getString("Make")+"\n"
                                +"Description = "+set.getString("Description")+"\n";
                        JOptionPane.showMessageDialog(null,partId+part);
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
            JOptionPane.showMessageDialog(null,"Please select what you want to search");
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
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking INNER JOIN Customer_Accounts ON SPCBooking.CustomerID = Customer_Accounts.ID WHERE Firstname like '%" + searchSPC.getText() + "%' OR Surname like '%" + searchSPC.getText() +"%'");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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

            dataTable.setItems(allSPCBooking);
        }
        else if(vehiRegistration.isSelected())
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking WHERE RegistrationNumber like '%" + searchSPC.getText() + "%'");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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

            dataTable.setItems(allSPCBooking);
        }
        else if(custName.isSelected() && vehiRegistration.isSelected())
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPCBooking= FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking INNER JOIN Customer_Accounts ON "
                        + "SPCBooking.CustomerID = Customer_Accounts.ID WHERE Firstname like '%" + searchSPC.getText() + "%' "
                                + "OR Surname like '%" + searchSPC.getText() +"%' "
                                        + "OR RegistrationNumber like '%" + searchSPC.getText() + "%'");
                while(set.next()){
                    allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                            set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8),
                            set.getInt(9), set.getString(10), set.getString(11), set.getDouble(12))); 
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

            dataTable.setItems(allSPCBooking);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Please select what you want to search");
        }
        
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
