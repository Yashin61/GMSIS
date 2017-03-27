/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;

import diagrep.logic.BookingTableE;
import diagrep.logic.Database;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class EditController implements Initializable {

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
    private TextField Mileage;
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
    private TableColumn<BookingTableE, Integer> MileE;
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
    @FXML
    private Button Submit_Button_E;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ShowAllBookingsE();
    }    

    // CLEAR EDITBOOKINGS PAGE
    @FXML
    private void EclearPage(ActionEvent event) {
        RegNo.setText(null);
        BookingType.setText(null);
        MechanicID.setText(null);
        Mileage.setText(null);
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
        ShowAllBookingsE();
    }
    
    private void ShowAllBookingsE() {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allBookingsE = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM Booking");
            while(set.next()){
                allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
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
        
        BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
        RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
        MileE.setCellValueFactory(new PropertyValueFactory("Mileage"));
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
                    String sql = "SELECT * FROM Booking INNER JOIN Customer_Accounts "
                               + "ON Booking.CustomerID = Customer_Accounts.ID "
                               + "WHERE Firstname like '" + Search_Bar.getText() + "%'";
                    stmt = connect.prepareStatement(sql);
                    allBookingsE = FXCollections.observableArrayList();
                    ResultSet set = stmt.executeQuery();
                    while(set.next()){
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
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

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                MileE.setCellValueFactory(new PropertyValueFactory("Mileage"));
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
                    String sql = "SELECT * FROM Booking INNER JOIN Customer_Accounts "
                               + "ON Booking.CustomerID = Customer_Accounts.ID "
                               + "WHERE Surname like '" + Search_Bar.getText() + "%'";
                    stmt = connect.prepareStatement(sql);
                    allBookingsE = FXCollections.observableArrayList();
                    ResultSet set = stmt.executeQuery();
                    while(set.next()){
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
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

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                MileE.setCellValueFactory(new PropertyValueFactory("Mileage"));
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
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
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

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                MileE.setCellValueFactory(new PropertyValueFactory("Mileage"));
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
                    String sql = "SELECT * FROM Booking INNER JOIN Vehicles "
                               + "ON Booking.RegistrationNumber = Vehicles.RegistrationNumber "
                               + "WHERE Make like '" + Search_Bar.getText() + "%'";
                    stmt = connect.prepareStatement(sql);
                    allBookingsE = FXCollections.observableArrayList();
                    ResultSet set = stmt.executeQuery();
                    while(set.next()){
                        allBookingsE.add(new BookingTableE(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
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

                BIDE.setCellValueFactory(new PropertyValueFactory("BookingID"));
                RegE.setCellValueFactory(new PropertyValueFactory("RegNumber"));
                MileE.setCellValueFactory(new PropertyValueFactory("Mileage"));
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
    
    // DELETE ENTRY FROM DATABASE
    @FXML
    private void deleteBooking(ActionEvent event) throws SQLException {
        BookingTableE book = BookingE.getSelectionModel().getSelectedItem();
        
        if(book == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select Booking");
            alert.setHeaderText("No Booking Selected");
            alert.setContentText("Please select a booking from the table");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to continue deleting the booking?");
            
            ButtonType yes = new ButtonType("YES");
            ButtonType no = new ButtonType("NO");
            alert.getButtonTypes().setAll(yes, no);
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if(result.get() == yes)
            {
                int BookID = book.getBookingID();
                Connection connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                PreparedStatement stmt = null;
                try
                {
                    //DELETE FROM BOOKING TABLE
                    String sql = "DELETE FROM Booking WHERE BookingID = ?";
                    stmt = connect.prepareStatement(sql);
                    stmt.setInt(1, BookID);
                    stmt.executeUpdate(); 
                    stmt.close();
                    
                    //DELETE FROM BILLSPAID TABLE
                    String sql1 = "DELETE FROM BillsPaid WHERE BookingID = ?";
                    stmt = connect.prepareStatement(sql1);
                    stmt.setInt(1, BookID);
                    stmt.executeUpdate(); 
                    stmt.close();
                    
                    //DELETE FROM PARTSUSED TABLE
                    String sql2 = "DELETE FROM PartsUsed WHERE BookingID = ?";
                    stmt = connect.prepareStatement(sql2);
                    stmt.setInt(1, BookID);
                    stmt.executeUpdate(); 
                    stmt.close();
                    
                    //DELETE FROM SPCBookings TABLE
                    String sql3 = "DELETE FROM SPCBooking WHERE BookingID = ?";
                    stmt = connect.prepareStatement(sql3);
                    stmt.setInt(1, BookID);
                    stmt.executeUpdate(); 
                    stmt.close();
                     
                    connect.close();
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                alert.close();
            }
            
            ShowAllBookingsE();
        }
    }    
   
    // ENTERS INFORMATION INTO TEXTBOX FROM TABLE
    @FXML
    private void EditDetails(MouseEvent event) {
        BookingTableE book = BookingE.getSelectionModel().getSelectedItem();
        
        // SET REGISTRATION NUMBER TEXTFIELD
        String setReg = book.getRegNumber();
        RegNo.setText(setReg);
        
        // SET TYPE OF BOOKING TEXTFIELD
        String setToB = book.getBookingType();
        BookingType.setText(setToB);
        
        // SET MECHANIC ID TEXTFIELD
        String setMID = Integer.toString(book.getMechanicID());
        MechanicID.setText(setMID);
        
        // SET MILEAGE TEXTFIELD
        String setMileage = Integer.toString(book.getMileage());
        Mileage.setText(setMileage);
        
        // SET BOOKING DATE TEXTFIELD
        String setBD = book.getBookingDate();
        BookingDate.setText(setBD);
        
        // SET BOOKING TIME TEXTFIELD
        String setBT = book.getBookingTime();
        BookingTime.setText(setBT);
        
        // SET REPAIR DURATION TEXTFIELD
        String setRepDur = book.getRepairTime();
        RepairTime.setText(setRepDur);
    }
    
    // UPDATE DATABASE WITH VALUES IN TEXTFIELDS
    @FXML
    private void submitDetailsE(ActionEvent event) throws SQLException {
        Connection connect = null;
        PreparedStatement stmt = null;
        Statement stmte = null;
        
        // IF TEXTFIELDS ARE EMPTY, PRINT ERROR MESSAGE
        if(RegNo.getText().trim().isEmpty() || BookingType.getText().trim().isEmpty() || 
           MechanicID.getText().trim().isEmpty() || Mileage.getText().trim().isEmpty() || 
           BookingDate.getText().trim().isEmpty() || BookingTime.getText().trim().isEmpty() || 
           RepairTime.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter in all pieces of information");
            alert.showAndWait();
        }
        
        // ELSE UPDATE THE TABLE
        else
        {
            BookingTableE book = BookingE.getSelectionModel().getSelectedItem();
            int Mechanic = book.getMechanicID();
            
            //GET WAGE FOR MECHANIC
            String sql1 = "SELECT Hourly_Wage FROM Employees WHERE ID = '"+Mechanic+"'";

            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.prepareStatement(sql1);
            ResultSet rs1 = stmt.executeQuery();
                
            int wage = rs1.getInt("Hourly_Wage");
                
            rs1.close();
            stmt.close();
            connect.close();
                
            
            int getBID = book.getBookingID();
            String sql2 = "SELECT * FROM Booking WHERE BookingID = '"+getBID+"'";

            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.prepareStatement(sql2);
            ResultSet rs = stmt.executeQuery();
            
            //REMOVE OLD WAGE COST FROM BILL
            double Bill = rs.getInt("Bill");
            String RT = rs.getString("RepairTime");
            double RT2 = Double.parseDouble(RT.replaceAll("[^0-9]", ""));
            Bill = Bill - (RT2*wage);
            if (Bill < 0)
            {
                Bill = 0;
            }
                
            rs.close();
            stmt.close();
            connect.close();
                
            //GET REPAIR TIME AND MECHANIC WAGE TO CALCULATE BILL           
            String Repair = RepairTime.getText();      
            double RepT = Double.parseDouble(Repair.replaceAll("[^0-9]", "")); //REMOVE TEXT
            double BillUpdate = Bill + (RepT*wage); //UPDATE BILL WITH NEW WAGE COST
                
            String sql = "UPDATE Booking SET RegistrationNumber = ? , " + "BookingType = ? , "
                       + "MechanicID = ? , " + "BookingDate = ? , " + "BookingTime = ? , "
                       + "RepairTime = ? , " + "Mileage = ? , " + "Bill = ? " + " WHERE BookingID = ?";
            
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.prepareStatement(sql);

            stmt.setString(1, RegNo.getText());         
            stmt.setString(2, BookingType.getText());
            stmt.setString(3, MechanicID.getText());
            stmt.setString(4, BookingDate.getText());
            stmt.setString(5, BookingTime.getText());
            stmt.setString(6, RepairTime.getText());
            stmt.setString(7, Mileage.getText());
            stmt.setDouble(8, BillUpdate);
            stmt.setInt(9, getBID);
            
            stmt.executeUpdate();
            
            stmt.close();
            connect.close();
            
            String sql3 = "UPDATE Vehicles SET Mileage = ? " + " WHERE RegistrationNumber = ?";
            
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.prepareStatement(sql3);
            
            stmt.setString(1, Mileage.getText());
            stmt.setString(2, RegNo.getText());         
            
            
            stmt.executeUpdate();
            
            stmt.close();
            connect.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Submitted");
            alert.setContentText("Data Submitted To Table");
            alert.showAndWait();
            
            // SHOW THE UPDATED TABLE
            ShowAllBookingsE();
        }    
    }
}
