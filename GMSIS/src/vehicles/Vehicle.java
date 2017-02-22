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

    public StringProperty getVehicleType() {
        return vehicleType;
    }

    public StringProperty getMake() {
        return make;
    }

    public IntegerProperty getModel() {
        return model;
    }

    public IntegerProperty getYear() {
        return year;
    }

    public StringProperty getEngineSize() {
        return engineSize;
    }

    public StringProperty getFuelType() {
        return fuelType;
    }

    public IntegerProperty getMilage() {
        return milage;
    }

    public StringProperty getColour() {
        return colour;
    }

    public StringProperty getRegistrationNumber() {
        return registrationNumber;
    }

    public IntegerProperty getCustomerID() {
        return customerID;
    }

    public IntegerProperty getWarrantyID() {
        return warrantyID;
    }

    public StringProperty getMOTRenewalDate() {
        return MOTRenewalDate;
    }

    public StringProperty getLastServiceDate() {
        return lastServiceDate;
    }

    public StringProperty getDeliveryDate() {
        return deliveryDate;
    }

    public StringProperty getReturnDate() {
        return returnDate;
    }
}