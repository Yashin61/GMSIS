/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.gui;

/**
 *
 * @author Nandhini
 */

import customer.ConnectToDatabase;
import customer.logic.customers;
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
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class accountController implements Initializable 
{
   
    
    /****** For Add Page **********/
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
    private RadioButton private_type;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton business_type;  
    
    
    public void add(ActionEvent evt)
    {
        if(firstname.getText() == null || surname.getText() == null || address.getText() == null|| postcode.getText() == null || phone.getText() == null || email.getText() == null)
        { 
            System.out.println("Fill in all details");
            clearDetails(evt);
        }
        else
        {
            String account_type = "private";
            if(business_type.isSelected())
            {
                account_type = "business";
            }

            customers acc = new customers(firstname.getText(), surname.getText(), address.getText(), postcode.getText(), phone.getText(), email.getText(), account_type);
            String sql = "INSERT INTO Customer_Accounts( ID, Firstname, Surname, Address, Postcode, Phone, Email) VALUES(?, ?, ?, ?, ?, ?, ?)";
            acc.addCustomer(sql); 
            clearDetails(evt);
        }
    }
    
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
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
}
