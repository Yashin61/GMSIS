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
    private IntegerProperty model;
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
    private StringProperty deliveryDate;
    private StringProperty returnDate;
    
    public Vehicle(String vehicleTypeX, String makeX, int modelX, int yearX, String engineSizeX, 
            String fuelTypeX, int milageX, String colourX, String registrationNumberX, 
            int customerIDX, int warrantyIDX, String MOTRenewalDateX, 
            String lastServiceDateX, String deliveryDateX, String returnDateX)
    {
        vehicleType=new SimpleStringProperty(vehicleTypeX);
        make=new SimpleStringProperty(makeX);
        model=new SimpleIntegerProperty(modelX);
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
        deliveryDate=new SimpleStringProperty(deliveryDateX);
        returnDate=new SimpleStringProperty(returnDateX);
    }

    public StringProperty getVehicleType1() {
        return vehicleType;
    }
    public StringProperty getMake1() {
        return make;
    }
    public IntegerProperty getModel1() {
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
    public StringProperty getDeliveryDate1() {
        return deliveryDate;
    }
    public StringProperty getReturnDate1() {
        return returnDate;
    }
    
    public String getVehicleType2() {
        return vehicleType.get();
    }
    public String getMake2() {
        return make.get();
    }
    public int getModel2() {
        return model.get();
    }
    public int getYear2() {
        return year.get();
    }
    public String getEngineSize2() {
        return engineSize.get();
    }
    public String getFuelType2() {
        return fuelType.get();
    }
    public int getMilage2() {
        return milage.get();
    }
    public String getColour2() {
        return colour.get();
    }
    public String getRegistrationNumber2() {
        return registrationNumber.get();
    }
    public int getCustomerID2() {
        return customerID.get();
    }
    public int getWarrantyID2() {
        return warrantyID.get();
    }
    public String getMOTRenewalDate2() {
        return MOTRenewalDate.get();
    }
    public String getLastServiceDate2() {
        return lastServiceDate.get();
    }
    public String getDeliveryDate2() {
        return deliveryDate.get();
    }
    public String getReturnDate2() {
        return returnDate.get();
    }
    
    public void setVehicleType(String i) {
        vehicleType.set(i);
    }

    public void setMake(String i) {
        make.set(i);
    }

    public void setModel(Integer i) {
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

    public void setDeliveryDate(String i) {
        deliveryDate.set(i);
    }

    public void setReturnDate(String i) {
        returnDate.set(i);
    }
}