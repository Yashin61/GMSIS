/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import common.CommonDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import specialist.logic.SpecialistDB;
import specialist.logic.theSPC;

/**
 * FXML Controller class
 *
 * @author prashant
 */
public class SpcMainPageController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField custName;

    @FXML
    private TextField vechRegistration;

    @FXML
    private TableView<theSPC> dataTable;

    @FXML
    private TableColumn<theSPC, String> tableSpcName;

    @FXML
    private TableColumn<theSPC, String> tableCustomerName;

    @FXML
    private TableColumn<theSPC, String> tableRegistrationNo;

    @FXML
    private TableColumn<theSPC, String> tableWorkOn;

    @FXML
    private Label theSpcName;

    @FXML
    private Label theVehicleName;

    @FXML
    private Label theRegistrationNo;

    @FXML
    private Label theCustomerId;

    @FXML
    private Label theCustomerName;

    @FXML
    private Label theCustomerType;

    @FXML
    private Label theCustomerPhone;

    @FXML
    private Label theCustomerEmail;

    @FXML
    private Label theCustomerAddress;

    @FXML
    private Label theCustomerPostcode;

    @FXML
    private ListView<Label> spcList;

    @FXML
    void clearSearchAddEdit(ActionEvent event) {

    }

    @FXML
    private void home(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void spcAdmin(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("specialistGUI.fxml" ));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void setDetails(theSPC spc){
        if(spc != null)
        {
            theSpcName.setText(spc.getSPCname());
            Connection connect = null;
            Statement stmt = null;
            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                ResultSet set = stmt.executeQuery( "SELECT SPCBooking.RegistrationNumber, Vehicles.RegistrationNumber, Vehicles.Model, Vehicles.VehicleType "
                        + "FROM SPCBooking INNER JOIN Vehicles ON SPCBooking.RegistrationNumber = Vehicles.RegistrationNumber WHERE SPCBooking.ID = '" + spc.getSPCid()+ "' ");
                while(set.next())
                {
                    theVehicleName.setText(set.getString("Model") + set.getString("VehicleType"));
                    theRegistrationNo.setText(set.getString("RegistrationNumber"));
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
            
            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                ResultSet set = stmt.executeQuery( "SELECT SPCBooking.CustomerID, Customer_Accounts.ID, Customer_Accounts.Firstname, Customer_Accounts.Surname, Customer_Accounts.Address,"
                        + " Customer_Accounts.Postcode, Customer_Accounts.Phone, Customer_Accounts.Email, Customer_Accounts.Account "
                        + "FROM SPCBooking INNER JOIN Customer_Accounts ON SPCBooking.CustomerID = Customer_Accounts.ID WHERE SPCBooking.ID = '" + spc.getSPCid()+ "' ");
                while(set.next())
                {
                    theVehicleName.setText(set.getString("Model") + set.getString("VehicleType"));
                    theRegistrationNo.setText(set.getString("RegistrationNumber"));
                    theCustomerId.setText(set.getString("ID"));
                    theCustomerName.setText(set.getString("Firstname")+" "+set.getString("Surname"));
                    theCustomerType.setText(set.getString("Account"));
                    theCustomerPhone.setText(set.getString("Phone"));
                    theCustomerEmail.setText(set.getString("Email"));
                    theCustomerAddress.setText(set.getString("Address"));
                    theCustomerPostcode.setText(set.getString("Postcode"));
                }
                stmt.close();
                set.close();
                connect.close();
            }catch(SQLException e)
            {
                Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            theSpcName.setText("");
            theVehicleName.setText("");
            theRegistrationNo.setText("");
            theCustomerId.setText("");
            theCustomerName.setText("");
            theCustomerType.setText("");
            theCustomerPhone.setText("");
            theCustomerEmail.setText("");
            theCustomerAddress.setText("");
            theCustomerPostcode.setText("");
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SpecialistDB a = new SpecialistDB();
        String [] listOfSPC = a.getSPC();
        for(int i=0; i<10; i++)
        {
            Label lbl = new Label(listOfSPC[i]);
            spcList.getItems().add(lbl);
        }
    }    
    
}
