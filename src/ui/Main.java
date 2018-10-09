package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Login");
        primaryStage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("login/login.fxml")))
        );
        Image applicationIcon = new Image(getClass().getResourceAsStream("image/file-video-icon.png"));
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
