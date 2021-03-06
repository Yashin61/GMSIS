
package customer.gui;

import common.CommonDatabase;
import customer.logic.allCustomers;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Nandhini Manoharan
 */
public class EditController implements Initializable
{
    
    
    @FXML
    private AnchorPane rootPane;
    
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
    private TextField ID;
    
    @FXML
    private int customer_ID;
    
    @FXML
    private String account;
    
    
    public void initialize(URL url, ResourceBundle rb)
    {
    }
    
    
    // method to set the fields when a particular customer is selected to edit their details
    @FXML
    public void setAllFields(allCustomers cust)
    {
        customer_ID = cust.getID();
        account = cust.getAccount();
        if(account.equals("business"))
        {
            business_type.setSelected(true);
        }
        firstname.setText(cust.getFirstname());
        surname.setText(cust.getSurname());
        address.setText(cust.getAddress());
        postcode.setText(cust.getPostcode());
        phone.setText(cust.getPhone());
        email.setText(cust.getEmail());
        
        
    }
    
    // method to edit the customer account 
    // checks if any fields are empty 
    @FXML
    private void edit(ActionEvent evt) throws IOException
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        PreparedStatement statement = null;
        RealController controller = new RealController();
        
        if(firstname.getText().trim().isEmpty() || surname.getText().trim().isEmpty() || address.getText().trim().isEmpty() || postcode.getText().trim().isEmpty() || phone.getText().trim().isEmpty() || email.getText().trim().isEmpty())
        { 
            
            controller.printMissing();
        }
        else
        {
            boolean check = controller.checkForString(phone.getText());
            if(check)
            {
                String sql = "UPDATE Customer_Accounts SET Firstname = ? , " + "Surname = ? , " + "Address = ? , " + "Postcode = ? , " + "Phone = ? , " + "Email = ? , " + "Account = ? " + "WHERE ID = ?";
               
                try
                {
                    connection = db.getConnection();
                    statement = connection.prepareStatement(sql);

                    statement.setString(1, firstname.getText());         
                    statement.setString(2, surname.getText());
                    statement.setString(3, address.getText());
                    statement.setString(4, postcode.getText());
                    statement.setString(5, phone.getText());
                    statement.setString(6, email.getText());

                    if(private_type.isSelected())
                    {
                        account = "private";
                    }
                    else
                    {
                        account = "business";
                    }
                    statement.setString(7, account);
                    statement.setInt(8, customer_ID);
                    statement.executeUpdate();   

                }
                catch(SQLException e)    
                {   
                    System.out.println(e.getMessage());

                }
                close(connection);
                controller.infoGiven(firstname.getText(), "edit");
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
            }
            else
            {
                controller.printPhone();
                phone.setText("");
            }
        }
    }
    
    
    @FXML
    private void clear(ActionEvent event)
    {
        firstname.setText(null);
        surname.setText(null);
        address.setText(null);
        postcode.setText(null);
        phone.setText(null);
        email.setText(null);
    }

    // method to close the connection for the database
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
