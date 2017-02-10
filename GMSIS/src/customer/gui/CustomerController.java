/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.gui;


import customer.ConnectToDatabase;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * FXML Controller class
 *
 * @author Manoharan
 */
public class CustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField firstname;

    @FXML
    private TextField surname;

    @FXML
    private TextField address;

    @FXML
    private TextField postcode;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;
    
    
    
    @FXML
    private void clearDetails(ActionEvent event)
    {
        firstname.setText(null);
        surname.setText(null);
        address.setText(null);
        postcode.setText(null);
        phone.setText(null);
        email.setText(null);
    }
    
      
    private Connection connect()
    {
        String url = "jdbc:sqlite:src/common/Records.db";
        Connection conn = null;
        
        try 
        {
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    
    @FXML
    private void addCustomer(ActionEvent event) 
    {        
        if(firstname.getText().equals("") || surname.getText().equals("") || address.getText().equals("")|| postcode.getText().equals("") || phone.getText().equals("") || email.getText().equals(""))
        { 
            System.out.println("Some fields are missing");
            clearDetails(event);
        }
        else
        {
            String sql = "INSERT INTO Customer_Accounts(ID, Firstname, Surname, Address, Postcode, Phone, Email) VALUES(?, ?, ?, ?, ?, ?, ?)";
               
            try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(2, firstname.getText());         
                pstmt.setString(3, surname.getText());
                pstmt.setString(4, address.getText());
                pstmt.setString(5, postcode.getText());
                pstmt.setString(6, phone.getText());
                pstmt.setString(7, email.getText());
                pstmt.executeUpdate();   

                clearDetails(event);
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
