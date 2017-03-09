/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

import customer.gui.EditController;
import customer.gui.RealController;
import customer.logic.allCustomers;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Nandhini Manoharan
 */
public class AdminController implements Initializable 
{
    
    
    @FXML
    private TextField firstname;

    @FXML
    private TextField surname;

    @FXML
    private TextField password;

    @FXML
    private TextField hourlyWage;

    @FXML
    private TextField id;
    
    @FXML
    private TextField idE;
    
    @FXML
    private TextField firstnameE;

    @FXML
    private TextField surnameE;

    @FXML
    private TextField passwordE;

    @FXML
    private TextField hourlyWageE;

    @FXML
    private TableView<UserAccount> dataTable;

    @FXML
    private TableColumn<UserAccount, Integer> table_id;

    @FXML
    private TableColumn<UserAccount, String> table_firstname;

    @FXML
    private TableColumn<UserAccount, String> table_surname;

    @FXML
    private TableColumn<UserAccount, String> table_password;

    @FXML
    private TableColumn<UserAccount, Integer> table_wage;
    
    @FXML
    private ObservableList<UserAccount> data;
    
    @FXML
    private int user_ID = 0;
    
    @FXML
    private AnchorPane editPane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    }
    
    @FXML
    private void addUser(ActionEvent event)
    {
        if(firstname.getText().trim().isEmpty() || surname.getText().trim().isEmpty() ||  password.getText().trim().isEmpty() || hourlyWage.getText().trim().isEmpty())
        {
            printMissing();
            //clearDetails(event);
        }
        else
        {
            
            //boolean check = checkUnique(password.getText());
            //System.out.println(check);
            boolean integerOr = checkInteger(hourlyWage.getText());
            if(integerOr == true)
            {
                System.out.println("HELLO");
                CommonDatabase db = new CommonDatabase();
                String sql = "INSERT INTO Employees( ID, Firstname, Surname, Password, Hourly_Wage) VALUES(?, ?, ?, ?, ?)";
                Connection conn = db.getConnection();
                
                try
                {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(2, firstname.getText());         
                    stmt.setString(3, surname.getText());
                    stmt.setString(4, password.getText());
                    stmt.setInt(5, Integer.parseInt(hourlyWage.getText()));
                    stmt.execute(); 
                   
                }
                catch(SQLException e)
                {
                    e.getMessage();
                }
                close(conn);
                infoGiven(firstname.getText(), "add");
                clearDetails(event);
                display();
            }
            else if(integerOr == false)
            {
                printIncorrect();
                //clearDetails(event);
            }
        }
    }
    
    public boolean checkInteger(String str)
    {
        try
        {
            int n = Integer.parseInt(str);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    @FXML
    public void printNotUnique()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Password is already taken");
        alert.setContentText("Please enter a different one");
        alert.showAndWait();
    }
            
    @FXML
    private void displayUsers(ActionEvent event)
    {
        display();
    }
    
    @FXML
    private void openEdit(ActionEvent event) throws IOException
    {
        UserAccount user = dataTable.getSelectionModel().getSelectedItem();
        if(user == null)
        {
            noChosen();
        }
        else
        {
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminEdit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            AdminController controller=fxmlLoader.<AdminController>getController();
            controller.setAllFields(user);
            Stage stage = new Stage();
            stage.setTitle("Edit User");
            stage.setScene(new Scene(root1));
            stage.showAndWait(); 
            display();
            
            
        }
    } 
    
    
    @FXML
    private void editUser(ActionEvent event) throws IOException
    {   
        if(firstnameE.getText().trim().isEmpty() || surnameE.getText().trim().isEmpty() ||  passwordE.getText().trim().isEmpty() || hourlyWageE.getText().trim().isEmpty())
        { 
            printMissing();
        }
        else
        {
            //boolean check = checkUnique(passwordE.getText());
         
            boolean integerOr = checkInteger(hourlyWageE.getText());
            if(integerOr == true)
            {
                CommonDatabase db = new CommonDatabase();
                Connection connection = null;
                PreparedStatement statement = null;
                String sql = "UPDATE Employees SET Firstname = ? , " + "Surname = ? , "  + "Password = ? , "  + "Hourly_Wage = ? " + "WHERE ID = ?";

                try
                {
                    connection = db.getConnection();
                    statement = connection.prepareStatement(sql);

                    statement.setString(1, firstnameE.getText());         
                    statement.setString(2, surnameE.getText());
                    statement.setString(3, passwordE.getText());
                    statement.setInt(4, Integer.parseInt(hourlyWageE.getText()));
                    statement.setInt(5, user_ID);
                    statement.executeUpdate();  
                    System.out.println("DONE");
                }
                catch(SQLException e)    
                {   
                    System.out.println(e.getMessage());

                }
                close(connection);
                
                infoGiven(firstnameE.getText(), "edit");
                Stage stage = (Stage) editPane.getScene().getWindow();
                
                stage.close();
                System.out.println("Hello");
                //firstname.setText("HELLO");
                
            }
            else if(integerOr == false)
            {
                printIncorrect();
            }
        }
    }
    
    @FXML
    private void deleteUser(ActionEvent event)
    {
        UserAccount user = dataTable.getSelectionModel().getSelectedItem();
        if(user == null)
        {
            noChosen();
        }
        else
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
            
                int user_id = user.getID();
                String sql = "DELETE FROM Employees WHERE ID = ?";
                CommonDatabase db = new CommonDatabase();
                Connection connection = null;

                try
                {
                    connection = db.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, user_id);
                    statement.executeUpdate();
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
                close(connection);
                     
            }
            else
            {
                alert.close();
            }
        }
        display();
    } 
    
    @FXML
    private void searchUser(ActionEvent event)
    {
        Connection connection = null;
        try
        {
            String sql = "select * from Employees where ID = '" + id.getText()+ "' " ;
                
            PreparedStatement statement = null;
            
            CommonDatabase db = new CommonDatabase();
            connection = db.getConnection();

            statement = connection.prepareStatement(sql);
                
            ResultSet rs = statement.executeQuery();
            data = FXCollections.observableArrayList();
            if(rs.next())
            {
                data.add(new UserAccount(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
               
        }
        catch(SQLException e)
        {
            System.out.println("Doesnt Work");
        }
           
        table_id.setCellValueFactory(new PropertyValueFactory("ID"));
        table_firstname.setCellValueFactory(new PropertyValueFactory("Firstname"));
        table_surname.setCellValueFactory(new PropertyValueFactory("Surname"));
        table_password.setCellValueFactory(new PropertyValueFactory("Password"));
        table_wage.setCellValueFactory(new PropertyValueFactory("Hourly_Wage"));
        close(connection);  
        dataTable.setItems(null);
        dataTable.setItems(data);
        
    }
            
    @FXML
    private void noChosen()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("No account selected");
        alert.setContentText("Please select a User account");
        alert.showAndWait();
    }
    
    
    @FXML
    private void setAllFields(UserAccount user)
    {
        user_ID = user.getID();
        idE.setText(String.valueOf(user_ID));
        idE.setEditable(false);
        firstnameE.setText(user.getFirstname());
        surnameE.setText(user.getSurname());
        passwordE.setText(user.getPassword());
        hourlyWageE.setText(String.valueOf(user.getHourly_Wage()));
    }
            
    /*@FXML
    public boolean checkUnique(String passWord)
    {
        boolean check = true;
        
        Connection conn = null;
        try
        {
            CommonDatabase db = new CommonDatabase();
            conn = db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Employees");
            
            while(rs.next())
            {
                if(rs.getString(4).equals(passWord) && user_ID != rs.getInt(1))
                {
                    return false;
                }
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        close(conn);
        return check;
        
    }*/
    
    @FXML
    public void display()
    {
        CommonDatabase db = new CommonDatabase();
        Connection connection = null;
        
        try
        {
            connection = db.getConnection();
            data = FXCollections.observableArrayList();
            
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Employees");
            while(rs.next())
            {
                data.add(new UserAccount(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Doesn't work");
        }
        close(connection);
        table_id.setCellValueFactory(new PropertyValueFactory("ID"));
        table_firstname.setCellValueFactory(new PropertyValueFactory("Firstname"));
        table_surname.setCellValueFactory(new PropertyValueFactory("Surname"));
        table_password.setCellValueFactory(new PropertyValueFactory("Password"));
        table_wage.setCellValueFactory(new PropertyValueFactory("Hourly_Wage"));
     
        dataTable.setItems(null);
        dataTable.setItems(data);
    }
    
    @FXML
    private void clearDetails(ActionEvent event)
    {
        firstname.setText("");
        surname.setText("");
        password.setText("");
        hourlyWage.setText("");
        id.setText("");
    }
    
    @FXML
    public void printMissing()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Missing Fields");
        alert.setHeaderText("Missing Fields");
        alert.setContentText("Fill in all the details");
        alert.showAndWait();
    }
    
    @FXML
    public void printIncorrect()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Incorrect Information");
        alert.setHeaderText("Incorrect Information");
        alert.setContentText("Fill in all the details properly");
        alert.showAndWait();
    }
    
    @FXML
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
    
    @FXML
    public void infoGiven(String name, String type)
    {
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("COMPLETED");
        if(type == "add")
        {
            alert.setContentText("User " + name + " has been successfully added");
        }
        else
        {
            alert.setContentText("User " + name + " has been successfully edited");
        }
        alert.showAndWait();
    }
    
    
    
}