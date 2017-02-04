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
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class ReplacementPart extends Parts{
    String dateInstalled;
    String dateWarranty;
    ReplacementPart(String pname, String pdescription, int pid, int pqty, int pcost){
        super(pname,pdescription, pid, pqty, pcost);
        //set to current date
        dateInstalled="date";
        dateWarranty="date";
        
    }
    
   
}

