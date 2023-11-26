package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            var userInfoService = new UserInfoService(new DatabaseDriver(new Configuration()));

            var loginLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            loginScene = new Scene(loginLoader.load());
            LoginController loginController = loginLoader.getController();
            loginController.setMainController(this);
            loginController.setUserInfoService(userInfoService);

            var newUserLoader = new FXMLLoader(getClass().getResource("NewUserScreen.fxml"));
            newUserScene = new Scene(newUserLoader.load());
            NewUserController newUserController = newUserLoader.getController();
            newUserController.setMainController(this);
            newUserController.setUserInfoService(userInfoService);
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
