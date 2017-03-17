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
import diagrep.logic.VehicleTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vehicles.Vehicle;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class AddController implements Initializable {

    @FXML
    private AnchorPane AddBooking;
    @FXML
    private DatePicker Date;
    @FXML
    private ComboBox<String> Time;
    @FXML
    private ComboBox<String> Mechanic;
    @FXML
    private Button Clear;
    @FXML
    private Button Submit_Button;
    @FXML
    private Button Back_Button;
    @FXML
    private ComboBox<String> CustomerName;
    @FXML
    private TableView<VehicleTable> VehiclesA;
    @FXML
    private TableColumn<VehicleTable, String> MakeA;
    @FXML
    private TableColumn<VehicleTable, String> ModelA;
    @FXML
    private TableColumn<VehicleTable, String> RegNoA;
    @FXML
    private TableColumn<VehicleTable, Integer> MileageA;
    @FXML
    private ObservableList<VehicleTable> VehicleData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
           CustomerName.setItems(CustomerFill());
           Mechanic.setItems(MechanicFill());
           Time.setItems(TimeFill());
       } catch (SQLException ex) {
           Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }    

    @FXML
    private void AclearPage(ActionEvent event) {
        CustomerName.setValue(null);
        Mechanic.setValue(null);
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
    
    private ObservableList<String> CustomerFill() throws SQLException
    {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
        ArrayList<String> CustomerList = new ArrayList<>();
        String query = "SELECT Firstname FROM Customer_Accounts";
        ResultSet rs = conn.createStatement().executeQuery(query);
        while(rs.next())
        {
            CustomerList.add(rs.getString("Firstname"));
        }

        return FXCollections.observableArrayList(CustomerList);
    }
    
    private ObservableList<String> MechanicFill() throws SQLException
    {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
        ArrayList<String> MechanicList = new ArrayList<>();
        String query = "SELECT ID FROM Employees WHERE UserType = 'USER'";
        ResultSet rs = conn.createStatement().executeQuery(query);
        while(rs.next())
        {
            MechanicList.add(rs.getString("ID"));
        }

        return FXCollections.observableArrayList(MechanicList);
    }
    
    private ObservableList<String> TimeFill() throws SQLException
    {
        ObservableList<String> List = FXCollections.observableArrayList("9:00", "10:00", "11:00");

        return FXCollections.observableArrayList(List);
    }

    @FXML
    private void ShowVehicles(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;
        
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            VehicleData = FXCollections.observableArrayList();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicles INNER JOIN Customer_Accounts ON Vehicles.CustomerID = Customer_Accounts.ID WHERE Customer_Accounts.Firstname = '"+CustomerName.getValue()+"'");
            while(rs.next()){
                VehicleData.add(new VehicleTable(rs.getString(2), rs.getString(3), rs.getString(9), rs.getInt(7), rs.getInt(10))); 
            }
            stmt.close();
            rs.close();
            connect.close();
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        
        MakeA.setCellValueFactory(new PropertyValueFactory("Make"));
        ModelA.setCellValueFactory(new PropertyValueFactory("Model"));
        RegNoA.setCellValueFactory(new PropertyValueFactory("RegNo"));
        MileageA.setCellValueFactory(new PropertyValueFactory("Mileage"));

        VehiclesA.setItems(VehicleData);
    }
}
