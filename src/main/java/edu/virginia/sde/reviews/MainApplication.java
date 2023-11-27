package edu.virginia.sde.reviews;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.util.Objects;

import static javafx.application.Application.launch;

public class MainApplication extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var mainController = new MainController(primaryStage);
        mainController.switchToLogin();
    }
}