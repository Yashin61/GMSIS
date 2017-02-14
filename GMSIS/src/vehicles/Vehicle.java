/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles;

/**
 *
 * @author yhk30
 */
public class Vehicle
{
    private String vehicleType;
    private String make;
    private int model;
    private int year;
    private String engineSize;
    private String fuelType;
    private int milage;
    private String colour;
    private String registrationNumber;
    private int customerID;
    private String warranty;
    private String MOTRenewalDate;
    private String lastServiceDate;
    private String deliveryDate;
    private String returnDate;
    
    public Vehicle(String vehicleTypeX, int modelX, int yearX, String engineSizeX, String fuelTypeX, int milageX, String colourX, String registrationNumberX, int customerIDX, String warrantyX, String MOTRenewalDateX, String lastServiceDateX, String deliveryDateX, String returnDateX)
    {
        vehicleType=vehicleTypeX;
        model=modelX;
        year=yearX;
        engineSize=engineSizeX;
        fuelType=fuelTypeX;
        milage=milageX;
        colour=colourX;
        registrationNumber=registrationNumberX;
        customerID=customerIDX;
        warranty=warrantyX;
        MOTRenewalDate=MOTRenewalDateX;
        lastServiceDate=lastServiceDateX;
        deliveryDate=deliveryDateX;
        returnDate=returnDateX;
    }
    
    public String getVehicleType()
    {
        return vehicleType;
    }
    public String getMake()
    {
        return make;
    }
    public int getModel()
    {
        return model;
    }
    public int getYear()
    {
        return year;
    }
    public String getEngineSize()
    {
        return engineSize;
    }
    public String getFuelType()
    {
        return fuelType;
    }
    public int getMilage()
    {
        return milage;
    }
    public String getColour()
    {
        return colour;
    }
    public String getRegistrationNumber()
    {
        return registrationNumber;
    }
    public int getCustomerID()
    {
        return customerID;
    }
    public String getWarranty()
    {
        return warranty;
    }
    public String getMOTRenewalDate()
    {
        return MOTRenewalDate;
    }
    public String getLastServiceDate()
    {
        return lastServiceDate;
    }
    public String getDeliveryDate()
    {
        return deliveryDate;
    }
    public String getReturnDate()
    {
        return returnDate;
    }
} 
