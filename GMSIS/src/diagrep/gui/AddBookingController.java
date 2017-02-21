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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class AddBookingController implements Initializable {

    @FXML
    private AnchorPane AddBooking;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clearPage(ActionEvent event) {
        Mileage.setText(null);
        Part1ID.setText(null);
        Part2ID.setText(null);
        Part3ID.setText(null);
        Part4ID.setText(null);
        Part5ID.setText(null);   
    }

    @FXML
    private void submitDetails(ActionEvent event) {
    }

    @FXML
    private void openBookingDetails(ActionEvent event) {

    }
    
}
