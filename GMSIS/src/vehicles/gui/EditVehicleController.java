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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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
    private TextField customerID;
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
    private String type="";
    private boolean flag=false;
    Button viewVeh;
    private CommonDatabase db=new CommonDatabase();
    private Connection con=db.getConnection();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {}
    
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
        customerID.setText(Integer.toString(veh.getCustomerID()));
        warrantyID.setText(Integer.toString(veh.getWarrantyID()));
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        motRenDate.setValue(LocalDate.parse(veh.getMOTRenewalDate(), formatter1));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        lastServiceDate.setValue(LocalDate.parse(veh.getLastServiceDate(), formatter2));
        if(veh.getWarrantyID()>0)
        {
            flag=true;
            try
            {
                String sql = "SELECT * FROM Warranty WHERE WarrantyID = ?";
                PreparedStatement p = con.prepareStatement(sql);
                p.setInt(1, veh.getWarrantyID());
                ResultSet info = p.executeQuery();
                companyName.setText(info.getString(2));
                companyAddress.setText(info.getString(3));
                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                expiryDate.setValue(LocalDate.parse(info.getString(4),formatter3));
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(veh.getWarrantyID()<0)
        {
            JOptionPane.showMessageDialog(null, "There are an inappropriate value(s)!");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "The selected vehicle does not have warranty!");
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
    private void edit(ActionEvent event)
    { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String sql = "UPDATE Vehicles SET Make = '" + make.getText() + "' ,  Model = '" + model.getText() + "' , "
                + "Year = " + year.getText() + ", EngineSize = '" + engineSize.getText() + "' , "
                + "FuelType = '" + fuelType.getText() + "', Mileage = " + mileage.getText() + " , "
                + "Colour = '" + colour.getText() + "', CustomerID = " + customerID.getText() + ", "
                + "MOTRenewalDate = '" + motRenDate.getValue().format(formatter) + "', "
                + "LastServiceDate = '" + lastServiceDate.getValue().format(formatter) + "', WarrantyID = " + warrantyID.getText() + ", "
                + "VehicleType = '" + type + "' WHERE RegistrationNumber = '" + regNumber.getText() + "'";
        
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            if(flag)
            {
                String sql2 = "UPDATE Warranty SET Name = '" + companyName.getText() + "' , Address = '" + companyAddress.getText() + "' , "
                        + "ExpiryDate = '" +expiryDate.getValue().format(formatter) + "' WHERE WarrantyID = " + warrantyID.getText();
                PreparedStatement ps2 = con.prepareStatement(sql2);
                ps2.executeUpdate();
            }
            viewVeh.fire();
            JOptionPane.showMessageDialog(null, "The vehicle is now edited!");
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Fail!");
        }
    }
    
    @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void clear(ActionEvent event) // think about other possible way
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
        regNumber.clear();
        customerID.clear();
        warrantyID.clear();
        companyName.clear();
        companyAddress.clear();
        expiryDate.setValue(null);
        edVan.setSelected(false);
        edTruck.setSelected(false);
        edCar.setSelected(false);
    }
}