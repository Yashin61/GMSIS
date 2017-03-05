// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vehicle
{
    private StringProperty vehicleType;
    private StringProperty make;
    private StringProperty model;
    private IntegerProperty year;
    private StringProperty engineSize;
    private StringProperty fuelType;
    private IntegerProperty milage;
    private StringProperty colour;
    private StringProperty registrationNumber;
    private IntegerProperty customerID;
    private IntegerProperty warrantyID;
    private StringProperty MOTRenewalDate;
    private StringProperty lastServiceDate;
    
    public Vehicle(String vehicleTypeX, String makeX, String modelX, int yearX, String engineSizeX, 
            String fuelTypeX, int milageX, String colourX, String registrationNumberX, 
            int customerIDX, int warrantyIDX, String MOTRenewalDateX, 
            String lastServiceDateX)
    {
        vehicleType=new SimpleStringProperty(vehicleTypeX);
        make=new SimpleStringProperty(makeX);
        model=new SimpleStringProperty(modelX);
        year=new SimpleIntegerProperty(yearX);
        engineSize=new SimpleStringProperty(engineSizeX);
        fuelType=new SimpleStringProperty(fuelTypeX);
        milage=new SimpleIntegerProperty(milageX);
        colour=new SimpleStringProperty(colourX);
        registrationNumber=new SimpleStringProperty(registrationNumberX);
        customerID=new SimpleIntegerProperty(customerIDX);
        warrantyID=new SimpleIntegerProperty(warrantyIDX);
        MOTRenewalDate=new SimpleStringProperty(MOTRenewalDateX);
        lastServiceDate=new SimpleStringProperty(lastServiceDateX);
    }

    public StringProperty getVehicleType1() {
        return vehicleType;
    }
    public StringProperty getMake1() {
        return make;
    }
    public StringProperty getModel1() {
        return model;
    }
    public IntegerProperty getYear1() {
        return year;
    }
    public StringProperty getEngineSize1() {
        return engineSize;
    }
    public StringProperty getFuelType1() {
        return fuelType;
    }
    public IntegerProperty getMilage1() {
        return milage;
    }
    public StringProperty getColour1() {
        return colour;
    }
    public StringProperty getRegistrationNumber1() {
        return registrationNumber;
    }
    public IntegerProperty getCustomerID1() {
        return customerID;
    }
    public IntegerProperty getWarrantyID1() {
        return warrantyID;
    }
    public StringProperty getMOTRenewalDate1() {
        return MOTRenewalDate;
    }
    public StringProperty getLastServiceDate1() {
        return lastServiceDate;
    }
    
    public String getVehicleType() {
        return vehicleType.get();
    }
    public String getMake() {
        return make.get();
    }
    public String getModel() {
        return model.get();
    }
    public int getYear() {
        return year.get();
    }
    public String getEngineSize() {
        return engineSize.get();
    }
    public String getFuelType() {
        return fuelType.get();
    }
    public int getMilage() {
        return milage.get();
    }
    public String getColour() {
        return colour.get();
    }
    public String getRegistrationNumber() {
        return registrationNumber.get();
    }
    public int getCustomerID() {
        return customerID.get();
    }
    public int getWarrantyID() {
        return warrantyID.get();
    }
    public String getMOTRenewalDate() {
        return MOTRenewalDate.get();
    }
    public String getLastServiceDate() {
        return lastServiceDate.get();
    }
    
    public void setVehicleType(String i) {
        vehicleType.set(i);
    }
    public void setMake(String i) {
        make.set(i);
    }
    public void setModel(String i) {
        model.set(i);
    }
    public void setYear(Integer i) {
        year.set(i);
    }
    public void setEngineSize(String i) {
        engineSize.set(i);
    }
    public void setFuelType(String i) {
        fuelType.set(i);
    }
    public void setMilage(Integer i) {
        milage.set(i);
    }
    public void setColour(String i) {
        colour.set(i);
    }
    public void setRegistrationNumber(String i) {
        registrationNumber.set(i);
    }
    public void setCustomerID(Integer i) {
        customerID.set(i);
    }
    public void setWarrantyID(Integer i) {
        warrantyID.set(i);
    }
    public void setMOTRenewalDate(String i) {
        MOTRenewalDate.set(i);
    }
    public void setLastServiceDate(String i) {
        lastServiceDate.set(i);
    }
}
//