/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author prashant
 */
public class specialistGui extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        //Parent root = FXMLLoader.load(getClass().getResource("specialistGUI.fxml"));       
        Parent root = FXMLLoader.load(getClass().getResource("spcAddBooking.fxml"));        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
