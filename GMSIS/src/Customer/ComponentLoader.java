/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customer;

/**
 *
 * @author Manoharan
 */
public class ComponentLoader {
    
    private String firstname;
    private String surname;
    private String address;
    private String postcode;
    private String phone;
    private String email;
    private boolean check;
    
    public ComponentLoader(String fName, String lName, String addr, String pCode, String phone_no, String email_addr)
    {
        firstname = fName;
        surname = lName;
        address = addr;
        postcode = pCode;
        phone = phone_no;
        email = email_addr;
    }   
    
    //public static void makeBooking(Vehicle vehicle)
  
        // linked to Diagnostic and Repairs
    //}
    
    public static void payBill()
    {
    
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println("HELLO");
    }
    
}
