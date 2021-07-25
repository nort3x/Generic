package controller;

import exceptions.AlreadyExist;
import facades.QuestionFacadeAdaptor;
import interfaces.IDataBase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;
import objects.Exam;
import objects.Student;
import runner.MainUI;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ExamController implements Initializable {


    @FXML
    private ListView<QuestionFacadeAdaptor> list_q;

    @FXML
    private VBox fillBox;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_setAnswer;

    @FXML
    private Button btn_end;

    Consumer<Boolean> tashviq = new Consumer<Boolean>() {
        @Override
        public void accept(Boolean aBoolean) {
            if(aBoolean){
                int i = new Random().nextInt(3);
                Media m;
                if (i == 0)
                    m = new Media(MainUI.class.getClassLoader().getResource("0.mp3").toString());
                else if (i == 1)
                    m = new Media(MainUI.class.getClassLoader().getResource("1.mp3").toString());
                else
                    m = new Media(MainUI.class.getClassLoader().getResource("2.mp3").toString());

                MediaPlayer mp = new MediaPlayer(m);
                mp.play();
            }
            showNextQuestionOrFinish();
        }
    };

    QuestionNode qn  = new QuestionNode(tashviq);
    Student s;
    IDataBase db;
    Exam e;

    Map<QuestionFacadeAdaptor,Boolean> answered = new HashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_setAnswer.setOnAction(e->{
            Node n = fillBox.getChildren().get(0);
            if(n instanceof QuestionNode nn){
                try {
                    answered.put(list_q.getSelectionModel().getSelectedItem(),nn.getNode().finalizeAnswer());
                } catch (AlreadyExist alreadyExist) {

                }
            }
        });
        list_q.setCellFactory(new Callback<ListView<QuestionFacadeAdaptor>, ListCell<QuestionFacadeAdaptor>>() {
            @Override
            public ListCell<QuestionFacadeAdaptor> call(ListView<QuestionFacadeAdaptor> param) {
                return new QuestionCell(param);
            }
        });

        list_q.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    (list_q.getSelectionModel().getSelectedItem()).getQuestion().getVisitedBy(qn);
                }
            }
        });

        fillBox.getChildren().add(0,qn);

        btn_end.setOnAction(e->{
            int i = list_q.getItems().size();
            AtomicInteger j = new AtomicInteger();
            answered.forEach((key,val)->{
                if(val)
                    j.getAndIncrement();
            });

            db.saveExamResults(s,(int)((((double)j.get())/i)*100));
        });
    }

    void provideStudentAndDB(Student s, IDataBase db) {
        this.s = s;
        this.db = db;
        db.provideExamForCorrespondingStudent(s).ifPresentOrElse(exam -> {
            e = exam;
            loadExam();
        }, () -> {
            System.out.println("shit");
            //TODO when there is no exam available
        });
    }

    private void loadExam() {
        list_q.getItems().clear();
        List<QuestionFacadeAdaptor> questions = e.getQuestionList();
        Collections.shuffle(questions);
        list_q.getItems().addAll(questions);
    }



    private void showNextQuestionOrFinish() {
        //todo
    }
}
