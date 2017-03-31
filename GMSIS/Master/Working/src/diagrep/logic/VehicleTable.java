/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.logic;

import diagrep.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import specialist.logic.SpecialistDB;

/**
 *
 * @author Mustakim
 */
public class VehicleTable {
    private final StringProperty Make;
    private final StringProperty Model;
    private final StringProperty RegNo;
    private final IntegerProperty Mileage;
    private final IntegerProperty CustomerID;
    private final StringProperty CustomerFName;
    private final StringProperty CustomerSName;
    
    public VehicleTable(String Make, String Model, String RegNo, Integer Mileage, Integer CID){
        this.Make = new SimpleStringProperty(Make);
        this.Model = new SimpleStringProperty(Model);
        this.RegNo = new SimpleStringProperty(RegNo);
        this.Mileage = new SimpleIntegerProperty(Mileage);
        this.CustomerID = new SimpleIntegerProperty(CID);
        this.CustomerFName = new SimpleStringProperty(getCustName(CID));
        this.CustomerSName = new SimpleStringProperty(getCustName(CID));
    }
    
    /******Get Customer Name using ID******/
    private String getCustName(Integer cust)
    {
        String name ="";
        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT Firstname, Surname FROM Customer_Accounts WHERE ID = "+cust+";");
            while(set.next()){
                name = set.getString("Firstname")+" "+set.getString("Surname");
            }
            stmt.close();
            set.close();
            connect.close();
        }catch(SQLException e)
        {
            Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return name;
    }
    
    /******Customer ID******/
    public int getCustomerID(){
        return CustomerID.get();
    }
    public IntegerProperty CustomerIDProperty() {
        return CustomerID;
    }
    public void setCustomerID(Integer CID){
        CustomerID.set(CID);
    }
    
    /******Customer Name******/
    public String getCustomerFName(){
        return CustomerFName.get();
    }
    public StringProperty CustomerFNameProperty() {
        return CustomerFName;
    }
    public void setCustomerFName(String CName){
        CustomerFName.set(CName);
    }
    
    /******Customer Name******/
    public String getCustomerSName(){
        return CustomerSName.get();
    }
    public StringProperty CustomerSNameProperty() {
        return CustomerSName;
    }
    public void setCustomerSName(String CName){
        CustomerSName.set(CName);
    }
    
    /******Make******/
    public String getMake(){
        return Make.get();
    }
    
    public StringProperty MakeProperty(){
        return Make;
    }
    
    public void setMake(String x){
        Make.set(x);
    }
   
    /******Model******/
    public String getModel(){
        return Model.get();
    }
    
    public StringProperty ModelProperty(){
        return Model;
    }
    
    public void setModel(String x){
        Model.set(x);
    }
    
    /******RegNo******/
    public String getRegNo(){
        return RegNo.get();
    }
    
    public StringProperty RegNoProperty(){
        return RegNo;
    }
    
    public void setRegNo(String x){
        RegNo.set(x);
    }
   
    /******Mileage******/
    public Integer getMileage(){
        return Mileage.get();
    }
    
    public IntegerProperty MileageProperty(){
        return Mileage;
    }
    
    public void setMileage(Integer x){
        Mileage.set(x);
    }
}