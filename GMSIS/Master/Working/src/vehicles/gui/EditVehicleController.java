// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import vehicles.*;
import common.CommonDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditVehicleController implements Initializable
{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField make;
    @FXML
    private TextField model;
    @FXML
    private TextField year;
    @FXML
    private TextField engineSize;
    @FXML
    private TextField fuelType;
    @FXML
    private TextField mileage;
    @FXML
    private TextField colour;
    @FXML
    private TextField regNumber;
    @FXML
    private DatePicker motRenDate;
    @FXML
    private DatePicker lastServiceDate;
    @FXML
    private ComboBox <String> customerID;
    @FXML
    private TextField warrantyID;
    @FXML
    private DatePicker expiryDate;
    @FXML
    private TextField companyName;
    @FXML
    private TextField companyAddress;
    @FXML
    private ToggleGroup vehType;
    @FXML
    private Button closeButton;
    @FXML
    private Button editTable;
    @FXML
    private RadioButton edCar;
    @FXML
    private RadioButton edVan;
    @FXML
    private RadioButton edTruck;
    private Stage stage=null;
    private String type="";
    private static int custID=0;
    private boolean checkWarr=false;
    Button viewVeh;
    private CommonDatabase db=new CommonDatabase();
    private Connection con=db.getConnection();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            customerID.setItems(filling());
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EditVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        futureDateRestrictor();
        pastDateRestrictor();
        
    }
    
    private ObservableList<String> filling() throws SQLException
    {
        ArrayList<String> custIDList =new ArrayList<>();
        String query = "SELECT ID FROM Customer_Accounts";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            custIDList.add(String.valueOf(rs.getInt("ID")));
        }
        return FXCollections.observableArrayList(custIDList);
    }
    
    public void setAllFields(Vehicle veh) 
    {
        make.setText(veh.getMake());
        model.setText(veh.getModel());
        year.setText(Integer.toString(veh.getYear()));
        engineSize.setText(veh.getEngineSize());
        fuelType.setText(veh.getFuelType());
        mileage.setText(Integer.toString(veh.getMileage()));
        colour.setText(veh.getColour());
        regNumber.setText(veh.getRegistrationNumber());
        customerID.getSelectionModel().select(veh.getCustomerID()-1);
        warrantyID.setText(Integer.toString(veh.getWarrantyID()));
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter2;
        formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try
        {
            motRenDate.setValue(LocalDate.parse(veh.getMOTRenewalDate(), formatter1));
            lastServiceDate.setValue(LocalDate.parse(veh.getLastServiceDate(), formatter2));
        }
        catch(Exception e)
        {}
        
        custID=veh.getCustomerID();
        String message="";
        if(veh.getWarrantyID()!=0)
        {
            checkWarr=true;
            try
            {
                String sql = "SELECT * FROM Warranty WHERE WarrantyID = ?";
                PreparedStatement p = con.prepareStatement(sql);
                p.setInt(1, veh.getWarrantyID());
                ResultSet info = p.executeQuery();
                companyName.setText(info.getString(2));
                companyAddress.setText(info.getString(3));
                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                try
                {
                    expiryDate.setValue(LocalDate.parse(info.getString(4),formatter3));
                }
                catch(Exception e)
                {}
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            message="The selected vehicle does not have warranty!";
            VehiclePageController.infoAlert(message);
        }
        if(veh.getVehicleType().equals("Car"))
        {
            edCar.setSelected(true);
            type = "Car";
        }
        else if(veh.getVehicleType().equals("Van"))
        {
            edVan.setSelected(true);
            type = "Van";
        }
        else if(veh.getVehicleType().equals("Truck"))
        {
            edTruck.setSelected(true);
            type = "Truck";
        }
    }

    @FXML
    private void edit(ActionEvent event) throws SQLException
    {
        String message="";
        if(checkWarr)
        {
            try
            {
                if(vehType.getSelectedToggle() == null || make.getText().isEmpty() || model.getText().isEmpty() || year.getText().isEmpty() || engineSize.getText().isEmpty() || 
                fuelType.getText().isEmpty() || mileage.getText().isEmpty() || colour.getText().isEmpty() || customerID.getValue().isEmpty() || 
                motRenDate.getValue()==null || lastServiceDate.getValue()==null || expiryDate.getValue()==null || companyName.getText().isEmpty() || companyAddress.getText().isEmpty())
                {
                    message="There are empty option(s)!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                if(make.getText().trim().isEmpty() || model.getText().trim().isEmpty() || year.getText().trim().isEmpty() || engineSize.getText().trim().isEmpty() || 
                fuelType.getText().trim().isEmpty() || mileage.getText().trim().isEmpty() || Integer.parseInt(mileage.getText())<0 || 
                colour.getText().trim().isEmpty() || customerID.getValue().trim().isEmpty() || customerID.getValue().equals("0") || 
                companyName.getText().trim().isEmpty() || companyAddress.getText().trim().isEmpty())
                {
                    message="There are an inappropriate value(s)!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                if(Integer.parseInt(year.getText())<1940)
                {
                    message="The given year cannot be smaller than 1940!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                int mileageInt = Integer.parseInt(mileage.getText());
                int yearInt= Integer.parseInt(year.getText());;
                Calendar now = Calendar.getInstance();
                if(yearInt>now.get(Calendar.YEAR))
                {
                    message="The given year cannot be greater than the current year!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                if(edCar.isSelected())
                {
                    type = "Car";
                }
                else if(edVan.isSelected())
                {
                    type = "Van";
                }
                else if(edTruck.isSelected())
                {
                    type = "Truck";
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String sql = "UPDATE Vehicles SET Make = '" + make.getText() + "' ,  Model = '" + model.getText() + "' , "
                        + "Year = " + year.getText() + ", EngineSize = '" + engineSize.getText() + "' , "
                        + "FuelType = '" + fuelType.getText() + "', Mileage = " + mileage.getText() + " , "
                        + "Colour = '" + colour.getText() + "', CustomerID = " + customerID.getValue() + ", "    // String.valueOf(customerID.getValue())  When it was Integer in combobox
                        + "MOTRenewalDate = '" + motRenDate.getValue().format(formatter) + "', "
                        + "LastServiceDate = '" + lastServiceDate.getValue().format(formatter) + "', WarrantyID = " + warrantyID.getText() + ", "
                        + "VehicleType = '" + type + "' WHERE RegistrationNumber = '" + regNumber.getText() + "'";
                try
                {
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.executeUpdate();
                    String sql2 = "UPDATE Warranty SET Name = '" + companyName.getText() + "' , Address = '" + companyAddress.getText() + "' , "
                            + "ExpiryDate = '" + expiryDate.getValue().format(formatter) + "' WHERE WarrantyID = " + warrantyID.getText();
                    ps = con.prepareStatement(sql2);
                    ps.executeUpdate();
                    message="The vehicle is now edited!";
                    VehiclePageController.infoAlert(message);
//                    message="The warranty details is now edited!";
//                    VehiclePageController.infoAlert(message);
                    viewVeh.fire();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            catch(NumberFormatException e)
            {
                VehiclePageController.warningAlert("Please change the values of Year or Mileage to integer!");
                return;
            }
        }
        else
        {
            try
            {
                if(vehType.getSelectedToggle() == null || make.getText().isEmpty() || model.getText().isEmpty() || year.getText().isEmpty() || engineSize.getText().isEmpty() || 
                fuelType.getText().isEmpty() || mileage.getText().isEmpty() || colour.getText().isEmpty() || customerID.getValue().isEmpty() || 
                motRenDate.getValue()==null || lastServiceDate.getValue()==null)
                {
                    message="There are empty option(s)!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                if(expiryDate.getValue()!=null || !companyName.getText().isEmpty() || !companyAddress.getText().isEmpty())
                {
                    if(expiryDate.getValue()==null || companyName.getText().isEmpty() || companyAddress.getText().isEmpty())
                    {
                        
                        message="There are empty option(s)!";
                        VehiclePageController.warningAlert(message);
                        return;
                    }
//                companyName.getText().trim().isEmpty() || companyAddress.getText().trim().isEmpty())
                }
                if(make.getText().trim().isEmpty() || model.getText().trim().isEmpty() || year.getText().trim().isEmpty() || engineSize.getText().trim().isEmpty() || 
                fuelType.getText().trim().isEmpty() || mileage.getText().trim().isEmpty() || Integer.parseInt(mileage.getText())<0 || 
                colour.getText().trim().isEmpty() || customerID.getValue().trim().isEmpty() || customerID.getValue().equals("0"))
                {
                    message="There are an inappropriate value(s)!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                if(Integer.parseInt(year.getText())<1940)
                {
                    message="The given year cannot be smaller than 1940!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                int yearInt = Integer.parseInt(year.getText());
                int mileageInt = Integer.parseInt(mileage.getText());
                Calendar now = Calendar.getInstance();
                if(yearInt>now.get(Calendar.YEAR))
                {
                    message="The given year cannot be greater than the current year!";
                    VehiclePageController.warningAlert(message);
                    return;
                }
                if(edCar.isSelected())
                {
                    type = "Car";
                }
                else if(edVan.isSelected())
                {
                    type = "Van";
                }
                else if(edTruck.isSelected())
                {
                    type = "Truck";
                }
                int warrId=0;
                String sql="";
                try
                {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    if(expiryDate.getValue()!=null && !companyName.getText().isEmpty() && !companyAddress.getText().isEmpty())
                    {
                        String sql2 = "INSERT INTO Warranty(Name, Address, ExpiryDate, RegistrationNumber) VALUES(?,?,?,?)";
                        PreparedStatement stmt2=con.prepareStatement(sql2);
                        stmt2.setString(1, companyName.getText());
                        stmt2.setString(2, companyAddress.getText());
                        try
                        {
                            stmt2.setString(3, expiryDate.getValue().format(formatter));
                        }
                        catch(NullPointerException e)
                        {}
                        stmt2.setString(4, regNumber.getText());
                        stmt2.executeUpdate();
                        ResultSet rs = con.createStatement().executeQuery("SELECT WarrantyID FROM Warranty WHERE RegistrationNumber= '" + regNumber.getText() + "'" );
                        warrId = rs.getInt(1);
                        sql = "UPDATE Vehicles SET Make = '" + make.getText() + "' ,  Model = '" + model.getText() + "' , "
                        + "Year = " + year.getText() + ", EngineSize = '" + engineSize.getText() + "' , "
                        + "FuelType = '" + fuelType.getText() + "', Mileage = " + mileage.getText() + " , "
                        + "Colour = '" + colour.getText() + "', CustomerID = " + customerID.getValue() + ", "
                        + "MOTRenewalDate = '" + motRenDate.getValue().format(formatter) + "', "
                        + "LastServiceDate = '" + lastServiceDate.getValue().format(formatter) + "', WarrantyID = " + warrId + ", "
                        + "VehicleType = '" + type + "' WHERE RegistrationNumber = '" + regNumber.getText() + "'";
                    }
                    else if(expiryDate.getValue()==null && companyName.getText().isEmpty() && companyAddress.getText().isEmpty())
                    {
                        sql = "UPDATE Vehicles SET Make = '" + make.getText() + "' ,  Model = '" + model.getText() + "' , "
                        + "Year = " + year.getText() + ", EngineSize = '" + engineSize.getText() + "' , "
                        + "FuelType = '" + fuelType.getText() + "', Mileage = " + mileage.getText() + " , "
                        + "Colour = '" + colour.getText() + "', CustomerID = " + customerID.getValue() + ", "
                        + "MOTRenewalDate = '" + motRenDate.getValue().format(formatter) + "', "
                        + "LastServiceDate = '" + lastServiceDate.getValue().format(formatter) + "', WarrantyID = NULL" + ", "
                        + "VehicleType = '" + type + "' WHERE RegistrationNumber = '" + regNumber.getText() + "'";
                    }
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.executeUpdate();
                    message="The vehicle is now edited!";
                    VehiclePageController.infoAlert(message);
//                    message="The warranty details is now edited!";
//                    VehiclePageController.infoAlert(message);
                    viewVeh.fire();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            catch(NumberFormatException e)
            {
                VehiclePageController.warningAlert("Please change the values of Year or Mileage to integer!");
                return;
            }
        }
        closeWin();
    }
    
    private void closeWin() throws SQLException
    {
        con.close();
        stage=(Stage)editTable.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void cancel(ActionEvent event) throws SQLException
    {
        closeWin();
    }
    
    @FXML
    private void clear(ActionEvent event) // Think about other possible way
    {
        make.clear();
        model.clear();
        year.clear();
        motRenDate.setValue(null);
        lastServiceDate.setValue(null);
        engineSize.clear();
        fuelType.clear();
        colour.clear();
        mileage.clear();
        customerID.getSelectionModel().clearSelection();
        companyName.clear();
        companyAddress.clear();
        expiryDate.setValue(null);
        edVan.setSelected(false);
        edTruck.setSelected(false);
        edCar.setSelected(false);
    }

    private void futureDateRestrictor()
    {
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isAfter(LocalDate.now()))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));
                }
            }
        };
        lastServiceDate.setDayCellFactory(dayCellFactory);        
    }
    
    private void pastDateRestrictor()
    {
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));
                }
            }
        };
        expiryDate.setDayCellFactory(dayCellFactory);
        motRenDate.setDayCellFactory(dayCellFactory);
    }
}