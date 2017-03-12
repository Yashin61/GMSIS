/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;

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
class CustomerTable {
    private final StringProperty CustFN;
    private final StringProperty CustSN;
    
    public CustomerTable(String CustFN, String CustSN) {
        this.CustFN = new SimpleStringProperty(CustFN);
        this.CustSN = new SimpleStringProperty(CustSN);
    }
    
    /******Customer First Name******/
    public String getFirstName(){
        return CustFN.get();
    }
    
    public StringProperty FirstNameProperty(){
        return CustFN;
    }
    
    public void setFirstName(String x){
        CustFN.set(x);
    }
    
    /******Customer Surname******/
    public String getSurname(){
        return CustSN.get();
    }
    
    public StringProperty SurnameProperty(){
        return CustSN;
    }
    
    public void setSurname(String x){
        CustSN.set(x);
    }
}
