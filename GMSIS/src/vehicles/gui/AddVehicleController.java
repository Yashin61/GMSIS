// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import common.CommonDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddVehicleController implements Initializable {

    @FXML
    private AnchorPane rootPane;
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
    @FXML
    private TextField warrIDTemp;
    @FXML
    private TextField expDateTemp;
    @FXML
    private Button closeButton;
    @FXML
    private TextField milTemp;
    @FXML
    private TextField regNumTemp;
    @FXML
    private ComboBox<String> engSizeTemp;
    @FXML
    private ComboBox<String> clTemp;
    @FXML
    private TextField motRenTemp;
    @FXML
    private TextField lSrvDtTemp;
    @FXML
    private TextField cmpNameTemp;
    @FXML
    private TextField cmpAddressTemp;
    @FXML
    private ComboBox<Integer> cstIDTemp;
    @FXML
    private ComboBox<String> fTypeTemp;
    @FXML
    private ToggleGroup vehType;
    private static String make;
    private static String model;
    private static String engSize;
    private static String fType;
    private static String cl;
    private static int cstID;
    CommonDatabase db=new CommonDatabase();
    Connection con=db.getConnection();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            makeTemp.setItems(filling1());
            modelTemp.setItems(filling2());
            engSizeTemp.setItems(filling3());
            fTypeTemp.setItems(filling4());
            clTemp.setItems(filling5());
            cstIDTemp.setItems(filling6());
        }
        catch(SQLException e)
        {}
        
        makeTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> make=newValue);
        modelTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> model=newValue);
        engSizeTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> engSize=newValue);
        fTypeTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> fType=newValue);
        clTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> cl=newValue);
        cstIDTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> cstID=newValue);
    }

    private ObservableList<String> filling1() throws SQLException  // think of making aall in one method, possibly linkedlist?!
    {
        ArrayList<String> makeList =new ArrayList<>();
        String query = "SELECT DISTINCT Make FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            makeList.add(rs.getString("Make"));
        }
//        makeList = removeDuplicates(makeList);
        return FXCollections.observableArrayList(makeList);
    }
    
    private ObservableList<String> filling2() throws SQLException
    {
        ArrayList<String> modelList =new ArrayList<>();
        String query = "SELECT DISTINCT Model FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            modelList.add(rs.getString("Model"));
        }
//        modelList = removeDuplicates(modelList);
        return FXCollections.observableArrayList(modelList);
    }
    
    private ObservableList<String> filling3() throws SQLException
    {
        ArrayList<String> engSizeList =new ArrayList<>();
        String query = "SELECT DISTINCT EngineSize FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            engSizeList.add(rs.getString("EngineSize"));
        }
//        engSizeList = removeDuplicates(engSizeList);
        return FXCollections.observableArrayList(engSizeList);
    }
    
    private ObservableList<String> filling4() throws SQLException
    {
        ArrayList<String> fTypeList =new ArrayList<>();
        String query = "SELECT DISTINCT FuelType FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            fTypeList.add(rs.getString("FuelType"));
        }
//        fTypeList = removeDuplicates(fTypeList);
        return FXCollections.observableArrayList(fTypeList);
    }

    private ObservableList<String> filling5() throws SQLException  // think of making aall in one method, possibly linkedlist?!
    {
        ArrayList<String> clList =new ArrayList<>();
        String query = "SELECT DISTINCT Colour FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            clList.add(rs.getString("Colour"));
        }
//        clList = removeDuplicates(clList);
        return FXCollections.observableArrayList(clList);
    }
    
    private ObservableList<Integer> filling6() throws SQLException  // think of making aall in one method, possibly linkedlist?!
    {
        ArrayList<Integer> cstIDList =new ArrayList<>();
        String query = "SELECT DISTINCT CustomerID FROM VehiclesTemplate";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            cstIDList.add(rs.getInt("CustomerID"));
        }
//        cstIDList = removeDuplicates2(cstIDList);
        return FXCollections.observableArrayList(cstIDList);
    }
    
    @FXML
    private void add(ActionEvent event)
    {
        String regNum = regNumTemp.getText();
        int year = Integer.parseInt(yearTemp.getText());
        int mil = Integer.parseInt(milTemp.getText());
        String motRen = motRenTemp.getText();
        String lSrvDt = lSrvDtTemp.getText();
        int warrID = Integer.parseInt(warrIDTemp.getText());
        String cmpName = cmpNameTemp.getText();
        String cmpAddress = cmpAddressTemp.getText();
        String expDate = expDateTemp.getText();
        String sql1 = "INSERT INTO Vehicles(Make, Model, Year, EngineSize, FuelType, Mileage, Colour, RegistrationNumber, CustomerID, MOTRenewalDate, LastServiceDate, WarrantyID, VehicleType) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO Warranty(WarrantyID, Name, Address, ExpiryDate) VALUES(?,?,?,?)";
//        if(warrIDTemp.getText().equals("") && expDateTemp.getText().equals("") && cmpNameTemp.getText().equals("") && cmpAddressTemp.getText().equals(""))
        try
        {
            PreparedStatement stmt=con.prepareStatement(sql1);
            stmt.setString(1, make);
            stmt.setString(2, model);
            stmt.setInt(3, year);
            stmt.setString(4, engSize);
            stmt.setString(5, fType);
            stmt.setInt(6, mil);
            stmt.setString(7, cl);
            stmt.setString(8, regNum);
            stmt.setInt(9, cstID);
            stmt.setString(10, motRen);
            stmt.setString(11, lSrvDt);
            stmt.setInt(12, warrID);
            RadioButton btnSelected = (RadioButton) vehType.getSelectedToggle();
            stmt.setString(13, btnSelected.getText());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        try
        {
            PreparedStatement stmt2=con.prepareStatement(sql2);
            stmt2.setInt(1, warrID);
            stmt2.setString(2, cmpName);
            stmt2.setString(3, cmpAddress);
            stmt2.setString(4, expDate);
            stmt2.executeUpdate();
        }
        catch(SQLException e)
        {}
//        update();
    } 
    
    @FXML
    private void clear(ActionEvent event) // think about other possible way
    {
        yearTemp.clear();
        milTemp.clear();
        regNumTemp.clear();
        motRenTemp.clear();
        lSrvDtTemp.clear();
        warrIDTemp.clear();
        expDateTemp.clear();
        cmpNameTemp.clear();
        cmpAddressTemp.clear();
        makeTemp.getSelectionModel().clearSelection();
        modelTemp.getSelectionModel().clearSelection();
        engSizeTemp.getSelectionModel().clearSelection();
        fTypeTemp.getSelectionModel().clearSelection();
        clTemp.getSelectionModel().clearSelection();
        cstIDTemp.getSelectionModel().clearSelection();
        vanTemp.setSelected(false);
        truckTemp.setSelected(false);
        carTemp.setSelected(false);
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // This is a method for the customer module to access
    public void setCustomerID(int id)
    {
        cstIDTemp.setValue(id);
    }
    
//    private ArrayList removeDuplicates(ArrayList<String> temp)
//    {
//        Set<String> hs = new HashSet<>();
//        hs.addAll(temp);
//        temp.clear();
//        temp.addAll(hs);
//        return temp;
//    }
    
//    private ArrayList removeDuplicates2(ArrayList<Integer> temp)  // how to make these 2 methods 1???
//    {
//        Set<Integer> hs = new HashSet<>();
//        hs.addAll(temp);
//        temp.clear();
//        temp.addAll(hs);
//        return temp;
//    }
    
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
//                }
//            }
//        }
//        return array;
//    }
}