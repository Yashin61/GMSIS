/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.util.Calendar;
import java.util.List;
/**
 *
 * @author prashant
 */
public class Main 
{
    private int id;
    private String name, address, phone;
    
    public int getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String n){
        this.name = n;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String a){
        this.address = a;
    } 

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String p){
        this.phone = p;
    }
}
