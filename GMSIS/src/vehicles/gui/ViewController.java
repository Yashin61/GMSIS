// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles.gui;

import common.CommonDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import vehicles.Vehicle;

public class ViewController implements Initializable
{
    @FXML
    private AnchorPane Pane;
    @FXML
    private Label none;
    @FXML
    private ListView<String> allParts;
    @FXML
    private ListView<String> allBookings;
    @FXML
    private ListView<String> allCustomers;
    private CommonDatabase db=new CommonDatabase();
    private Connection con=db.getConnection();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {}    
    
    void setParts(Vehicle v) throws SQLException
    {
        viewParts(v);      
    }
    
    void setBookings(Vehicle v) throws SQLException
    {
        viewBookings(v);      
    }
    
    void setCustomers(Vehicle v) throws SQLException
    {
        viewCustomers(v);      
    }
    
    private void viewParts(Vehicle v) throws SQLException
    {
        db = new CommonDatabase();
        con = db.getConnection();
        ObservableList<String> data = FXCollections.observableArrayList();
        try
        {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM PartsUsed WHERE RegistrationNumber = '" + v.getRegistrationNumber() + "' ");
            if(!rs.isBeforeFirst())
            {
                allParts.setVisible(false);
                none.setVisible(true);
            }
            else
            {
                ArrayList<Integer> partsIDs = new ArrayList<Integer>();
                String answer = "";
                data.add("Registration Number: " + v.getRegistrationNumber());
                while(rs.next())
                {
                    partsIDs.add(rs.getInt("PartsID"));
                }
                removeDuplicates(partsIDs);
                for(int i=0; i<partsIDs.size(); i++)
                {
                    rs = con.createStatement().executeQuery("SELECT * FROM Parts WHERE ID = '" + partsIDs.get(i) + "' ");
                    if(rs != null)
                    {
                        answer = answer + "Part ID: " + rs.getInt("ID") + "\nPart Name: " + rs.getString("Name") + 
                                "\nPart Description: " + rs.getString("Description") + "\nPrice: £"+ rs.getString("Cost") + "\n\n";
                    }
                }
                data.add(answer);
                allParts.setItems(data);
            }
        }
        catch(SQLException  e)
        {
            System.out.println("SQL Error");
        }
        con.close();
    }
    
    private void viewBookings(Vehicle v) throws SQLException
    {
//        DateFormat df = new SimpleDateFormat("dd/MM/yy");
//        Date dateobj = new Date();
//        String dateT = df.format(dateobj);
//        todayDate.setText("Today's Date: " + df.format(dateobj));
//        todayDate.setVisible(true);
        db = new CommonDatabase();
        con = db.getConnection();
        ObservableList<String> data = FXCollections.observableArrayList();
        try
        {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Booking WHERE RegistrationNumber = '" + v.getRegistrationNumber() + "' ");
            String answer = "";
            if(!rs.isBeforeFirst())
            {
                allBookings.setVisible(false);
                none.setVisible(true);
            }
            else
            {
                answer = answer + "Registration Number: " + rs.getString("RegistrationNumber") + "\n\nBooking ID: " + rs.getInt("BookingID") + "\nBooking Type: " 
                        + rs.getString("BookingType") + "\nMechanic ID: " + rs.getString("MechanicID") 
                        + "\nBooking Date: " + rs.getString("BookingDate") + "\nBooking Time: " + 
                        rs.getString("BookingTime") + "\nRepair Time: " + rs.getString("RepairTime") + "\nBill: £" + rs.getDouble("Bill") + "\n\n";
                data.add(answer);
                allBookings.setItems(data);
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQL Error");
        }
        con.close();
    }
    
    private void viewCustomers(Vehicle v) throws SQLException
    {
        db = new CommonDatabase();
        con = db.getConnection();
        ObservableList<String> data = FXCollections.observableArrayList();
        try
        {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Customer_Accounts WHERE ID = '" + v.getCustomerID() + "' ");
//            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Customer_Accounts INNER JOIN Vehicles ON Customer_Accounts.ID = Vehicles.CustomerID WHERE Customer_Accounts.ID = '" + v.getCustomerID() + "';");  // With showing the registration number
//            SELECT * FROM Customer_Accounts INNER JOIN Vehicles ON Customer_Accounts.RegistrationNumber = Vehicles.RegistrationNumber WHERE ID = '1';
            String answer = "";
            if(!rs.isBeforeFirst())
            {
                allCustomers.setVisible(false);
            }
            else
            {
                        answer = answer + "Customer ID: " + rs.getInt("ID") + "\nFirstname: " 
//                answer = answer + "Registration Number: " + rs.getString("RegistrationNumber") + "\n\nCustomer ID: " + rs.getInt("ID") + "\nFirstname: " 
                        + rs.getString("Firstname") + "\nSurname: " + rs.getString("Surname") 
                        + "\nAddress: " + rs.getString("Address") + "\nPostcode: " + 
                        rs.getString("Postcode") + "\nPhone Number: " + rs.getString("Phone") + 
                        "\nEmail Address: " + rs.getString("Email") + "\nAccount Type: " + rs.getString("Account") + "\n\n";
                data.add(answer);
                allCustomers.setItems(data);
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQL Error: "+e);
        }
        con.close();
    }
    
    private void removeDuplicates(ArrayList<Integer> array)
    {
        for(int i=0; i<array.size(); i++)
        {
            for(int j = i+1; j<array.size(); j++)
            {
                if(array.get(j) == array.get(i))
                {
                    array.remove(j);
                    j--;
                           
                }
            }
        }
//        System.out.println(array);
    }
}