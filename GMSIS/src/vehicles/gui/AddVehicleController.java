// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import common.CommonDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddVehicleController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField address111;
    @FXML
    private TextField address11311;
    @FXML
    private TextField address1111;
    @FXML
    private TextField address1121;
    @FXML
    private TextField address113111;
    @FXML
    private TextField address1131111;
    @FXML
    private Button addToTable;
    @FXML
    private TextField yearTemp;
    @FXML
    private ComboBox<String> makeTemp;
    @FXML
    private ComboBox<String> modelTemp;
    @FXML
    private RadioButton carTemp;
    @FXML
    private RadioButton vanTemp;
    @FXML
    private RadioButton truckTemp;
    CommonDatabase db=new CommonDatabase();;
    Connection con=db.getConnection();
    @FXML
    private TextField warrIDTemp;
    @FXML
    private TextField expDateTemp;
    @FXML
    private TextField regNum;
    
    private static String make;
    @FXML
    private Button closeButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
        
        
        makeTemp.setItems(filling1());
        modelTemp.setItems(filling2());
        
        
                        
        }
        catch(SQLException e)
        {}

        makeTemp.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                        make=newValue);
                 
        
//        makeTemp.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) ->
//                        make=newValue);
        
    }
    
    
    
    
    
    private ObservableList<String> filling1() throws SQLException  // think of making aall in one method, possibly linkedlist?!
    {
        
        ArrayList<String> makeList =new ArrayList<>();
        String query = "SELECT Make FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        
        while(rs.next()){
                makeList.add(rs.getString("Make"));
                        }
        
        makeList = removeDuplicates(makeList);
        return FXCollections.observableArrayList(makeList);
        
    }
    
    private ObservableList<String> filling2() throws SQLException
    {
        
        ArrayList<String> modelList =new ArrayList<>();
        String query = "SELECT Model FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        
        while(rs.next()){
                modelList.add(rs.getString("Model"));
                        }
        
        modelList = removeDuplicates(modelList);
        return FXCollections.observableArrayList(modelList);
        
    }

    @FXML
    private void add(ActionEvent event) {
        int warrID = Integer.parseInt(warrIDTemp.getText());
        String expDate = expDateTemp.getText();
        
        String reg = regNum.getText();
        int year = Integer.parseInt(yearTemp.getText());
        String sql = "INSERT INTO Vehicles(Make, RegistrationNumber, Year) VALUES(?,?,?)";
        
        String sql2 = "INSERT INTO Warranty(WarrantyID, ExpiryDate) VALUES(?,?)";
        
//        if(!warrIDTemp.getText().equals("") && !expDateTemp.getText().equals(""))
//        {
            
        try{

            PreparedStatement stmt=con.prepareStatement(sql2);
            stmt.setInt(1, warrID);
            stmt.setString(2, expDate);
            stmt.executeUpdate();

            
        }
        catch(SQLException e)
        {}
        
//        }
        try{

            PreparedStatement stmt2=con.prepareStatement(sql);
            stmt2.setString(1, make);
            stmt2.setString(2, reg);
            stmt2.setInt(3, year);
            stmt2.executeUpdate();

            
        }
        catch(SQLException e)
        {}
        
        
    } 
    
    @FXML
    private void cancel(ActionEvent event) {
        
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    
    private ArrayList removeDuplicates(ArrayList<String> al)
    {
        // add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(al);
        al.clear();
        al.addAll(hs);
        return al;
        
        
        
    }
//    private ArrayList removeDuplicates(ArrayList<String> array)
//    {
//        for(int i=0; i<array.size(); i++)
//        {
//            for(int j = i+1; j<array.size(); j++)
//            {
//                if(array.get(j).equals(array.get(i)))
//                {
//                    array.remove(j);
//                    j--;
//                           
//                }
//            }
//        }
//        return array;
//    }
}