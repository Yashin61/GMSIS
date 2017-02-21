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
        SPCid = new SimpleIntegerProperty(id);
        SPCname = new SimpleStringProperty(name);
        SPCaddress = new SimpleStringProperty(address);
        SPCphone = new SimpleStringProperty(phone);
        SPCemail = new SimpleStringProperty(email);
        
    }

    public IntegerProperty getSPCid() {
        return SPCid;
    }

    public StringProperty getSPCname() {
        return SPCname;
    }

    public StringProperty getSPCaddress() {
        return SPCaddress;
    }

    public StringProperty getSPCphone() {
        return SPCphone;
    }

    public StringProperty getSPCemail() {
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
}
