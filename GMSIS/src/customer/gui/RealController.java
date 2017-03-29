package customer.gui;

/**
 *
 * @author Nandhini Manoharan
 */ 

import common.CommonDatabase;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import customer.logic.allCustomers;
import customer.logic.customers;
import diagrep.gui.AddController;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import vehicles.gui.AddVehicleController;
import vehicles.gui.VehiclePageController;


public class RealController implements Initializable 
{
    // the main root pane
    @FXML
    private AnchorPane rootPane;
    
    // the root page for add page
    @FXML
    private AnchorPane addPane;
    
    // 
    @FXML
    private TextField ID;

    /****** For Add Page **********/
    @FXML
    private TextField firstname;

    @FXML
    private TextField surname;

    @FXML
    private TextField address;

    @FXML
    private TextField postcode;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private RadioButton private_type;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton business_type; 
    
    // table view
    @FXML
    private TableView<allCustomers> dataTable;

    @FXML
    private TableColumn<allCustomers, Integer> customer_ID;

    @FXML
    private TableColumn<allCustomers, String> first;

    @FXML
    private TableColumn<allCustomers, String> sur;

    @FXML
    private TableColumn<allCustomers, String> adr;

    @FXML
    private TableColumn<allCustomers, String> post;

    @FXML
    private TableColumn<allCustomers, String> mobile;

    @FXML
    private TableColumn<allCustomers, String> ema;
    
    @FXML
    private TableColumn<allCustomers, String> type;

    @FXML
    private TextField regNumber;
    
    @FXML
    private ObservableList<allCustomers> data;
    
    @FXML
    private CheckBox private_c;
    @FXML
    private CheckBox business_c;
    
