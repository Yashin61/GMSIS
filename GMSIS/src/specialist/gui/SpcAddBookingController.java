/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import diagrep.gui.AddController;
import diagrep.logic.Database;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private ObservableList<String> repairTypeList;
    
    @FXML
    private ObservableList<String> repairOnList;
    
    @FXML
    private ObservableList<SpcBookingTables> vehicleData;
    
    @FXML
    private ObservableList<SpcBookingTables> partData;
    
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
    
    //show all the repair types on the combo box
    private ObservableList<String> repairTypeFill()
    {     
        repairTypeList = FXCollections.observableArrayList();
        repairTypeList.add("Repair");
        repairTypeList.add("Re-condition");
        return repairTypeList;
    }
    
    //show which item user wants to repair on the combo box
    private ObservableList<String> repairOnFill()
    {     
        repairOnList = FXCollections.observableArrayList();
        repairOnList.add("Part");
        repairOnList.add("Vehicle");
        return repairOnList;
    }
    
    @FXML
    private void bookingCheck(ActionEvent event) throws ParseException
    {
        //IF STATEMENT CHECKING DATE AND COMPARING
        String date = bookingDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = format.parse(date);
        Date currentDate = new Date();
        //ALERT IF USER PICKS DATE IN THE PAST
        if (date1.before(currentDate))
        { 
            bookingDate.setValue(null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Date");
            alert.setHeaderText("Your date cannot be registered");
            alert.setContentText("Please select a date in the Future");
            alert.showAndWait();
        }
        else
        {
            LocalDate d = bookingDate.getValue();
            System.out.println(d.getDayOfWeek().name());
            String day = d.getDayOfWeek().name();
            if(day.equals("SUNDAY"))
            {
                bookingDate.setValue(null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Date");
                alert.setHeaderText("Garage is closed on Sunday");
                alert.setContentText("Please select another date");
                alert.showAndWait();
            }else{}
        }
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
        //System.out.println(vehicleData);
        tableVehicleMake.setCellValueFactory(new PropertyValueFactory("make"));
        tableVehicleModel.setCellValueFactory(new PropertyValueFactory("model"));
        tableVehicleReg.setCellValueFactory(new PropertyValueFactory("regNo"));
        tableVehicleMileage.setCellValueFactory(new PropertyValueFactory("mileage"));
        vehicleList.setItems(vehicleData);
    }
    
    @FXML
    private void displayParts(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;
        if(repairOn.getValue() != null)
        {
            if(repairOn.getValue().equals("Part"))
            {
                try
                {   
                    connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                    stmt = connect.createStatement();
                    partData = FXCollections.observableArrayList();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Parts");
                    while(rs.next()){
                        partData.add(new SpcBookingTables(rs.getInt("ID"),rs.getString("Name"))); 
                    }
                    stmt.close();
                    rs.close();
                    connect.close();
                }
                catch(SQLException e)
                {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
                }
                //System.out.println(vehicleData);
                tablePartId.setCellValueFactory(new PropertyValueFactory("partId"));
                tablePartName.setCellValueFactory(new PropertyValueFactory("partName"));
                partList.setItems(partData);
            }
            else
            {
                partList.setItems(null);
            }   
        }
        else
            {
                partList.setItems(null);
            }  
    }
    
    //resets all the choices that has been made
    @FXML
    private void Reset(ActionEvent event) {
        custName.setValue(null);
        bookingDate.setValue(null);
        repairType.setValue(null);
        spcList.getSelectionModel().clearSelection();
        repairOn.setValue(null);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        custName.setItems(customerFill());
        repairType.setItems(repairTypeFill());
        repairOn.setItems(repairOnFill());
        SpecialistDB a = new SpecialistDB();
        String [] listOfSPC = a.getSPC();
        for(int i=0; i<10; i++)
        {
            String lbl = listOfSPC[i];
            spcList.getItems().add(lbl);
        }
    }    
    
}

