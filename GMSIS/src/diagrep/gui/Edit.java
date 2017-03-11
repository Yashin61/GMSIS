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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class Edit implements Initializable {

    @FXML
    private AnchorPane EditBooking;
    @FXML
    private TextField Search_Bar;
    @FXML
    private Button Search_Button;
    @FXML
    private TableView<?> BookingE;
    @FXML
    private TableColumn<?, ?> BIDE;
    @FXML
    private TableColumn<?, ?> RegE;
    @FXML
    private TableColumn<?, ?> ToBE;
    @FXML
    private TableColumn<?, ?> MIDE;
    @FXML
    private TableColumn<?, ?> BDE;
    @FXML
    private TableColumn<?, ?> BTE;
    @FXML
    private TableColumn<?, ?> RepDurE;
    @FXML
    private TableColumn<?, ?> BE;
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
    private Button Submit_Button;
    @FXML
    private Button Back_Button;
    @FXML
    private Button Delete_Button;
    @FXML
    private Button ShowE;
    @FXML
    private RadioButton RN;
    @FXML
    private ToggleGroup SearchType;
    @FXML
    private RadioButton CFN;
    @FXML
    private RadioButton CSN;
    @FXML
    private RadioButton M;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchBookings(ActionEvent event) {
    }

    @FXML
    private void EditDetails(MouseEvent event) {
    }

    @FXML
    private void EclearPage(ActionEvent event) {
    }

    @FXML
    private void submitDetails(ActionEvent event) {
    }

    @FXML
    private void EopenBookingDetails(ActionEvent event) {
    }

    @FXML
    private void deleteBooking(ActionEvent event) {
    }

    @FXML
    private void ShowBookingsE(ActionEvent event) {
    }
    
}
