/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableColumn<?, ?> adr;
    @FXML
    private TextField regNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
