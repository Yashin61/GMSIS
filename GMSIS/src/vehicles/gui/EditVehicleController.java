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
    private ComboBox<Integer> customerID;
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
    private boolean checkWarr=false;
    private static int custID=0;
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
    
    private ObservableList<Integer> filling() throws SQLException
    {
        ArrayList<Integer> custIDList =new ArrayList<>();
        String query = "SELECT ID FROM Customer_Accounts";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            custIDList.add(rs.getInt("ID"));
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
        motRenDate.setValue(LocalDate.parse(veh.getMOTRenewalDate(), formatter1));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        lastServiceDate.setValue(LocalDate.parse(veh.getLastServiceDate(), formatter2));
        custID=veh.getCustomerID();
        String message="";
        if(veh.getWarrantyID()>0)
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
                expiryDate.setValue(LocalDate.parse(info.getString(4),formatter3));
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
        if(make.getText().isEmpty() || model.getText().isEmpty() || year.getText().isEmpty() || engineSize.getText().isEmpty() || 
                fuelType.getText().isEmpty() || mileage.getText().isEmpty() || colour.getText().isEmpty() || 
                regNumber.getText().isEmpty() || custID==0 || motRenDate.toString().isEmpty() || lastServiceDate.toString().isEmpty())
        {
            message="There are empty option(s)!";
            VehiclePageController.warningAlert(message);
            return;
        }
        try
        {
            int a = Integer.parseInt(year.getText());
            int b = Integer.parseInt(mileage.getText());
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
                    + "Colour = '" + colour.getText() + "', CustomerID = " + String.valueOf(customerID.getValue()) + ", "
                    + "MOTRenewalDate = '" + motRenDate.getValue().format(formatter) + "', "
                    + "LastServiceDate = '" + lastServiceDate.getValue().format(formatter) + "', WarrantyID = " + warrantyID.getText() + ", "
                    + "VehicleType = '" + type + "' WHERE RegistrationNumber = '" + regNumber.getText() + "'";
            try
            {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.executeUpdate();
                message="The vehicle is now edited!";
                VehiclePageController.infoAlert(message);
                if(checkWarr)
                {
                    String sql2 = "UPDATE Warranty SET Name = '" + companyName.getText() + "' , Address = '" + companyAddress.getText() + "' , "
                            + "ExpiryDate = '" + expiryDate.getValue().format(formatter) + "' WHERE WarrantyID = " + warrantyID.getText();
                    ps = con.prepareStatement(sql2);
                    ps.executeUpdate();
                    message="The warranty details is now edited!";
                    VehiclePageController.infoAlert(message);
                }
                viewVeh.fire();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch(NumberFormatException e)
        {
            VehiclePageController.warningAlert("There are an inappropriate value(s)!");
            return;
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
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
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
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
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