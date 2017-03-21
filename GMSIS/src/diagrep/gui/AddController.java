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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private ObservableList<VehicleTable> VehicleData;
    @FXML
    private ComboBox<String> RepairDuration;
    @FXML
    private RadioButton DiagRep;
    @FXML
    private ToggleGroup BookType;
    @FXML
    private RadioButton SPC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           BookType.selectToggle(DiagRep);
           try {
               RepairDuration.setItems(RepairTimeFill());
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
        VehiclesA.setItems(null);
        BookType.selectToggle(DiagRep);
    }

    @FXML
    private void submitDetails(ActionEvent event) throws SQLException {
        Connection connect = null;
        PreparedStatement stmt = null;
        Statement stmte = null;
        
        //SET VARIABLES TO ENTER INTO TABLE
        VehicleTable vt = VehiclesA.getSelectionModel().getSelectedItem();
        try {
                String Reg = vt.getRegNo();
                String BookingType = "";
                if(DiagRep.isSelected())
                {
                    BookingType = "Diagnosis and Repair";
                }
                else if (SPC.isSelected())
                {
                    BookingType = "Specialist Repair";
                }
                /*else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Booking Type");
                    alert.setHeaderText("Booking Type Not Selected");
                    alert.setContentText("Please select a booking type");
                    alert.showAndWait();
                }*/
                int MechID = Integer.parseInt(Mechanic.getValue()); 
                String Repair = RepairDuration.getValue();
                
                String sql1 = "SELECT Hourly_Wage FROM Employees WHERE ID = '"+Mechanic.getValue()+"'";

                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.prepareStatement(sql1);
                ResultSet rs = stmt.executeQuery();
                
                int wage = rs.getInt("Hourly_Wage");
                int RepT = Integer.parseInt(Repair.replaceAll("[^0-9]", "")); //REMOVE TEXT
                double Bill = RepT*wage;
                int CustID = vt.getCustomerID();
                
                rs.close();
                stmt.close();
                connect.close();
                
                //INSERT INFORMATION INTO DATABASE
                String sql = "INSERT INTO Booking(RegistrationNumber, BookingType, MechanicID, BookingDate, "
                            + "BookingTime, RepairTime, Bill, CustomerID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.prepareStatement(sql);

                stmt.setString(1, Reg);         
                stmt.setString(2, BookingType);
                stmt.setInt(3, MechID);
                stmt.setString(4, BookingDate.getValue().toString());
                stmt.setString(5, BookingTime.getValue());
                stmt.setString(6, Repair);
                stmt.setDouble(7, Bill);
                stmt.setInt(8, CustID);
                
                stmt.executeUpdate();
                stmt.close();
                connect.close();
                
                SubmitToBillsPaid();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Submitted");
                alert.setContentText("Data Submitted To Table");
                alert.showAndWait();
        } 
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter in all pieces of information");
            alert.showAndWait();
        }
    }

    public void SubmitToBillsPaid() throws SQLException
    {
        Connection connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        VehicleTable vt = VehiclesA.getSelectionModel().getSelectedItem();
        String Reg = vt.getRegNo();
        int CID = vt.getCustomerID();
        
        String sql2 = "SELECT * FROM Booking ORDER BY BookingID DESC LIMIT 1";

                stmt = connect.prepareStatement(sql2);
                rs = stmt.executeQuery();
                
                int BID = rs.getInt("BookingID");
                System.out.println(BID);
                
                rs.close();
                stmt.close();
        
        String sql1 = "SELECT * FROM Vehicles WHERE RegistrationNumber = '"+Reg+"'";

                stmt = connect.prepareStatement(sql1);
                rs = stmt.executeQuery();
                
                int WarrantyCheck = rs.getInt("WarrantyID");
                String Settle;
                if (WarrantyCheck != 0)
                {
                    Settle = "SETTLED";
                }
                else
                {
                    Settle = "OUTSTANDING";
                }
                
                rs.close();
                stmt.close();
        
        String sql = "INSERT INTO BillsPaid(CustomerID, BookingID, SettleBill) VALUES(?, ?, ?)";

                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.prepareStatement(sql);
                
                stmt.setInt(1, CID);         
                stmt.setInt(2, BID);
                stmt.setString(3, Settle);
                
                stmt.executeUpdate();
                stmt.close();
                connect.close();
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
        String query = "SELECT Firstname, Surname FROM Customer_Accounts";
        ResultSet rs = conn.createStatement().executeQuery(query);
        while(rs.next())
        {
            CustomerList.add(rs.getString("Firstname") + " " + rs.getString("Surname"));
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
    private ObservableList<String> RepairTimeFill() throws SQLException
    {
        ObservableList<String> List = FXCollections.observableArrayList("1 Hour", "2 Hours", "3 Hours", "4 Hours", 
                                                                        "5 Hours", "6 Hours", "7 Hours", "8 Hours");

        return FXCollections.observableArrayList(List);
    }
    
    //FILL BOOKINGTIME WITH OPENING TIMES FOR WEEKDAY
    private ObservableList<String> TimeFillWeekDay() throws SQLException
    {
        ObservableList<String> List = FXCollections.observableArrayList("9:00 am", "9:30 am", "10:00 am", "10:30 am", "11:00 am", 
                                                                        "11:30 am", "12:00 pm", "12:30 pm", "1:00 pm", "1:30 pm", 
                                                                        "2:00 pm", "2:30 pm", "3:00 pm", "3:30 pm", "4:00 pm", "4:30 pm");

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
    public void ShowVehicles(ActionEvent event) {
        Connection connect = null;
        Statement stmt = null;
        
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            VehicleData = FXCollections.observableArrayList();
            String[] name = CustomerName.getValue().split(" ");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicles INNER JOIN Customer_Accounts "
                                            + "ON Vehicles.CustomerID = Customer_Accounts.ID WHERE "
                                            + "Customer_Accounts.Firstname = '"+name[0]
                                            +"' AND Customer_Accounts.Surname = '"+name[1]+"'");
            while(rs.next()){
                VehicleData.add(new VehicleTable(rs.getString(2), rs.getString(3), 
                                    rs.getString(9), rs.getInt(7), rs.getInt(10))); 
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
        if (BookingDate.getValue()!=null)
        {
            String date = BookingDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            //System.out.println(date);
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
                String dateCheck = BookingDate.getValue().toString();
                String day = d.getDayOfWeek().name();

                String Holiday1 = "2017-04-14";
                String Holiday2 = "2017-04-17";
                String Holiday3 = "2017-05-01";
                String Holiday4 = "2017-05-29";
                String Holiday5 = "2017-08-28";
                String Holiday6 = "2017-12-25";
                String Holiday7 = "2017-12-26";
                
                //ALERT IF USER PICKS HOLIDAY DATE
                if(dateCheck.equals(Holiday1) || dateCheck.equals(Holiday2) || dateCheck.equals(Holiday3) ||
                   dateCheck.equals(Holiday4) || dateCheck.equals(Holiday5) || dateCheck.equals(Holiday6) ||
                   dateCheck.equals(Holiday7))
                {
                    BookingDate.setValue(null);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Date");
                    alert.setHeaderText("Garage Closed On This Date");
                    alert.setContentText("Select Another Date");
                    alert.showAndWait();
                }
                else if(day.equals("MONDAY") || day.equals("TUESDAY") || day.equals("WEDNESDAY") || 
                   day.equals("THURSDAY") || day.equals("FRIDAY"))
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
                else if(day.equals("SUNDAY"))
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
    }
    
    // a method for the customer module to access
    public void setCustomerID(String name, int ID)
    {
        CustomerName.setValue(name);
    }
    
}
