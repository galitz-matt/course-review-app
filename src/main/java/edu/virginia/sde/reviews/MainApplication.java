package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        var mainController = new MainController(primaryStage);
        mainController.switchToLogin();
    }
}