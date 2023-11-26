package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene newUserScene;

    public MainController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initScenes();
    }

    private void initScenes() {
        try {
            loginScene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScreen.fxml"))));
            newUserScene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewUserScreen.fxml"))));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load scenes");
        }
    }

    public void switchToLogin() {
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public void switchToNewUserSetup() {
        primaryStage.setScene(newUserScene);
        primaryStage.show();
    }
}
