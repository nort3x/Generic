package controller;

import interfaces.IDataBase;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import objects.Mark;
import objects.Student;
import runner.MainUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML
    private VBox main;
    @FXML
    private ImageView img_prof;

    @FXML
    private Button history_btn;

    @FXML
    private Button exam_btn;

    @FXML
    private Button exit_btn;

    IDataBase providedDB;
    Student s;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        img_prof.setImage(new Image(MainUI.class.getClassLoader().getResourceAsStream("ls.jpg")));
        exam_btn.setOnAction(e -> goToExam());
        exit_btn.setOnAction(e -> {
            System.exit(0);
        });
        history_btn.setOnAction(e -> goToHistory());
    }

    public void setProvidedDBAndStudent(IDataBase providedDB, Student s) {
        this.providedDB = providedDB;
        this.s = s;
    }

    private void goToHistory() {

        List<Mark> marks = providedDB.getAllStudentMark(s);
        XYChart.Data[] series1Data = new XYChart.Data[marks.size()];
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 100, 1);
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Nomarat "+ s.getId());

        for (int i = 0; i < series1Data.length; i++) {
            series1Data[i] = new XYChart.Data<>(Integer.toString(i + 1), marks.get(i).getMark());
            series1.getData().add(series1Data[i]);
        }


        bc.getData().add(series1);

        main.getChildren().remove(1,main.getChildren().size());
        main.getChildren().add(bc);
    }

    private void goToExam() {
        FXMLLoader loader = new FXMLLoader(MainUI.class.getClassLoader().getResource("exam.fxml"));
        HBox box = null;
        try {
            box = loader.load();
            ExamController controller = loader.getController();
            controller.provideStudentAndDB(s, providedDB);
            main.getChildren().remove(1,main.getChildren().size());
            main.getChildren().add(box);
            VBox.setVgrow(box, Priority.ALWAYS);
            HBox.setHgrow(box, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace(); //  should not happen
        }
    }
}
