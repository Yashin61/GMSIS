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
    
    public Vehicle(String vehicleTypeX, int modelX, int yearX, String engineSizeX, String fuelTypeX, int milageX, String colourX, String registrationNumberX, int customerIDX, String warrantyX, String MOTRenewalDateX, String lastServiceDateX, String deliveryDateX, String returnDate)
    {
        vehicleType=vehicleTypeX;
        
    }
}
