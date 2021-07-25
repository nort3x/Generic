package controller;

import exceptions.AlreadyExist;
import exceptions.NotFoundException;
import interfaces.IDataBase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import objects.Student;
import runner.MainUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginSignupController implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private Button ActionButton;
    @FXML
    private VBox fields;
    @FXML
    private ImageView img;

    @FXML
    private TextField id;

    @FXML
    private TextField password;

    @FXML
    private Button switcher;

    private TextField password2 = new TextField();

    AtomicBoolean isOnLoginPage = new AtomicBoolean(true);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        img.setImage(new Image(MainUI.class.getClassLoader().getResourceAsStream("ls.jpg")));
        password2.setPromptText("Password again ...");
        switcher.setOnAction(e -> {
            if (isOnLoginPage.get()) {
                fields.getChildren().add(password2);
                isOnLoginPage.set(false);
            } else {
                fields.getChildren().remove(password2);
                isOnLoginPage.set(true);
            }
            e.consume();
        });

        ActionButton.setOnAction(e->{
            if(isOnLoginPage.get()){
                try {
                    login(Student.login(id.getText(),password.getText(),db));
                } catch (NotFoundException notFoundException) {
                    showDialog("wrong user or password ");
                    notFoundException.getCause().printStackTrace();
                }
            }else {
                try {
                    if(! password.getText().equals(password2.getText())){
                        showDialog("Passwords doesnt Match!");
                        return;
                    }

                    signUp(Student.signUp(id.getText(),password.getText(),db));
                } catch (AlreadyExist alreadyExist) {
                    showDialog("User already exist!");
                    alreadyExist.getCause().printStackTrace();
                }
            }
        });
    }

    private void signUp(Student s) {
            login(s);
    }

    private void login(Student s) {
        try {
            launchMainPage(s);
        } catch (IOException e) {
            e.printStackTrace(); // wont happen
        }
    }

    private void launchMainPage(Student s) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainUI.class.getClassLoader().getResource("main.fxml"));
        VBox mainPage  = loader.load();
        MainPage mainPageController = loader.getController();
        mainPageController.setProvidedDBAndStudent(db,s);
        mainPane.getScene().setRoot(mainPage);

    }

    IDataBase db;
    public void provideDB(IDataBase db){
        this.db = db;
    }

    private void showDialog(String s){
        VBox dialog  = new VBox();
        dialog.setAlignment(Pos.CENTER);
        dialog.getChildren().add(new Text(s));
        dialog.minWidthProperty().bind(mainPane.widthProperty().multiply(0.2));
        dialog.minHeightProperty().bind(mainPane.heightProperty().multiply(0.2));
        dialog.setStyle("-fx-background-color: lightgray;\n -fx-background-radius: 30");
        VBox owner = new VBox();
        HBox honer = new HBox();
        owner.setAlignment(Pos.CENTER);
        honer.setAlignment(Pos.CENTER);
        honer.getChildren().add(dialog);
        dialog.setPadding(new Insets(20,20,20,20));
        owner.getChildren().add(honer);
        mainPane.getChildren().add(owner);
        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()->{
                mainPane.getChildren().remove(owner);
            });
        }).start();
    }
}
