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
public class PartsEditController implements Initializable {

    @FXML
    private CheckBox Search_ID_CheckBox;
    @FXML
    private Label Search_ID;
    @FXML
    private TextField txt_Search_By_ID;
    @FXML
    private Button Search_By_ID_btn;
    @FXML
    private Button Search_Firstname;
    @FXML
    private Label lbl_First_search;
    @FXML
    private Label lbl_Surename_search;
    @FXML
    private TextField txt_First_search;
    @FXML
    private TextField txt_Surename_search;
    @FXML
    private Label lbl_firstname_display;
    @FXML
    private Label lbl_surname_display;
    @FXML
    private Label lbl_RN_display;
    @FXML
    private Label lbl_Make_display;
    @FXML
    private Label lbl_colour_display;
    @FXML
    private Label txt_firstname_display;
    @FXML
    private Label txt_Make_display;
    @FXML
    private Label txt_RN_display;
    @FXML
    private Label lbl_model_display;
    @FXML
    private Label txt_colour_display;
    @FXML
    private Label txt_Surname_display;
    @FXML
    private Button btn_delete_By_Part_ID;
    @FXML
    private Button Add_Parts_By_ID;
    @FXML
    private Button Clear;
    @FXML
    private TextField Search_ID_txt;
    @FXML
    private TextField Delete_ID_Part;
    @FXML
    private TextField Add_ID_Part;
    @FXML
    private Label txt_model_display;
    @FXML
    private Label txt_ID_Name;
    @FXML
    private Label txt_ID_Cost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Search_ID_CheckBox(ActionEvent event) {
        if(Search_ID_CheckBox.isSelected()){
            id_Search(false, true);
        }
        else{
            id_Search(true, false);
        }
        
    }

    @FXML
    private void Search_ID(ActionEvent event) {
    }

    @FXML
    private void Search_using_Surname_Firstname(ActionEvent event) {
    }
    
    public void id_Search(boolean t, boolean f){
       Search_ID.setVisible(f);
       txt_Search_By_ID.setVisible(f);
  Search_By_ID_btn.setVisible(f);
   Search_Firstname.setVisible(t);
 lbl_First_search.setVisible(t);
   lbl_Surename_search.setVisible(t);
txt_First_search.setVisible(t);
  txt_Surename_search.setVisible(t);
        
        
    }

    @FXML
    private void DeletePartByID(ActionEvent event) {
        
        
    }

    @FXML
    private void Add_Parts_By_ID(ActionEvent event) {
    }

    @FXML
    private void Clear(ActionEvent event) {
        Search_ID_txt.clear();
            Delete_ID_Part.clear();
    Add_ID_Part.clear();
    txt_First_search.clear();
    txt_Surename_search.clear();
    txt_Search_By_ID.clear();
          txt_ID_Name.setText("");
    txt_ID_Cost.setText("");
txt_model_display.setText("");
txt_colour_display.setText("");
    txt_Surname_display.setText("");
    txt_firstname_display.setText("");
  txt_Make_display.setText("");
  txt_RN_display.setText("");

    }
    
    
}
