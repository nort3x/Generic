package controller;

import basics.AnswerNode;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import objects.EntekhabiQuestion;
import runner.MainUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EntekhabiCell extends AnswerNode implements Initializable {
    public VBox select_box;
    public Text txt_description;

    public EntekhabiCell(){
        FXMLLoader loader = new FXMLLoader(MainUI.class.getClassLoader().getResource("entekhabi.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    List<SelectAble> controllers = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public boolean onFinalAnswerCalled() {
        ArrayList<Integer> providedInt = new ArrayList<>();
        controllers.forEach(x->{
            if(x.isSelected())
                providedInt.add(controllers.indexOf(x));
        });
        Collections.sort(providedInt);
        return correctAnswers.equals(providedInt);
    }

    List<Integer> correctAnswers;
    public void consume(EntekhabiQuestion entekhabiQuestion) {

        correctAnswers =  entekhabiQuestion.getCorrectItems();
        txt_description.setText(entekhabiQuestion.provideQuestionDescription());

        entekhabiQuestion.getAnswers().forEach((x)->{
            try {
                FXMLLoader loader = new FXMLLoader(MainUI.class.getClassLoader().getResource("selectable.fxml"));
                 Node box = loader.load();
                select_box.getChildren().add(box);
                controllers.add(loader.getController());
            }catch (Exception ignored){
                ignored.printStackTrace();
            }
        });
    }
}
