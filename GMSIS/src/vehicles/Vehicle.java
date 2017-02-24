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

    public StringProperty getVehicleTypee() {
        return vehicleType;
    }

    public StringProperty getMakee() {
        return make;
    }

    public IntegerProperty getModell() {
        return model;
    }

    public IntegerProperty getYearr() {
        return year;
    }

    public StringProperty getEngineSizee() {
        return engineSize;
    }

    public StringProperty getFuelTypee() {
        return fuelType;
    }

    public IntegerProperty getMilagee() {
        return milage;
    }

    public StringProperty getColourr() {
        return colour;
    }

    public StringProperty getRegistrationNumberr() {
        return registrationNumber;
    }

    public IntegerProperty getCustomerIDd() {
        return customerID;
    }

    public IntegerProperty getWarrantyIDd() {
        return warrantyID;
    }

    public StringProperty getMOTRenewalDatee() {
        return MOTRenewalDate;
    }

    public StringProperty getLastServiceDatee() {
        return lastServiceDate;
    }

    public StringProperty getDeliveryDatee() {
        return deliveryDate;
    }

    public StringProperty getReturnDatee() {
        return returnDate;
    }
     public String getVehicleType() {
        return vehicleType.get();
    }

    public String getMake() {
        return make.get();
    }

    public int getModel() {
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

    public String getDeliveryDate() {
        return deliveryDate.get();
    }

    public String getReturnDate() {
        return returnDate.get();
    }
    
    public void setVehicleTypee(String i) {
        vehicleType.set(i);
    }

    public void setMakee(String i) {
        make.set(i);
    }

    public void getModell(Integer i) {
        model.set(i);
    }

   /* public IntegerProperty getYearr() {
        return year;
    }

    public StringProperty getEngineSizee() {
        return engineSize;
    }

    public StringProperty getFuelTypee() {
        return fuelType;
    }

    public IntegerProperty getMilagee() {
        return milage;
    }

    public StringProperty getColourr() {
        return colour;
    }

    public StringProperty getRegistrationNumberr() {
        return registrationNumber;
    }

    public IntegerProperty getCustomerIDd() {
        return customerID;
    }

    public IntegerProperty getWarrantyIDd() {
        return warrantyID;
    }

    public StringProperty getMOTRenewalDatee() {
        return MOTRenewalDate;
    }

    public StringProperty getLastServiceDatee() {
        return lastServiceDate;
    }

    public StringProperty getDeliveryDatee() {
        return deliveryDate;
    }

    public StringProperty getReturnDatee() {
        return returnDate;
    }*/
}