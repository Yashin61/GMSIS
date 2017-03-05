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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

public class VehiclePageController
{
//    Pane pane;
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
    
    CommonDatabase db;
    Connection con;
    boolean flag=false;
    boolean flag2=true;
    
    @FXML
    private TextField address11;
    @FXML
    private TextField address111;
    @FXML
    private TextField address112;
    
    @FXML
    public void handleButton() throws IOException
    {
        db = new CommonDatabase();
        con = db.getConnection();
        System.out.println("Works1!");
        
        try
        {
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Vehicles");
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13)));
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
        dataTable.setItems(null);
        dataTable.setItems(data);
        actionCar.setSelected(false);
        actionVan.setSelected(false);
        actionTruck.setSelected(false);
    }
    
    @FXML
    private void handleButton2(ActionEvent event) {
        db = new CommonDatabase();
        con=db.getConnection();
        System.out.println("Works10!");
        Vehicle veh=dataTable.getSelectionModel().getSelectedItem();
        String sql="SELECT * FROM Warranty WHERE WarrantyID=?";
        
        try
        {
            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setString(1, veh.getRegistrationNumber());
//            Result set=stmt.executeQuery();
            
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "The selected vehicle does not have a warranty!");
            System.out.println("Doesn't work10!");
        }
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
        db = new CommonDatabase();
        Vehicle veh=dataTable.getSelectionModel().getSelectedItem();
        if(veh==null)
        {
            noChosen();
        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ATTENTION");
            alert.setContentText("Do you want to continue deleting the selected vehicle?");
            ButtonType yes=new ButtonType("YES");
            ButtonType no=new ButtonType("NO");
            alert.getButtonTypes().setAll(yes, no);
            Optional<ButtonType> result=alert.showAndWait();
            if(result.get()==yes)
            {
                String sql="DELETE FROM Vehicles WHERE RegistrationNumber=?";
                con=null;

                try
                {
                    con=db.getConnection();
                    PreparedStatement stmt=con.prepareStatement(sql);
                    stmt.setString(1, veh.getRegistrationNumber());
                    stmt.execute();
                    JOptionPane.showMessageDialog(null, "The entery is now deleted!");
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                    System.out.println("Doesn't work4!");
                }
                update();
            }
        }
    }
    
    private void noChosen()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("ATTENTION");
        alert.setContentText("First select a vehicle from the table!");
        alert.showAndWait();
    }
    
    public void update()
    {
        db = new CommonDatabase();
        con = db.getConnection();
        System.out.println("Works11!");
        
        try
        {
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Vehicles");
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work11!");
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
        dataTable.setItems(null);
        dataTable.setItems(data);
        actionCar.setSelected(false);
        actionVan.setSelected(false);
        actionTruck.setSelected(false);
    }

    @FXML
    private void car(ActionEvent event)
    {
        db = new CommonDatabase();
        con = db.getConnection();
        System.out.println("Works5!");
        String choice="";
        if(actionCar.isSelected())
        {
            choice="Car";
            actionVan.setSelected(false);
            actionTruck.setSelected(false);
            actionWarranty.setSelected(false);
        }
        
        try
        {
            data = FXCollections.observableArrayList();
            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13)));
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
        dataTable.setItems(null);
        dataTable.setItems(data);
        flag2=false;
    }

    @FXML
    private void van(ActionEvent event)
    {
        db = new CommonDatabase();
        con = db.getConnection();
        System.out.println("Works6!");
        String choice="";
        if(actionVan.isSelected())
        {
            choice="Van";
            actionCar.setSelected(false);
            actionTruck.setSelected(false);
            actionWarranty.setSelected(false);
        }
        
        try
        {
            data = FXCollections.observableArrayList();
            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13)));
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
        dataTable.setItems(null);
        dataTable.setItems(data);
        flag2=false;
    }

    @FXML
    private void truck(ActionEvent event)
    {
        db = new CommonDatabase();
        Connection con = db.getConnection();
        System.out.println("Works7!");
        String choice="";
        if(actionTruck.isSelected())
        {
            choice="Truck";
            actionCar.setSelected(false);
            actionVan.setSelected(false);
            actionWarranty.setSelected(false);
         }
    
         try
         {
            data = FXCollections.observableArrayList();
            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            s.setString(1,choice);
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13)));
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
        dataTable.setItems(null);
        dataTable.setItems(data);
        flag2=false;
    }

    @FXML
    private void warranty(ActionEvent event) {
        db = new CommonDatabase();
        con = db.getConnection();
        System.out.println("Works8!");
        if(actionWarranty.isSelected())
        {
            actionCar.setSelected(false);
            actionVan.setSelected(false);
            actionTruck.setSelected(false);
         }
    
         try
         {
            data = FXCollections.observableArrayList();
            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE WarrantyID>1");
            ResultSet rs = s.executeQuery();
            while(rs.next())
            {
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13)));
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
        dataTable.setItems(null);
        dataTable.setItems(data);
        flag2=false;
    }
    
    @FXML
    private void searchVehRegNum(ActionEvent event) {
        db = new CommonDatabase();
        con = db.getConnection();
        System.out.println("Works9!");
       
        if(!regNumber.getText().equals(""))
        {
            try
            {
                data = FXCollections.observableArrayList();
                String sql = "SELECT * FROM Vehicles WHERE RegistrationNumber LIKE '" + regNumber.getText() + "%'";
                PreparedStatement statement = con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while(rs.next())
                {
                    System.out.println(rs.getString(1));
                    data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                            rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                            rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                            rs.getString(12), rs.getString(13)));
                }
//                if(data==null)
//                {
//                    JOptionPane.showMessageDialog(null, "No data were found!");
//                }
            }
            catch(SQLException e)
            {
//                JOptionPane.showMessageDialog(null, "No data were found!");
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
            dataTable.setItems(null);
            dataTable.setItems(data);
            flag=true;
            flag2=true;
            actionCar.setSelected(false);
            actionVan.setSelected(false);
            actionTruck.setSelected(false);
            actionWarranty.setSelected(false);
        }
        else
        {
//            JOptionPane.showMessageDialog(null, "Type the Registration Number in the Given Format!");
            JOptionPane.showMessageDialog(null, "You have not typed anything to search yet!");
        }
    }

    @FXML
    private void clearDetails(ActionEvent event)
    {
        regNumber.setText("");
        if(flag && flag2)
        {
            dataTable.setItems(null);
            flag=false;
        }
    }
}
//



//// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami
//
//package vehicles.gui;
//
//import vehicles.*;
//import common.CommonDatabase;
//import vehicles.gui.VehicleEditController;
//import java.io.IOException;
//import java.net.URL;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Pane;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Optional;
//import java.util.ResourceBundle;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//import javax.swing.JOptionPane;
//
//public class VehiclePageController
//{
////    Pane pane;
//    @FXML
//    private TableView<Vehicle> dataTable;
//    @FXML
//    private TableColumn<Vehicle, String> vehicleType;
//    @FXML
//    private TableColumn<Vehicle, String> make;
//    @FXML
//    private TableColumn<Vehicle, String> model;
//    @FXML
//    private TableColumn<Vehicle, Integer> year;
//    @FXML
//    private TableColumn<Vehicle, String> engineSize;
//    @FXML
//    private TableColumn<Vehicle, String> fuelType;
//    @FXML
//    private TableColumn<Vehicle, Integer> milage;
//    @FXML
//    private TableColumn<Vehicle, String> colour;
//    @FXML
//    private TableColumn<Vehicle, String> registrationNumber;
//    @FXML
//    private TableColumn<Vehicle, Integer> customerID;
//    @FXML
//    private TableColumn<Vehicle, Integer> warrantyID;
//    @FXML
//    private TableColumn<Vehicle, String> MOTRenewalDate;
//    @FXML
//    private TableColumn<Vehicle, String> lastServiceDate;
//    @FXML
//    private TextField regNumber;
//    private ObservableList<Vehicle> data;
//    @FXML
//    private CheckBox actionCar;
//    @FXML
//    private CheckBox actionVan;
//    @FXML
//    private CheckBox actionTruck;
//    @FXML
//    private CheckBox actionWarranty;
//    @FXML
//    private Button clearButton;
//    
//    CommonDatabase db;
//    Connection con;
//    boolean flag=false;
//    @FXML
//    private GridPane gridPaneWarrantyInfo;
//    @FXML
//    private Label lblExpirationDate;
//    @FXML
//    private Label lblCompAddress;
//    @FXML
//    private Label lblCompName;
//    @FXML
//    private TextField txtCompName;
//    @FXML
//    private TextField txtCompAddress;
//    @FXML
//    private TextField txtCompName1;
//    
//    @FXML
//    public void handleButton() throws IOException
//    {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works1!");
//        
//        try
//        {
//            data = FXCollections.observableArrayList();
//            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Vehicles");
//            while(rs.next())
//            {
//                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
//                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
//                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
//                        rs.getString(12), rs.getString(13)));
//            }
//        }
//        catch(SQLException e)
//        {
//            System.out.println("Doesn't work1!");
//        }
//        
//        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
//        make.setCellValueFactory(new PropertyValueFactory("Make"));
//        model.setCellValueFactory(new PropertyValueFactory("Model"));
//        year.setCellValueFactory(new PropertyValueFactory("Year"));
//        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
//        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
//        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
//        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
//        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
//        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
//        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
//        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
//        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
//        dataTable.setItems(null);
//        dataTable.setItems(data);
//        actionCar.setSelected(false);
//        actionVan.setSelected(false);
//        actionTruck.setSelected(false);
//    }
//    
//    @FXML
//    private void handleButton2(ActionEvent event) {
//        db = new CommonDatabase();
//        con=db.getConnection();
//        System.out.println("Works10!");
//        Vehicle veh=dataTable.getSelectionModel().getSelectedItem();
//        String sql="SELECT * FROM Warranty WHERE WarrantyID=?";
//        
//        try
//        {
//            PreparedStatement stmt=con.prepareStatement(sql);
//            stmt.setString(1, veh.getRegistrationNumber());
////            Result set=stmt.executeQuery();
//            
//        }
//        catch(SQLException e)
//        {
//            JOptionPane.showMessageDialog(null, "The selected vehicle does not have a warranty!");
//            System.out.println("Doesn't work10!");
//        }
//    }
//    
//    @FXML
//    private void openEditPage(ActionEvent event) throws IOException
//    {
//        Vehicle veh = dataTable.getSelectionModel().getSelectedItem();
//        if(veh==null)
//        {
//            noChosen();
//        }
//        else
//        {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VehicleEdit.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//            VehicleEditController controller=fxmlLoader.<VehicleEditController>getController();
//            controller.setAllFields(veh);
//            Stage stage = new Stage();
//            stage.setTitle("Edit Vehicle");
//            stage.setScene(new Scene(root1));
//            stage.show();
//            
//        } 
//    }
//    
//    @FXML
//    private void openAddPage(ActionEvent event)
//    {}
//    
//    @FXML
//    private void deleteVehicle(ActionEvent event)
//    {
//        db = new CommonDatabase();
//        Vehicle veh=dataTable.getSelectionModel().getSelectedItem();
//        if(veh==null)
//        {
//            noChosen();
//        }
//        else
//        {
//            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setHeaderText("ATTENTION");
//            alert.setContentText("Do you want to continue deleting the selected vehicle?");
//            ButtonType yes=new ButtonType("YES");
//            ButtonType no=new ButtonType("NO");
//            alert.getButtonTypes().setAll(yes, no);
//            Optional<ButtonType> result=alert.showAndWait();
//            if(result.get()==yes)
//            {
//                String sql="DELETE FROM Vehicles WHERE RegistrationNumber=?";
//                con=null;
//
//                try
//                {
//                    con=db.getConnection();
//                    PreparedStatement stmt=con.prepareStatement(sql);
//                    stmt.setString(1, veh.getRegistrationNumber());
//                    stmt.execute();
//                    JOptionPane.showMessageDialog(null, "The entery is now deleted!");
//                }
//                catch(SQLException e)
//                {
//                    e.printStackTrace();
//                    System.out.println("Doesn't work4!");
//                }
//                update();
//            }
//        }
//    }
//    
//    private void noChosen()
//    {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("");
//        alert.setHeaderText("ATTENTION");
//        alert.setContentText("First select a vehicle from the table!");
//        alert.showAndWait();
//    }
//    
//    public void update()
//    {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works11!");
//        
//        try
//        {
//            data = FXCollections.observableArrayList();
//            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Vehicles");
//            while(rs.next())
//            {
//                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
//                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
//                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
//                        rs.getString(12), rs.getString(13)));
//            }
//        }
//        catch(SQLException e)
//        {
//            System.out.println("Doesn't work11!");
//        }
//        
//        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
//        make.setCellValueFactory(new PropertyValueFactory("Make"));
//        model.setCellValueFactory(new PropertyValueFactory("Model"));
//        year.setCellValueFactory(new PropertyValueFactory("Year"));
//        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
//        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
//        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
//        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
//        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
//        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
//        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
//        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
//        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
//        dataTable.setItems(null);
//        dataTable.setItems(data);
//        actionCar.setSelected(false);
//        actionVan.setSelected(false);
//        actionTruck.setSelected(false);
//    }
//
//    @FXML
//    private void car(ActionEvent event)
//    {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works5!");
//        String choice="";
//        if(actionCar.isSelected())
//        {
//            choice="Car";
//            actionVan.setSelected(false);
//            actionTruck.setSelected(false);
//            actionWarranty.setSelected(false);
//        }
//        
//        try
//        {
//            data = FXCollections.observableArrayList();
//            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
//            s.setString(1,choice);
//            ResultSet rs = s.executeQuery();
//            while(rs.next())
//            {
//                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
//                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
//                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
//                        rs.getString(12), rs.getString(13)));
//            }
//        }
//        catch(SQLException e)
//        {
//            System.out.println("Doesn't work5!");
//        }
//        
//        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
//        make.setCellValueFactory(new PropertyValueFactory("Make"));
//        model.setCellValueFactory(new PropertyValueFactory("Model"));
//        year.setCellValueFactory(new PropertyValueFactory("Year"));
//        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
//        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
//        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
//        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
//        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
//        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
//        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
//        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
//        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
//        dataTable.setItems(null);
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void van(ActionEvent event)
//    {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works6!");
//        String choice="";
//        if(actionVan.isSelected())
//        {
//            choice="Van";
//            actionCar.setSelected(false);
//            actionTruck.setSelected(false);
//            actionWarranty.setSelected(false);
//        }
//        
//        try
//        {
//            data = FXCollections.observableArrayList();
//            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
//            s.setString(1,choice);
//            ResultSet rs = s.executeQuery();
//            while(rs.next())
//            {
//                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
//                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
//                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
//                        rs.getString(12), rs.getString(13)));
//            }
//        }
//        
//        catch(SQLException e)
//        {
//            System.out.println("Doesn't work6!");
//        }
//        
//        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
//        make.setCellValueFactory(new PropertyValueFactory("Make"));
//        model.setCellValueFactory(new PropertyValueFactory("Model"));
//        year.setCellValueFactory(new PropertyValueFactory("Year"));
//        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
//        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
//        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
//        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
//        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
//        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
//        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
//        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
//        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
//        dataTable.setItems(null);
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void truck(ActionEvent event)
//    {
//        db = new CommonDatabase();
//        Connection con = db.getConnection();
//        System.out.println("Works7!");
//        String choice="";
//        if(actionTruck.isSelected())
//        {
//            choice="Truck";
//            actionCar.setSelected(false);
//            actionVan.setSelected(false);
//            actionWarranty.setSelected(false);
//         }
//    
//         try
//         {
//            data = FXCollections.observableArrayList();
//            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
//            s.setString(1,choice);
//            ResultSet rs = s.executeQuery();
//            while(rs.next())
//            {
//                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
//                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
//                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
//                        rs.getString(12), rs.getString(13)));
//            }
//        }
//        catch(SQLException e)
//        {
//            System.out.println("Doesn't work7!");
//        }
//
//        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
//        make.setCellValueFactory(new PropertyValueFactory("Make"));
//        model.setCellValueFactory(new PropertyValueFactory("Model"));
//        year.setCellValueFactory(new PropertyValueFactory("Year"));
//        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
//        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
//        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
//        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
//        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
//        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
//        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
//        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
//        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
//        dataTable.setItems(null);
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void warranty(ActionEvent event) {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works8!");
//        if(actionWarranty.isSelected())
//        {
//            actionCar.setSelected(false);
//            actionVan.setSelected(false);
//            actionTruck.setSelected(false);
//         }
//    
//         try
//         {
//            data = FXCollections.observableArrayList();
//            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE WarrantyID>1");
//            ResultSet rs = s.executeQuery();
//            while(rs.next())
//            {
//                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
//                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
//                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
//                        rs.getString(12), rs.getString(13)));
//            }
//        }
//        catch(SQLException e)
//        {
//            System.out.println("Doesn't work8!");
//        }
//
//        vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
//        make.setCellValueFactory(new PropertyValueFactory("Make"));
//        model.setCellValueFactory(new PropertyValueFactory("Model"));
//        year.setCellValueFactory(new PropertyValueFactory("Year"));
//        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
//        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
//        milage.setCellValueFactory(new PropertyValueFactory("Milage"));
//        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
//        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
//        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
//        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
//        MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
//        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
//        dataTable.setItems(null);
//        dataTable.setItems(data);
//    }
//    
//    @FXML
//    private void searchVehRegNum(ActionEvent event) {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works9!");
//       
//        if(!regNumber.getText().equals(""))
//        {
//            try
//            {
//                data = FXCollections.observableArrayList();
//                String sql = "SELECT * FROM Vehicles WHERE RegistrationNumber LIKE '" + regNumber.getText() + "%'";
//                PreparedStatement statement = con.prepareStatement(sql);
//                ResultSet rs = statement.executeQuery();
//                while(rs.next())
//                {
//                    System.out.println(rs.getString(1));
//                    data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
//                            rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
//                            rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
//                            rs.getString(12), rs.getString(13)));
//                }
////                if(data==null)
////                {
////                    JOptionPane.showMessageDialog(null, "No data were found!");
////                }
//            }
//            catch(SQLException e)
//            {
////                JOptionPane.showMessageDialog(null, "No data were found!");
//                System.out.println("Doesn't work9!");
//            }
//
//            vehicleType.setCellValueFactory(new PropertyValueFactory<>("VehicleType"));
//            make.setCellValueFactory(new PropertyValueFactory("Make"));
//            model.setCellValueFactory(new PropertyValueFactory("Model"));
//            year.setCellValueFactory(new PropertyValueFactory("Year"));
//            engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
//            fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
//            milage.setCellValueFactory(new PropertyValueFactory("Milage"));
//            colour.setCellValueFactory(new PropertyValueFactory("Colour"));
//            registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
//            customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
//            warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
//            MOTRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
//            lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
//            dataTable.setItems(null);
//            dataTable.setItems(data);
//            flag=true;
//            actionCar.setSelected(false);
//            actionVan.setSelected(false);
//            actionTruck.setSelected(false);
//            actionWarranty.setSelected(false);
//        }
//        else
//        {
////            JOptionPane.showMessageDialog(null, "Type the Registration Number in the Given Format!");
//            JOptionPane.showMessageDialog(null, "You have not typed anything to search yet!");
//        }
//    }
//
//    @FXML
//    private void clearDetails(ActionEvent event)
//    {
//        if(flag)
//        {
//            dataTable.setItems(null);
//            flag=false;
//            if(!regNumber.getText().equals(""))
//            {
//                regNumber.setText("");
//            }
//        }
//    }
//}
////