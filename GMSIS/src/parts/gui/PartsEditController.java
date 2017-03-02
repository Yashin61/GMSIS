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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author rohim
 */
public class PartsEditController implements Initializable {

    @FXML
    private CheckBox Search_ID_CheckBox;
    @FXML
    private Label Search_ID;
    @FXML
    private TextField txt_Search_By_ID;
    @FXML
    private Button Search_By_ID_btn;
    @FXML
    private Button Search_Firstname;
    @FXML
    private Label lbl_First_search;
    @FXML
    private Label lbl_Surename_search;
    @FXML
    private TextField txt_First_search;
    @FXML
    private TextField txt_Surename_search;
    @FXML
    private Label lbl_firstname_display;
    @FXML
    private Label lbl_surname_display;
    @FXML
    private Label lbl_RN_display;
    @FXML
    private Label lbl_Make_display;
    @FXML
    private Label lbl_colour_display;
    @FXML
    private Label txt_firstname_display;
    @FXML
    private Label txt_Make_display;
    @FXML
    private Label txt_RN_display;
    @FXML
    private Label lbl_model_display;
    @FXML
    private Label txt_colour_display;
    @FXML
    private Label txt_Surname_display;
    @FXML
    private Button btn_delete_By_Part_ID;
    @FXML
    private Button Add_Parts_By_ID;
    @FXML
    private Button Clear;
    @FXML
    private TextField Search_ID_txt;
    @FXML
    private TextField Delete_ID_Part;
    @FXML
    private TextField Add_ID_Part;
    @FXML
    private Label txt_model_display;
    @FXML
    private Label txt_ID_Name;
    @FXML
    private Label txt_ID_Cost;
    @FXML
    private Button Search_Parts_Using_ID;
    @FXML
    private TableColumn<Customers_Parts_Edit, String> Reg_no;
    @FXML
    private TableColumn<Customers_Parts_Edit, Integer> Part_ID;
    @FXML
    private TableColumn<Customers_Parts_Edit, Integer> Booking_ID;
    @FXML
    private TableView<Customers_Parts_Edit> Customers_Parts_Editt;

    private static ObservableList<Customers_Parts_Edit> l;
    private ObservableList<Customers_Parts_Edit> data;
    private int Booking;
    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void Search_ID_CheckBox(ActionEvent event) {
        if (Search_ID_CheckBox.isSelected()) {
            id_Search(false, true);
        } else {
            id_Search(true, false);
        }

    }

    @FXML
    private void Search_ID(ActionEvent event) {

        if (Search_ID_CheckBox.isSelected()) {
            ID_Search();
        } else {
            name_search();
        }
        /*   ObservableList<Customers_Parts_Edit> list=null;
        table_controles tb = new table_controles();
        tb.partstable(Customers_Parts_Edit);
        int i = Integer.parseInt(txt_Search_By_ID.getText());
         String s=txt_Search_By_ID.getText();
        ConnectionToParts cn = new ConnectionToParts();
       ArrayList<Customers_Parts_Edit> p = cn.SearchCustomerparts(i);
       
             // System.out.println(p.get(0).getregNo());
       
       for(int a = 0 ; a<= p.size() ; a++ ){
              //list = FXCollections.observableArrayList(new Customers_Parts_Edit(i,p.get(a).getregNo(),p.get(a).getpID(),p.get(a).getbID()));
       }
       Customers_Parts_Edit.setItems(list);*/

    }

    public void id_Search(boolean t, boolean f) {
        Search_ID.setVisible(f);
        txt_Search_By_ID.setVisible(f);
        Search_By_ID_btn.setVisible(f);
        Search_Firstname.setVisible(t);
        lbl_First_search.setVisible(t);
        lbl_Surename_search.setVisible(t);
        txt_First_search.setVisible(t);
        txt_Surename_search.setVisible(t);

    }

    @FXML
    private void DeletePartByID(ActionEvent event) {
        ConnectionToParts conn = new ConnectionToParts();
         Connection con = conn.connect();
        Parts p=null;
       
        String sql = "DELETE FROM PartsUsed WHERE PartsID=? AND RegistrationNumber = ?";
        try{
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1,Integer.parseInt(Delete_ID_Part.getText()));
         stat.setString(2,txt_RN_display.getText());
     stat.execute();
        
               
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
          try {

            System.out.println("1");
            String h = "SELECT * FROM PartsUsed WHERE RegistrationNumber = ?";
            System.out.println("2");
            PreparedStatement ko = con.prepareStatement(h);
            System.out.println("3");
            ko.setString(1, txt_RN_display.getText());
            System.out.println("4");
            ResultSet info = ko.executeQuery();
            data = FXCollections.observableArrayList();
            Part_ID.setCellValueFactory(new PropertyValueFactory("PartsID"));

            Booking_ID.setCellValueFactory(new PropertyValueFactory("BookingID"));

            Reg_no.setCellValueFactory(new PropertyValueFactory("RegistrationNo"));

            while (info.next()) {
                System.out.println(info.getString(5));
                data.add(new Customers_Parts_Edit(info.getString(1), info.getInt(2), info.getInt(3)));

            }

            Customers_Parts_Editt.setItems(null);
            Customers_Parts_Editt.setItems(data);
con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Doesn't work");
        }
   
       
Delete_ID_Part.setText("");
    }

