package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessageLabel;

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
}
