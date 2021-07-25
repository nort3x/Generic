package controller;

import basics.AnswerNode;
import interfaces.QuestionVisitor;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import objects.CharGozineii;
import objects.EntekhabiQuestion;
import runner.MainUI;

import java.io.IOException;
import java.util.function.Consumer;

public class QuestionNode extends VBox implements QuestionVisitor {


    Consumer<Boolean> qAnswered;
    AnswerNode node;

    public QuestionNode(Consumer<Boolean> qAnswered) {
        this.qAnswered = qAnswered;
        this.setAlignment(Pos.TOP_CENTER);
        this.setVgrow(this,Priority.ALWAYS);
    }

    @Override
    public void visit(EntekhabiQuestion entekhabiQuestion) {

            EntekhabiCell controller =new EntekhabiCell();
            controller.consume(entekhabiQuestion);
            node = controller;
            node.consumeAnswerSensor(qAnswered);
            this.getChildren().clear();
            this.getChildren().add(controller);
            VBox.setVgrow(controller, Priority.ALWAYS);

    }

    @Override
    public void visit(CharGozineii charGozineii) {


            CharGozineCell controller = new CharGozineCell();
            controller.consume(charGozineii);
            node = controller;
            node.consumeAnswerSensor(qAnswered);
            this.getChildren().clear();
            this.getChildren().add(controller);
            VBox.setVgrow(controller, Priority.ALWAYS);
    }

    public AnswerNode getNode(){
        return node;
    }
}
