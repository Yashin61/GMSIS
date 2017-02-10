
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts;
/**
 *
 * @author rohim
 */

public class Partss {
 
    int ID;
   
    
    public Partss(int pid){
    
    ID = pid;
    
    }
    
    public void addStock(int newqty){
        ConnectionToParts c = new ConnectionToParts();
        
        c.add(ID,newqty);
          //  qty= qty+newqty;
    }
    
    public void partWithdraw(){
       //qty=qty-1; 
    }

    
    public void SeachByID(){
        ConnectionToParts con = new ConnectionToParts();
        con.IDSearch(ID);
    }
 

 
 }
