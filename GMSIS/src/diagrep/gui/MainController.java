/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;

import diagrep.logic.BookingTable;
import diagrep.logic.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class MainController implements Initializable {

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
    private TableColumn<BookingTable, Integer> Mile;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ShowAllBookings();
    }    

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
        ShowAllBookings();
    }
    
    private void ShowAllBookings() {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allBookings = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM Booking");
            while(set.next()){
                allBookings.add(new BookingTable(set.getInt(1), set.getString(2),set.getInt(3), set.getString(4),
                                                 set.getInt(5), set.getString(6), set.getString(7),
                                                 set.getString(8), set.getDouble(9))); 
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
        Mile.setCellValueFactory(new PropertyValueFactory("Mileage"));
        ToB.setCellValueFactory(new PropertyValueFactory("BookingType"));
        MID.setCellValueFactory(new PropertyValueFactory("MechanicID"));
        BD.setCellValueFactory(new PropertyValueFactory("BookingDate"));
        BT.setCellValueFactory(new PropertyValueFactory("BookingTime"));
        RepDur.setCellValueFactory(new PropertyValueFactory("RepairTime"));
        B.setCellValueFactory(new PropertyValueFactory("Bill"));

        Booking.setItems(allBookings);
    }
    
    // SHOW PAST BOOKINGS ON BOOKINGDETAILS PAGE
    @FXML
    private void ShowPastBookings(ActionEvent event) throws ParseException {
        Connection connect = null;
        Statement stmt = null;
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allBookings = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM Booking");
            while(set.next()){
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date dateobj = new Date();
                String date = set.getString(6);
                Date date1 = format.parse(date);
                if (date1.compareTo(dateobj) <= 0)
                {
                    allBookings.add(new BookingTable(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
                                                     set.getInt(5), set.getString(6), set.getString(7),
                                                     set.getString(8), set.getDouble(9))); 
                }
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
        Mile.setCellValueFactory(new PropertyValueFactory("Mileage"));
        ToB.setCellValueFactory(new PropertyValueFactory("BookingType"));
        MID.setCellValueFactory(new PropertyValueFactory("MechanicID"));
        BD.setCellValueFactory(new PropertyValueFactory("BookingDate"));
        BT.setCellValueFactory(new PropertyValueFactory("BookingTime"));
        RepDur.setCellValueFactory(new PropertyValueFactory("RepairTime"));
        B.setCellValueFactory(new PropertyValueFactory("Bill"));

        Booking.setItems(allBookings);
    }
    
    // SHOW FUTURE BOOKINGS ON BOOKINGDETAILS PAGE
    @FXML
    private void ShowFutureBookings(ActionEvent event) throws ParseException {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allBookings = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM Booking");
            while(set.next()){
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date dateobj = new Date();
                String date = set.getString(6);
                Date date1 = format.parse(date);
                if (date1.compareTo(dateobj) >= 0)
                allBookings.add(new BookingTable(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
                                                 set.getInt(5), set.getString(6), set.getString(7),
                                                 set.getString(8), set.getDouble(9))); 
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
        Mile.setCellValueFactory(new PropertyValueFactory("Mileage"));
        ToB.setCellValueFactory(new PropertyValueFactory("BookingType"));
        MID.setCellValueFactory(new PropertyValueFactory("MechanicID"));
        BD.setCellValueFactory(new PropertyValueFactory("BookingDate"));
        BT.setCellValueFactory(new PropertyValueFactory("BookingTime"));
        RepDur.setCellValueFactory(new PropertyValueFactory("RepairTime"));
        B.setCellValueFactory(new PropertyValueFactory("Bill"));

        Booking.setItems(allBookings);
    }
    
    @FXML 
    private void viewParts(ActionEvent event) throws IOException
    {
        BookingTable book = Booking.getSelectionModel().getSelectedItem();
        if(book == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No Booking Selected");
            alert.setContentText("Please select a booking");
            alert.showAndWait();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PartsUsed.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            PUController controller = fxmlLoader.<PUController>getController();
            controller.setBooking(book);
            Stage stage = new Stage();
            stage.setTitle("View Parts");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
    
    // switch to home page 
    @FXML
    private void change2Home(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to vehicle page
    @FXML
    private void change2Vehicle(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to bookings page
    @FXML
    private void change2Customer(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/customer/gui/CustomerRealPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to parts page
    @FXML
    private void change2Parts(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/parts/gui/PartsPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to specialist page
    @FXML
    private void change2Specialist(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/guispcMainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
