/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

/**
 *
 * @author Manoharan
 */
public class createCustomer {
    
    private int ID;
    private String firstname;
    private String surname;
    private String address;
    private String postcode;
    private String email;
    private String phone;
    
    public createCustomer(int id, String fn, String sn, String a, String p, String e, String pn)
    {
        ID = id;
        firstname = fn;
        surname = sn;
        address = a;
        postcode = p;
        email = e;
        phone = pn;
    }
    
    
    
}
