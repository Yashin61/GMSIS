/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Parts.gui;
import Parts.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    }

    @FXML
    private void addPart(ActionEvent event) {
         
       
       int ID=Integer.parseInt(Parts.getText());
       int number = Integer.parseInt(qty.getText());
        Partss p = new Partss(ID);
        p.addStock(number);
      
      
            
       
        
        
    }
    
   
}
