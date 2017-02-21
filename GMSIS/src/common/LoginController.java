/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author prashant
 */

public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton signUp;

    @FXML
    private JFXRadioButton adminAccount;

    @FXML
    private JFXRadioButton userAccount;

    @FXML
    public void dologin(ActionEvent event) throws IOException{
        String username = user.getText();
        String passWord = password.getText();
        Connection connect = null;
        Statement stmt = null;      
        /*
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection("jdbc:sqlite:Records.db");
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM Login");

            while(set.next()){
                if(username.equals(set.getString("Username")) && passWord.equals(set.getString("Password")))
                {
                    System.out.println("HELLO");
                }
            }*/
            if(username.equals("hello") && passWord.equals("ok"))
            {
                System.out.println("HELLO");
                AnchorPane pane = FXMLLoader.load(getClass().getResource("specialistGUI.fxml"));
                rootPane.getChildren().setAll(pane);
                
            }
            else
            {
                System.out.println("Incorrect username or password");
                user.clear();
                password.clear();
            }

            /*
            stmt.close();
            set.close();
            connect.close();

          }catch(SQLException e)
          {
              JOptionPane.showMessageDialog(null, "Login cannot be located");   
          } */
    }

        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
    }    
    
}
