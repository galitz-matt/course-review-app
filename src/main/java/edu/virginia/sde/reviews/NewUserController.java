package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NewUserController {
    private MainController mainController;
    private UserInfoService userInfoService;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorMessageLabel;

    protected void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    protected void setUserInfoService(UserInfoService userInfoService) { this.userInfoService = userInfoService; }

    @FXML
    private void handleCreateAccountAction() {
        var username = usernameField;
        var password = passwordField;
        var confirmPassword = confirmPasswordField;
        // TODO: verify user info, check if passwords match, check if username is available
    }

    @FXML
    private void handleGoBackAction() {
        errorMessageLabel.setText("");
        mainController.switchToLogin();
    }
}
