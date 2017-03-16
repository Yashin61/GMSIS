// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import vehicles.*;
import common.CommonDatabase;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class VehiclePageController
{
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TableView<Vehicle> dataTable;
    private TableView<Warranty> dataTable2;
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
    private TableColumn<Vehicle, Integer> mileage;
    @FXML
    private TableColumn<Vehicle, String> colour;
    @FXML
    private TableColumn<Vehicle, String> registrationNumber;
    @FXML
    private TableColumn<Vehicle, Integer> customerID;
    @FXML
    private TableColumn<Vehicle, Integer> warrantyID;
    @FXML
    private TableColumn<Vehicle, String> motRenewalDate;
    @FXML
    private TableColumn<Vehicle, String> lastServiceDate;
    @FXML
    private TextField regNumber;
    @FXML
    private RadioButton warranty;
    @FXML
    private Button addVeh;
    @FXML
    private Button clearButton;
    @FXML
    private TextField expDate;
    @FXML
    private TextField compName;
    @FXML
    private TextField compName1;
    @FXML
    private RadioButton carType;
    @FXML
    private ToggleGroup vehType;
    @FXML
    private RadioButton truckType;
    @FXML
    private RadioButton vanType;
    private TextField compAddress;
    private ObservableList<Vehicle> data;  // Dynamic array of Vehicle
    private ObservableList<Warranty> data2;
    boolean flag=false;
    CommonDatabase db=new CommonDatabase();
    Connection con=db.getConnection();
    RadioButton btnSelected;

    
    // Deselects selected row when clicking on the skin
    public void initialize()
    {
        mainAnchor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                dataTable.getSelectionModel().clearSelection();
            }
        });
    }
    
    private void setTableValue()
    {
        vehicleType.setCellValueFactory(new PropertyValueFactory("VehicleType"));
        make.setCellValueFactory(new PropertyValueFactory("Make"));
        model.setCellValueFactory(new PropertyValueFactory("Model"));
        year.setCellValueFactory(new PropertyValueFactory("Year"));
        engineSize.setCellValueFactory(new PropertyValueFactory("EngineSize"));
        fuelType.setCellValueFactory(new PropertyValueFactory("FuelType"));
        mileage.setCellValueFactory(new PropertyValueFactory("Mileage"));
        colour.setCellValueFactory(new PropertyValueFactory("Colour"));
        registrationNumber.setCellValueFactory(new PropertyValueFactory("RegistrationNumber"));
        customerID.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        warrantyID.setCellValueFactory(new PropertyValueFactory("WarrantyID"));
        motRenewalDate.setCellValueFactory(new PropertyValueFactory("MOTRenewalDate"));
        lastServiceDate.setCellValueFactory(new PropertyValueFactory("LastServiceDate"));
        flag=false;
    }
    
    @FXML
    private void getVehicleDetails() throws IOException
    {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works1!");
        
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
//            System.out.println("Doesn't work1!");
        }
        
        setTableValue();
        dataTable.setItems(data);
        carType.setSelected(false);
        vanType.setSelected(false);
        truckType.setSelected(false);
        warranty.setSelected(false);
    }
    
    @FXML
    private void openEditPage(ActionEvent event) throws IOException
    {
        Vehicle veh = dataTable.getSelectionModel().getSelectedItem();
        Warranty war = dataTable2.getSelectionModel().getSelectedItem();
        if(veh==null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("EditVehicle.fxml"));
            Parent root1=(Parent)fxmlLoader.load();
            EditVehicleController controller=fxmlLoader.<EditVehicleController>getController();
            controller.setAllFields(veh, war);
            Stage stage=new Stage();
            stage.setTitle("Edit Vehicle");
            stage.setScene(new Scene(root1));
            stage.show();
        } 
    }
    
    @FXML
    private void openAddPage(ActionEvent event) throws IOException
    {
        Stage stage;
        Parent root;
        if(event.getSource() == addVeh)
        {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("AddVehicle.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Add Vehicle");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addVeh.getScene().getWindow());
            stage.show();
            dataTable.setItems(data);
        }
    }

    @FXML
    private void deleteVehicle(ActionEvent event)
    {
//        db = new CommonDatabase();
        Vehicle veh=dataTable.getSelectionModel().getSelectedItem();
        if(veh==null)
        {
            noChosen();
        }
        else
        {
//            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setHeaderText("ATTENTION");
//            alert.setContentText("Do you want to continue deleting the selected vehicle?");
//            ButtonType yes=new ButtonType("YES");
//            ButtonType no=new ButtonType("NO");
//            alert.getButtonTypes().setAll(yes, no);
//            Optional<ButtonType> ans=alert.showAndWait();
//            if(ans.get()==yes)
            if(JOptionPane.showConfirmDialog(null, "Do you want to continue deleting the selected vehicle?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
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
//                    System.out.println("Doesn't work4!");
                }
                update();
            }
        }
    }
    
    private void noChosen()
    {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("");
//        alert.setHeaderText("ATTENTION");
//        alert.setContentText("First select a vehicle from the table!");
//        alert.showAndWait();
        JOptionPane.showMessageDialog(null, "First select a vehicle from the table!");
    }
    
    void update()
    {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works11!");
        
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
//            System.out.println("Doesn't work11!");
        }
        
        setTableValue();
        dataTable.setItems(null);
        dataTable.setItems(data);
        carType.setSelected(false);
        vanType.setSelected(false);
        truckType.setSelected(false);
        warranty.setSelected(false);
    }
    
    @FXML
    private void actionVehType(ActionEvent event)
    {
        try
        {
            data = FXCollections.observableArrayList();
            PreparedStatement s=con.prepareStatement("SELECT * FROM Vehicles WHERE VehicleType=?");
            btnSelected = (RadioButton) vehType.getSelectedToggle();   // how can i put this out of this method?
            s.setString(1, btnSelected.getText());
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
//            System.out.println("Doesn't work5!");
        }
        
        setTableValue();
        dataTable.setItems(data);
    }
    
    @FXML
    private void actionWarranty(ActionEvent event)
    {
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
//            System.out.println("Doesn't work8!");
        }

        setTableValue();
        dataTable.setItems(data);
    }
    
    @FXML
    private void searchVehRegNum(ActionEvent event)
    {
//        db = new CommonDatabase();
//        con = db.getConnection();
//        System.out.println("Works9!");
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
                    data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                            rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                            rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                            rs.getString(12), rs.getString(13)));
                }
                if(data.isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "No data were found!");
                }
            }
            catch(SQLException e)
            {
//                JOptionPane.showMessageDialog(null, "No data were found!");
//                System.out.println("Doesn't work9!");
            }
            
            setTableValue();
            if(!data.isEmpty())
            {
                dataTable.setItems(data);
                flag=true;
            }
