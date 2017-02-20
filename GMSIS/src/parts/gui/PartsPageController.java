/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rohim
 */
public class PartsPageController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private TextField surname;
    @FXML
    private TableView<?> dataTable;
    @FXML
    private TableColumn<?, ?> customer_ID;
    @FXML
    private TableColumn<?, ?> first;
    @FXML
    private TableColumn<?, ?> sur;
    @FXML
    private TextField regNumber;
    @FXML
    private Button Edit;
    @FXML
    private Button Add_Parts;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label postcode;
    @FXML
    private Label phone;
    @FXML
    private Button clear;
    @FXML
    private TableColumn<?, ?> booking;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Edit_Page(ActionEvent event) {
        try{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PartsEdit.fxml"));
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
    private void Add_Parts(ActionEvent event) {
        try{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PartsAdd.fxml"));
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
    private void clear(ActionEvent event) {
            email.setText("");
  address.setText("");
 postcode.setText("");
   phone.setText("");
  regNumber.clear();
     firstname.clear();
   surname.clear();
        
    }
    
}
