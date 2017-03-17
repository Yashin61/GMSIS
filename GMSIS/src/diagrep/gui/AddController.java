/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import customer.gui.*;
import customer.logic.allCustomers;
import diagrep.logic.BookingTable;
import diagrep.logic.Database;
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
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class AddController implements Initializable {

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
    private ComboBox<String> CustomerName;
    @FXML
    private TextField Vehicle;
    //Customer DropDown
    @FXML
    private TableView<allCustomers> dataTable;
    @FXML
    private TableColumn<allCustomers, Integer> customer_ID;
    @FXML
    private TableColumn<allCustomers, String> first;
    @FXML
    private TableColumn<allCustomers, String> sur;
    @FXML
    private TableColumn<allCustomers, String> adr;
    @FXML
    private TableColumn<allCustomers, String> post;
    @FXML
    private TableColumn<allCustomers, String> mobile;
    @FXML
    private TableColumn<allCustomers, String> ema;
    @FXML
    private TableColumn<allCustomers, String> type;
    @FXML
    private ObservableList<allCustomers> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void AclearPage(ActionEvent event) {
        CustomerName.setValue(null);
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
    public void customerBox(ActionEvent event) {       
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            Statement stmt = conn.createStatement();
            data = FXCollections.observableArrayList();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customer_Accounts");
    
            while(rs.next())
            {
                CustomerName.getItems().addAll(rs.getString(2));
            }
            
            stmt.close();
            rs.close();
            conn.close();
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
