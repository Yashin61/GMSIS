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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<?> dataTable;
    @FXML
    private TableColumn<?, ?> tableSpcId;
    @FXML
    private TableColumn<?, ?> tableSpcName;
    @FXML
    private TableColumn<?, ?> tableSpcAddress;
    @FXML
    private TableColumn<?, ?> tableSpcPhone;
    @FXML
    private TableColumn<?, ?> tableSpcEmail;


    @FXML
    private void clearSearchAddEdit(ActionEvent event)
    {
        spcId.clear();
        spcName.clear();
        spcAddress.clear();
        spcPhone.clear();
        spcEmail.clear();
    }
    
    @FXML
    private void specificSpcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("spcRepairer.fxml"));
        rootPane.getChildren().setAll(pane);
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
    private void addSpcButton(ActionEvent event) throws IOException
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
    private void updateSpcButton(ActionEvent event) throws IOException
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
            System.out.println("It works");
            for(int i=1; i<5; i++)
            {
                SpecialistDB a= new SpecialistDB();
                a.editSPC(id,data[i],i);
            }            
        }
        else
        {
            System.out.println("Please input all the details.");
        }
    }
    
    @FXML
    private void deleteSPCButton(ActionEvent event) throws IOException
    {
        //get the selection. from there the name and id
        //then delete it using the method in specialistdb
    }
    
    @FXML
    private void showData(ActionEvent event) throws IOException
    {
        
        tableSpcId.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSpcName.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableSpcAddress.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dataTable = new TableView<>();
        //dataTable.setItems(getproduct());
        //dataTable.getColumns().addAll(tableSpcId,tableSpcName,tableSpcAddress);
    }
    
    public ObservableList<theSPC> getproduct()
    {
        ObservableList<theSPC> products = FXCollections.observableArrayList();
        SpecialistDB data = new SpecialistDB();
        data.getSPC();
        //products.add(new theSPC());
        return products;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