    @FXML
    private void Add_Parts_By_ID(ActionEvent event) {
        
        
          ConnectionToParts conn = new ConnectionToParts();
         Connection con = conn.connect();
        Parts p=null;
       
        String sql = "INSERT INTO PartsUsed (RegistrationNumber, PartsID, BookingID) VALUES(?,?,?)";
        try{
        PreparedStatement stat = con.prepareStatement(sql);
         stat.setString(1,txt_RN_display.getText());
         stat.setInt(2,Integer.parseInt(Add_ID_Part.getText()));
         stat.setInt(3,Booking);
     stat.execute();
     
           System.out.println("1");
            String h = "SELECT * FROM PartsUsed WHERE RegistrationNumber = ?";
            System.out.println("2");
            PreparedStatement ko = con.prepareStatement(h);
            System.out.println("3");
            ko.setString(1, txt_RN_display.getText());
            System.out.println("4");
            ResultSet info = ko.executeQuery();
            data = FXCollections.observableArrayList();
            Part_ID.setCellValueFactory(new PropertyValueFactory("PartsID"));

            Booking_ID.setCellValueFactory(new PropertyValueFactory("BookingID"));

            Reg_no.setCellValueFactory(new PropertyValueFactory("RegistrationNo"));

            while (info.next()) {
                System.out.println(info.getString(5));
                data.add(new Customers_Parts_Edit(info.getString(1), info.getInt(2), info.getInt(3)));

            }

            Customers_Parts_Editt.setItems(null);
            Customers_Parts_Editt.setItems(data);
 
       
Delete_ID_Part.setText("");
        
          con.close();     
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        
        
    }

    @FXML
    private void Clear(ActionEvent event) {
        Search_ID_txt.clear();
        Delete_ID_Part.clear();
        Add_ID_Part.clear();
        txt_First_search.clear();
        txt_Surename_search.clear();
        txt_Search_By_ID.clear();
        txt_ID_Name.setText("");
        txt_ID_Cost.setText("");
        txt_model_display.setText("");
        txt_colour_display.setText("");
        txt_Surname_display.setText("");
        txt_firstname_display.setText("");
        txt_Make_display.setText("");
        txt_RN_display.setText("");
 Customers_Parts_Editt.setItems(null);
    }

    @FXML
    private void Search_ID_Parts(ActionEvent event) {
        Parts p = new Parts(Integer.parseInt(Search_ID_txt.getText()));
        Parts newP = p.SeachByID();
     
        txt_ID_Name.setText(newP.name());txt_ID_Name.setStyle("-fx-background-color:White");   
       
        txt_ID_Cost.setText("£"+newP.Cost());txt_ID_Cost.setStyle("-fx-background-color:White");
    }

    private void ID_Search() {
        txt_Search_By_ID.getText();
        ResultSet in = null;
        ResultSet info = null;
        ResultSet cu = null;
        ConnectionToParts conn = new ConnectionToParts();
        String sqlRegno = "SELECT * FROM Vehicles WHERE CustomersID = ? ";
        String sql = "SELECT * FROM PartsUsed WHERE RegistrationNumber = ? ";
        String cust = "SELECT * FROM Customer_Accounts WHERE ID = ?";
        Connection con = conn.connect();
        try {
            PreparedStatement s = con.prepareStatement(sqlRegno);

            s.setInt(1, Integer.parseInt(txt_Search_By_ID.getText()));

            in = s.executeQuery();

            PreparedStatement stat = con.prepareStatement(sql);

            stat.setString(1, in.getString(9));

            info = stat.executeQuery();
Booking = info.getInt(3);
            PreparedStatement a = con.prepareStatement(cust);

            a.setInt(1, Integer.parseInt((txt_Search_By_ID.getText())));

            cu = a.executeQuery();

            txt_firstname_display.setText(cu.getString(2));
            txt_firstname_display.setStyle("-fx-background-color:White");
            txt_Surname_display.setText(cu.getString(3));
            txt_Surname_display.setStyle("-fx-background-color:White");
            txt_RN_display.setText(in.getString(9));
            txt_RN_display.setStyle("-fx-background-color:White");
            txt_Make_display.setText(in.getString(2));
            txt_Make_display.setStyle("-fx-background-color:White");
            txt_model_display.setText(in.getString(3));
            txt_model_display.setStyle("-fx-background-color:White");
            txt_colour_display.setText(in.getString(8));
            txt_colour_display.setStyle("-fx-background-color:White");

            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            data = FXCollections.observableArrayList();
            Part_ID.setCellValueFactory(new PropertyValueFactory("PartsID"));

            Booking_ID.setCellValueFactory(new PropertyValueFactory("BookingID"));

            Reg_no.setCellValueFactory(new PropertyValueFactory("RegistrationNo"));

            while (info.next()) {
                System.out.println(info.getString(5));
                data.add(new Customers_Parts_Edit(info.getString(1), info.getInt(2), info.getInt(3)));

            }

            Customers_Parts_Editt.setItems(null);
            Customers_Parts_Editt.setItems(data);
con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Doesn't work");
        }
   
    }

    private void name_search() {

    }

}
