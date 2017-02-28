// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import vehicles.*;
import common.CommonDatabase;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VehiclePageController
{
    Pane pane;
    @FXML
    private TableView<Vehicle> dataTable;
    @FXML
    private TableColumn<Vehicle, String> vehicleType;
    @FXML
    private TableColumn<Vehicle, String> make;
    @FXML
    private TableColumn<Vehicle, Integer> model;
    @FXML
    private TableColumn<Vehicle, Integer> year;
    @FXML
    private TableColumn<Vehicle, String> engineSize;
    @FXML
    private TableColumn<Vehicle, String> fuelType;
    @FXML
    private TableColumn<Vehicle, Integer> milage;
    @FXML
    private TableColumn<Vehicle, String> colour;
    @FXML
    private TableColumn<Vehicle, String> registrationNumber;
    @FXML
    private TableColumn<Vehicle, Integer> customerID;
    @FXML
    private TableColumn<Vehicle, Integer> warrantyID;
    @FXML
    private TableColumn<Vehicle, String> MOTRenewalDate;
    @FXML
    private TableColumn<Vehicle, String> lastServiceDate;
    @FXML
    private TableColumn<Vehicle, String> deliveryDate;
    @FXML
    private TableColumn<Vehicle, String> returnDate;
    @FXML
    private TextField regNumber;
    private ObservableList<Vehicle> data;
    @FXML
    private CheckBox actionCar;
    @FXML
    private CheckBox actionVan;
    @FXML
    private CheckBox actionTruck;
    @FXML
    private Button clearButton;
    @FXML
    /*private ChoiceBox<String> VehicleType;
    
    //@Override
    public void initialize()
    {
        VehicleType.getItems().add("Car");
        VehicleType.getItems().add("Van");
        VehicleType.getItems().add("Truck");
        VehicleType.setValue("Car");
     
    }
    @FXML */
    public void handleButton() throws IOException
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
         System.out.println("Works");
        
        try
        {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Vehicles");
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
        
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
       make.setCellValueFactory(new PropertyValueFactory("Make"));
        model.setCellValueFactory(new PropertyValueFactory("Model"));
        year.setCellValueFactory(new PropertyValueFactory("Year"));
        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory("DeliveryDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory("ReturnDate"));
        dataTable.setItems(null);
        dataTable.setItems(data);
        
        
        actionCar.setSelected(false);
        actionVan.setSelected(false);
        actionTruck.setSelected(false);
        
    }
    
    @FXML
    private void clearDetails(ActionEvent event) {
        
        if(actionCar.isSelected()|| actionVan.isSelected()|| actionTruck.isSelected())
        {
             
        }
        else{
            dataTable.setItems(null);
            regNumber.setText("");
        }
       
              
         
        
    }
    @FXML
    private void openEditPage(ActionEvent event) {
    }
    @FXML
    private void openAddPage(ActionEvent event) {
    }
    @FXML
    private void deleteVehicle(ActionEvent event) {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        System.out.println("Works");
        
        try
        {
            String sql = "SELECT * FROM Vehicles";
            System.out.println("1");
            connection = db.getConnection();
            System.out.println("2");
            data = FXCollections.observableArrayList();
            System.out.println("3");
            Statement st = connection.createStatement();
            System.out.println("4");
            ResultSet rs = st.executeQuery(sql);
            System.out.println("5");
            System.out.println(rs.getString(1));
            while(rs.next())
            {
                System.out.println(rs.getString(1));
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
        
        vehicleType.setCellValueFactory(new PropertyValueFactory("VehicleType"));
        make.setCellValueFactory(new PropertyValueFactory("Make"));
        model.setCellValueFactory(new PropertyValueFactory("Model"));
        year.setCellValueFactory(new PropertyValueFactory("Year"));
        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory("DeliveryDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory("ReturnDate"));
        dataTable.setItems(null);
        dataTable.setItems(data);
    }

    @FXML
   
    private void Car(ActionEvent event) {
              CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
         System.out.println("Works");
        String choice="";
         if(actionCar.isSelected()){
             choice="Car";
             actionVan.setSelected(false);
             actionTruck.setSelected(false);
         }
      
    
         try{
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
         
        
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
       make.setCellValueFactory(new PropertyValueFactory("Make"));
        model.setCellValueFactory(new PropertyValueFactory("Model"));
        year.setCellValueFactory(new PropertyValueFactory("Year"));
        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory("DeliveryDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory("ReturnDate"));
        dataTable.setItems(null);
        dataTable.setItems(data);
        
        
        
    }

    @FXML
    private void Van(ActionEvent event) {
                   CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
         System.out.println("Works");
        String choice="";
       
        if(actionVan.isSelected()){
             choice="Van";
             actionCar.setSelected(false);
             actionTruck.setSelected(false);
         }
    
         try{
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
         
        
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
       make.setCellValueFactory(new PropertyValueFactory("Make"));
        model.setCellValueFactory(new PropertyValueFactory("Model"));
        year.setCellValueFactory(new PropertyValueFactory("Year"));
        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory("DeliveryDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory("ReturnDate"));
        dataTable.setItems(null);
        dataTable.setItems(data);
        
        
    }

    @FXML
    private void Truck(ActionEvent event) {
                   CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
         System.out.println("Works");
        String choice="";
       
      if(actionTruck.isSelected()){
             choice="Truck";
             actionCar.setSelected(false);
             actionVan.setSelected(false);
         }
    
         try{
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
         
        
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
       make.setCellValueFactory(new PropertyValueFactory("Make"));
        model.setCellValueFactory(new PropertyValueFactory("Model"));
        year.setCellValueFactory(new PropertyValueFactory("Year"));
        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory("DeliveryDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory("ReturnDate"));
        dataTable.setItems(null);
        dataTable.setItems(data);
        
        
    }

    @FXML
    private void searchVehRegNum(ActionEvent event) {
          CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
         System.out.println("Works");
       
         try{
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE RegistrationNumber=?");
            s.setString(1,regNumber.getText());
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
         
        
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
       make.setCellValueFactory(new PropertyValueFactory("Make"));
        model.setCellValueFactory(new PropertyValueFactory("Model"));
        year.setCellValueFactory(new PropertyValueFactory("Year"));
        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory("DeliveryDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory("ReturnDate"));
        dataTable.setItems(null);
        dataTable.setItems(data);
    }
 
        
    
}