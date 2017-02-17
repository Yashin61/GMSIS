/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author rohim
 */
public class PartsAddController implements Initializable {

    @FXML
    private Label New_Part_Name;
    @FXML
    private Label New_Part_Description;
    @FXML
    private Label New_Part_Cost;
    @FXML
    private Label New_Part_Quantity;
    @FXML
    private CheckBox New_Part_Select;
    @FXML
    private TextField txt_New_Part_Name;
    @FXML
    private TextField txt_New_Part_Description;
    @FXML
    private TextField txt_New_Part_Cost;
    @FXML
    private TextField txt_New_Part_Quantity;
    @FXML
    private CheckBox Update_CurrentPart;
    @FXML
    private CheckBox add_Quantity;
    @FXML
    private Label lbl_Search_ID_ID;
    @FXML
    private Label lbl_Search_ID_Name;
    @FXML
    private Label lbl_Search_ID_Description;
    @FXML
    private TextField txt_ID_Search_ID;
    @FXML
    private Label lbl_data_Name;
    @FXML
    private Label lbl_data_Description;
    @FXML
    private Button Search_Part_by_ID_btn;
    @FXML
    private Label lbl_Search_ID_QTY;
    @FXML
    private TextField txt_Search_ID_QTY;
    @FXML
    private Button btn_Add_Part_by_ID;
    @FXML
    private Button Add_Quantity_btn;
    @FXML
    private Button BAck_btn;
    @FXML
    private Button btn_Clear;

    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    public void New_Part_Choice(ActionEvent event) {
        if(New_Part_Select.isSelected()){
            Visibility_Curent_Parts_Update(false);
           Visibility_New_Parts(true);
           add_Quantity.setSelected(false);
           Update_CurrentPart.setSelected(false);
           lbl_Search_ID_ID.setVisible(false);
                   txt_ID_Search_ID.setVisible(false);
                   Search_Part_by_ID_btn.setVisible(false);
                   lbl_Search_ID_ID.setVisible(false);
        }
    }
    @FXML
    public void Update_Currrent_Part_V(ActionEvent event) {
         if(Update_CurrentPart.isSelected()){
           Visibility_Curent_Parts_Update(false);
           Visibility_New_Parts(true);
           add_Quantity.setSelected(false);
           New_Part_Select.setSelected(false);
            txt_ID_Search_ID.setVisible(true);
                   Search_Part_by_ID_btn.setVisible(true);
                   lbl_Search_ID_ID.setVisible(true);
        }
    }
    public void add_quantity_V(ActionEvent event) {
         if(add_Quantity.isSelected()){
           Visibility_Curent_Parts_Update(true);
           Visibility_New_Parts(false);
                   New_Part_Select.setSelected(false); 
           Update_CurrentPart.setSelected(false);
        }
        
    }

    
    public void Search_Part(ActionEvent event) {
        
    }
     public void Visibility_Curent_Parts_Update(boolean t){
         

  lbl_Search_ID_Name.setVisible(t);
   lbl_Search_ID_Description.setVisible(t);
    lbl_data_Name.setVisible(t);
 lbl_data_Description.setVisible(t);
lbl_Search_ID_QTY.setVisible(t);
 txt_Search_ID_QTY.setVisible(t);
   btn_Add_Part_by_ID.setVisible(t); 
    }
    
      public void Visibility_New_Parts(boolean t){
         New_Part_Name.setVisible(t);
            New_Part_Description.setVisible(t);
            New_Part_Cost.setVisible(t);
            New_Part_Quantity.setVisible(t);
            txt_New_Part_Name.setVisible(t);
            txt_New_Part_Description.setVisible(t);
            txt_New_Part_Cost.setVisible(t);
            txt_New_Part_Quantity.setVisible(t);
   Add_Quantity_btn.setVisible(t);
            
    }

  
 
    public void close(ActionEvent event) {
    }
  
   public void Clear_Add_Page(ActionEvent event) {
            txt_Search_ID_QTY.clear();
            txt_ID_Search_ID.clear();
             txt_New_Part_Name.clear();
    txt_New_Part_Description.clear();
    txt_New_Part_Cost.clear();
    txt_New_Part_Quantity.clear();
    lbl_data_Name.setText("");
    lbl_data_Description.setText("");
    }
}
