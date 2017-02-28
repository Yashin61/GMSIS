// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.logic;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("VEHICLE RECORDS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}