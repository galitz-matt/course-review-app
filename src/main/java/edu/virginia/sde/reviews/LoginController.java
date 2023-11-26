package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button exitButton;

    @FXML
    private void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // TODO: check if username and password exist
        // TODO: switch to course selection screen
    }

    @FXML
    private void handleNewUserAction() {
        // TODO: switch to set up screen
    }

    @FXML
    private void handleExitAction() {
        var stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}