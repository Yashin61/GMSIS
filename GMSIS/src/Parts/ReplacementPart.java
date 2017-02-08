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

public class ReplacementPart extends Partss{
    String dateInstalled;
    String dateWarranty;
    ReplacementPart(int pid){
        super(pid);
        //set to current date
        dateInstalled=setDate();
        dateWarranty=setDate();
        
    }
    public String setDate(){
        
    
    return null;
    }
}

