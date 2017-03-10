/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;


import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author Mustakim
 */
public class Controller implements Initializable {
    
    /************** BOOKING DETAILS **************/
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button Add_Button;
    @FXML
    private Button Edit_Button;
    @FXML
    private Button Show;    
    @FXML
    private TableView<BookingTable> Booking; 
    @FXML
    private TableColumn<BookingTable, Integer> BID;
    @FXML
    private TableColumn<BookingTable, String> Reg;
    @FXML
    private TableColumn<BookingTable, String> ToB;
    @FXML
    private TableColumn<BookingTable, Integer> MID;
    @FXML
    private TableColumn<BookingTable, String> BD;
    @FXML
    private TableColumn<BookingTable, String> BT;
    @FXML
    private TableColumn<BookingTable, String> RepDur;
    @FXML
    private TableColumn<BookingTable, Double> B;
    @FXML
    private ObservableList<BookingTable> allBookings;
    
    /************** ADD BOOKING **************/
    @FXML
    private AnchorPane AddBooking;
    @FXML
    private JFXDatePicker Date;
    @FXML
    private JFXDatePicker Time;
    @FXML
    private TextField Mechanic;
    @FXML
     private TextField Part1ID;
    @FXML
    private TextField Part5ID;
    @FXML
    private TextField Part4ID;
    @FXML
    private TextField Part3ID;
    @FXML
    private TextField Part2ID;
    @FXML
    private Button Clear;
    @FXML
    private Button Submit_Button;
    @FXML
    private Button Back_Button;
    @FXML
    private TextField Mileage;
    @FXML
    private TableColumn<?, ?> PartIDTable;
    @FXML
    private TableColumn<?, ?> PartNameTable;
    @FXML
    private TextField CustomerName;
    @FXML
    private TextField Vehicle;
    /*@FXML
    private TableView<CustomerTable> CustomerTable;
    @FXML
    private TableColumn<CustomerTable, String> CustFN;
    @FXML
    private TableColumn<CustomerTable, String> CustSN;
    @FXML
    private ObservableList<CustomerTable> allCustomers;*/
    
