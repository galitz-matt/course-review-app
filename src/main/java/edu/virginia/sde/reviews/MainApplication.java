package edu.virginia.sde.reviews;

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScreen.fxml")));
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
}
