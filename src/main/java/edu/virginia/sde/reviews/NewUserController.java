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
        var username = usernameField.getText();
        var password = passwordField.getText();
        var confirmPassword = confirmPasswordField.getText();
        // TODO: Add user to database
        // TODO: Switch to course selection scene
        try {
            verifyUserInfo(username, password, confirmPassword);
        } catch (UsernameNotAvailableException e) {
            errorMessageLabel.setText(String.format("Username \"%s\" is taken", username));
        } catch (InvalidPasswordException e) {
            errorMessageLabel.setText("Password must be at least 8 characters");
        } catch (IncorrectPasswordException e) {
            errorMessageLabel.setText("Passwords do not match");
        }
    }

    private void verifyUserInfo(String username, String password, String confirmPassword) {
        userInfoService.isUsernameAvailable(username);
        if (password.length() < 8) {
            throw new InvalidPasswordException();
        }
        if (!confirmPassword.equals(password)) {
            throw new IncorrectPasswordException();
        }
    }

    @FXML
    private void handleGoBackAction() {
        errorMessageLabel.setText("");
        mainController.switchToLogin();
    }
}
