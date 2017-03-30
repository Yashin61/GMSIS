// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import vehicles.*;
import common.CommonDatabase;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VehiclePageController
{
    @FXML
    private AnchorPane mainAnchor;
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
    private TextField compAddress;
    @FXML
    private TextField compName;
    @FXML
    private RadioButton carType;
    @FXML
    private ToggleGroup vehType;
    @FXML
    private RadioButton truckType;
    @FXML
    private RadioButton vanType;
    @FXML
    private Button edVeh;
    @FXML
    private Button vParts;
    @FXML
    private Button vBookings;
    @FXML
    private Button vCustomers;
    @FXML
    private Button viewVeh;
    @FXML
    private ToggleButton vWarr;
    @FXML
    private ComboBox<String> vwMake;
    RadioButton btnSelected;
    private static ObservableList<Vehicle> data;  // Dynamic array of Vehicle
    private boolean flag=false;
    private CommonDatabase db=new CommonDatabase();
    private Connection con=db.getConnection();

    public void initialize()
    {
        ActionEvent event=null;
        try
        {
            getVehicleDetails(event);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
            db=new CommonDatabase();
            con=db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Make FROM Vehicles");
            while(rs.next())
            {
                vwMake.getItems().add(rs.getString("Make"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        // Deselects the selecteds
        mainAnchor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                dataTable.getSelectionModel().clearSelection();
                resetToggleButton();
                expDate.clear();
                compName.clear();
                compAddress.clear();
            }
        });
        
        // Gets warranty details when mouse is clicked
//        dataTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
//        {
//            @Override
//            public void handle(MouseEvent event)
//            {
//                try
//                {
//                    viewWarranty2();
//                }
//                catch (SQLException ex)
//                {
//                    Logger.getLogger(VehiclePageController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
        // Detects row is changed
        dataTable.setRowFactory(tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            row.setOnMouseClicked(ev -> {
                if (ev.getClickCount() == 1 && (!row.isEmpty())){
                    resetToggleButton();
                    expDate.clear();
                    compName.clear();
                    compAddress.clear();
                }
            });
            return row ;
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
    public void getVehicleDetails(ActionEvent event) throws IOException
    {
        try
        {
            con = DriverManager.getConnection("jdbc:sqlite:Records.db");
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Vehicles");
            while(rs.next())
            {
//                System.out.println(rs.getInt(11));  It shows null if was getString(11)
                data.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), 
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), 
                        rs.getString(12), rs.getString(13)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        setTableValue();
        dataTable.setItems(data);
        carType.setSelected(false);
        vanType.setSelected(false);
        truckType.setSelected(false);
        warranty.setSelected(false);
    }
    
    @FXML
    private void viewByMake(ActionEvent event)
    {
        try
        {
            con = DriverManager.getConnection("jdbc:sqlite:Records.db");
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Vehicles WHERE Make = '" + vwMake.getValue() + "'");
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
            e.printStackTrace();
        }
        setTableValue();
        dataTable.setItems(data);
        carType.setSelected(false);
        vanType.setSelected(false);
        truckType.setSelected(false);
        warranty.setSelected(false);
    }
    
    @FXML
    private void viewWarranty(ActionEvent event) throws SQLException
    {
        db=new CommonDatabase();
        con=db.getConnection();
        String message="";
        Vehicle veh = dataTable.getSelectionModel().getSelectedItem();
        if(veh==null)
        {
            noChosen();
        }
        else
        {
            if(vWarr.isSelected())
            {
                try
                {
                    if(dataTable.getSelectionModel().getSelectedItem().getWarrantyID()!=0)
                    {
                        data = FXCollections.observableArrayList();
                        PreparedStatement statement = con.prepareStatement("SELECT * FROM Warranty WHERE WarrantyID=?");
                        statement.setInt(1, dataTable.getSelectionModel().getSelectedItem().getWarrantyID());
                        ResultSet rs = statement.executeQuery();
                        expDate.setText(rs.getString("ExpiryDate"));
                        compName.setText(rs.getString("Name"));
                        compAddress.setText(rs.getString("Address"));
                    }
                }
                catch(SQLException e)
                {}
                con.close();
            }
            else
            {
                expDate.clear();
                compName.clear();
                compAddress.clear();
            }
            if(dataTable.getSelectionModel().getSelectedItem().getWarrantyID()==0)
            {
                message="The selected vehicle does not have warranty!";
                infoAlert(message);
                resetToggleButton();
            }
        }
    }
    
    private void resetToggleButton()
    {
        vWarr.setSelected(false);
    }
    
    @FXML
    private void viewParts(ActionEvent event) throws IOException, SQLException
    {
        Vehicle vehObject = dataTable.getSelectionModel().getSelectedItem();
        if(vehObject == null)
        {
            noChosen();
            return;
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewParts.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ViewController controller=fxmlLoader.<ViewController>getController();
            controller.setParts(vehObject);
            Stage stage = new Stage();
            stage.setTitle("View Parts");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(vParts.getScene().getWindow());
            stage.showAndWait();
        }
        flag=false;
    }
    
    @FXML
    private void viewBookings(ActionEvent event) throws IOException, SQLException
    {
        Vehicle vehObject = dataTable.getSelectionModel().getSelectedItem();
        if(vehObject == null)
        {
            noChosen();
            return;
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewBookings.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ViewController controller=fxmlLoader.<ViewController>getController();
            controller.setBookings(vehObject);
            Stage stage = new Stage();
            stage.setTitle("View Bookings");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(vBookings.getScene().getWindow());
            stage.showAndWait();
        }
        flag=false;
        
        // Without freazing the subwindow
//        Vehicle vehObject = dataTable.getSelectionModel().getSelectedItem();
//        if(vehObject == null)
//        {
//            noChosen();
//        }
//        else
//        {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewBookings.fxml"));
//            Parent root = (Parent) fxmlLoader.load();
//            ViewController controller=fxmlLoader.<ViewController>getController();
//            controller.setBookings(vehObject);
//            Stage stage = new Stage();
//            stage.setTitle("View Bookings");
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.show();
//        }
//        flag=false;
    }
    
    @FXML
    private void viewCustomers(ActionEvent event) throws IOException, SQLException
    {
        Vehicle vehObject = dataTable.getSelectionModel().getSelectedItem();
        if(vehObject == null)
        {
            noChosen();
            return;
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewCustomers.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ViewController controller=fxmlLoader.<ViewController>getController();
            controller.setCustomers(vehObject);
            Stage stage = new Stage();
            stage.setTitle("View Bookings");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(vCustomers.getScene().getWindow());
            stage.showAndWait();
        }
        flag=false;
    }
    
    @FXML
    private void actionVehType(ActionEvent event) throws SQLException
    {
        db=new CommonDatabase();
        con=db.getConnection();
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
            System.out.println(e.getMessage());
        }
        setTableValue();
        dataTable.setItems(data);
        con.close();
    }
    
    @FXML
    private void actionWarranty(ActionEvent event) throws SQLException
    {
        db=new CommonDatabase();
        con=db.getConnection();
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
        {}
        setTableValue();
        dataTable.setItems(data);
        con.close();
    }
    
    private void searchEntry(ActionEvent event) throws SQLException
    {
        db=new CommonDatabase();
        con=db.getConnection();
        String message="";
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
                    message="No data were found!";
                    infoAlert(message);
                }
            }
            catch(SQLException e)
            {}
            setTableValue();
            if(!data.isEmpty())
            {
                dataTable.setItems(data);
                flag=true;
            }
            carType.setSelected(false);
            vanType.setSelected(false);
            truckType.setSelected(false);
            warranty.setSelected(false);
            con.close();
        }
        else
        {
            message="You have not typed anything to search yet!";
            infoAlert(message);
        }
    }
    
    @FXML
    private void searchVehRegNum(ActionEvent event) throws SQLException
    {
        searchEntry(event);
    }
    
    @FXML
    private void onEnter(ActionEvent event) throws SQLException
    {
        searchEntry(event);
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
    }
    
    @FXML
    private void deleteVehicle(ActionEvent event) throws IOException, SQLException
    {
        db=new CommonDatabase();
        con=db.getConnection();
        String message="";
        Vehicle veh=dataTable.getSelectionModel().getSelectedItem();
        if(veh==null)
        {
            noChosen();
        }
        else
        {
//            if(JOptionPane.showConfirmDialog(null, "Do you want to continue deleting? "
//                    + "If the vehicle has booking or warranty details, they also will be deleted!", 
//                    "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            Alert alert=null;
            message="Do you want to continue deleting? If the vehicle has booking or warranty details, they also will be deleted!";
            alert=confirmationAlert(message);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
            {
                String sql="DELETE FROM Vehicles WHERE RegistrationNumber=?";
                try
                {
                    con=db.getConnection();
                    PreparedStatement stmt=con.prepareStatement(sql);
                    stmt.setString(1, veh.getRegistrationNumber());
                    stmt.execute();
                    message="Deleting was successfull!";
                    infoAlert(message);
                    getVehicleDetails(event);
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
                con.close();
            }
        }
    }
    
    @FXML
    private void openEditPage(ActionEvent event) throws IOException
    {
        Vehicle veh = dataTable.getSelectionModel().getSelectedItem();
        if(veh==null)
        {
            noChosen();
            return;
        }
        Stage stage;
        if(event.getSource() == edVeh)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditVehicle.fxml"));
//            fxmlLoader.setLocation(getClass().getResource("EditVehicle.fxml"));
            AnchorPane frame = (AnchorPane) fxmlLoader.load();
//            EditVehicleController controller = (EditVehicleController) fxmlLoader.getController();
            EditVehicleController controller=fxmlLoader.<EditVehicleController>getController();
            controller.setAllFields(veh);
            controller.viewVeh = viewVeh;
            stage = new Stage();
            stage.setScene(new Scene(frame));
            stage.setResizable(false);
            stage.setTitle("Edit Vehicle");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(edVeh.getScene().getWindow());
            stage.showAndWait();
            getVehicleDetails(event);
//            dataTable.setItems(data);
        }
        
//        The version without background update
//        Stage stage;
//        Parent root;
//        if(event.getSource() == edVeh)
//        {
//            stage = new Stage();
//            root = FXMLLoader.load(getClass().getResource("EditVehicle.fxml"));
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.setTitle("Edit Vehicle");
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.initOwner(edVeh.getScene().getWindow());
//            stage.show();
//        }

//        Another version without background update
//        else
//        {
//            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("EditVehicle.fxml"));
//            Parent root1=(Parent)fxmlLoader.load();
//            EditVehicleController controller=fxmlLoader.<EditVehicleController>getController();
//            controller.setAllFields(veh);
//            Stage stage=new Stage();
//            stage.setTitle("Edit Vehicle");
//            stage.setScene(new Scene(root1));
//            stage.show();
//        }
    }
    
    @FXML
    private void openAddPage(ActionEvent event) throws IOException
    {
        Stage stage;
        if(event.getSource() == addVeh)
        {
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddVehicle.fxml"));
            AnchorPane frame = fxmlLoader.load();
            AddVehicleController controller = (AddVehicleController) fxmlLoader.getController();
            controller.viewVeh = viewVeh;
            stage.setScene(new Scene(frame));
            stage.setResizable(false);
            stage.setTitle("Add Vehicle");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addVeh.getScene().getWindow());
            stage.showAndWait();
            getVehicleDetails(event);
        }
    }
    
    private void noChosen()
    {
        String message="First select a vehicle from the table!";
        infoAlert(message);
    }
    
    static boolean checkIfExists(String regNo)
    {
        if(data.isEmpty())
        {
            return false;
        }
        else
        {
            for(Vehicle veh : data)
            {
                if(veh.getRegistrationNumber().equalsIgnoreCase(regNo))
                {
                    return true;
                }
//                else  // why with this, the mathod still need return statement, the all possible circumstances have been defined?!
//                {
//                    return false;
//                }
            }
        }
        return false;
    }
    
    static void infoAlert(String message)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("INFORMATION");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    static void warningAlert(String message)
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("WARNING");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    static Alert confirmationAlert(String message)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("CONFIRMATION");
        alert.setContentText(message);
        return alert;
    }
    
