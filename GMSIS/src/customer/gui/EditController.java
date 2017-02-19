/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.gui;

import customer.logic.allCustomers;
import customer.logic.customers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Manoharan
 */
public class EditController implements Initializable
{
    
    @FXML
    private TextField firstname;

    @FXML
    private TextField surname;

    @FXML
    private TextField address;

    @FXML
    private TextField postcode;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private RadioButton private_type;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton business_type;

    @FXML
    private TextField ID;
    
    private String fn;
    private String sn;
    private String a;
    private String p;
    private String pn;
    private String e;
    
    public void initialize(URL url, ResourceBundle rb)
    {
        firstname.setText(fn);
        surname.setText(sn);
        address.setText(a);
        postcode.setText(p);
        phone.setText(pn);
        email.setText(e);
    }
    
    public void initData(allCustomers cust)
    {
        fn = cust.getFirstname();
        sn = cust.getSurname();
        a = cust.getAddress();
        p = cust.getPostcode();
        pn = cust.getPhone();
        e = cust.getEmail();
                
    }
    
}
