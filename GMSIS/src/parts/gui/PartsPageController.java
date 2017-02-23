/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import parts.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
/**
 * FXML Controller class
 *
 * @author rohim
 */
public class PartsPageController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private TextField surname;
    @FXML
    private TableView<Customers_Parts_Edit> dataTable;
    @FXML
    private TextField regNumber;
    @FXML
    private Button Edit;
    @FXML
    private Button Add_Parts;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label postcode;
    @FXML
    private Label phone;
    @FXML
    private Button clear;
    @FXML
    private TableColumn<Customers_Parts_Edit, Integer> booking;
    @FXML
    private TableColumn<Customers_Parts_Edit, String> installed;
    @FXML
    private TableColumn<Customers_Parts_Edit, Integer> customerid;
    @FXML
    private TableColumn<Customers_Parts_Edit, Integer> regno;
    @FXML
    private TableColumn<Customers_Parts_Edit, Integer> parts;
    @FXML
    private TableColumn<Customers_Parts_Edit, String> expire;
    @FXML
    private Button search;
    @FXML
    private ChoiceBox<String> Parts;
    @FXML
    private Button add_choice;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Date date = new Date();
System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
         ConnectionToParts conn = new ConnectionToParts();
        String sql = "SELECT * FROM Parts ";
         Connection con=conn.connect();
        try{
        PreparedStatement stat = con.prepareStatement(sql);
   
       ResultSet info = stat.executeQuery();
       boolean i=true;
         while(info.next()){
             Parts.getItems().add(info.getString(2));
             if(i){
                 Parts.setValue(info.getString(2));
                 i=false;
             }
         }
         
         con.close();
        
        }
        catch(SQLException e)
        {
            
        }
        
        
    }    

    @FXML
    private void Edit_Page(ActionEvent event) {
        try{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PartsEdit.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        }
        catch(Exception e){
        
        System.out.println("No doesnt work");
        
        }
    }

    @FXML
    private void Add_Parts(ActionEvent event) {
        try{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PartsAdd.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        }
        catch(Exception e){
        
        System.out.println("No doesnt work");
        
        }
    }

    @FXML
    private void clear(ActionEvent event) {
            email.setText("");colour(email,true);
  address.setText("");colour(address,true);
 postcode.setText("");colour(postcode,true);
   phone.setText("");colour(phone,true);
  regNumber.clear();
     firstname.clear();
   surname.clear();
        
    }

    @FXML
    private void search_customer(ActionEvent event) {
       
        try{
        if(firstname.getText() !=null && surname.getText() !=null){ 
            first_sur(firstname.getText(),surname.getText());
            
        }
       
        if(regNumber.getText() !=null){
             System.out.println("1");
            int i=Integer.parseInt(regNumber.getText());
                 reg_no(i);
                     
        }
        }
        catch(NumberFormatException e){
            
        }
       
       
        
    }
    
    public void first_sur(String f, String s){
        ConnectionToParts conn = new ConnectionToParts();
        String sql = "SELECT * FROM Customer_Accounts WHERE Firstname = ? AND Surname = ? ";
         Connection con=conn.connect();
        try{
            System.out.println("2");
        PreparedStatement stat = con.prepareStatement(sql);
         System.out.println("3");
        stat.setString(1,f);
         stat.setString(2,s);
          System.out.println("4");
       ResultSet info = stat.executeQuery();
       regNumber.setText(info.getString(1));
        address.setText(info.getString(4));colour(address,false);
        email.setText(info.getString(7));colour(email,false);
        postcode.setText(info.getString(5));colour(postcode,false);
        phone.setText(info.getString(6));colour(phone,false);
         con.close();
        }
        catch(SQLException e)
        {
            
        }
    }
    
    public void reg_no(int r){
                ConnectionToParts conn = new ConnectionToParts();
        String sql = "SELECT * FROM Customer_Accounts WHERE ID = ? ";
         Connection con=conn.connect();
        try{
        PreparedStatement stat = con.prepareStatement(sql);
     stat.setInt(1,r);
       ResultSet info = stat.executeQuery();
       firstname.setText(info.getString(2));
         firstname.setText(info.getString(3));
        address.setText(info.getString(4));colour(address,false);
        email.setText(info.getString(7));colour(email,false);
        postcode.setText(info.getString(5));colour(postcode,false);
        phone.setText(info.getString(6));colour(phone,false);
         con.close();
        }
        catch(SQLException e)
        {
            
        }
    }
    
    public void visible(Label l,boolean t){
      l.setVisible(t);
        
    }
    
    public void colour(Label l, boolean p){
        if(!p){
      l.setStyle("-fx-background-color:White");
      return;
        }
        l.setStyle("-fx-background-color:Grey");
    }
    

    @FXML
    private void Add_Choice(ActionEvent event) {
        
        System.out.println(Parts.getValue());
    }
            
            
}
