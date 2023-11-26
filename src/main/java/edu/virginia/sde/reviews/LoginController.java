package edu.virginia.sde.reviews;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoginController {
    private MainController mainController;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button exitButton;

    protected void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // TODO: check if username and password exist
        // TODO: switch to course selection screen
    }

    @FXML
    private void handleNewUserAction() {
        mainController.switchToNewUserSetup();
    }

    @FXML
    private void handleExitAction() {
        var stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}