    // buttons for delete page
    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    
    // populated the table with all the customer accounts from the database as soon as the window is opened
    @Override
    public void initialize(URL url, ResourceBundle rb)  
    {
        display();
    }    
    
    
    // clear method for the add page
    @FXML
    public void clearDetails(ActionEvent event)
    {
        firstname.setText("");
        surname.setText("");
        address.setText("");
        postcode.setText("");
        phone.setText("");
        email.setText("");
    }
    
    
    // opens the add page
    @FXML
    private void openAdd(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RealAddPage.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));  
        stage.showAndWait();
        display();
    }
    
    
    // opens the edit page
    @FXML
    private void openEditPage(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerEdit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            EditController controller=fxmlLoader.<EditController>getController();
            controller.setAllFields(cust);
            Stage stage = new Stage();
            stage.setTitle("Edit Customer");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
            display();
            
        } 
    }
    
    
    // alert box when no account has been selected 
    @FXML
    private void noChosen()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("No account selected");
        alert.setContentText("Please select a customer account");
        alert.showAndWait();
    }
    

    /* method to delete the chosen customer account, the vehicles that the customer owns, 
    and bookings and any parts associated with the vehicles owned*/
    @FXML
    private void deleteCustomer(ActionEvent event)
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            boolean check = checkCustomerBookings(cust.getID());
            if(check)
            {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Do you want to continue deleting the account?");

                ButtonType yes = new ButtonType("YES");
                ButtonType no = new ButtonType("NO");
                alert.getButtonTypes().setAll(yes, no);

                Optional<ButtonType> result = alert.showAndWait();


                if(result.get() == yes)
                {
                    try
                    {
                        int cust_id = cust.getID();

                        deleteBP(cust.getID());
                        deleteSPC(cust.getID());
                        deleteB(cust.getID());
                        deleteV(cust.getID());
                        deleteC(cust.getID());
                    }
                    catch(SQLException e)
                    {
                        System.out.println("DELETE METHOD DOESNT WORK");
                    }

                }
                else
                {
                    alert.close();
                }
            }
            else
            {
                CannotDelete();
            }
        }
        display();
    }
    
    
    // method to check if all the past bookings for a customer account has been settled
    // if they are settled then the customr account deleted, if not then the account cannot be deleted
    private boolean checkCustomerBookings(int id)
    {
        boolean check  = true;
        Connection conn = new CommonDatabase().getConnection();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        String dateT = df.format(dateobj);
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT BillsPaid.SettleBill, Booking.BookingDate from BillsPaid INNER JOIN Booking ON BillsPaid.BookingID = Booking.BookingID WHERE BillsPaid.CustomerID = '" + id + "' ");
            if(rs.next())
            {
                do
                {
                    String d = rs.getString("BookingDate").replace("-", "/");
                    Date dateBooking = new Date();
                    try
                    {
                        dateBooking = df.parse(d);
                    }
                    catch(ParseException e)
                    {
                        System.out.println("DATE ERROR");
                    }
                    if(dateBooking.before(dateobj) && rs.getString("SettleBill").equals("OUTSTANDING"))
                    {
                        check = false;
                        break;
                    }
                }
                while(rs.next());
            }
            else
            {
                return true;
            }
        }
        catch(SQLException e)
        {
            System.out.println("New SQLError");
        }
        close(conn);
        return check;
    }
    
    
    // alert message to say the selected account cannot be deleted due to unsettled bills
    @FXML
    public void CannotDelete()
    {   
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Deleting Account");
        alert.setHeaderText("This account cannot be deleted");
        alert.setContentText("There are still bills to be settled");
        alert.showAndWait();
    }
    
    // helper method to delete the customer account
    private void deleteC(int id) throws SQLException
    {      
        Connection conn = new CommonDatabase().getConnection();
        String sql = "DELETE FROM Customer_Accounts WHERE ID = '" + id + "' ";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.executeUpdate();
        close(conn);
    }
    
    // helper method to delete the vehicles owned by a customer
    private void deleteV(int id) throws SQLException
    {        
        Connection conn = new CommonDatabase().getConnection();
        
        String sql = "DELETE FROM Vehicles WHERE CustomerID = '" + id + "' ";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.executeUpdate();
        
      
        close(conn);
    }
    
    // helper method to delete the booking from the bills paid table
    public void deleteBP(int id) throws SQLException
    {
        Connection conn = new CommonDatabase().getConnection();
        String sql2 = "DELETE FROM BillsPaid WHERE CustomerID = '" + id + "' ";
        PreparedStatement stmt = conn.prepareStatement(sql2);
        stmt.executeUpdate();
        close(conn);
    }
    
    // helper method to delete the bookings and the parts associated with the vehicles
    private void deleteB(int id) throws SQLException
    {       
        Connection conn = new CommonDatabase().getConnection();
        
        
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Booking WHERE CustomerID = '" + id + "' ");
        ArrayList<Integer> bookings = new ArrayList<Integer>();
        while(rs.next())
        {
            bookings.add(rs.getInt("BookingID"));
        }
        for(int i = 0; i < bookings.size(); i++)
        {
            String sql = "DELETE FROM PartsUsed  WHERE BookingID = '" + bookings.get(i) + "' " ;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            sql =  "DELETE FROM Booking  WHERE BookingID = '" + bookings.get(i) + "' " ;
            PreparedStatement statement2 = conn.prepareStatement(sql);
            statement2.executeUpdate();
        }
        close(conn);
    }
    
    // helper method to delete any spc bookings
    public void deleteSPC(int id)
    {
        Connection conn = new CommonDatabase().getConnection();
        
        try
        {
            String sql = "DELETE FROM SPCBooking  WHERE CustomerID = '" + id + "' " ;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println("deleting error");
        }
        close(conn);
    }
    
    
    // displays all the customers in the table view
    @FXML
    private void displayCustomers(ActionEvent event)
    {
        display();
    }
    
    
    // helper method for diplaying all the customers
    // Code Reference/Credit: https://www.daniweb.com/programming/software-development/threads/457555/populating-tableview-in-javafx-using-mysql-database
    
    @FXML
    public void display()
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        
        try
        {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Customer_Accounts");
            while(rs.next())
            {
                data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }

        customer_ID.setCellValueFactory(new PropertyValueFactory("ID"));
        first.setCellValueFactory(new PropertyValueFactory("Firstname"));
        sur.setCellValueFactory(new PropertyValueFactory("Surname"));
        adr.setCellValueFactory(new PropertyValueFactory("Address"));
        post.setCellValueFactory(new PropertyValueFactory("Postcode"));
        mobile.setCellValueFactory(new PropertyValueFactory("Phone"));
        ema.setCellValueFactory(new PropertyValueFactory("Email"));
        type.setCellValueFactory(new PropertyValueFactory("Account"));
        dataTable.setItems(null);
        dataTable.setItems(data);
        business_c.setSelected(false);
        private_c.setSelected(false);
    }
               
    
    
    
    // opens view Vehicles page
    @FXML 
    private void viewVehicle(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewVehicles.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            viewController controller=fxmlLoader.<viewController>getController();
            controller.setCustomer(cust, "Vehicles");
            Stage stage = new Stage();
            stage.setTitle("View Vehicles");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
    
    // opens view bookings page
    @FXML 
    private void viewBookings(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Bookings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            viewController controller=fxmlLoader.<viewController>getController();
            controller.setCustomer(cust, "Bookings");
            Stage stage = new Stage();
            stage.setTitle("View Bookings");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
    
    
    // opens view parts page
    @FXML 
    private void viewParts(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewParts.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            viewController controller=fxmlLoader.<viewController>getController();
            controller.setCustomer(cust, "Parts");
            Stage stage = new Stage();
            stage.setTitle("View Parts");
            stage.setScene(new Scene(root1));
            stage.show();
            
        } 
    }
 
    // search for a customer using partial name (both private and business customers) or using registration number
    @FXML
    private void searchCustomer(ActionEvent event)
    {
        String account_type = "";
        if(business_c.isSelected())
        {
            account_type = "business";
        }
        else if(private_c.isSelected())
        {
            account_type = "private";
        }
        
        String sql ="select * from Customer_Accounts where Firstname like '%" + firstname.getText() + "%'" + "and Surname like '%" + surname.getText() + "%'";
        if(!firstname.getText().equals("") || !surname.getText().equals(""))
        {
            if(account_type != "")
            {
                sql = "select * from Customer_Accounts where Firstname like '%" + firstname.getText() + "%'" + "and Account = '" + account_type + "' " + "and Surname like '%" + surname.getText() + "%'";
            }
 
            try
            {
                Connection connection = null;
                CommonDatabase db = new CommonDatabase();
                connection = db.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet rs = statement.executeQuery();
                data = FXCollections.observableArrayList();
                while(rs.next())
                {
                    data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                }
            
            }
            catch(SQLException e)
            {
                System.out.println("Error");
            }
            
        }
        else if(!regNumber.getText().equals(""))
        {
            sql ="SELECT Vehicles.RegistrationNumber, Customer_Accounts.ID, Customer_Accounts.Firstname, Customer_Accounts.Surname, Customer_Accounts.Address, Customer_Accounts.Email, Customer_Accounts.Postcode, Customer_Accounts.Phone, Customer_Accounts.Account FROM Vehicles INNER JOIN Customer_Accounts ON Vehicles.CustomerID = Customer_Accounts.ID WHERE Vehicles.RegistrationNumber = '" + regNumber.getText() + "' ";
            try
            {
                Connection connection = null;
                CommonDatabase db = new CommonDatabase();
                connection = db.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet rs = statement.executeQuery();
                data = FXCollections.observableArrayList();
                while(rs.next())
                {
                    data.add(new allCustomers(rs.getInt("ID"), rs.getString("Firstname"), rs.getString("Surname"), rs.getString("Address"), rs.getString("Postcode"), rs.getString("Phone"), rs.getString("Email"), rs.getString("Account")));
                }
            
            }
            catch(SQLException e)
            {
                System.out.println("Error");
            }
        
        }
        customer_ID.setCellValueFactory(new PropertyValueFactory("ID"));
        first.setCellValueFactory(new PropertyValueFactory("Firstname"));
        sur.setCellValueFactory(new PropertyValueFactory("Surname"));
        adr.setCellValueFactory(new PropertyValueFactory("Address"));
        post.setCellValueFactory(new PropertyValueFactory("Postcode"));
        mobile.setCellValueFactory(new PropertyValueFactory("Phone"));
        ema.setCellValueFactory(new PropertyValueFactory("Email"));
        type.setCellValueFactory(new PropertyValueFactory("Account"));
        dataTable.setItems(null);
        dataTable.setItems(data);
    }
    
    
    // method to search for just private customers
    @FXML
    private void searchPrivateCustomer(ActionEvent event)
    {
        Connection conn = new CommonDatabase().getConnection();
        if(private_c.isSelected())
        {
            business_c.setSelected(false);
            
            try
            {
                data = FXCollections.observableArrayList();
                PreparedStatement s=conn.prepareStatement("SELECT * FROM Customer_Accounts WHERE Account = 'private'");
                ResultSet rs = s.executeQuery();
                while(rs.next())
                {
                    data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), 
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), 
                            rs.getString(8)));
                }
            }
            catch(SQLException e)
            {
                System.out.println("Doesn't work8!");
            }

            customer_ID.setCellValueFactory(new PropertyValueFactory("ID"));
            first.setCellValueFactory(new PropertyValueFactory("Firstname"));
            sur.setCellValueFactory(new PropertyValueFactory("Surname"));
            adr.setCellValueFactory(new PropertyValueFactory("Address"));
            post.setCellValueFactory(new PropertyValueFactory("Postcode"));
            mobile.setCellValueFactory(new PropertyValueFactory("Phone"));
            ema.setCellValueFactory(new PropertyValueFactory("Email"));
            type.setCellValueFactory(new PropertyValueFactory("Account"));
            dataTable.setItems(null);
            dataTable.setItems(data);
        }
        else
        {
            clearSearch(event);
            display();
        }
    }

    
    // method to search for just business customers
    @FXML
    private void serachBusinessCustomer(ActionEvent event)
    {
        Connection conn = new CommonDatabase().getConnection();
        if(business_c.isSelected())
        {
            private_c.setSelected(false);
        
            try
            {
                data = FXCollections.observableArrayList();
                PreparedStatement s=conn.prepareStatement("SELECT * FROM Customer_Accounts WHERE Account = 'business'");
                ResultSet rs = s.executeQuery();
                while(rs.next())
                {
                    data.add(new allCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), 
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), 
                            rs.getString(8)));
                }
            }
            catch(SQLException e)
            {
                System.out.println("Doesn't work8!");
            }

            customer_ID.setCellValueFactory(new PropertyValueFactory("ID"));
            first.setCellValueFactory(new PropertyValueFactory("Firstname"));
            sur.setCellValueFactory(new PropertyValueFactory("Surname"));
            adr.setCellValueFactory(new PropertyValueFactory("Address"));
            post.setCellValueFactory(new PropertyValueFactory("Postcode"));
            mobile.setCellValueFactory(new PropertyValueFactory("Phone"));
            ema.setCellValueFactory(new PropertyValueFactory("Email"));
            type.setCellValueFactory(new PropertyValueFactory("Account"));
            dataTable.setItems(null);
            dataTable.setItems(data);
        }
        else
        {
            clearSearch(event);
            display();
        }
    } 
    
    
    // search functions to hide
    @FXML
    public void handleNames(MouseEvent event)
    {
        regNumber.setDisable(true);
        regNumber.setText("");
    }
    
     // search functions to hide
    @FXML
    public void handleOther(MouseEvent event)
    {
        regNumber.setDisable(false);
        firstname.setDisable(true);
        surname.setDisable(true);
        firstname.setText("");
        surname.setText("");
        business_c.setSelected(false);
        private_c.setSelected(false);
    }
    
     // search functions to hide
    @FXML
    public void handle(MouseEvent event)
    {   
        regNumber.setDisable(false);
        firstname.setDisable(false);
        surname.setDisable(false);
    }
    
    
    
    // switch to home page 
    @FXML
    private void change2Home(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/common/Template.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to vehicle page
    @FXML
    private void change2Vehicle(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to bookings page
    @FXML
    private void change2Bookings(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/diagrep/gui/BookingDetails.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to parts page
    @FXML
    private void change2Parts(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/parts/gui/PartsPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    // switch to specialist page
    @FXML
    private void change2Specialist(ActionEvent event) throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/specialist/gui/spcMainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    
    // printing missing fields alert box
    @FXML
    public void printMissing()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Missing Fields");
        alert.setHeaderText("Missing Fields");
        alert.setContentText("Fill in all the details");
        alert.showAndWait();
    }
    
    // prints if phone number is in wrong format
    @FXML
    public void printPhone()
    {   
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Incorrect Fields");
        alert.setHeaderText("Invalid Phone Number");
        alert.setContentText("Please type in a valid phone number");
        alert.showAndWait();
    }
  
    
    // checks if the phone number entered is valid
    public boolean checkForString(String number)
    {
        for(int i=0; i<number.length(); i++)
        {
            if(!Character.isDigit(number.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    
    // method to clear all the text fields
    @FXML
    private void clearSearch(ActionEvent event)
    {
        business_c.setSelected(false);
        private_c.setSelected(false);
        firstname.setText("");
        surname.setText("");
        regNumber.setText("");
        display();
    }
    
    // summary for add and edit method
    @FXML
    public void infoGiven(String name, String type)
    {
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("COMPLETED");
        if(type == "add")
        {
            alert.setContentText("Customer " + name + " has been successfully added");
        }
        else
        {
            alert.setContentText("Customer Account " + name + " has been successfully edited");
        }
        alert.showAndWait();
    }
    
    
    // method to add a vehicle (initiate vehicle access) for a selected customer account
    @FXML
    private void addVehicle(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            int ID = cust.getID();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vehicles/gui/VehiclePage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            VehiclePageController controller = fxmlLoader.<VehiclePageController>getController();
            controller.getVehicleDetails(event);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();       
            fxmlLoader = new FXMLLoader(getClass().getResource("/vehicles/gui/AddVehicle.fxml"));
            root1 = (Parent) fxmlLoader.load();
            AddVehicleController controller2=fxmlLoader.<AddVehicleController>getController();  
            controller2.setCustomerID(ID);
            stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
            display();
        }
    }
    
    // method to add a new booking for a selected customer account
    @FXML
    private void addBooking(ActionEvent event) throws IOException
    {
        allCustomers cust = dataTable.getSelectionModel().getSelectedItem();
        if(cust == null)
        {
            noChosen();
        }
        else
        {
            int ID = cust.getID();
            String name = cust.getFirstname() + " " + cust.getSurname();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/diagrep/gui/AddBooking.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            AddController controller=fxmlLoader.<AddController>getController();
            controller.setCustomerID(name, ID);
            controller.ShowVehicles(event);
            ((Node)(event.getSource())).getScene().getWindow().hide();  
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.showAndWait();
            display();
        }
    }
    
    // common method to close the database connection
    public void close(Connection connection)
    {
        try
        {
            if(connection != null)
            {
                connection.close();
                System.out.println("CLOSED");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
}

