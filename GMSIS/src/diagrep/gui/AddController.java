/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import diagrep.logic.Database;
import diagrep.logic.VehicleTable;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class AddController implements Initializable {

    @FXML
    private AnchorPane AddBooking;
    @FXML
    private DatePicker BookingDate;
    @FXML
    private ComboBox<String> BookingTime;
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
       } catch (SQLException ex) {
           Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }    

    @FXML
    private void AclearPage(ActionEvent event) {
        CustomerName.setValue(null);
        Mechanic.setValue(null);
        BookingDate.setValue(null);
        BookingTime.setItems(null);
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
    
    //DISPLAY ALL CUSTOMERS
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
    
    //DISPLAY ALL MECHANICS
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
    
    //FILL BOOKINGTIME WITH OPENING TIMES FOR WEEKDAY
    private ObservableList<String> TimeFillWeekDay() throws SQLException
    {
        ObservableList<String> List = FXCollections.observableArrayList("9:00 am", "9:30 am", "10:00 am", "10:30 am", "11:00 am", "11:30 am", "12:00 pm", "12:30 pm", "1:00 pm", "1:30 pm", "2:00 pm", "2:30 pm", "3:00 pm", "3:30 pm", "4:00 pm", "4:30 pm");

        return FXCollections.observableArrayList(List);
    }
    
    //FILL BOOKINGTIME WITH OPENING TIMES FOR WEEKEND
    private ObservableList<String> TimeFillWeekEnd() throws SQLException
    {
        ObservableList<String> List = FXCollections.observableArrayList("9:00 am", "9:30 am", "10:00 am", "10:30 am", "11:00 am");
        return FXCollections.observableArrayList(List);
    }
    
    //DISPLAY VEHICLES FOR SELECTED CUSTOMER
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
    
    @FXML
    public void DateCheck(ActionEvent event) throws ParseException {
        //IF STATEMENT CHECKING DATE AND COMPARING
        String date = BookingDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = format.parse(date);
        Date dateobj = new Date();
        //ALERT IF USER PICKS DATE IN THE PAST
        if (date1.before(dateobj))
                {
                    BookingDate.setValue(null);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Date");
                    alert.setHeaderText("Picked A Date In The Past");
                    alert.setContentText("Select A Date In The Future");
                    alert.showAndWait();
                }
        //DISPLAY OPENING TIMES FOR WEEKDAY IF DATE CHOSEN IS ON A WEEKDAY
        else
        {
            LocalDate d = BookingDate.getValue();
            System.out.println(d.getDayOfWeek().name());
            String day = d.getDayOfWeek().name();
            if(day.equals("MONDAY") || day.equals("TUESDAY") || day.equals("WEDNESDAY") || day.equals("THURSDAY") || day.equals("FRIDAY"))
            {
                try {
                    BookingTime.setItems(TimeFillWeekDay());
                } catch (SQLException ex) {
                    Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //DISPLAY OPENING TIMES FOR WEEKEND IF DATE CHOSEN IS ON A WEEKEND
            else if(day.equals("SATURDAY"))
            {
                try {    
                    BookingTime.setItems(TimeFillWeekEnd());
                } catch (SQLException ex) {
                    Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //ALERT IF USER PICKS DATE WHERE THE DAY IS SUNDAY
            else
            {
                BookingDate.setValue(null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Date");
                alert.setHeaderText("Garage Closed On Sunday");
                alert.setContentText("Select Another Date");
                alert.showAndWait();
            }
        }
    }
    
    // a method for the customer module to access
    public void setCustomerID(String name, int ID)
    {
        CustomerName.setValue(name);
    }
    
}
