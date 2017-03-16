// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import vehicles.*;

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
    private RadioButton EdCar;
    @FXML
    private ToggleGroup vehType;
    @FXML
    private RadioButton EdVan;
    @FXML
    private RadioButton EdTruck;
    
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
//        motRenDate.setText(veh.getMOTRenewalDate());
//        lastServiceDate.setText(veh.getLastServiceDate());
//        expiryDate.setText(war.getExpiryDate());
//        companyName.setText(war.getName());
//        companyAddress.setText(war.getAddress());
    }    

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void edit(ActionEvent event) {
    }
    
    @FXML
    private void clear(ActionEvent event) {
    }
}