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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

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
    private TableColumn<Vehicle, String> model;
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
    private CheckBox actionWarranty;
    @FXML
    private Button clearButton;
    
    @FXML
    public void handleButton() throws IOException
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        System.out.println("Works1!");
        
        try
        {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Vehicles");
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work1!");
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
    private void openEditPage(ActionEvent event)
    {}
    
    @FXML
    private void openAddPage(ActionEvent event)
    {}
    
    @FXML
    private void deleteVehicle(ActionEvent event)
    {
        Vehicle veh = dataTable.getSelectionModel().getSelectedItem();
        /*if(veh == null)
        {
            noChosen();
        }*/
        //else
        //{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to continue deleting the selected vehicle?");
            ButtonType yes = new ButtonType("YES");
            ButtonType no = new ButtonType("NO");
            alert.getButtonTypes().setAll(yes, no);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == yes)
            {
                String vehType = veh.getVehicleType();
                String sql = "DELETE FROM Vehicles WHERE VehicleType = ?";
                CommonDatabase db = new CommonDatabase();
                Connection connection = null;

                try
                {
                    connection = db.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, vehType);
                    statement.executeUpdate();
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
                //close(connection);
                     
            }
            else
            {
                //alert.close();
            }
        //}
        //display();
    }

        /*CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        System.out.println("Works4!");
        
        try
        {
            String sql = "SELECT * FROM Vehicles";
            //System.out.println("1");
            connection = db.getConnection();
            //System.out.println("2");
            data = FXCollections.observableArrayList();
            //System.out.println("3");
            Statement st = connection.createStatement();
            //System.out.println("4");
            ResultSet rs = st.executeQuery(sql);
            //System.out.println("5");
            //System.out.println(rs.getString(1));
            while(rs.next())
            {
                //System.out.println(rs.getString(1));
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work4!");
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

*/

    @FXML
    private void car(ActionEvent event)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        System.out.println("Works5!");
        String choice="";
        if(actionCar.isSelected())
        {
            choice="Car";
            actionVan.setSelected(false);
            actionTruck.setSelected(false);
        }
        
        try
        {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work5!");
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
    private void van(ActionEvent event)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        System.out.println("Works6!");
        String choice="";
        if(actionVan.isSelected())
        {
            choice="Van";
            actionCar.setSelected(false);
            actionTruck.setSelected(false);
        }
        
        try
        {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        
        catch(SQLException e)
        {
            System.out.println("Doesn't work6!");
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
    private void truck(ActionEvent event)
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        System.out.println("Works7!");
        String choice="";
        if(actionTruck.isSelected())
        {
            choice="Truck";
            actionCar.setSelected(false);
            actionVan.setSelected(false);
         }
    
         try
         {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work7!");
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
    private void warranty(ActionEvent event) {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        System.out.println("Works8!");
//        String choice="";
        if(actionTruck.isSelected())
        {
//            choice="Truck";
//            actionCar.setSelected(false);
//            actionVan.setSelected(false);
         }
    
         try
         {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            PreparedStatement s=connection.prepareStatement("SELECT * FROM Vehicles WHERE WarrantyID>1");
//            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work8!");
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
                   // connection = db.getConnection();
        System.out.println("Works9!");
       
        try
        {
            data = FXCollections.observableArrayList();
            Connection connection = db.getConnection();
            String sql = "SELECT * FROM Vehicles WHERE RegistrationNumber LIKE '" + regNumber.getText() + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getString(1));
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
            }
        }
        catch(SQLException e)
        {
            //JOptionPane.showMessageDialog(null, "Not a valid Registration Number format!");
                //return;
            //e.printStackTrace();
            System.out.println("Doesn't work9!");
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
    private void clearDetails(ActionEvent event)
    {
        if(actionCar.isSelected() || actionVan.isSelected() || actionTruck.isSelected())
        {}
        else
        {
            dataTable.setItems(null);
        }
        regNumber.setText("");
    }
}