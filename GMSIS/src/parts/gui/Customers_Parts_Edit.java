/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

/**
 *
 * @author rohim
 */
public class Customers_Parts_Edit {
   int CustomerID;
   String RegistrationNo;
   int PartsID;
   int BookingID;
   public  Customers_Parts_Edit( String regNo, int pID, int bID){
      
     this. RegistrationNo=regNo;
      this.PartsID=pID;
      this.BookingID=bID; 
 }
 public  Customers_Parts_Edit(int cID, String regNo, int pID, int bID){
      this.CustomerID=cID;
     this. RegistrationNo=regNo;
      this.PartsID=pID;
      this.BookingID=bID; 
 }
   public int getcID(){
       return CustomerID;
   }
   
    public String getregNo(){
       return RegistrationNo;
   }
    
     public int getpID(){
       return PartsID;
   }
     
      public int getbID(){
       return BookingID;
   }
      
      public void setcID(int id){
       CustomerID=id;
   }
        public void setRegNo(String id){
       RegistrationNo=id;
   }
         public void setpID(int id){
          PartsID=id;
   }
          public void setbID(int id){
       BookingID=id;
   }
          
        
           }
