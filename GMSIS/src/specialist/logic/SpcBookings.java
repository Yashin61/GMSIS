/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;

/**
 *
 * @author prashant
 */

public class SpcBookings 
{
    private final IntegerProperty SpcBookingid;
    private final StringProperty SpcBookingname;
    private final StringProperty SpcDeliveryDate;
    private final StringProperty SpcArrived;
    private final StringProperty SpcReturnDate;
    private final StringProperty SpcReturned;
    private final IntegerProperty SpcPartID;
    private final StringProperty SPCRNumber;
    private final IntegerProperty SpcCustomerID;
    private final StringProperty SpcCustomerName;
    private final StringProperty SpcWorkOn;
    private final StringProperty SpcRepairType;
    private final DoubleProperty SpcCost;
    private final IntegerProperty SpcBookId;
    
     
    public SpcBookings(Integer id, String name, String dDate, String arrived, String rDate, String returned, Integer parts, String rNumber, Integer cust, String workOn, String type, Double cost, Integer bookingId)
    {
        this.SpcBookingid = new SimpleIntegerProperty(id);
        this.SpcBookingname = new SimpleStringProperty(name);
        this.SpcDeliveryDate = new SimpleStringProperty(dDate);
        this.SpcArrived = new SimpleStringProperty(arrived);
        this.SpcReturnDate = new SimpleStringProperty(rDate);
        this.SpcReturned = new SimpleStringProperty(returned);
        this.SpcPartID = new SimpleIntegerProperty(parts);
        this.SPCRNumber = new SimpleStringProperty(rNumber);
        this.SpcCustomerID = new SimpleIntegerProperty(cust);
        this.SpcCustomerName = new SimpleStringProperty(getCustName(cust));
        this.SpcWorkOn = new SimpleStringProperty(workOn);   
        this.SpcRepairType = new SimpleStringProperty(type);
        this.SpcCost = new SimpleDoubleProperty(cost);
        this.SpcBookId = new SimpleIntegerProperty(bookingId);
    }

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
    
    public int getSpcBookingId() {
        return SpcBookingid.get();
    }

    public String getSpcBookingName() {
        return SpcBookingname.get();
    }

    public String getSpcDDate() {
        return SpcDeliveryDate.get();
    }
    
    public String getSpcArrive() {
        return SpcArrived.get();
    }

    public String getSpcRDate() {
        return SpcReturnDate.get();
    }

    public String getSpcReturn() {
        return SpcReturned.get();
    }
    
    public int getSpcPartId(){
        return SpcPartID.get();
    }
    
    public String getSpcRNumber(){
        return SPCRNumber.get();
    }
    
    public int getSpcCustID(){
        return SpcCustomerID.get();
    }
    
    public String getSpcCustName(){
        return SpcCustomerName.get();
    }
    
    public String getSpcWOn() {
        return SpcWorkOn.get();
    }
    
    public String getSpcType(){
        return SpcRepairType.get();
    }
    
    public Double getSpcCost(){
        return SpcCost.get();
    }
    
    public int getSpcBookId(){
        return SpcBookId.get();
    }
    
    public IntegerProperty SpcBookingidProperty() {
        return SpcBookingid;
    }

    public StringProperty SpcBookingnameProperty() {
        return SpcBookingname;
    }

    public StringProperty SpcDeliveryDateProperty() {
        return SpcDeliveryDate;
    }

    public StringProperty SpcArrivedProperty() {
        return SpcArrived;
    }

    public StringProperty SpcReturnDateProperty() {
        return SpcReturnDate;
    }

    public StringProperty SpcReturnedProperty() {
        return SpcReturned;
    }

    public IntegerProperty SpcPartIDProperty() {
        return SpcPartID;
    }

    public StringProperty SPCRNumberProperty() {
        return SPCRNumber;
    }

    public IntegerProperty SpcCustomerIDProperty() {
        return SpcCustomerID;
    }

    public StringProperty SpcCustomerNameProperty() {
        return SpcCustomerName;
    }

    public StringProperty SpcWorkOnProperty() {
        return SpcWorkOn;
    }
    
    public StringProperty SpcRepairTypeProperty() {
    return SpcRepairType;
    }

    public DoubleProperty SpcCostProperty() {
        return SpcCost;
    }
    
    public IntegerProperty SpcBookIdProperty(){
        return SpcBookId;
    }

    public void setSpcBookingId(Integer id) {
        SpcBookingid.set(id);
    }

    public void setSpcBookingName(String name) {
        SpcBookingname.set(name);
    }

    public void setSpcDDate(String dDate) {
        SpcDeliveryDate.set(dDate);
    }
    
    public void setSpcArrive(String arrived) {
        SpcArrived.set(arrived);
        Connection connect = null;
        Statement stmt = null;
        
        try
        {
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();

            String sql = "UPDATE SPCBooking SET Arrived = '"+arrived+"' WHERE Id = "+getSpcBookingId()+" ;";

            stmt.executeUpdate(sql);
            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
           Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setSpcRDate(String rDate) {
        SpcReturnDate.set(rDate);
    }

    public void setSpcReturn(String returned) {
        SpcReturned.set(returned);
        Connection connect = null;
        Statement stmt = null;
        
        try
        {
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            stmt = connect.createStatement();

            String sql = "UPDATE SPCBooking SET Returned = '"+returned+"' WHERE Id = "+getSpcBookingId()+" ;";

            stmt.executeUpdate(sql);
            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
           Logger.getLogger(SpecialistDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setSpcRNumber(String rNumber){
        SPCRNumber.set(rNumber);
    }
    
    public void setSpcCustID(Integer cust){
        SpcCustomerID.set(cust);
    }
    
    public void setSpcCustName(String custName){
        SpcCustomerName.set(custName);
    }
    
    public void setSpcPartId(Integer parts){
        SpcPartID.set(parts);
    }
    
    public void setSpcWOn(String workOn) {
        SpcWorkOn.set(workOn);
    }
    private static class StringPoperty {
        public StringPoperty() {}
    }
}
