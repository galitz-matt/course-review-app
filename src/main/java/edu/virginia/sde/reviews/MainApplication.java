package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var mainController = new MainController(primaryStage);
        mainController.switchToLogin();
    }
}