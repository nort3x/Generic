package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectAble implements Initializable {
    @FXML
    RadioButton btn_select;
    @FXML Text txt_txt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setAnswer(String answer){
        txt_txt.setText(answer);
    }

    public boolean isSelected(){
        return btn_select.isSelected();
    }
}
