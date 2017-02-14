/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Parts.gui;
import Parts.*;
import customer.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author rohim
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button Search_Parts;
    @FXML
    private TableView<?> Customer_Det;
    @FXML
    private Button Add_To_Repair;
    @FXML
    private Button Add_Parts;
    @FXML
    private Button Search_Customer;
    @FXML
    private TextField qty;
    @FXML
    private TextField Parts;
    @FXML
    private TextField Surename;
    @FXML
    private TextField Postcode;
    @FXML
    private SplitMenuButton CarPartsList;
    @FXML
    private MenuItem Engine;
    @FXML
    private MenuItem Tyre;
    @FXML
    private MenuItem FrontBumper;
    @FXML
    private MenuItem BackBumper;
    @FXML
    private MenuItem RightSideMirror;
    @FXML
    private MenuItem LeftSideMirror;
    @FXML
    private MenuItem BrakeLights;
    @FXML
    private MenuItem Spoilers;
    @FXML
    private MenuItem Brakes;
    @FXML
    private MenuItem DippedHeadLights;
    
    private void handleButtonAction(ActionEvent event) {
   
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void runcu(ActionEvent event) {
       
        String surename=Surename.getText();
        String postcode=Postcode.getText();
        ConnectionToParts con = new ConnectionToParts();
        con.searchByNameandPostcode(surename,postcode);
        System.out.println("W");
        
    }

    @FXML
    private void addPart(ActionEvent event) {
         
      
       int ID=Integer.parseInt(Parts.getText());
       int number = Integer.parseInt(qty.getText());
        Parts p = new Parts(ID);
        p.addStock(number);
          
    }

    @FXML
    private void IDSearch(ActionEvent event) {
        int ID=Integer.parseInt(Parts.getText());
        Parts p = new Parts(ID);
        p.SeachByID();
    }
    
    
   
}
