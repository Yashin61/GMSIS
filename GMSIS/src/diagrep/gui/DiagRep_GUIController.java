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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class DiagRep_GUIController implements Initializable {
   
    /* ADD BOOKING */   
    @FXML
    private MenuButton CustomerNames;
    @FXML
    private JFXDatePicker Date;
    @FXML
    private JFXDatePicker Time;
    @FXML
    private MenuButton Vehicles;
    @FXML
    private MenuButton Mechanic;
    @FXML
    private TextField Part1ID;
    @FXML
    private TextField Part4ID;
    @FXML
    private TextField Part3ID;
    @FXML
    private TextField Part2ID;
    @FXML
    private Button Clear;
    @FXML
    private TextField Mileage;
    @FXML
    private Button Submit_Button;
    @FXML
    private Button Back_Button;
    
    /* EDIT BOOKING */
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
    
    /* BOOKING DETAILS */
    @FXML
    private Button Add_Button;
    @FXML
    private Button Edit_Button;
  
    /* METHODS */ 
    @FXML
    private void openAddBooking(ActionEvent event) {
    }

    @FXML
    private void openEditBooking(ActionEvent event) {
    }
    
    @FXML
    private void searchBookings(ActionEvent event) {
    }

    @FXML
    private void clearPage(ActionEvent event) {
        CustomerNames.setText(null);
        
        
    }

    @FXML
    private void submitDetails(ActionEvent event) {
    }

    @FXML
    private void openBookingDetails(ActionEvent event) {
    }

    @FXML
    private void deleteBooking(ActionEvent event) {
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
