/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import diagrep.logic.Database;
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
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import specialist.logic.SpcBookingTables;
import specialist.logic.SpecialistDB;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class SpcAddBookingController implements Initializable {

    @FXML
    private AnchorPane AddBooking;

    @FXML
    private JFXDatePicker bookingDate;

    @FXML
    private JFXListView<String> spcList;

    @FXML
    private ComboBox<String> custName;

    @FXML
    private TableView<SpcBookingTables> vehicleList;

    @FXML
    private TableColumn<SpcBookingTables, String> tableVehicleMake;

    @FXML
    private TableColumn<SpcBookingTables, String> tableVehicleModel;

    @FXML
    private TableColumn<SpcBookingTables, String> tableVehicleReg;

    @FXML
    private TableColumn<SpcBookingTables, Integer> tableVehicleMileage;

    @FXML
    private ComboBox<String> repairType;

    @FXML
    private TableView<SpcBookingTables> partList;

    @FXML
    private TableColumn<SpcBookingTables, Integer> tablePartId;

    @FXML
    private TableColumn<SpcBookingTables, String> tablePartName;

    @FXML
    private ComboBox<String> repairOn;

    @FXML
    private ObservableList<String> customerList;
    
    @FXML
    private ObservableList<SpcBookingTables> vehicleData;
    
    //show all the customers on the combo box
    private ObservableList<String> customerFill()
    {
        Connection connect = null;
        Statement stmt = null;
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();         
            customerList = FXCollections.observableArrayList();
            ResultSet rs = stmt.executeQuery("SELECT Firstname, Surname FROM Customer_Accounts");
            while(rs.next()){
                customerList.add(rs.getString("Firstname")/*+" "+rs.getString("Surname")*/);
            }
            stmt.close();
            rs.close();
            connect.close();
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return customerList;
    }
    
    @FXML
    private void bookingCheck(ActionEvent event) {

    }

    @FXML
    private void displayVehicles(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;
        
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            vehicleData = FXCollections.observableArrayList();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicles INNER JOIN Customer_Accounts ON Vehicles.CustomerID = Customer_Accounts.ID WHERE Customer_Accounts.Firstname = '"+custName.getValue()+"'");
            while(rs.next()){
                vehicleData.add(new SpcBookingTables(rs.getString("Make"),rs.getString("Model"),rs.getString("RegistrationNumber"),rs.getInt("Mileage"))); 
            }
            stmt.close();
            rs.close();
            connect.close();
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println(vehicleData);
        tableVehicleMake.setCellValueFactory(new PropertyValueFactory("make"));
        tableVehicleModel.setCellValueFactory(new PropertyValueFactory("model"));
        tableVehicleReg.setCellValueFactory(new PropertyValueFactory("regNo"));
        tableVehicleMileage.setCellValueFactory(new PropertyValueFactory("mileage"));
        vehicleList.setItems(vehicleData);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        custName.setItems(customerFill());
        SpecialistDB a = new SpecialistDB();
        String [] listOfSPC = a.getSPC();
        for(int i=0; i<10; i++)
        {
            String lbl = listOfSPC[i];
            spcList.getItems().add(lbl);
        }
    }    
    
}

