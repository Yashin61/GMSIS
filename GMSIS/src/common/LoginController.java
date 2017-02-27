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
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML
    private JFXTextField userAdmin;
    @FXML
    private JFXPasswordField passwordAdmin;
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
        
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection("jdbc:sqlite:src/spcpage/TestRecords.db");
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM Login");
            boolean flag = false;
            while(set.next()){              
                if(username.equals(set.getString("Username")) && passWord.equals(set.getString("Password")))
                {
                    flag = true;
                    break;
                }
            }
            if(flag)
            {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("Template.fxml"));
                rootPane.getChildren().setAll(pane);
            }
            else
            {
                System.out.println("Incorrect username or password");
                user.clear();
                password.clear();
            }
        
            stmt.close();
            set.close();
            connect.close();

          }catch(SQLException e)
          {
              JOptionPane.showMessageDialog(null, "Login cannot be located");   
          } 
    }
    
    @FXML
    public void adminLoginPage(ActionEvent event) throws IOException{
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adminLogin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } 
        catch(IOException e) {}
    }
    
    @FXML
    public void doAdminlogin(ActionEvent event) throws IOException{
        String username = userAdmin.getText();
        String passWord = passwordAdmin.getText();
        Connection connect = null;
        Statement stmt = null;      
        
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection("jdbc:sqlite:src/spcpage/TestRecords.db");
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM Login WHERE Id = 1");
            boolean flag = false;
            while(set.next()){              
                if(username.equals(set.getString("Username")) && passWord.equals(set.getString("Password")))
                {
                    flag = true;
                    break;
                }
            }
            if(flag)
            {
                System.out.println("You are now a admin");
                AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                rootPane.getChildren().setAll(pane);
            }
            else
            {
                System.out.println("Incorrect username or password");
                user.clear();
                password.clear();
            }
        
            stmt.close();
            set.close();
            connect.close();

          }catch(SQLException e)
          {
              JOptionPane.showMessageDialog(null, "Login cannot be located");   
          } 
    }

        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
    }    
    
}
