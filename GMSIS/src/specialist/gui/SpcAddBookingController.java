/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private JFXDatePicker Date;

    @FXML
    private JFXDatePicker Date1;

    @FXML
    private TableColumn<?, ?> PartIDTable;

    @FXML
    private TableColumn<?, ?> PartNameTable;

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
    private TextField CustomerName;

    @FXML
    private TextField Vehicle;
     @FXML
    private JFXListView<Label> spcList;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SpecialistDB a = new SpecialistDB();
        String [] listOfSPC = a.getSPC();
        for(int i=0; i<10; i++)
        {
            Label lbl = new Label(listOfSPC[i]);
            spcList.getItems().add(lbl);
        }
    }    
    
}
