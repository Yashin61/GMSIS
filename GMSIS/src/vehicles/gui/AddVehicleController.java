// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import static vehicles.gui.VehiclePageController.checkIfExists;
import common.CommonDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import vehicles.Vehicle;

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
    private static int cstID=0;
    Button viewVeh; // think why not private!
    private Stage stage=null;
    private static Vehicle veh;
    private CommonDatabase db=new CommonDatabase();
    private Connection con=db.getConnection();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) // it is public, so, does not it effect other classes, even in other packages?
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
        futureDateRestrictor();
        pastDateRestrictor();
//        setUpDatePickers();
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
        String query = "SELECT ID FROM Customer_Accounts";
        ResultSet rs = con.createStatement().executeQuery(query);
        while(rs.next())
        {
            cstIDList.add(rs.getInt("ID"));
        }
//        cstIDList = removeDuplicates2(cstIDList);
        return FXCollections.observableArrayList(cstIDList);
    }
    
    @FXML
    private void add(ActionEvent event) throws SQLException
    {
//        Vehicle veh=new Vehicle();
        String message="";
        make = makeTemp.getEditor().getText();
        model = modelTemp.getEditor().getText();
        engSize = engSizeTemp.getEditor().getText();
        fType = fTypeTemp.getEditor().getText();
        cl = clTemp.getEditor().getText();
        String regNum = regNumTemp.getText();
        if(checkIfExists(regNum))
        {
            message="The entered registration number already exists! Try again...";
            VehiclePageController.infoAlert(message);
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
                cl.isEmpty() || regNum.isEmpty() || cstID==0 || motRenTemp.toString().isEmpty() || lSrvDtTemp.toString().isEmpty()
                || vehType.getSelectedToggle() == null)
        {
            message="There are empty option(s)!";
            VehiclePageController.warningAlert(message);
            return;
        }
        int year = 0;
        int mil = 0;
        try
        {
            year = Integer.parseInt(yearTemp.getText());
            mil = Integer.parseInt(milTemp.getText());
        }
        catch(NumberFormatException e)
        {
            message="There are an inappropriate value(s)!";
            VehiclePageController.warningAlert(message);
            return;
        }
//        String motRen = motRenTemp.getConverter().toString(motRenTemp.getValue());  // It records the date with "/" separation
//        String motRen = toDate(motRenTemp).toString();  // If using just toDate method, the format is yyyy-mm-dd
//        String motRen = motRenTemp.getConverter().toString(motRenTemp.getValue());
//        String lSrvDt = lSrvDtTemp.getConverter().toString(lSrvDtTemp.getValue());
//        String expDate = expDateTemp.getConverter().toString(expDateTemp.getValue());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        motRenTemp.setValue(LocalDate.parse(veh.getMOTRenewalDate(), formatter1));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        lSrvDtTemp.setValue(LocalDate.parse(veh.getMOTRenewalDate(), formatter2));
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        expDateTemp.setValue(LocalDate.parse(veh.getMOTRenewalDate(), formatter3));
        String sql1 = "INSERT INTO Vehicles(Make, Model, Year, EngineSize, FuelType, Mileage, Colour, "
                + "RegistrationNumber, CustomerID, MOTRenewalDate, LastServiceDate, WarrantyID, VehicleType) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO Warranty(Name, Address, ExpiryDate) VALUES(?,?,?)";
        if(expDateTemp.getValue() == null && cmpNameTemp.getText().isEmpty() && cmpAddressTemp.getText().isEmpty()){    
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
//                stmt.setString(10, motRen);
//                stmt.setString(11, lSrvDt);
                stmt.setString(10, motRenTemp.getValue().format(formatter1));
                stmt.setString(11, lSrvDtTemp.getValue().format(formatter2));                
                stmt.setNull(12, Types.NULL);
                RadioButton btnSelected = (RadioButton) vehType.getSelectedToggle();
                stmt.setString(13, btnSelected.getText());
                stmt.executeUpdate();
                message="The vehicle is now added!";
                VehiclePageController.infoAlert(message);
                message="No warranty details is added!";
                VehiclePageController.infoAlert(message);
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
                PreparedStatement stmt2=con.prepareStatement(sql2);
                stmt2.setString(1, cmpName);
                stmt2.setString(2, cmpAddress);
                stmt2.setString(3, expDateTemp.getValue().format(formatter3));
                stmt2.executeUpdate();
                PreparedStatement statement = con.prepareStatement("SELECT WarrantyID FROM Warranty WHERE Name = ? AND Address = ? AND ExpiryDate = ?");
                statement.setString(1, cmpNameTemp.getText());
                statement.setString(2, cmpAddressTemp.getText());
                statement.setString(3, expDateTemp.getConverter().toString(expDateTemp.getValue()));
                ResultSet results = statement.executeQuery();
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
                stmt.setString(10, motRenTemp.getValue().format(formatter1));
                stmt.setString(11, lSrvDtTemp.getValue().format(formatter2));
                if(results.next())
                {
                    stmt.setInt(12, results.getInt("WarrantyID"));
                }
                else
                {
                    stmt.setInt(12, Types.NULL);
                }
                RadioButton btnSelected = (RadioButton) vehType.getSelectedToggle();
                stmt.setString(13, btnSelected.getText());
                stmt.executeUpdate();
                message="The vehicle is now added!";
                VehiclePageController.infoAlert(message);
                message="The warranty details is added!";
                VehiclePageController.infoAlert(message);
                viewVeh.fire();
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            closeWin();
        }
    } 
    
    @FXML
    private void clear(ActionEvent event) // think about other possible way
    {
        yearTemp.clear();
        milTemp.clear();
        regNumTemp.clear();
        motRenTemp.setValue(null);
        lSrvDtTemp.setValue(null);
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

    private void closeWin() throws SQLException
    {
        con.close();
        stage=(Stage)addToTable.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void cancel(ActionEvent event) throws SQLException
    {
        closeWin();
    }

    private void futureDateRestrictor()  // think about how not having these methods in the class editcontriller
    {
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isAfter(LocalDate.now()))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));
                }
            }
        };
        lSrvDtTemp.setDayCellFactory(dayCellFactory);        
    }
    
    private void pastDateRestrictor()
    {
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));
                }
            }
        };
        expDateTemp.setDayCellFactory(dayCellFactory);
        motRenTemp.setDayCellFactory(dayCellFactory);
    }
    