    /************** EDIT BOOKING **************/
    @FXML
    private AnchorPane EditBooking;
    @FXML
    private TextField Search_Bar;
    @FXML
    private Button Search_Button;
    @FXML
    private TextField RegNo;
    @FXML
    private TextField BookingType;
    @FXML
    private TextField MechanicID;
    @FXML
    private TextField Bill;
    @FXML
    private TextField BookingDate;
    @FXML
    private TextField BookingTime;
    @FXML
    private TextField RepairTime;
    @FXML
    private Button Clear_Button;
    @FXML
    private Button Delete_Button;
    @FXML
    private Button ShowE;
    @FXML
    private TableView<BookingTableE> BookingE; 
    @FXML
    private TableColumn<BookingTableE, Integer> BIDE;
    @FXML
    private TableColumn<BookingTableE, String> RegE;
    @FXML
    private TableColumn<BookingTableE, String> ToBE;
    @FXML
    private TableColumn<BookingTableE, Integer> MIDE;
    @FXML
    private TableColumn<BookingTableE, String> BDE;
    @FXML
    private TableColumn<BookingTableE, String> BTE;
    @FXML
    private TableColumn<BookingTableE, String> RepDurE;
    @FXML
    private TableColumn<BookingTableE, Double> BE;
    @FXML
    private ObservableList<BookingTableE> allBookingsE;
    @FXML
    private RadioButton CFN;
    @FXML
    private RadioButton CSN;
    @FXML
    private RadioButton RN;
    @FXML
    private RadioButton M;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }  
    
    /*************** BOOKING DETAILS ***************/ 
    // OPEN ADDBOOKING FROM BOOKINGDETAILS
    @FXML
    private void openAddBooking(ActionEvent event) throws IOException {
        try
        {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("AddBooking.fxml"));
            rootPane.getChildren().setAll(pane);
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
    }

    // OPEN EDITBOOKING FROM BOOKINGDETAILS
    @FXML
    private void openEditBooking(ActionEvent event) {
        try
        {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("EditBooking.fxml"));
            rootPane.getChildren().setAll(pane);
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
    
    }
    
    // SHOW BOOKINGS ON BOOKINGDETAILS PAGE
    @FXML
    private void ShowBookings(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allBookings = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM Booking");
            while(set.next()){
                allBookings.add(new BookingTable(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getString(5), set.getString(6), set.getString(7), set.getDouble(8))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        
        BID.setCellValueFactory(new PropertyValueFactory("BookingID"));
        Reg.setCellValueFactory(new PropertyValueFactory("RegNumber"));
        ToB.setCellValueFactory(new PropertyValueFactory("BookingType"));
        MID.setCellValueFactory(new PropertyValueFactory("MechanicID"));
        BD.setCellValueFactory(new PropertyValueFactory("BookingDate"));
        BT.setCellValueFactory(new PropertyValueFactory("BookingTime"));
        RepDur.setCellValueFactory(new PropertyValueFactory("RepairTime"));
        B.setCellValueFactory(new PropertyValueFactory("Bill"));

        Booking.setItems(allBookings);
    }
    
    /*************** ADD BOOKINGS ***************/ 
    // CLEAR ADDBOOKINGS PAGE
    @FXML
    private void AclearPage(ActionEvent event) {
        CustomerName.setText(null);
        Vehicle.setText(null);
        Mechanic.setText(null);
        Mileage.setText(null);
        Part1ID.setText(null);
        Part2ID.setText(null);
        Part3ID.setText(null);
        Part4ID.setText(null);
        Part5ID.setText(null); 
        Date.setValue(null);
        Time.setValue(null);
        
    }

    // SUBMIT DETAILS INTO DATABASE ON ADDBOOKINGS PAGE
    @FXML
    private void submitDetails(ActionEvent event) {
    }

    // OPEN BOOKINGDETAILS FROM ADDBOOKING
    @FXML
    private void AopenBookingDetails(ActionEvent event) {
        try
        {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("BookingDetails.fxml"));
            AddBooking.getChildren().setAll(pane);
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
    }
    
    /*************** EDIT BOOKINGS ***************/ 
    // CLEAR EDITBOOKINGS PAGE
    @FXML
    private void EclearPage(ActionEvent event) {
        RegNo.setText(null);
        BookingType.setText(null);
        MechanicID.setText(null);
        Bill.setText(null);
        BookingDate.setText(null);
        BookingTime.setText(null);
        RepairTime.setText(null);
    }
    
    // OPEN BOOKINGDETAILS FROM EDITBOOKING
    @FXML
    private void EopenBookingDetails(ActionEvent event) {
        try
        {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("BookingDetails.fxml"));
            EditBooking.getChildren().setAll(pane);
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
    }
    
    // SHOW BOOKINGS ON EDITBOOKINGS PAGE
    @FXML
    private void ShowBookingsE(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allBookingsE = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM Booking");
            while(set.next()){
                allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getString(5), set.getString(6), set.getString(7), set.getDouble(8))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        
        BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
        RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
        ToBE.setCellValueFactory(new PropertyValueFactory("BookingType"));
        MIDE.setCellValueFactory(new PropertyValueFactory("MechanicID"));
        BDE.setCellValueFactory(new PropertyValueFactory("BookingDate"));
        BTE.setCellValueFactory(new PropertyValueFactory("BookingTime"));
        RepDurE.setCellValueFactory(new PropertyValueFactory("RepairTime"));
        BE.setCellValueFactory(new PropertyValueFactory("Bill"));

        BookingE.setItems(allBookingsE);
    }
    
    // SEARCH BOOKINGS ON EDITBOOKINGS PAGE
    @FXML
    private void searchBookings(ActionEvent event) {
            Connection connect = null;
            PreparedStatement stmt = null;
            // SEARCH USING CUSTOMER FIRST NAME
            if(CFN.isSelected()) {
                try
                {   
                    connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                    String sql = "SELECT * FROM Booking INNER JOIN Customer_Accounts ON Booking.CustomerID = Customer_Accounts.ID WHERE Firstname like '" + Search_Bar.getText() + "%'";
                    stmt = connect.prepareStatement(sql);
                    allBookingsE = FXCollections.observableArrayList();
                    ResultSet set = stmt.executeQuery();
                    while(set.next()){
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getString(5), set.getString(6), set.getString(7), set.getDouble(8))); 
                    }
                    stmt.close();
                    set.close();
                    connect.close();
                }
                catch(SQLException e)
                {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
                }

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                ToBE.setCellValueFactory(new PropertyValueFactory("BookingType"));
                MIDE.setCellValueFactory(new PropertyValueFactory("MechanicID"));
                BDE.setCellValueFactory(new PropertyValueFactory("BookingDate"));
                BTE.setCellValueFactory(new PropertyValueFactory("BookingTime"));
                RepDurE.setCellValueFactory(new PropertyValueFactory("RepairTime"));
                BE.setCellValueFactory(new PropertyValueFactory("Bill"));

                BookingE.setItems(allBookingsE);
        }
            // SEARCH USING CUSTOMER SURNAME
            else if(CSN.isSelected()) {
                try
                {   
                    connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                    String sql = "SELECT * FROM Booking INNER JOIN Customer_Accounts ON Booking.CustomerID = Customer_Accounts.ID WHERE Surname like '" + Search_Bar.getText() + "%'";
                    stmt = connect.prepareStatement(sql);
                    allBookingsE = FXCollections.observableArrayList();
                    ResultSet set = stmt.executeQuery();
                    while(set.next()){
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getString(5), set.getString(6), set.getString(7), set.getDouble(8))); 
                    }
                    stmt.close();
                    set.close();
                    connect.close();
                }
                catch(SQLException e)
                {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
                }

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                ToBE.setCellValueFactory(new PropertyValueFactory("BookingType"));
                MIDE.setCellValueFactory(new PropertyValueFactory("MechanicID"));
                BDE.setCellValueFactory(new PropertyValueFactory("BookingDate"));
                BTE.setCellValueFactory(new PropertyValueFactory("BookingTime"));
                RepDurE.setCellValueFactory(new PropertyValueFactory("RepairTime"));
                BE.setCellValueFactory(new PropertyValueFactory("Bill"));

                BookingE.setItems(allBookingsE);
        }
            // SEARCH USING VEHICLE REGISTRATION NUMBER
            else if(RN.isSelected()) {
                try
                {   
                    connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                    String sql = "SELECT * FROM Booking WHERE RegistrationNumber like '" + Search_Bar.getText() + "%'";
                    stmt = connect.prepareStatement(sql);
                    allBookingsE = FXCollections.observableArrayList();
                    ResultSet set = stmt.executeQuery();
                    while(set.next()){
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getString(5), set.getString(6), set.getString(7), set.getDouble(8))); 
                    }
                    stmt.close();
                    set.close();
                    connect.close();
                }
                catch(SQLException e)
                {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
                }

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                ToBE.setCellValueFactory(new PropertyValueFactory("BookingType"));
                MIDE.setCellValueFactory(new PropertyValueFactory("MechanicID"));
                BDE.setCellValueFactory(new PropertyValueFactory("BookingDate"));
                BTE.setCellValueFactory(new PropertyValueFactory("BookingTime"));
                RepDurE.setCellValueFactory(new PropertyValueFactory("RepairTime"));
                BE.setCellValueFactory(new PropertyValueFactory("Bill"));

                BookingE.setItems(allBookingsE);
        }
            // SEARCH USING VEHICLE MANUFACTURER
            else if(M.isSelected()) {
                try
                {   
                    connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                    String sql = "SELECT * FROM Booking INNER JOIN Vehicles ON Booking.RegistrationNumber = Vehicles.RegistrationNumber WHERE Make like '" + Search_Bar.getText() + "%'";
                    stmt = connect.prepareStatement(sql);
                    allBookingsE = FXCollections.observableArrayList();
                    ResultSet set = stmt.executeQuery();
                    while(set.next()){
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getString(5), set.getString(6), set.getString(7), set.getDouble(8))); 
                    }
                    stmt.close();
                    set.close();
                    connect.close();
                }
                catch(SQLException e)
                {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
                }

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                ToBE.setCellValueFactory(new PropertyValueFactory("BookingType"));
                MIDE.setCellValueFactory(new PropertyValueFactory("MechanicID"));
                BDE.setCellValueFactory(new PropertyValueFactory("BookingDate"));
                BTE.setCellValueFactory(new PropertyValueFactory("BookingTime"));
                RepDurE.setCellValueFactory(new PropertyValueFactory("RepairTime"));
                BE.setCellValueFactory(new PropertyValueFactory("Bill"));

                BookingE.setItems(allBookingsE);
        }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pick Search Type");
                alert.setHeaderText("Search Type left unselected");
                alert.setContentText("Select a Search Type");
                alert.showAndWait();
            }
    }

    /*@FXML
    private void ShowCustomers(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allCustomers = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM Customer_Accounts");
            while(set.next()){
                allCustomers.add(new CustomerTable(set.getString(1), set.getString(2))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        
        CustFN.setCellValueFactory(new PropertyValueFactory("CustFN"));
        CustSN.setCellValueFactory(new PropertyValueFactory("CustSN"));

        CustomerTable.setItems(allCustomers);
    }*/
    
    @FXML
    private void deleteBooking(ActionEvent event) {
    }         
}
