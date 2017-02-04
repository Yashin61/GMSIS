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
public class Parts {
    String name;
    String description;
    int ID;
    int qty;
    int cost;
    
    public Parts(String pname, String pdescription, int pid, int pqty, int pcost){
    name=pname;
    description = pdescription;
    ID = pid;
    //update in database then assign pqty
    qty = pqty;
    cost = pcost;
    }
    
    void addStock(int newqty){
        qty= qty+newqty;
    }
    
    void partWithdraw(){
       qty=qty-1; 
    }

 
 }
