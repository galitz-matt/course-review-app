package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NewUserController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorMessageLabel;

    @FXML
    private void handleCreateAccountAction() {
        var username = usernameField;
        var password = passwordField;
        var confirmPassword = confirmPasswordField;
        // TODO: verify user info, check if passwords match, check if username is available
    }

    @FXML
    private void handleGoBackAction() {
        // TODO: go back to login screen
    }
}
