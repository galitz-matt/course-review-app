package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    private final Stage primaryStage;
    private Scene loginScene;
    private Scene newUserScene;

    public MainController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initScenes();
    }

    private void initScenes() {
        try {
            var loginLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            loginScene = new Scene(loginLoader.load());
            LoginController loginController = loginLoader.getController();
            loginController.setMainController(this);
            loginController.setLoginService(new LoginService(new DatabaseDriver(new Configuration())));

            var newUserLoader = new FXMLLoader(getClass().getResource("NewUserScreen.fxml"));
            newUserScene = new Scene(newUserLoader.load());
            NewUserController newUserController = newUserLoader.getController();
            newUserController.setMainController(this);
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
