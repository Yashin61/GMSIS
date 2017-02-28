// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class VehicleEditController implements Initializable
{

    @FXML
    private TextField firstname;
    @FXML
    private TextField surname;
    @FXML
    private TextField address;
    @FXML
    private TextField postcode;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private RadioButton private_type;
    @FXML
    private ToggleGroup Type;
    @FXML
    private RadioButton business_type;
    @FXML
    private TextField ID;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void edit(ActionEvent event) {
    }
}