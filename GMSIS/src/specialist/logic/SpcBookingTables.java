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

    private final StringProperty make;
    private final StringProperty model;
    private final StringProperty regNo;
    private final IntegerProperty mileage;
    
    public SpcBookingTables(String make, String model, String reg, int mileage)
    {
        this.make = new SimpleStringProperty(make);
        this.model= new SimpleStringProperty(model);
        this.regNo = new SimpleStringProperty(reg);
        this.mileage = new SimpleIntegerProperty(mileage);
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
}
