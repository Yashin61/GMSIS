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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
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

    @FXML
    private void clearSearchAddEdit(ActionEvent event) throws IOException
    {
        spcId.clear();
        spcName.clear();
        spcAddress.clear();
        spcPhone.clear();
        spcEmail.clear();
    }

    @FXML
    public void setAllFields(theSPC spc)
    {
        spcId.setText(""+spc.getSPCid());
        spcName.setText(spc.getSPCname());
        spcAddress.setText(spc.getSPCaddress());
        spcPhone.setText(spc.getSPCphone());
        spcEmail.setText(spc.getSPCemail());
    }
    
    @FXML
    void home(ActionEvent event) {

    }

    @FXML
    private void spcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("spcMainPage.fxml" ));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void updateSpcButton(ActionEvent event) 
    {
        //get all the texts first
        String id = spcId.getText();
        String name = spcName.getText();
        String address = spcAddress.getText();
        String phone = spcPhone.getText();
        String email = spcEmail.getText();
        String[] data = {id,name,address,phone,email};
        if(!(spcId.getText().equals("") || spcName.getText().equals("") || spcAddress.getText().equals("") || spcPhone.getText().equals("") || spcEmail.getText().equals("")))
        {
            for(int i=1; i<5; i++)
            {
                SpecialistDB a= new SpecialistDB();
                a.editSPC(id,data[i],i);
            }     
            JOptionPane.showMessageDialog(null, "SPC list has been updated");
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }
        else
        {
            System.out.println("Please input all the details.");
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
