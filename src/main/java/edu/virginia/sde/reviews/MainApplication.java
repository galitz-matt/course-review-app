package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApplication extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        var databaseDriver = new DatabaseDriver(new Configuration());
        databaseDriver.connect();
        databaseDriver.createTables();
        databaseDriver.disconnect();
        var mainController = new MainController(primaryStage, databaseDriver);
        mainController.switchToLogin();
    }
}