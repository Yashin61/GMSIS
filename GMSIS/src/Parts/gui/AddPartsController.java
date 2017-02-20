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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rohim
 */
public class AddPartsController implements Initializable {

    @FXML
    private TextField qty;
    @FXML
    private Button Add_Parts;
    @FXML
    private TextField Parts;
    @FXML
    private Button Search_Parts;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
