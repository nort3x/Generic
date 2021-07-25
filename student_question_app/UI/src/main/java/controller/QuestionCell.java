package controller;

import basics.AnswerNode;
import facades.QuestionFacadeAdaptor;
import interfaces.QuestionVisitor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import objects.CharGozineii;
import objects.EntekhabiAnswer;
import objects.EntekhabiQuestion;
import runner.MainUI;

import java.io.IOException;
import java.util.function.Consumer;

public class QuestionCell extends ListCell<QuestionFacadeAdaptor> {

    ListView<QuestionFacadeAdaptor> param;
    public QuestionCell(ListView<QuestionFacadeAdaptor> param) {
        this.param  = param;
    }

    @Override
    protected void updateItem(QuestionFacadeAdaptor item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            this.setText("Question: "+param.getItems().indexOf(item));
        }else {
            this.setText(null);
            this.setGraphic(null);
        }
    }


}