//    private void setUpDatePickers()
//    {
//        motRenTemp.setConverter(new StringConverter<LocalDate>()
//        {
//            @Override
//            public String toString(LocalDate object)
//            {
//                if(object != null)
//                {
//                    try
//                    {
//                        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(object);
//                    }
//                    catch(DateTimeException e)
//                    {}
//                }
//                return null;
//            }
//
//            @Override
//            public LocalDate fromString(String string)
//            {
//                if(string != null && !string.isEmpty())
//                {
//                    try
//                    {
//                        return LocalDate.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                    }
//                    catch(DateTimeParseException e)
//                    {}
//                }
//                return null;
//            }
//        });
//        lSrvDtTemp.setConverter(new StringConverter<LocalDate>()
//        {
//            @Override
//            public String toString(LocalDate object)
//            {
//                if(object != null)
//                {
//                    try
//                    {
//                        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(object);
//                    }
//                    catch(DateTimeException e)
//                    {}
//                }
//                return null;
//            }
//
//            @Override
//            public LocalDate fromString(String string)
//            {
//                if(string != null && !string.isEmpty())
//                {
//                    try
//                    {
//                        return LocalDate.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                    }
//                    catch(DateTimeParseException e)
//                    {}
//                }
//                return null;
//            }
//        });
//        expDateTemp.setConverter(new StringConverter<LocalDate>()
//        {
//            @Override
//            public String toString(LocalDate object)
//            {
//                if(object != null)
//                {
//                    try
//                    {
//                        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(object);
//                    }
//                    catch(DateTimeException e)
//                    {}
//                }
//                return null;
//            }
//            @Override
//            public LocalDate fromString(String string)
//            {
//                if(string != null && !string.isEmpty())
//                {
//                    try
//                    {
//                        return LocalDate.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                    }
//                    catch(DateTimeParseException e)
//                    {}
//                }
//                return null;
//            }
//        });     
//    }
    
    // This is a method for the customer module to access
    public void setCustomerID(int id)
    {
        cstIDTemp.setValue(id);
    }
    
    // Sets the format of date with - instead of /
//    private Date toDate(DatePicker DatePickerObject)
//    {
//        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
//        return sqlDate;
//    }
    
//    I have used SELECT DISTINCT instead of using removeDuplicates method
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