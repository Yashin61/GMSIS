/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import specialist.logic.SpecialistDB;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class AddSPCController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField spcName;
    @FXML
    private TextField spcAddress;
    @FXML
    private TextField spcEmail;
    @FXML
    private TextField spcPhone;

    //adds the details of the spc to the database
    @FXML
    private void addSpcButton(ActionEvent event) 
    {
        String name = spcName.getText();
        String address = spcAddress.getText();
        String phone = spcPhone.getText();
        String email = spcEmail.getText();
        if(!(name.trim().isEmpty() || address.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()))
        {
            //System.out.println("It works");
            SpecialistDB a= new SpecialistDB();
            a.addSPC(name,address,phone,email);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Form Incomplete");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please input all the details.");
            alert.showAndWait();
        }
    }

    //clears all the details that has been inputted on the textbox
    @FXML
    private void clearSearchAddEdit(ActionEvent event) throws IOException
    {
        spcName.clear();
        spcAddress.clear();
        spcPhone.clear();
        spcEmail.clear();
    }

    //the back button closes the add spc page window
    @FXML
    private void back(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
