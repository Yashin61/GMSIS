/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;


import diagrep.*;
import com.jfoenix.controls.JFXDatePicker;
import diagrep.gui.BookingTable;
import diagrep.gui.Database;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 *
 * @author Mustakim
 */
public class Controller implements Initializable {
    
    /*******BOOKING DETAILS*******/
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
    
    /*******ADD BOOKING*******/
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
    
    /*******EDIT BOOKING*******/
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
    private void openAddBooking(ActionEvent event) throws IOException {
        try
        {
            /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddBooking.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();*/
            AnchorPane pane = FXMLLoader.load(getClass().getResource("AddBooking.fxml"));
            rootPane.getChildren().setAll(pane);
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
    }

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

    @FXML
    private void submitDetails(ActionEvent event) {
    }

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
    
    @FXML
    private void ShowBookings(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/diagrep/gui/Records.db");
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
    
    
    @FXML
    private void searchBookings(ActionEvent event) {
    }

    @FXML
    private void deleteBooking(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }       
    
}
