// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami
/*
package vehicles.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class VehicleAddController implements Initializable
{

    @FXML
    private RadioButton private_type;
    @FXML
    private ToggleGroup Type;
    @FXML
    private RadioButton business_type;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    
    
    public void templateDrop()//call from initializable
    {
         try {
            conn = dc.Connect();
 
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Template ");
            
            while (rs.next()) {
                //templates is the combobox
                templates.getItems().add(rs.getString("Make")+ "-" + rs.getString("Model") + "-" + rs.getString("Engine_Size") + "-" + rs.getString("Fuel_Type"));
            
            }
            conn.close();
        } 
       catch (SQLException ex) {
           ex.printStackTrace();
            System.err.println("Error"+ex);
        }
    }

    @FXML
    private void fillBoxes(ActionEvent event) //onaction
    {
       
        String line=(String) templates.getValue();
        //System.out.println(line);

        String []array=line.split("-");
        modelTxt.setText(array[0]);
        makeTxt.setText(array[1]);
        engineSize.setText(array[2]);
        fuelType.setValue(array[3]);
    }
    
    
    
    @FXML
    private void add(MouseEvent event) {
    }
}

*/