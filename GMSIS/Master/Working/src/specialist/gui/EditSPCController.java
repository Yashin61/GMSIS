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
import specialist.logic.theSPC;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class EditSPCController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField spcId;
    @FXML
    private TextField spcAddress;
    @FXML
    private TextField spcEmail;
    @FXML
    private TextField spcName;
    @FXML
    private TextField spcPhone;

    //clears all the details that has been inputted on the textbox
    @FXML
    private void clearSearchAddEdit(ActionEvent event) throws IOException
    {
        spcId.clear();
        spcName.clear();
        spcAddress.clear();
        spcPhone.clear();
        spcEmail.clear();
    }

    //sets the textfields already to the details of the spcBooking that the user wants to change
    @FXML
    public void setAllFields(theSPC spc)
    {
        spcId.setText(""+spc.getSPCid());
        spcId.setEditable(false);
        spcName.setText(spc.getSPCname());
        spcAddress.setText(spc.getSPCaddress());
        spcPhone.setText(spc.getSPCphone());
        spcEmail.setText(spc.getSPCemail());
    }

    //the back button closes the edit spc page window
    @FXML
    private void back(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    //the update button makes changes to the data stored in the database
    @FXML
    private void updateSpcButton(ActionEvent event) 
    {
        //get all the texts first
        String id = spcId.getText();
        String name = spcName.getText();
        String address = spcAddress.getText();
        String phone = spcPhone.getText();
        String email = spcEmail.getText();

        if(!(spcId.getText().trim().isEmpty() || spcName.getText().trim().isEmpty() || spcAddress.getText().trim().isEmpty() || spcPhone.getText().trim().isEmpty() || spcEmail.getText().trim().isEmpty()))
        {
            
            SpecialistDB a= new SpecialistDB();
            a.editSPC(id, name, address, phone, email);      
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
