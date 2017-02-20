/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Manoharan
 */
public class AuthenticationController implements Initializable
{
    @FXML
    private TextField id;

    @FXML
    private TextField password;

    @FXML
    private RadioButton admin_type;

    @FXML
    private RadioButton user_type;
    
    @FXML
    private ToggleGroup Type;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
         
        
    } 
    
    @FXML
    public void clickLogin(ActionEvent event)
    {
        if(id.getText() == null || password.getText() == null)
        {
            System.out.println("Missing Fields");
        }
        System.out.println("HELLO");
    }

    
}
