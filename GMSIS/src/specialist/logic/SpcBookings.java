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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author prashant
 */
public class SpcBookings 
{
    private final IntegerProperty SpcBookingid;
    private final StringProperty SpcBookingname;
    private final StringProperty SpcDeliveryDate;
    private final IntegerProperty SpcArrived;
    private final StringProperty SpcReturnDate;
    private final IntegerProperty SpcReturned;
    private final IntegerProperty SpcPartID;
    private final StringProperty SPCRNumber;
    private final IntegerProperty SpcCustomerID;
    private final StringProperty SpcCustomerName;
    private final IntegerProperty SpcRepairID;
    private final StringProperty SpcWorkOn;
     
    public SpcBookings(Integer id, String name, String dDate, Integer arrived, String rDate, Integer returned, Integer parts, String rNumber, Integer cust, Integer repair, String workOn)
    {
        this.SpcBookingid = new SimpleIntegerProperty(id);
        this.SpcBookingname = new SimpleStringProperty(name);
        this.SpcDeliveryDate = new SimpleStringProperty(dDate);
        this.SpcArrived = new SimpleIntegerProperty(arrived);
        this.SpcReturnDate = new SimpleStringProperty(rDate);
        this.SpcReturned = new SimpleIntegerProperty(returned);
        this.SpcPartID = new SimpleIntegerProperty(parts);
        this.SPCRNumber = new SimpleStringProperty(rNumber);
        this.SpcCustomerID = new SimpleIntegerProperty(cust);
        this.SpcCustomerName = new SimpleStringProperty(getCustName(cust));
        this.SpcRepairID = new SimpleIntegerProperty(repair);
        this.SpcWorkOn = new SimpleStringProperty(workOn);      
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
    
    public int getSpcArrive() {
        return SpcArrived.get();
    }

    public String getSpcRDate() {
        return SpcReturnDate.get();
    }

    public int getSpcReturn() {
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
    
    public int getSPCPartId(){
        return SpcRepairID.get();
    }
    
    public String getSpcWOn() {
        return SpcWorkOn.get();
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

    public IntegerProperty SpcArrivedProperty() {
        return SpcArrived;
    }

    public StringProperty SpcReturnDateProperty() {
        return SpcReturnDate;
    }

    public IntegerProperty SpcReturnedProperty() {
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

    public IntegerProperty SpcRepairIDProperty() {
        return SpcRepairID;
    }

    public StringProperty SpcWorkOnProperty() {
        return SpcWorkOn;
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
    
    public void setSpcArrive(Integer arrived) {
        SpcArrived.set(arrived);
    }

    public void setSpcRDate(String rDate) {
        SpcReturnDate.set(rDate);
    }

    public void setSpcReturn(Integer returned) {
        SpcReturned.set(returned);
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
    
    public void setSpcRepairId(Integer repair){
        SpcRepairID.set(repair);
    }
    
    public void setSpcWOn(String workOn) {
        SpcWorkOn.set(workOn);
    }
    private static class StringPoperty {
        public StringPoperty() {}
    }
}
