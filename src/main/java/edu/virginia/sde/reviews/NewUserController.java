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
    private Label messageLabel;

    protected void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    protected void setUserInfoService(UserInfoService userInfoService) { this.userInfoService = userInfoService; }

    @FXML
    private void handleCreateAccountAction() {
        var username = usernameField.getText();
        var password = passwordField.getText();
        var confirmPassword = confirmPasswordField.getText();
        try {
            validateUserInfo(username, password, confirmPassword);
            userInfoService.addUser(new User(username, password));
            messageLabel.setText("User creation successful (navigate to login)");
            clearTextFields();
        } catch (InvalidUsernameException e) {
            messageLabel.setText("Username is empty");
        } catch (UsernameNotAvailableException e) {
            messageLabel.setText(String.format("Username \"%s\" is already in use", username));
        } catch (InvalidPasswordException e) {
            messageLabel.setText("Password must be at least 8 characters");
        } catch (IncorrectPasswordException e) {
            messageLabel.setText("Passwords do not match");
        }
    }

    private void clearTextFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void validateUserInfo(String username, String password, String confirmPassword) {
        if (username.isEmpty()) {
            throw new InvalidUsernameException();
        }
        if (!userInfoService.isUsernameAvailable(username)) {
            throw new UsernameNotAvailableException();
        }
        if (password.length() < 8) {
            throw new InvalidPasswordException();
        }
        if (!confirmPassword.equals(password)) {
            throw new IncorrectPasswordException();
        }
    }

    @FXML
    private void handleGoBackAction() {
        messageLabel.setText("");
        clearTextFields();
        mainController.switchToLogin();
    }
}