//    Connection with other pages
    @FXML
    private void homePage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        mainAnchor.getChildren().setAll(pane);
    }
    
    @FXML
    private void spcPage(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/gui/specialistGUI.fxml"));
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
    
//    Having choice boxes instead of radio buttons
//    @FXML
//    private void actionCar(ActionEvent event)
//    {
//        String choice="";
//        if(car.isSelected())
//        {
//            choice="Car";
//            van.setSelected(false);
//            truck.setSelected(false);
//            warranty.setSelected(false);
//        }
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
//        {}
//        setTableValue();
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void actionVan(ActionEvent event)
//    {
//        String choice="";
//        if(van.isSelected())
//        {
//            choice="Van";
//            car.setSelected(false);
//            truck.setSelected(false);
//            warranty.setSelected(false);
//        }
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
//        {}
//        
//        setTableValue();
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void actionTruck(ActionEvent event)
//    {
//        String choice="";
//        if(truck.isSelected())
//        {
//            choice="Truck";
//            car.setSelected(false);
//            van.setSelected(false);
//            warranty.setSelected(false);
//        }
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
//        {}
//        setTableValue();
//        dataTable.setItems(data);
//    }
//
//    @FXML
//    private void actionWarranty(ActionEvent event)
//    {
//        if(warranty.isSelected())
//        {
//            car.setSelected(false);
//            van.setSelected(false);
//            truck.setSelected(false);
//         }
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
//        {}
//        setTableValue();
//        dataTable.setItems(data);
//    }
}