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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import common.*;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class SpecialistGUIController implements Initializable {

     @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField spcId;
    @FXML
    private TextField spcName;
    @FXML
    private TextField spcPhone;
    @FXML
    private TextField spcAddress;
    @FXML
    private TextField spcEmail;
    @FXML
    private TableView<?> dataTable;
    @FXML
    private TableColumn<?, ?> tableSpcId;
    @FXML
    private TableColumn<?, ?> tableSpcName;
    @FXML
    private TableColumn<?, ?> tableSpcAddress;
    @FXML
    private TableColumn<?, ?> tableSpcPhone;
    @FXML
    private TableColumn<?, ?> tableSpcEmail;


    @FXML
    private void clearSearchAddEdit(ActionEvent event)
    {
        spcId.setText(null);
        spcName.setText(null);
        spcAddress.setText(null);
        spcPhone.setText(null);
        spcEmail.setText(null);
    }
    
    @FXML
    private void SpcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void SpcAddPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("addSPC.fxml"));
        rootPane.getChildren().setAll(pane);
        /*
         try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerAdd.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
        */
    }
    
    @FXML
    private void AddSpcButton(ActionEvent event) 
    {
        String id = spcId.getText();
        String name = spcName.getText();
        String address = spcAddress.getText();
        String phone = spcPhone.getText();
        String email = spcEmail.getText();
        if(!(id.equals("") || name.equals("") || address.equals("") || phone.equals("") || email.equals("")))
        {
            System.out.println("It works");
            SpecialistDB a= new SpecialistDB();
            a.addSPC(name,address,phone,email);
        }
        else
        {
            System.out.println("Please input all the details.");
        }
    }
    
    @FXML
    private void SpcEditPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("editSPC.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void UpdateSpcButton(ActionEvent event) 
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
            System.out.println("It works");
            for(int i=1; i<5; i++)
            {
                SpecialistDB a= new SpecialistDB();
                a.editSPC(id,data[i],i);
            }            
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
