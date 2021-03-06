/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import customer.gui.EditController;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author prashant
 */

public class TemplateController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label user;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label idLabel;
    
    public static String userType;
    public static String name;
    public static String allID;
    
    
    // sets the label(User ID and name) on the front page
    public void setLabel(String userAccount, String id, String username)
    {
        userType = userAccount;
        allID = id;
        name = username;
        user.setText(userAccount);
        idLabel.setText(id);
        welcomeLabel.setText(username);
    }
    
    @FXML
    public static String getUserID()
    {
        return allID;
    }
    // opens the specialist page
    @FXML
    private void spcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/gui/spcMainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // opens the customer page
    @FXML
    private void customerPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/customer/gui/CustomerRealPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // opens the vehicles page
    @FXML
    private void vehiclePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        rootPane.getChildren().setAll(pane);
//        pane.setTitle("Vehicle Records");
//        pane.setResizable(false);
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
    
    // If the user is an admin, then they can access the admin page. 
    // Else prints a message saying admin access only
    @FXML
    public void adminLoginPage(ActionEvent event) throws IOException
    {
        String sentence = idLabel.getText();  
        String[] words = sentence.split(" "); 
        System.out.println(words[0]);
        int userID = Integer.parseInt(words[0]);
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
    
    
    private void adminAccess()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("ADMIN ACCESS ONLY");
        alert.setContentText("");
        alert.showAndWait();
    }

    
    @FXML
    private void logOut(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Do you want to log out?");
            
        ButtonType yes = new ButtonType("YES");
        ButtonType no = new ButtonType("NO");
        
        alert.getButtonTypes().setAll(yes, no);
            
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == yes)
        {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            
            Stage stage = new Stage();
            stage.setTitle("GMSIS login");
            stage.setScene(new Scene(root1));
            stage.show();
  
        }
        else
        {
                alert.close();
        }
        
    }
    
    private void printAlert()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("LOGOUT");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("");
        alert.showAndWait();
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        System.out.println(userType);
        System.out.println(name);
        System.out.println(allID);
        this.setLabel(userType, allID, name);
    }    
    
}
