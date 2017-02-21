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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class BookingDetailsController implements Initializable {

    @FXML
    private AnchorPane BookingDetails;
    @FXML
    private Button Add_Button;
    @FXML
    private Button Edit_Button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openAddBooking(ActionEvent event) {
    }

    @FXML
    private void openEditBooking(ActionEvent event) {
    }
    
}