//            if(clearDetails(event))
//            {
//                flag=false;
//            }
            carType.setSelected(false);
            vanType.setSelected(false);
            truckType.setSelected(false);
            warranty.setSelected(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You have not typed anything to search yet!");
        }
    }

    @FXML
    private void clearDetails(ActionEvent event)
    {
        regNumber.setText("");
        if(flag)
        {
            dataTable.setItems(null);
            flag=false;
        }
//        return true;
    }
    
    // Connection with other pages
    @FXML
    private void spcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/gui/specialistGUI.fxml"));
        mainAnchor.getChildren().setAll(pane);
    }

    @FXML
    private void homePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        mainAnchor.getChildren().setAll(pane);
    }

    @FXML
    private void custAccPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/customer/gui/CustomerPage.fxml"));
        mainAnchor.getChildren().setAll(pane);
    }

    @FXML
    private void diagRepBkPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/diagrep/gui/BookingDetails.fxml"));
        mainAnchor.getChildren().setAll(pane);
    }

    @FXML
    private void partsPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/parts/gui/PartsPage.fxml"));
        mainAnchor.getChildren().setAll(pane);
    }

    @FXML
    private void viewWarranty(ActionEvent event)
    {
//        db = new CommonDatabase();
//        con=db.getConnection();
//        System.out.println("Works10!");
        Vehicle veh=dataTable.getSelectionModel().getSelectedItem();
        String sql="SELECT * FROM Warranty WHERE WarrantyID=?";
        
        try
        {
            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setString(1, veh.getRegistrationNumber());
            ResultSet rs =stmt.executeQuery();
            
            
            //expDate.setText(rs.gget);
            compName.setText("def");
            compAddress.setText("ghi");
            
            
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "The selected vehicle does not have a warranty!");
//            System.out.println("Doesn't work10!");
        }
        flag=false;
    }

    @FXML
    private void viewBookings(ActionEvent event) throws IOException
    {
        Vehicle vehObject = dataTable.getSelectionModel().getSelectedItem();
        if(vehObject == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewBookings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ViewController controller=fxmlLoader.<ViewController>getController();
            controller.setVehicle(vehObject);
            Stage stage = new Stage();
            stage.setTitle("View Bookings");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            stage.showAndWait();
        }
        flag=false;
    }
    
    @FXML
    private void viewParts(ActionEvent event) throws IOException  
    {
        Vehicle vehObject = dataTable.getSelectionModel().getSelectedItem();
        if(vehObject == null)
        {
            noChosen();
        }
        else
        {
//            System.out.println(vehObject.getRegistrationNumber());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewParts.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ViewController controller=fxmlLoader.<ViewController>getController();
            controller.setVehicle(vehObject);
            Stage stage = new Stage();
            stage.setTitle("View Parts");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        }
        flag=false;
    }
    
    @FXML
    private void viewCustomers(ActionEvent event)
    {
        
        flag=false;
    }
    
//    @FXML
//    private void actionCar(ActionEvent event)
//    {
////        db = new CommonDatabase();
////        con = db.getConnection();
////        System.out.println("Works5!");
//        String choice="";
//        if(car.isSelected())
//        {
//            choice="Car";
//            van.setSelected(false);
//            truck.setSelected(false);
//            warranty.setSelected(false);
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
////            System.out.println("Doesn't work5!");
//        }
//        
//        setTableValue();
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void actionVan(ActionEvent event)
//    {
////        db = new CommonDatabase();
////        con = db.getConnection();
////        System.out.println("Works6!");
//        String choice="";
//        if(van.isSelected())
//        {
//            choice="Van";
//            car.setSelected(false);
//            truck.setSelected(false);
//            warranty.setSelected(false);
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
////            System.out.println("Doesn't work6!");
//        }
//        
//        setTableValue();
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void actionTruck(ActionEvent event)
//    {
////        db = new CommonDatabase();
////        Connection con = db.getConnection();
////        System.out.println("Works7!");
//        String choice="";
//        if(truck.isSelected())
//        {
//            choice="Truck";
//            car.setSelected(false);
//            van.setSelected(false);
//            warranty.setSelected(false);
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
////            System.out.println("Doesn't work7!");
//        }
//
//        setTableValue();
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void actionWarranty(ActionEvent event) {
////        db = new CommonDatabase();
////        con = db.getConnection();
////        System.out.println("Works8!");
//        if(warranty.isSelected())
//        {
//            car.setSelected(false);
//            van.setSelected(false);
//            truck.setSelected(false);
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
////            System.out.println("Doesn't work8!");
//        }
//
//        setTableValue();
//        dataTable.setItems(data);
//    }
}