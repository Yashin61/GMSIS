/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import diagrep.logic.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import specialist.logic.SpcBookingTables;
import specialist.logic.SpcBookings;
import specialist.logic.SpecialistDB;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class SpcEditBookingController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDatePicker bookingDate;

    @FXML
    private JFXListView<String> spcList;

    @FXML
    private JFXTextField custName;

    @FXML
    private ComboBox<String> bookingID;
    
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
    private ObservableList<String> bookingList;
    
    @FXML
    private ObservableList<String> repairTypeList;
    
    @FXML
    private ObservableList<String> repairOnList;
    
    @FXML
    private ObservableList<SpcBookingTables> vehicleData;
    
    @FXML
    private ObservableList<SpcBookingTables> partData;
    
    private SpcBookings booking;
    
    //show all the repair types on the combo box
    private ObservableList<String> repairTypeFill()
    {     
        repairTypeList = FXCollections.observableArrayList();
        repairTypeList.add("Repair - 5 days");
        repairTypeList.add("Re-condition - 11 days");
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
        if(bookingDate.getValue() != null)
        {
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
                //System.out.println(d.getDayOfWeek().name());
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
        }else{}
    }

    @FXML
    private void displayVehicles(SpcBookings bookingSPC) {
        Connection connect = null;
        Statement stmt = null;
        
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            vehicleData = FXCollections.observableArrayList();
            String[] name = custName.getText().split(" ");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicles INNER JOIN Customer_Accounts ON Vehicles.CustomerID = Customer_Accounts.ID WHERE Customer_Accounts.Firstname = '"+name[0]
                    +"' AND Customer_Accounts.Surname = '"+name[1]
                    +"' AND Vehicles.RegistrationNumber = '"+bookingSPC.getSpcRNumber()+"'");
            while(rs.next()){
                vehicleData.add(new SpcBookingTables(rs.getString("Make"),rs.getString("Model"),rs.getString("RegistrationNumber"),rs.getInt("Mileage"),rs.getInt("ID"))); 
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
    
    @FXML
    private void displayParts2() {
        Connection connect = null;
        Statement stmt = null;
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
    
    @FXML
    private void updateSpcBookingButton(ActionEvent event) 
    {
        String name = spcList.getSelectionModel().getSelectedItem();
        String dDate = ""+bookingDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String arrived = "No";
        String rDate = "";
        String returned = "No";
        
        String workOn = repairOn.getValue();
        int parts = 0;
        if(workOn != null)
        {
            if(workOn.equals("Part"))
            {
                SpcBookingTables partSPC = partList.getSelectionModel().getSelectedItem();
                parts = partSPC.getPartId();
            }
        }
        else
        {
            //System.out.println("Select what to work on");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Data");
            alert.setHeaderText("Form is not completed properly");
            alert.setContentText("Select what to work on");
            alert.showAndWait();
        }
        
        
        String reg = "";
        int cust = 0;
        
        SpcBookingTables vehicleSPC = vehicleList.getSelectionModel().getSelectedItem();
        if(vehicleSPC == null)
        {
            //JOptionPane.showMessageDialog(null,"Please select a vehicle from the vehicle list displayed on the left side of the page");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Data");
            alert.setHeaderText("Form is not completed properly");
            alert.setContentText("Please select a vehicle from the vehicle list displayed on the left side of the page");
            alert.showAndWait();
        }
        else
        {
            reg = vehicleSPC.getRegNo();
            cust = vehicleSPC.getCust();
        } 
                     
        String type = "";
        double cost = 0.0;
        
        if(repairType.getValue().equals("Repair - 5 days"))
        {
            cost = 50.00;
            rDate = ""+bookingDate.getValue().plusDays(5).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            type = "Repair";
        }
        else if(repairType.getValue().equals("Re-condition - 11 days"))
        {
            cost = 100.00;
            rDate = ""+bookingDate.getValue().plusDays(11).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            type = "Re-condition";
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Data");
            alert.setHeaderText("Form is not completed properly");
            alert.setContentText("Please select the type of special repair you want");
            alert.showAndWait();
        }

        
        
        if(!name.equals("") && !dDate.equals("") && !reg.equals("") && !custName.getText().equals("") && !workOn.equals("") && !type.equals(""))
        {
            //System.out.println("It works");
            SpecialistDB a= new SpecialistDB();
            a.editSPCBooking(""+booking.getSpcBookingId(),name,dDate,arrived,rDate,returned,parts,reg,cust,workOn,type,cost);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }
        else
        {
            //System.out.println("Please input all the details.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Please complete");
            alert.setHeaderText("Some details are still missing");
            alert.setContentText("Please input all the details");
            alert.showAndWait();
        }
    }
    
    //resets all the choices that has been made
    @FXML
    private void Reset(ActionEvent event) {
        custName.setText(null);
        bookingDate.setValue(null);
        repairType.setValue(null);
        spcList.getSelectionModel().clearSelection();
        repairOn.setValue(null);
    }
    
    //sets the textfields already to the details of the spcBooking that the user wants to change
    @FXML
    public void setAllFields(SpcBookings spcBooking)throws ParseException
    {
        booking = spcBooking;
        custName.setText(spcBooking.getSpcCustName());
        custName.setEditable(false);
        //bookingID.setText(spcBooking.getSpcBookID());
        //bookingID.setEditable(false);
        
        displayVehicles(spcBooking);
        vehicleList.getSelectionModel().select(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = spcBooking.getSpcDDate();
        //convert String to LocalDate
        LocalDate lDate = LocalDate.parse(date, formatter);      
        bookingDate.setValue(lDate);
        
        if(spcBooking.getSpcType().equals("Repair"))
        {
            repairType.setValue("Repair - 5 days");
        }
        else
        {
             repairType.setValue("Re-condition - 11 days");
        }
        
        spcList.getSelectionModel().select(spcBooking.getSpcBookingName());
        
        repairOn.setValue(spcBooking.getSpcWOn());
        if(repairOn.getValue().equals("Part"))
        {
            displayParts2();
            partList.getSelectionModel().select(spcBooking.getSpcPartId()-1);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
