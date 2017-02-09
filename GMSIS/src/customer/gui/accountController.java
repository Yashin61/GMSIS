/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.gui;

/**
 *
 * @author Manoharan
 */

import customer.logic.customers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class accountController implements Initializable
{
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
    private MenuButton account;
    
    /**@FXML
    private TableView<customers> dataTable;

    @FXML
    private TableColumn id;

    @FXML
    private TableColumn first;

    @FXML
    private TableColumn sur;

    @FXML
    private TableColumn add;

    @FXML
    private TableColumn post;

    @FXML
    private TableColumn mobile;

    @FXML
    private TableColumn ema;
    ***/
    
    @FXML
    private void add(ActionEvent event)
    {
        if(firstname.getText().equals("") || surname.getText().equals("") || address.getText().equals("")|| postcode.getText().equals("") || phone.getText().equals("") || email.getText().equals(""))
        { 
            System.out.println("Fill in all details");
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
    
    // Clears all fields
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
    
    // DB Connection method
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
    public void searchCustomer(ActionEvent event)
    {
        openSearchPage open = new openSearchPage();
        open.launch();
    }
    
    @FXML 
    public void updateAll(ActionEvent event)
    {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
       
    
}
