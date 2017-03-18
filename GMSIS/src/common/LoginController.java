/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.Alert.AlertType;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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


    
    // Checks if username and password is correct
    @FXML
    public void dologin(ActionEvent event) throws IOException{
        
        
        int username = Integer.parseInt(user.getText());
        String passWord = password.getText();
        Connection connect = null;
        Statement stmt = null;    
        PreparedStatement statement = null;
        
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM Employees WHERE ID = '" + username + "' ");
            //String sql = "UPDATE Employees SET Status = '"  + 1 + "' WHERE ID = '" + username + "' ";
            //statement = connect.prepareStatement(sql);
            //statement.executeUpdate();  
            boolean flag = false;
            if(set != null)
            {              
                if(passWord.equals(set.getString("Password")))
                {
                    flag = true;
                    user.clear();
                    password.clear();
                }
            }
            else
            {
                System.out.println("Incorrect username or password");
                user.clear();
                password.clear();
            }
            if(flag)
            {
                Stage stage3 = (Stage) rootPane.getScene().getWindow();
                stage3.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Template.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                TemplateController controller=fxmlLoader.<TemplateController>getController();
                controller.setLabel(String.valueOf(username), set.getString("Firstname") + " " + set.getString("Surname"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root1);
                stage.setScene(scene);
                stage.show();  
                
                
                
                
            }
            else
            {
                System.out.println("Incorrect username or password");
                incorrectInfo();
                user.clear();
                password.clear();
            }
        
            stmt.close();
            set.close();
            connect.close();

        }catch(SQLException e)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);   
        } 
    }
    
    // If username or password is incorrect, prints this message
    @FXML
    private void incorrectInfo()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Incorrect Username or Password");
        alert.setContentText("Try again");
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }    
    
}
