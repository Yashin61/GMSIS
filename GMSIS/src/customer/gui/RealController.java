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
import customer.logic.allCustomers;
import customer.logic.customers;
import java.io.IOException;
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
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class RealController implements Initializable 
{
    @FXML
    private AnchorPane rootPane;
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
    
    @FXML
    private TableView<allCustomers> dataTable;

    @FXML
    private TableColumn<allCustomers, Integer> customer_ID;

    @FXML
    private TableColumn<allCustomers, String> first;

    @FXML
    private TableColumn<allCustomers, String> sur;

    @FXML
    private TableColumn<allCustomers, String> adr;

    @FXML
    private TableColumn<allCustomers, String> post;

    @FXML
    private TableColumn<allCustomers, String> mobile;

    @FXML
    private TableColumn<allCustomers, String> ema;
    
    @FXML
    private TableColumn<allCustomers, String> type;

    @FXML
    private TextField regNumber;
    
    @FXML
    private ObservableList<allCustomers> data;
    
    
    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  
    {
        //display();
    }    
    
    
    
    // clear method for the add page
    @FXML
    public void clearDetails(ActionEvent event)
    {
        firstname.setText(null);
        surname.setText(null);
        address.setText(null);
        postcode.setText(null);
        phone.setText(null);
        email.setText(null);
    }
    
    // opens the add page 
    @FXML
    private void openAddPage(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerAdd.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));  
        stage.show();
    }
    
    @FXML
    private void openAdd(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RealAddPage.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));  
        stage.show();
    }
    
    
    // opens the edit page
    @FXML
    private void openEditPage(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerEdit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            EditController controller=fxmlLoader.<EditController>getController();
            controller.setAllFields(cust);
            Stage stage = new Stage();
            stage.setTitle("Edit Customer");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
    
    
    // alert box when no account has been selected
    @FXML
    private void noChosen()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("No account selected");
        alert.setContentText("Please select a customer account");
        alert.showAndWait();
    }
    

    // method to delete the chosen customer account
    @FXML
    private void deleteCustomer(ActionEvent event)
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to continue deleting the account?");
            
            ButtonType yes = new ButtonType("YES");
            ButtonType no = new ButtonType("NO");
            alert.getButtonTypes().setAll(yes, no);
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if(result.get() == yes)
            {
            
                int cust_id = cust.getID();
                String sql = "DELETE FROM Customer_Accounts WHERE ID = ?";
                CommonDatabase db = new CommonDatabase();
                Connection connection = null;

                try
                {
                    connection = db.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, cust_id);
                    statement.executeUpdate();
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
                close(connection);
                     
            }
            else
            {
                alert.close();
            }
        }
        display();
    }
    
    
    // displays all the customers in the table view
    @FXML
    private void displayCustomers(ActionEvent event)
    {
        display();
    }
    
    @FXML
    public void display()
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        
        try
        {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Customer_Accounts");
            while(rs.next())
            {
                data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }

        customer_ID.setCellValueFactory(new PropertyValueFactory("ID"));
        first.setCellValueFactory(new PropertyValueFactory("Firstname"));
        sur.setCellValueFactory(new PropertyValueFactory("Surname"));
        adr.setCellValueFactory(new PropertyValueFactory("Address"));
        post.setCellValueFactory(new PropertyValueFactory("Postcode"));
        mobile.setCellValueFactory(new PropertyValueFactory("Phone"));
        ema.setCellValueFactory(new PropertyValueFactory("Email"));
        type.setCellValueFactory(new PropertyValueFactory("Account"));
        dataTable.setItems(null);
        dataTable.setItems(data);
    }
               
    
    
    @FXML 
    private void viewVehicle(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewVehicles.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            viewController controller=fxmlLoader.<viewController>getController();
            controller.setCustomer(cust, "Vehicles");
            Stage stage = new Stage();
            stage.setTitle("View Vehicles");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
    
    @FXML 
    private void viewBookings(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewBookings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            viewController controller=fxmlLoader.<viewController>getController();
            controller.setCustomer(cust, "Bookings");
            Stage stage = new Stage();
            stage.setTitle("View Bookings");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
    
    @FXML 
    private void viewParts(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewParts.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            viewController controller=fxmlLoader.<viewController>getController();
            controller.setCustomer(cust, "Parts");
            Stage stage = new Stage();
            stage.setTitle("View Parts");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
    
    // search customer by full firstname and partial surname or by vehicle registration number
    @FXML
    private void searchCustomer(ActionEvent event)
    {
        if(firstname.getText() != null)
        {
            try
            {
                String sql = "select * from Customer_Accounts where Firstname = '" + firstname.getText() + "'" + "and Surname like '" + surname.getText() + "%'";
                String sql2 = "select * from Vehicles where RegistrationNumber = '" + regNumber.getText() + "'";
                PreparedStatement statement = null;
                Connection connection = null;
                CommonDatabase db = new CommonDatabase();
                connection = db.getConnection();

                statement = connection.prepareStatement(sql);
                
                ResultSet rs = statement.executeQuery();
                data = FXCollections.observableArrayList();
                while(rs.next())
                {
                    data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                }
                
                PreparedStatement statement2 = connection.prepareStatement(sql2);
                rs = statement2.executeQuery();
                if(rs.next())
                {
                    String reg = rs.getString("CustomersID");
                    sql2 = "select * from Customer_Accounts where ID = '" + reg + "'";
                    statement2 = connection.prepareStatement(sql2);
                    rs = statement2.executeQuery();
                    if(rs.next())
                    {
                        data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                    }
                }
                
            }
            catch(SQLException e)
            {
                System.out.println("Doesnt Work");
            }
                    
            customer_ID.setCellValueFactory(new PropertyValueFactory("ID"));
            first.setCellValueFactory(new PropertyValueFactory("Firstname"));
            sur.setCellValueFactory(new PropertyValueFactory("Surname"));
            adr.setCellValueFactory(new PropertyValueFactory("Address"));
            post.setCellValueFactory(new PropertyValueFactory("Postcode"));
            mobile.setCellValueFactory(new PropertyValueFactory("Phone"));
            ema.setCellValueFactory(new PropertyValueFactory("Email"));
            type.setCellValueFactory(new PropertyValueFactory("Account"));
            dataTable.setItems(null);
            dataTable.setItems(data);
        }
            
        
    }
    
    
    // search functions to hide
    @FXML
    public void handleNames(MouseEvent event)
    {
        regNumber.setDisable(true);
        regNumber.setText("");
    }
     // search functions to hide
    @FXML
    public void handleOther(MouseEvent event)
    {
        regNumber.setDisable(false);
        firstname.setDisable(true);
        surname.setDisable(true);
        firstname.setText("");
        surname.setText("");
    }
     // search functions to hide
    @FXML
    public void handle(MouseEvent event)
    {   
        regNumber.setDisable(false);
        firstname.setDisable(false);
        surname.setDisable(false);
    }
    
    
    // switch to home page 
    @FXML
    private void change2Home(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/src/common/Template.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to vehicle page
    @FXML
    private void change2Vehicle(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to bookings page
    @FXML
    private void change2Bookings(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/diagrep/gui/BookingDetails.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to parts page
    @FXML
    private void change2Parts(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/parts/gui/PartsPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to specialist page
    @FXML
    private void change2Specialist(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/gui/specialistGUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    public void printMissing()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Missing Fields");
        alert.setHeaderText("Missing Fields");
        alert.setContentText("Fill in all the details");
        alert.showAndWait();
    }
    
    // method to add a new customer
    @FXML
    public void addCustomer(ActionEvent event)
    {
        if(firstname.getText().trim().isEmpty() || surname.getText().trim().isEmpty() || address.getText().trim().isEmpty() || postcode.getText().trim().isEmpty() || phone.getText().trim().isEmpty() || email.getText().trim().isEmpty())
        {
            printMissing();
        }
        else
        {
            String account_type = "private";
            if(business_type.isSelected())
            {
                account_type = "business";
            }

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
            infoGiven(acc.getFirstname(), "add");
        }
    }
    
    
    @FXML
    public void infoGiven(String name, String type)
    {
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("COMPLETED");
        if(type == "add")
        {
            alert.setContentText("Customer " + name + " has been successfully added");
        }
        else
        {
            alert.setContentText("Customer Account " + name + " has been successfully edited");
        }
        alert.showAndWait();
    }
    
    // common method to close the database connection
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