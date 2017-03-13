/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private final IntegerProperty SpcCustomerID;
    private final IntegerProperty SpcRepairID;
    private final StringProperty SpcWorkOn;
    
    public SpcBookings(Integer id, String name, String dDate, Integer arrived, String rDate, Integer returned, Integer parts, Integer cust, Integer repair, String workOn)
    {
        this.SpcBookingid = new SimpleIntegerProperty(id);
        this.SpcBookingname = new SimpleStringProperty(name);
        this.SpcDeliveryDate = new SimpleStringProperty(dDate);;
        this.SpcArrived = new SimpleIntegerProperty(arrived);
        this.SpcReturnDate = new SimpleStringProperty(rDate);;
        this.SpcReturned = new SimpleIntegerProperty(returned);
        this.SpcPartID = new SimpleIntegerProperty(parts);
        this.SpcCustomerID = new SimpleIntegerProperty(cust);
        this.SpcRepairID = new SimpleIntegerProperty(repair);
        this.SpcWorkOn = new SimpleStringProperty(workOn);      
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
    
    public String getSpcWOn() {
        return SpcWorkOn.get();
    }
    
    public IntegerProperty getSpcBookingid() {
        return SpcBookingid;
    }

    public StringProperty getSpcBookingname() {
        return SpcBookingname;
    }

    public StringProperty getSpcDeliveryDate() {
        return SpcDeliveryDate;
    }

    public IntegerProperty getSpcArrived() {
        return SpcArrived;
    }

    public StringProperty getSpcReturnDate() {
        return SpcReturnDate;
    }

    public IntegerProperty getSpcReturned() {
        return SpcReturned;
    }

    public IntegerProperty getSpcPartID() {
        return SpcPartID;
    }

    public IntegerProperty getSpcCustomerID() {
        return SpcCustomerID;
    }

    public IntegerProperty getSpcRepairID() {
        return SpcRepairID;
    }

    public StringProperty getSpcWorkOn() {
        return SpcWorkOn;
    }

    private static class StringPoperty {
        public StringPoperty() {}
    }
}