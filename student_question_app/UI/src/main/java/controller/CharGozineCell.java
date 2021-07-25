package controller;

import basics.AnswerNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import objects.CharGozineii;
import runner.MainUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CharGozineCell extends AnswerNode implements Initializable {

    @FXML
    private Text txt_description;

    @FXML
    private RadioButton btn_1;

    @FXML
    private Text txt_1;

    @FXML
    private RadioButton btn_2;

    @FXML
    private Text txt_2;

    @FXML
    private RadioButton btn_3;

    @FXML
    private Text txt_3;

    @FXML
    private RadioButton btn_4;

    @FXML
    private Text txt_4;

    public CharGozineCell(){
        FXMLLoader loader = new FXMLLoader(MainUI.class.getClassLoader().getResource("char_gozine.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_1.setOnAction(new MyEvent(btn_1));
        btn_2.setOnAction(new MyEvent(btn_2));
        btn_3.setOnAction(new MyEvent(btn_3));
        btn_4.setOnAction(new MyEvent(btn_4));
    }

    @Override
    public boolean onFinalAnswerCalled() {

        if(correctAnswer==1)
            return btn_1.isSelected();
        else if(correctAnswer==2)
            return btn_2.isSelected();
        else if(correctAnswer==3)
            return btn_3.isSelected();
        else if(correctAnswer==4)
            return btn_4.isSelected();

        else return false;
    }

    class MyEvent implements EventHandler<ActionEvent>{
        private final RadioButton btn;
        public MyEvent(RadioButton btn){
            this.btn = btn;
        }
        @Override
        public void handle(ActionEvent event) {
            if(btn != btn_1)
                btn_1.setSelected(false);
            if(btn != btn_2)
                btn_2.setSelected(false);
            if(btn != btn_3)
                btn_3.setSelected(false);
            if(btn != btn_4)
                btn_4.setSelected(false);
        }
    }

    Integer correctAnswer;
    public void consume(CharGozineii charGozineii) {

        txt_description.setText(charGozineii.provideQuestionDescription());
        List<String> answers = charGozineii.providedAnswers();

        correctAnswer = charGozineii.getCorrectChoice();
        txt_1.setText(answers.get(0));
        txt_2.setText(answers.get(1));
        txt_3.setText(answers.get(2));
        txt_4.setText(answers.get(3));

    }
}
