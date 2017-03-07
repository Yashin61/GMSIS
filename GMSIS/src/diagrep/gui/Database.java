/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;

import diagrep.*;
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
        String url = "jdbc:sqlite:src/common/Records.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private void selectBooking(){
        String sql = "SELECT * FROM Booking";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("BookingID") +  "\t" + 
                                   rs.getString("RegistrationNumber") + "\t" +
                                   rs.getString("BookingType") + "\t" +
                                   rs.getInt("MechanicID") + "\t" +
                                   rs.getString("BookingDate") + "\t" +
                                   rs.getString("BookingTime") + "\t" +
                                   rs.getString("RepairTime") + "\t" +
                                   rs.getFloat("Bill"));
                conn.close();
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
        app.selectBooking();
    }
}
    
