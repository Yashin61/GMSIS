// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import common.CommonDatabase;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.sql.Connection;

public class VehiclePageController
{
    @FXML
    Pane pane;
    @FXML
    public void handleButton() throws IOException
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = db.getConnection();
        // System.out.println("Works");
    }
}