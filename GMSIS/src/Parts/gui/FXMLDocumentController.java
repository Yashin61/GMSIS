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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author rohim
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private TableView<?> Customer_Det;
    @FXML
    private Button Add_To_Repair;
    @FXML
    private Button Search_Customer;
    private TextField qty;
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
    @FXML
    private Button UpdateParts;
    @FXML
    private Button Create_Query;
    @FXML
    private ChoiceBox<String> Parts_Coice;
    
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
        
    }

    private void addPart(ActionEvent event) {
         
      
       int ID=Integer.parseInt(Parts.getText());
       int number = Integer.parseInt(qty.getText());
        Parts p = new Parts(ID);
        p.addStock(number);
          
    }

    private void IDSearch(ActionEvent event) {
        
        int ID=Integer.parseInt(Parts.getText());
        Parts p = new Parts(ID);
        p.SeachByID();
    }

    @FXML
    private void Go_To_Parts(ActionEvent event) {
        
        try{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddParts.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        }
        catch(Exception e){
        
        System.out.println("No doesnt work");
        
        }
    }

    @FXML
    private void Go_To_Query(ActionEvent event) {
    }

    @FXML
    private void Refresh_Parts(KeyEvent event) {
        
      
        
    }
    
    
   
}
