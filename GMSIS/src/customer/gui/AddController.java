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

//import customer.logic.customers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import common.CommonDatabase;
import customer.logic.customers;
import javafx.scene.layout.AnchorPane;

public class AddController implements Initializable 
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
    private RadioButton private_type;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton business_type; 
    
    @FXML
    private AnchorPane addPane;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
    @FXML
    public void clearDetails(ActionEvent event)
    {
        firstname.setText("");
        surname.setText("");
        address.setText("");
        postcode.setText("");
        phone.setText("");
        email.setText("");
    }
    
    // method to add a new customer
    @FXML
    public void addCustomer(ActionEvent event)
    {
        RealController controller = new RealController();
        if(firstname.getText().trim().isEmpty() || surname.getText().trim().isEmpty() ||  address.getText().trim().isEmpty() || postcode.getText().trim().isEmpty() || phone.getText().trim().isEmpty() || email.getText().trim().isEmpty())
        {
            
            controller.printMissing();
        }
        else
        {
            int phoneNumber = 0;
            boolean check = controller.checkForString(phone.getText());
            String account_type = "private";
            if(business_type.isSelected())
            {
                account_type = "business";
            }
            
            if(check)
            {
                customers acc = new customers(firstname.getText(), surname.getText(), address.getText(), postcode.getText(), phone.getText(), email.getText(), account_type);
                String sql = "INSERT INTO Customer_Accounts( ID, Firstname, Surname, Address, Postcode, Phone, Email, Account) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = null;
                Connection connection = null;
                CommonDatabase db = new CommonDatabase();
                connection = db.getConnection();
                try
                {
                    statement = connection.prepareStatement(sql);
                    statement.setString(2, acc.getFirstname());         
                    statement.setString(3, acc.getSurname());
                    statement.setString(4, acc.getAddress());
                    statement.setString(5, acc.getPostcode());
                    statement.setString(6, acc.getPhone());
                    statement.setString(7, acc.getEmail());
                    statement.setString(8, acc.getAccount());
                    statement.execute(); 
                }
                catch(SQLException ex)
                {
                    ex.getMessage();
                }
                close(connection);
                clearDetails(event);
                controller.infoGiven(acc.getFirstname(), "add");
                Stage stage = (Stage) addPane.getScene().getWindow();
                stage.close();
            }
            else
            {
                controller.printPhone();
                phone.setText("");
            }
        }
    }
    
    public void close(Connection connection)
    {
        try
        {
            if(connection != null)
            {
                connection.close();
                System.out.println("CLOSED");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
}
