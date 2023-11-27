package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    private final Stage primaryStage;
    private final DatabaseDriver databaseDriver;
    private Scene loginScene;
    private Scene newUserScene;
    private Scene courseSelectionScene;
    private CourseSearchController courseSearchController;

    public MainController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.databaseDriver = new DatabaseDriver(new Configuration());
        initScenes();
    }

    private void initScenes() {
        try {
            var userInfoService = new UserInfoService(databaseDriver);
            var courseService = new CourseService(databaseDriver);

            var loginLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            loginScene = new Scene(loginLoader.load(), 300, 200);
            LoginController loginController = loginLoader.getController();
            loginController.setMainController(this);
            loginController.setUserInfoService(userInfoService);

            var newUserLoader = new FXMLLoader(getClass().getResource("NewUserScreen.fxml"));
            newUserScene = new Scene(newUserLoader.load(), 300, 200);
            NewUserController newUserController = newUserLoader.getController();
            newUserController.setMainController(this);
            newUserController.setUserInfoService(userInfoService);

            var courseSearchLoader = new FXMLLoader(getClass().getResource("CourseSearchScreen.fxml"));
            courseSelectionScene = new Scene(courseSearchLoader.load());
            courseSearchController = courseSearchLoader.getController();
            courseSearchController.setMainController(this);
            courseSearchController.setCourseService(courseService);

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

    public void switchToCourseSelection(User user) {
        primaryStage.setScene(courseSelectionScene);
        courseSearchController.setUser(user);
        primaryStage.show();
    }
}
