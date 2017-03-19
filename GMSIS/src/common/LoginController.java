/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author prashant
 */

public class LoginController implements Initializable
{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton login;
    
    // Checks if username and password is correct
    public void checkEntry(ActionEvent event) throws IOException
    {
        int username = 0;
        boolean check = true;
        try
        {
            username = Integer.parseInt(user.getText());
        }
        catch(NumberFormatException e)
        {
            incorrectInfo();
            check = false;
        }
        if(check)
        {
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
                    SoundEffect.playOpening();
                    controller.setLabel(set.getString("UserType"), String.valueOf(username), set.getString("Firstname") + " " + set.getString("Surname"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root1);
                    stage.setScene(scene);
                    stage.setResizable(false);
//                    stage.setTitle("Home Page");
                    stage.show();
                    
//                    scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//                    @Override
//                    public void handle(KeyEvent evt) {
//                        if (evt.getCode().equals(KeyCode.ESCAPE)) {
//                            stage.close();
//                        }
//                    }});
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
            }
            catch(SQLException e)
            {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);   
            }
        }
    }
    
    // Works by pressing Enter
    @FXML
    private void onEnter(ActionEvent e) throws IOException
    {
        checkEntry(e);
    }
    
    // Works by pressing Login
    @FXML
    private void dologin(ActionEvent e) throws IOException
    {
        checkEntry(e);
    }
    
    // If username or password is incorrect, prints this message
    private void incorrectInfo()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Incorrect Username or Password");
        alert.setContentText("Try again");
        alert.showAndWait();
    }
    
//    private void voidEntry()
//    {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("");
//        alert.setHeaderText("You have not typed anything to search yet!");
//        alert.setContentText("Type the username and password...");
//        alert.showAndWait();
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {}
}