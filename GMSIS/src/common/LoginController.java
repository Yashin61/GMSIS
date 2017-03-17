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
    private Label welcomeLabel;
    @FXML
    private Label idLabel;
    public static String name;
    public static String allID;

    
    // Checks if username and password is correct
    @FXML
    public void dologin(ActionEvent event) throws IOException{
        
        
        int username = Integer.parseInt(user.getText());
        String passWord = password.getText();
        Connection connect = null;
        Statement stmt = null;      
        
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
                    allID = "USER ID: " + set.getInt("ID") ;
                    name = "Welcome " + set.getString("Firstname") + " " + set.getString("Surname");
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
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Template.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                LoginController controller=fxmlLoader.<LoginController>getController();
                
                controller.setLabel(allID, name);
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
    
    
    // sets the label(User ID and name) on the front page
    @FXML
    public void setLabel(String id, String username)
    {
        idLabel.setText(id);
        welcomeLabel.setText(username);
    }
    
    // opens the specialist page
    @FXML
    private void spcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/gui/specialistGUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // opens the customer page
    @FXML
    private void customerPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/customer/gui/CustomerPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // opens the vehicles page
    @FXML
    private void vehiclePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // opens the parts page
    @FXML
    private void partsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/parts/gui/PartsPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // opens the diagnostics page
    @FXML
    private void diagPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/diagrep/gui/BookingDetails.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    /*@FXML
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
    }*/
    
   
    @FXML
    public void doAdminlogin(ActionEvent event) throws IOException{
        String username = userAdmin.getText();
        String passWord = passwordAdmin.getText();
        Connection connect = null;
        Statement stmt = null;      
        
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
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
                //AnchorPane pane = FXMLLoader.load(getClass().getResource(".fxml"));
                //rootPane.getChildren().setAll(pane);
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
    
    // If the user is an admin, then they can access the admin page. 
    // Else prints a message saying admin access only
    @FXML
    public void adminLoginPage(ActionEvent event) throws IOException
    {
        String sentence = idLabel.getText();  
        String[] words = sentence.split(" "); 
        System.out.println(words[2]);
        int userID = Integer.parseInt(words[2]);
        CommonDatabase db = new CommonDatabase();
        Connection conn = db.getConnection();
        try
        {
            String sql = "SELECT * FROM Employees WHERE ID = '" + userID + "' ";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            System.out.println(rs.getString("UserType"));
            if(rs != null && rs.getString("UserType").equals("ADMIN"))
            {
                System.out.println("THEY ARE AN ADMIN");
                AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
                rootPane.getChildren().setAll(pane);
            }
            else
            {
                System.out.println("THEY ARE AN USER");
                adminAccess();
                System.out.println("ADMIN ACCESS ONLY");
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQL ERROR");
        }
        
    }
    
    
    @FXML
    private void adminAccess()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("ADMIN ACCESS ONLY");
        alert.setContentText("");
        alert.showAndWait();
    }

    
    @FXML
    private void logOut(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Do you want to logout?");
            
        ButtonType yes = new ButtonType("YES");
        ButtonType no = new ButtonType("NO");
        
        alert.getButtonTypes().setAll(yes, no);
            
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == yes)
        {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            
        }
        else
        {
                alert.close();
        }
        
    }
    
    
    @FXML
    private void printAlert()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("LOGOUT");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("");
        alert.showAndWait();
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
    }    
    
}
