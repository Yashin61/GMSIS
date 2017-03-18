// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import common.CommonDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static vehicles.gui.VehiclePageController.checkIfExists;

public class AddVehicleController implements Initializable
{
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
    private DatePicker expDateTemp;
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
    private DatePicker motRenTemp;
    @FXML
    private DatePicker lSrvDtTemp;
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
    Button viewVeh;
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
        if(makeTemp.getSelectionModel().isEmpty())  // THINK ABOUT THIS!!!!!!!!!!!!
        {
            makeTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> make=newValue);
//            make = makeTemp.getSelectionModel().getSelectedItem();
        }
        else
        {
            makeTemp.getEditor().getText();
        }
        if(modelTemp.getSelectionModel().isEmpty())
        {
            modelTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> model=newValue);
        }
        else
        {
            modelTemp.getEditor().getText();
        }
        if(engSizeTemp.getSelectionModel().isEmpty())
        {
            engSizeTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> engSize=newValue);
        }
        else
        {
            engSizeTemp.getEditor().getText();
        }
        if(fTypeTemp.getSelectionModel().isEmpty())
        {
            fTypeTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> fType=newValue);
        }
        else
        {
            fTypeTemp.getEditor().getText();
        }
        if(clTemp.getSelectionModel().isEmpty())
        {
            clTemp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> cl=newValue);
        }
        else
        {
            clTemp.getEditor().getText();
        }
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

    private ObservableList<String> filling5() throws SQLException
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
    
    private ObservableList<Integer> filling6() throws SQLException
    {
        ArrayList<Integer> cstIDList =new ArrayList<>();
        String query = "SELECT DISTINCT ID FROM Customer_Accounts";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            cstIDList.add(rs.getInt("ID"));
        }
//        cstIDList = removeDuplicates2(cstIDList);
        return FXCollections.observableArrayList(cstIDList);
    }
    
    @FXML
    private void add(ActionEvent event)
    {
        make = makeTemp.getEditor().getText();
        model = modelTemp.getEditor().getText();
        engSize = engSizeTemp.getEditor().getText();
        fType = fTypeTemp.getEditor().getText();
        cl = clTemp.getEditor().getText();
        String regNum = regNumTemp.getText();
        if(checkIfExists(regNum))
        {
            JOptionPane.showMessageDialog(null, "The entered registration number already exists! Try again...");
            return;
        }
        String cmpName = cmpNameTemp.getText();
        String cmpAddress = cmpAddressTemp.getText();
        if(make.isEmpty() ||
                model.isEmpty() || 
                yearTemp.getText().isEmpty() ||
                engSize.isEmpty() || 
                fType.isEmpty() ||
                milTemp.getText().isEmpty() || 
                cl.isEmpty() || regNum.isEmpty() || cstID == 0 || motRenTemp.toString().isEmpty() || lSrvDtTemp.toString().isEmpty()
                || vehType.getSelectedToggle() == null)
        {
            JOptionPane.showMessageDialog(null, "There are empty option(s)!");
            return;
        }
        int warrID = 0;
        int year = 0;
        int mil = 0;
        try
        {
            warrID = Integer.parseInt(warrIDTemp.getText());
            year = Integer.parseInt(yearTemp.getText());
            mil = Integer.parseInt(milTemp.getText());
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "There are an inappropriate value(s)!");
        }
//        String motRen = motRenTemp.getConverter().toString(motRenTemp.getValue());  // It records the date with "/" separation
        String motRen = toDate(motRenTemp).toString();
        String lSrvDt = toDate(lSrvDtTemp).toString();
        String expDate = toDate(expDateTemp).toString();
        String sql1 = "INSERT INTO Vehicles(Make, Model, Year, EngineSize, FuelType, Mileage, Colour, "
                + "RegistrationNumber, CustomerID, MOTRenewalDate, LastServiceDate, WarrantyID, VehicleType) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO Warranty(WarrantyID, Name, Address, ExpiryDate) VALUES(?,?,?,?)";
        if(warrID == 0)
        {
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
                stmt.setNull(12, Types.NULL);
                RadioButton btnSelected = (RadioButton) vehType.getSelectedToggle();
                stmt.setString(13, btnSelected.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "The vehicle is now added!");
                JOptionPane.showMessageDialog(null, "No warranty details is added!");
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
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
                JOptionPane.showMessageDialog(null, "The vehicle is now added!");
                PreparedStatement stmt2=con.prepareStatement(sql2);
                stmt2.setInt(1, warrID);
                stmt2.setString(2, cmpName);
                stmt2.setString(3, cmpAddress);
                stmt2.setString(4, expDate);
                stmt2.executeUpdate();
                JOptionPane.showMessageDialog(null, "The warranty details is now added!");
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
        viewVeh.fire();
    } 
    
    @FXML
    private void clear(ActionEvent event) // think about other possible way
    {
        yearTemp.clear();
        milTemp.clear();
        regNumTemp.clear();
        motRenTemp.setValue(null);
        lSrvDtTemp.setValue(null);
        warrIDTemp.clear();
        expDateTemp.setValue(null);
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
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }

    // This is a method for the customer module to access
    public void setCustomerID(int id)
    {
        cstIDTemp.setValue(id);
    }
    
    // Sets the format of date with - instead of /
    private Date toDate(DatePicker DatePickerObject)
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
        return sqlDate;
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