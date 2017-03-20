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
public class SpcBookingTables {

    private StringProperty make;
    private StringProperty model;
    private StringProperty regNo;
    private IntegerProperty mileage;
    private IntegerProperty partId;
    private StringProperty partName;
    
    public SpcBookingTables(String make, String model, String reg, int mileage)
    {
        this.make = new SimpleStringProperty(make);
        this.model= new SimpleStringProperty(model);
        this.regNo = new SimpleStringProperty(reg);
        this.mileage = new SimpleIntegerProperty(mileage);
    }
    
    public SpcBookingTables(int partId, String partName)
    {
        this.partId = new SimpleIntegerProperty(partId);
        this.partName = new SimpleStringProperty(partName);
    }
    
    public String getMake(){
        return make.get();
    }
    
    public String getModel(){
        return model.get();
    }
    
    public String getRegNo(){
        return regNo.get();
    }
    
    public int getMileage(){
        return mileage.get();
    }
    
    public int getPartId(){
        return partId.get();
    }
    
    public String getPartName(){
        return partName.get();
    }
    
    public StringProperty getMakeProperty() {
        return make;
    }

    public StringProperty getModelProperty() {
        return model;
    }

    public StringProperty getRegNoProperty() {
        return regNo;
    }

    public IntegerProperty getMileageProperty() {
        return mileage;
    }
    
    public IntegerProperty getPartIdProperty() {
        return partId;
    }

    public StringProperty getPartNameProperty() {
        return partName;
    }
}
