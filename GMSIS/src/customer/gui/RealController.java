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
import javafx.scene.control.cell.PropertyValueFactory;


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
    private TextField regNumber;
    
    private ObservableList<allCustomers> data;
    

   
    
    
    
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
            String sql = "INSERT INTO Customer_Accounts( ID, Firstname, Surname, Address, Postcode, Phone, Email, AccountType) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
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
            int id = Integer.parseInt(ID.getText());
            String sql = "UPDATE Customer_Accounts SET ID = '"+id+"', Firstname= '"+firstname.getText()+"', Surname= '"+surname.getText()+"', Address= '"+address.getText()+"', Postcode= '"+postcode.getText()+"', Phone= '"+phone.getText()+"', Email= "+email.getText()+" WHERE ID= "+ id + "; ";
            customers acc = new customers(firstname.getText(), surname.getText(), address.getText(), postcode.getText(), phone.getText(), email.getText(), "private");
            acc.editCustomer(sql, id);   
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
    
    @FXML
    private void openAddPage(ActionEvent event) throws IOException
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerAdd.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
    }
    
    @FXML
    private void openEditPage(ActionEvent event) throws IOException
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerEdit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } 
        catch(Exception e) 
        {
           e.printStackTrace();
        }
    
    }
   
    /**
    private void setCellTable()
    {
        customer_ID.setCellValueFactory(new PropertyValueFactory("id"));
        
        first.setCellFactory(new PropertyValueFactory("firstname"));
        sur.setCellFactory(new PropertyValueFactory("surname"));
        adr.setCellFactory(new PropertyValueFactory("address"));
        post.setCellFactory(new PropertyValueFactory("postcode"));
        mobile.setCellFactory(new PropertyValueFactory("phone"));
        ema.setCellFactory(new PropertyValueFactory("email"));
    }
    
    private void loadDataFromDatabase()
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        try
        {
            PreparedStatement statement = connection.prepareStatement("select * from Customer_Accounts");
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                data.add(new customerDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("error");
        }
        
        dataTable.setItems(data);
        
    }
    
    @FXML
    private void viewAllCustomers(ActionEvent event)
    {
        data = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDatabase();
        
    }**/
    @FXML
    private void displayCustomers(ActionEvent event)
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
                data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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
  
        dataTable.setItems(null);
        dataTable.setItems(data);
                
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
         
    }    
    
}