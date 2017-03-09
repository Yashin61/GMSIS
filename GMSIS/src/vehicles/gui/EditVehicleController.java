// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import vehicles.*;

public class EditVehicleController implements Initializable {

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
    private TextField milage;
    @FXML
    private TextField colour;
    @FXML
    private TextField regNumber;
    @FXML
    private TextField motRenDate;
    @FXML
    private TextField lastServiceDate;
    @FXML
    private TextField customerID;
    @FXML
    private TextField warrantyID;
    @FXML
    private TextField expiryDate;
    @FXML
    private TextField companyName;
    @FXML
    private TextField companyAddress;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {}
    
    public void setAllFields(Vehicle veh, Warranty war)
    {
        make.setText(veh.getMake());
        model.setText(veh.getModel());
        year.setText(Integer.toString(veh.getYear()));
        engineSize.setText(veh.getEngineSize());
        fuelType.setText(veh.getFuelType());
        milage.setText(Integer.toString(veh.getMilage()));
        colour.setText(veh.getColour());
        regNumber.setText(veh.getRegistrationNumber());
        customerID.setText(Integer.toString(veh.getCustomerID()));
        warrantyID.setText(Integer.toString(war.getWarrantyID()));
        motRenDate.setText(veh.getMOTRenewalDate());
        lastServiceDate.setText(veh.getLastServiceDate());
        expiryDate.setText(war.getExpiryDate());
        companyName.setText(war.getName());
        companyAddress.setText(war.getAddress());
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