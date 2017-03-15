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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import specialist.logic.SpcBookings;
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
    private TableView<SpcBookings> dataTable;

    @FXML
    private TableColumn<SpcBookings, String> tableSpcName;

    @FXML
    private TableColumn<SpcBookings, String> tableCustomerName;

    @FXML
    private TableColumn<SpcBookings, String> tableRegistrationNo;

    @FXML
    private TableColumn<SpcBookings, String> tableWorkOn;

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
    private ObservableList<SpcBookings> allSPCBooking;

    //adding a comment to commit and push again as github not working properly
    @FXML
    private void Reset(ActionEvent event) {
        custName.clear();
        vechRegistration.clear();
        showData2();
    }
    
    @FXML
    private void showData(ActionEvent event) throws IOException
    {
        //used to make sure the event is handled
        showData2();
    }
    
    public void showData2()
    {
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            allSPCBooking= FXCollections.observableArrayList();
            ResultSet set = stmt.executeQuery("SELECT * FROM SPCBooking");
            while(set.next()){
                allSPCBooking.add(new SpcBookings(set.getInt(1), set.getString(2), set.getString(3),
                        set.getInt(4), set.getString(5), set.getInt(6), set.getInt(7), set.getString(8), set.getInt(9), set.getInt(10), set.getString(11))); 
            }
            stmt.close();
            set.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
        
        tableSpcName.setCellValueFactory(new PropertyValueFactory("SpcBookingname"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory("SpcCustomerName"));
        tableRegistrationNo.setCellValueFactory(new PropertyValueFactory("SPCRNumber"));
        tableWorkOn.setCellValueFactory(new PropertyValueFactory("SpcWorkOn"));
       
        dataTable.setItems(allSPCBooking);
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
    private void setDetails(SpcBookings spc){
        if(spc != null)
        {
            theSpcName.setText(spc.getSpcBookingName());
            Connection connect = null;
            Statement stmt = null;
            try
            {
                connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
                stmt = connect.createStatement();
                ResultSet set = stmt.executeQuery("SELECT SPCBooking.RegistrationNumber, Vehicles.RegistrationNumber, Vehicles.Model, Vehicles.VehicleType "
                        + "FROM SPCBooking INNER JOIN Vehicles ON SPCBooking.RegistrationNumber = Vehicles.RegistrationNumber WHERE SPCBooking.ID = '" + spc.getSpcBookingId()+ "' ");
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
                ResultSet set = stmt.executeQuery("SELECT SPCBooking.CustomerID, Customer_Accounts.ID, Customer_Accounts.Firstname, Customer_Accounts.Surname, Customer_Accounts.Address,"
                        + " Customer_Accounts.Postcode, Customer_Accounts.Phone, Customer_Accounts.Email, Customer_Accounts.Account "
                        + "FROM SPCBooking INNER JOIN Customer_Accounts ON SPCBooking.CustomerID = Customer_Accounts.ID WHERE SPCBooking.ID = '" + spc.getSpcBookingId()+ "' ");
                while(set.next())
                {
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
        showData2();
        setDetails(null);
        dataTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setDetails(newValue));
    }    
}
