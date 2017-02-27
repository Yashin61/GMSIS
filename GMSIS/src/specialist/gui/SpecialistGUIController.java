/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import specialist.logic.SpecialistDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import common.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import specialist.logic.theSPC;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class SpecialistGUIController implements Initializable {

   @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField spcId;
    @FXML
    private TextField spcName;
    @FXML
    private TextField spcPhone;
    @FXML
    private TextField spcAddress;
    @FXML
    private TextField spcEmail;
    @FXML
    private TableView<theSPC> dataTable;
    @FXML
    private TableColumn<theSPC, Integer> tableSpcId;
    @FXML
    private TableColumn<theSPC, String> tableSpcName; 
    @FXML
    private TableColumn<theSPC, String> tableSpcAddress;
    @FXML
    private TableColumn<theSPC, String> tableSpcPhone;
    @FXML
    private TableColumn<theSPC, String> tableSpcEmail;
    @FXML
    private ObservableList<theSPC> allSPC;
    
    
    @FXML
    private void showData(ActionEvent event) throws IOException
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allSPC = FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM SPC");
            while(set.next()){
                System.out.println(set.getInt(1)+ set.getString(2)+ set.getString(3)+ set.getString(4)+ set.getString(5));
                allSPC.add(new theSPC(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
        
        tableSpcId.setCellValueFactory(new PropertyValueFactory("SPCId"));
        tableSpcName.setCellValueFactory(new PropertyValueFactory("SPCname"));
        tableSpcAddress.setCellValueFactory(new PropertyValueFactory("SPCaddress"));
        tableSpcPhone.setCellValueFactory(new PropertyValueFactory("SPCphone"));
        tableSpcEmail.setCellValueFactory(new PropertyValueFactory("SPCemail"));

        dataTable.setItems(allSPC);
    }

    @FXML
    public void deleteSPCButton(ActionEvent event) throws IOException
    {
        //ObservableList<theSPC> spcSelected, allSPC;
        //allSPC = dataTable.getItems();
        //spcSelected = dataTable.getSelectionModel().getSelectedItems();
        int row = dataTable.getSelectionModel().getSelectedIndex();
        SpecialistDB a = new SpecialistDB();
        a.deleteSPC(row+1);
    }
        
    @FXML
    private void clearSearchAddEdit(ActionEvent event) throws IOException
    {
        spcId.clear();
        spcName.clear();
        spcAddress.clear();
        spcPhone.clear();
        spcEmail.clear();
    }
    
    @FXML
    private void spcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("specialistGUI.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void spcAddPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("addSPC.fxml"));
        rootPane.getChildren().setAll(pane);
        /*
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
        */
    }
    
    @FXML
    private void addSpcButton(ActionEvent event) 
    {
        String id = spcId.getText();
        String name = spcName.getText();
        String address = spcAddress.getText();
        String phone = spcPhone.getText();
        String email = spcEmail.getText();
        if(!(id.equals("") || name.equals("") || address.equals("") || phone.equals("") || email.equals("")))
        {
            System.out.println("It works");
            SpecialistDB a= new SpecialistDB();
            a.addSPC(name,address,phone,email);
        }
        else
        {
            System.out.println("Please input all the details.");
        }
    }
    
    @FXML
    private void spcEditPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("editSPC.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void updateSpcButton(ActionEvent event) 
    {
        //get all the texts first
        String id = spcId.getText();
        String name = spcName.getText();
        String address = spcAddress.getText();
        String phone = spcPhone.getText();
        String email = spcEmail.getText();
        String[] data = {id,name,address,phone,email};
        if(!(spcId.getText().equals("") || spcName.getText().equals("") || spcAddress.getText().equals("") || spcPhone.getText().equals("") || spcEmail.getText().equals("")))
        {
            for(int i=1; i<5; i++)
            {
                SpecialistDB a= new SpecialistDB();
                a.editSPC(id,data[i],i);
            }     
            JOptionPane.showMessageDialog(null, "SPC list has been updated");
        }
        else
        {
            System.out.println("Please input all the details.");
        }
    }
    
    //get products
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
