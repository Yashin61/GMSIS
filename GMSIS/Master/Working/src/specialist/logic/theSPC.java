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
public class theSPC 
{
    private final IntegerProperty SPCid;
    private final StringProperty SPCname;
    private final StringProperty SPCaddress;
    private final StringProperty SPCphone;
    private final StringProperty SPCemail;
    
    public theSPC(Integer id, String name, String address, String phone, String email)
    {
        this.SPCid = new SimpleIntegerProperty(id);
        this.SPCname = new SimpleStringProperty(name);
        this.SPCaddress = new SimpleStringProperty(address);
        this.SPCphone = new SimpleStringProperty(phone);
        this.SPCemail = new SimpleStringProperty(email);
        
    }

    public int getSPCid() {
        return SPCid.get();
    }

    public String getSPCname() {
        return SPCname.get();
    }

    public String getSPCaddress() {
        return SPCaddress.get();
    }

    public String getSPCphone() {
        return SPCphone.get();
    }

    public String getSPCemail() {
        return SPCemail.get();
    }
    
    public IntegerProperty idProperty()
    {
        return SPCid;
    }
    
    public StringProperty nameProperty()
    {
        return SPCname;
    }
    
    public StringProperty addressProperty()
    {
        return SPCaddress;
    }
    
    public StringProperty phoneProperty()
    {
        return SPCphone;
    }
    
    public StringProperty emailProperty()
    {
        return SPCemail;
    }
    public void setSPCid(Integer id)
    {
        SPCid.set(id);
    }
    
    public void setSPCname(String name)
    {
        SPCname.set(name);
    }
    
    public void setSPCaddress(String address)
    {
        SPCaddress.set(address);
    }
    
    public void setSPCphone(String phone)
    {
        SPCphone.set(phone);
    }
    
    public void setSPCemail(String email)
    {
        SPCemail.set(email);
    }

    private static class StringPoperty {
        public StringPoperty() {}
    }
}