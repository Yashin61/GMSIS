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

import common.CommonDatabase;
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

public class RealController implements Initializable 
{
    /****** For Edit Page **********/
    
    @FXML
    private TextField ID;

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
    
    
    public void searchEdit(ActionEvent evt)
    {
        if(ID.getText() == null){}
        else
        {
            firstname.setVisible(true);
            surname.setVisible(true);
            address.setVisible(true);
            postcode.setVisible(true);
            phone.setVisible(true);
            email.setVisible(true);
            try
            {
                String sql = "select * from Customer_Accounts where ID = ?";
                PreparedStatement statement = null;
                Connection connection = null;
                CommonDatabase db = new CommonDatabase();
                connection = db.getConnection();

                statement = connection.prepareStatement(sql);
                statement.setString(1, ID.getText());
                
                ResultSet rs = statement.executeQuery();
                if(rs.next())
                {
                    String fName = rs.getString("Firstname");
                    firstname.setText(fName);
                    String sName = rs.getString("Surname");
                    surname.setText(sName);
                    String adr = rs.getString("Address");
                    address.setText(adr);
                    String pCode = rs.getString("Postcode");
                    postcode.setText(pCode);
                    String number = rs.getString("Phone");
                    phone.setText(number);
                    String emailAdr = rs.getString("Email");
                    email.setText(emailAdr);
                    
                }
                else
                {
                    clearDetails(evt);
                }
            }
            catch(SQLException e)
            {
                System.out.println("There is no customer account with this id");
            }
        }
    }
    
    public void edit(ActionEvent evt)
    {
        if(firstname.getText() == null || surname.getText() == null || address.getText() == null|| postcode.getText() == null || phone.getText() == null || email.getText() == null)
        { 
            System.out.println("Some fields are empty");
        }
        else
        {
            String sql = "update Customer_Accounts set ID=?, Firstname=?, Surname=?, Address=?, Postcode=?, Phone=?, Email=? where ID=" + Integer.parseInt(ID.getText()) + "";
            customers acc = new customers(firstname.getText(), surname.getText(), address.getText(), postcode.getText(), phone.getText(), email.getText(), "private");
            acc.editCustomer(sql, Integer.parseInt(ID.getText()));   
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
