package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws SQLException, IOException {
        var databaseDriver = new DatabaseDriver(new Configuration());
        var mainController = new MainController(primaryStage, databaseDriver);
        mainController.switchToLogin();

    }
}