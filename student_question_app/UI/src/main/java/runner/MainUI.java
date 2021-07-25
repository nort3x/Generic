package runner;

import controller.LoginSignupController;
import file.FileIO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ls.fxml"));
        Scene s = new Scene(loader.load());
        ((LoginSignupController)loader.getController()).provideDB(new FileIO());

        primaryStage.setScene(s);
        primaryStage.show();
    }
}
