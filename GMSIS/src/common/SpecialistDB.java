/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author prashant
 */
public class SpecialistDB 
{
    private List<Object> rList = new ArrayList<Object>();
    private ArrayList SList = new ArrayList();
    private Object[][] rowData;
    private Object[] cols;
    private int rowsLength;
    private String[] SPCList;
    String Recordurl = "JDBC:sqlite:Records.db";
    
    public void addSPC(Object name)
    {
        
        Connection conn = null;
        Statement stmt = null;
        
        try
        { 
            conn = DriverManager.getConnection(Recordurl);

            stmt = conn.createStatement();
            String sql = "INSERT INTO SPC (SPCname) VALUES (" + "'" + name + "');"; 

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "A new SPC has been added. \n" + 
                    "Remember to input it's details!");

            stmt.close();
            conn.close();
        }catch(SQLException e)
        {
             JOptionPane.showMessageDialog(null, "Failed to add a new SPC");
        }
    }
    
    public void editSupplier(Object id, Object information, int row, int col)
    {
        
        Connection connect = null;
        Statement stmt = null;
      
        try
        {
            if((id==null) && (col==1))
            {
                this.addSPC(information);
            } 
            else
            {    
                String tableColumn ="";
                if(col==1){ tableColumn = "SPCname";}
                else if(col==2){ tableColumn =  "SPCaddress"; }
                else if(col==3){ tableColumn = "SPCphone"; }
                else if(col==4){ tableColumn = "SPCemail"; }
                connect = DriverManager.getConnection(Recordurl);
                stmt = connect.createStatement();
                String sql = "UPDATE SPC SET "+ tableColumn + " = '" + information + "' "+ "WHERE SPCId = " + id + " ;";

                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "SPC list has been updated");

                stmt.close();
                connect.close();  
            }
        }catch(SQLException e)
        {
           JOptionPane.showMessageDialog(null, "Update Failed");
        }
    }
    
    public void deleteSPC(Object id)
    {

        Connection connect = null;
        Statement stmt = null;

        try
        {   
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            String sql = "DELETE FROM SPC WHERE SPCId = " + id + ";";

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "SPC has been deleted."); 

            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Failed to delete SPC.");
        }
    }
    
    public String[] getSPC()
    {
        
        Connection connect = null;
        Statement stmt = null;      
        
        try
        {
            //Connect to database 
            connect = DriverManager.getConnection(Recordurl);
            stmt = connect.createStatement();
            ResultSet set = stmt.executeQuery("SELECT SPCname FROM SPC");

            while(set.next()){
                SList.add(set.getString(1)); 
            }

            SPCList = (String[]) SList.toArray(new String[SList.size()]);

            stmt.close();
            set.close();
            connect.close();

            return SPCList;

          }catch(SQLException e)
          {
              JOptionPane.showMessageDialog(null, "SPC cannot be located");   
          } 
          return SPCList;
    }
}
