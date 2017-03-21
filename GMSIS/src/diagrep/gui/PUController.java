/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.gui;

import diagrep.logic.BookingTable;
import diagrep.logic.Database;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Mustakim
 */
public class PUController implements Initializable {

    private static BookingTable book;
    
    @FXML
    private ListView<String> mainFrame = new ListView<String>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void setBooking(BookingTable b)
    {
        book = b;
        int BID = b.getBookingID();
        
        Connection connect = null;
        PreparedStatement stmt = null;
        try
        {   
            connect = DriverManager.getConnection("jdbc:sqlite:src/common/Records.db");
            ObservableList<String> data = FXCollections.observableArrayList();
            int PID = 0;
            String sql = "SELECT * FROM PartsUsed WHERE BookingID = '"+BID+"'";
            stmt = connect.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                PID = rs.getInt(2);
                
                String sql1 = "SELECT * FROM Parts WHERE ID = '"+PID+"'";
                stmt = connect.prepareStatement(sql1);
                ResultSet rs1 = stmt.executeQuery();
                while(rs1.next())
                {
                    data.add(rs1.getString(2));
                }
                stmt.close();
                rs1.close();
            }
            stmt.close();
            rs.close();

            connect.close();
            
            mainFrame.setItems(data);
        }
        catch(SQLException e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
