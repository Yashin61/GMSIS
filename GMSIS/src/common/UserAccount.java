/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author Nandhini
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;


public class UserAccount 
{
    private final IntegerProperty id;
    private final StringProperty firstname;
    private final StringProperty surname;
    private final StringProperty password;
    private final IntegerProperty hourly_wage;
    
    public UserAccount(Integer ID, String fn, String sn, String pw, Integer hw, String ut)
    {
        this.id = new SimpleIntegerProperty(ID);
        this.firstname = new SimpleStringProperty(fn);
        this.surname = new SimpleStringProperty(sn);
        this.password = new SimpleStringProperty(pw);
        this.hourly_wage = new SimpleIntegerProperty(hw);
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
    
    public String getPassword()
    {
        return password.get();
    }
    
    public int getHourly_Wage()
    {
        return hourly_wage.get();
    }
    
   
}
