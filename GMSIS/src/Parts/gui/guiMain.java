/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author rohim
 */
public class guiMain extends Application{
    



  
   @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root, 400, 264);
        stage.setTitle("Welcome to GM-SIS");
        stage.setScene(scene);
        stage.show();
    } 
    public static void main(String[]args){
    Application.launch(); 
    }
}
    

