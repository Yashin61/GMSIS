/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.logic;

/**
 *
 * @author Manoharan
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class allCustomers 
{
    
    private final IntegerProperty id;
    private final StringProperty firstname;
    private final StringProperty surname;
    private final StringProperty address;
    private final StringProperty postcode;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty type;
    //private final StringProperty account;
    
    public allCustomers(Integer ID, String fn, String sn, String a, String p, String pn, String e, String at)
    {
        this.id = new SimpleIntegerProperty(ID);
        this.firstname = new SimpleStringProperty(fn);
        this.surname = new SimpleStringProperty(sn);
        this.address = new SimpleStringProperty(a);
        this.postcode = new SimpleStringProperty(p);
        this.phone = new SimpleStringProperty(pn);
        this.email = new SimpleStringProperty(e);
        this.type = new SimpleStringProperty(at);
        
        //this.account = new SimpleStringProperty(at);

    }
    
    public int getID()
    {
        return id.get();
    }
    
    public String getFirstname()
    {
        return firstname.get();
    }
    
    public String getSurname()
    {
        return surname.get();
    }
    
    public String getAddress()
    {
        return address.get();
    }
    
    public String getPostcode()
    {
        return postcode.get();
    }
    
    public String getEmail()
    {
        return email.get();
    }
    
    public String getPhone()
    {
        return phone.get();
    }
    
    public String getAccount()
    {
        return type.get();
    }
    
    public IntegerProperty idProperty()
    {
        return id;
    }
    
    public StringProperty firstnameProperty()
    {
        return firstname;
    }
    
    public StringProperty surnameProperty()
    {
        return surname;
    }
    
    public StringProperty addressProperty()
    {
        return address;
    }
    
    public StringProperty postcodeProperty()
    {
        return postcode;
    }
    
    public StringProperty phoneProperty()
    {
        return phone;
    }
    
    public StringProperty emailProperty()
    {
        return email;
    }
    
    public StringProperty accountProperty()
    {
        return type;
    }
    
    public void setID(Integer v)
    {
        id.set(v);
    }
    
    public void setFirstname(String v)
    {
        firstname.set(v);
    }
    
    public void setSurname(String v)
    {
        surname.set(v);
    }
    
    public void setAddres(String v)
    {
        address.set(v);
    }
    
    public void setPostode(String v)
    {
        postcode.set(v);
    }
    
    public void setEmail(String v)
    {
        email.set(v);
    }
    
    public void setPhone(String v)
    {
        phone.set(v);
    }
    
    public void setAccount(String v)
    {
        type.set(v);
    }
    

}
