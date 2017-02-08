/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Mustakim
 */
public class Database {

    /**
     * @param args the command line arguments
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/Mustakim/NetBeansProjects/NBProjects/GMSIS/GMSIS/RecordsTest.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private void select(){
        String sql = "SELECT * FROM Booking";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("RepairTime") +  "\t" + 
                                   rs.getString("BookingType") + "\t" +
                                   rs.getFloat("Bill") + "\t" +
                                   rs.getString("RegistrationNumber") + "\t" +
                                   rs.getString("BookingTime"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database app = new Database();
        app.select();
    }
}
    
