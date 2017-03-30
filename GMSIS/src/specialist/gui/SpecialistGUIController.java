/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import com.jfoenix.controls.JFXRadioButton;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javax.swing.JOptionPane;
import specialist.logic.SpcBookings;
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
    private JFXRadioButton byID;
    @FXML
    private JFXRadioButton byNAME;
    
    //view all the lists of spc - triggered by an actionlistener / button
    @FXML
    private void showData(ActionEvent event) throws IOException
    {
        //used to make sure the event is handled
        showData2();
    }

    //this function gets the data from the database and displays it to the user as a table
    public void showData2()
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
                allSPC.add(new theSPC(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }catch (SQLException ex)
        {
           Logger.getLogger(SpecialistGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableSpcId.setCellValueFactory(new PropertyValueFactory("SPCid"));
        tableSpcName.setCellValueFactory(new PropertyValueFactory("SPCname"));
        tableSpcAddress.setCellValueFactory(new PropertyValueFactory("SPCaddress"));
        tableSpcPhone.setCellValueFactory(new PropertyValueFactory("SPCphone"));
        tableSpcEmail.setCellValueFactory(new PropertyValueFactory("SPCemail"));
       
        dataTable.setItems(allSPC);
    }
    
    //used to delete an item / row from the table
    public void deleteSPCButton(ActionEvent event) throws IOException
    {
        //ObservableList<theSPC> spcSelected, allSPC;
        //allSPC = dataTable.getItems();
        //spcSelected = dataTable.getSelectionModel().getSelectedItems();
        theSPC spc = dataTable.getSelectionModel().getSelectedItem();
        if(spc == null)
        {
            JOptionPane.showMessageDialog(null,"Please select a SPC");
        }
        else
        {
            //int row = dataTable.getSelectionModel().getSelectedIndex();
            int row = spc.getSPCid();
            String name = spc.getSPCname();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Please Confirm");
            alert.setContentText("Are you sure you want to delete the SPC?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // if the user chooses to proceed
                SpecialistDB a = new SpecialistDB();
                a.deleteSPC(row);
                a.deleteSPCBooking2(name);
                
            } 
            else { /* the user closes the confirmation dialog*/}
            showData2();
        }
    }

    //clears all the textboxes / data that was selected
    @FXML
    private void clearSearchAddEdit(ActionEvent event) throws IOException
    {
        spcId.clear();
        spcName.clear();
        byID.setSelected(false);
        byNAME.setSelected(false);
        showData2();
    }
    
    //switch to the homepage
    @FXML
    private void homePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the customer page
    @FXML
    private void customerPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/customer/gui/CustomerRealPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    // switch to the vehicle page
    @FXML
    private void vehiclePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the bookings page
    @FXML
    private void bookingsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/diagrep/gui/BookingDetails.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the parts page
    @FXML
    private void partsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/parts/gui/PartsPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the specialists page
    @FXML
    private void specialistsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("spcMainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to the spc add page where spc can be added to the list as long as there are less than 10 spc currently linked to the garage
    @FXML
    private void spcAddPage(ActionEvent event) throws IOException
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
                allSPC.add(new theSPC(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }catch (SQLException ex)
        {
           Logger.getLogger(SpecialistGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(allSPC.size() >= 10)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Already 10 SPCs are linked to the garage");
            alert.setContentText("Please delete one or more SPC before adding another");
            alert.showAndWait();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addSPC.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add SPC");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            showData2();
        } 
    }
    
    // linked to the edit page where details of the spc can be changed
    @FXML
    private void spcEditPage(ActionEvent event) throws IOException
    {
        theSPC spc = dataTable.getSelectionModel().getSelectedItem();
        if(spc == null)
        {
            JOptionPane.showMessageDialog(null,"Please select a SPC");
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editSPC.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            EditSPCController Econtroller = fxmlLoader.<EditSPCController>getController();
            Econtroller.setAllFields(spc);
            Stage stage = new Stage();
            stage.setTitle("Edit SPC");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            showData2();
        } 
    }
    
    //if search by id checkbox is checked - unselect/disable the search by name
    @FXML
    private void searchByID(ActionEvent event)
    {
        if(byID.isSelected())
        {
            byNAME.setSelected(false);
        }
    }
    
    //if search by name checkbox is checked - unselect/disable the search by id
    @FXML
    private void searchByName(ActionEvent event)
    {
        if(byNAME.isSelected())
        {
            byID.setSelected(false);
        }
    }
    
    //find the spc which fits the requirement inserted in the search box
    @FXML
    private void spcSearch(ActionEvent event)
    {
        Connection connect = null;
        Statement stmt = null;
        
        if(byID.isSelected())
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPC = FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPC WHERE SPCId = " + spcId.getText());
                while(set.next()){
                    allSPC.add(new theSPC(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch (SQLException ex) 
            {
                Logger.getLogger(SpecialistGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(byNAME.isSelected())
        {
            try
            {   
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                allSPC = FXCollections.observableArrayList();
                ResultSet set = stmt.executeQuery("SELECT * FROM SPC WHERE SPCname like '%" + spcName.getText() + "%'");
                while(set.next()){
                    allSPC.add(new theSPC(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5))); 
                }
                stmt.close();
                set.close();
                connect.close();
            }catch (SQLException ex) 
            {
                Logger.getLogger(SpecialistGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please select your method of searching - by id or name");
            alert.showAndWait();
        }
        
        tableSpcId.setCellValueFactory(new PropertyValueFactory("SPCid"));
        tableSpcName.setCellValueFactory(new PropertyValueFactory("SPCname"));
        tableSpcAddress.setCellValueFactory(new PropertyValueFactory("SPCaddress"));
        tableSpcPhone.setCellValueFactory(new PropertyValueFactory("SPCphone"));
        tableSpcEmail.setCellValueFactory(new PropertyValueFactory("SPCemail"));   
        dataTable.setItems(allSPC);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // makes sure the table already shows the list of SPCs
        showData2();
    }    
    
}
