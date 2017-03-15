/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.logic;

import diagrep.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

/**
 *
 * @author Mustakim
 */
public class BookingTableE {
    private final IntegerProperty BookingID;
    private final StringProperty RegNumber;
    private final StringProperty BookingType;
    private final IntegerProperty MechanicID;
    private final StringProperty BookingDate;
    private final StringProperty BookingTime;
    private final StringProperty RepairTime;
    private final DoubleProperty Bill; 
    
    public BookingTableE(Integer BookingID, String RegNumber, String BookingType, Integer MechanicID, String BookingDate, String BookingTime, String RepairTime, Double Bill){
        this.BookingID = new SimpleIntegerProperty(BookingID);
        this.RegNumber = new SimpleStringProperty(RegNumber);
        this.BookingType = new SimpleStringProperty(BookingType);
        this.MechanicID = new SimpleIntegerProperty(MechanicID);
        this.BookingDate = new SimpleStringProperty(BookingDate);
        this.BookingTime = new SimpleStringProperty(BookingTime);
        this.RepairTime = new SimpleStringProperty(RepairTime);
        this.Bill = new SimpleDoubleProperty(Bill);
    }
    
    /******Booking ID******/
    public int getBookingID(){
        return BookingID.get();
    }
    
    public IntegerProperty BookingIDProperty(){
        return BookingID;
    }
    
    public void setBookingID(Integer x){
        BookingID.set(x);
    }
   
    /******Registration Number******/
    public String getRegNumber(){
        return RegNumber.get();
    }
    
    public StringProperty RegNumberProperty(){
        return RegNumber;
    }
    
    public void setRegNumber(String x){
        RegNumber.set(x);
    }
    
    /******Booking Type******/
    public String getBookingType(){
        return BookingType.get();
    }
    
    public StringProperty BookingTypeProperty(){
        return BookingType;
    }
    
    public void setBookingType(String x){
        BookingType.set(x);
    }
    
    /******Mechanic ID******/
    public int getMechanicID(){
        return MechanicID.get();
    }
    
    public IntegerProperty MechanicIDProperty(){
        return MechanicID;
    }
    
    public void setMechanicID(Integer x){
        MechanicID.set(x);
    }
    
    /******Booking Date******/
    public String getBookingDate(){
        return BookingDate.get();
    }
    
    public StringProperty BookingDateProperty(){
        return BookingDate;
    }
    
    public void setBookingDate(String x){
        BookingDate.set(x);
    }
    
    /******Booking Time******/
    public String getBookingTime(){
        return BookingTime.get();
    }
    
    public StringProperty BookingTimeProperty(){
        return BookingTime;
    }
    
    public void setBookingTime(String x){
        BookingTime.set(x);
    }
    
    /******Repair Time******/
    public String getRepairTime(){
        return RepairTime.get();
    }
    
    public StringProperty RepairTimeProperty(){
        return RepairTime;
    }
    
    public void RepairTime(String x){
        RepairTime.set(x);
    }
    
    /******Bill******/
    public double getBill(){
        return Bill.get();
    }
    
    public DoubleProperty BillProperty(){
        return Bill;
    }
    
    public void setBill(Double x){
        Bill.set(x);
    }
}
