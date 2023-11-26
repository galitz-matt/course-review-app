package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoginController {
    private MainController mainController;
    private UserInfoService userInfoService;
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

    protected void setUserInfoService(UserInfoService userInfoService) { this.userInfoService = userInfoService; }

    @FXML
    private void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            var user = userInfoService.getUser(username, password);
            // TODO: switch to course selection screen
        } catch (InvalidUsernameException e) {
            errorMessageLabel.setText("Username is incorrect or does not exist");
        } catch (IncorrectPasswordException e) {
            errorMessageLabel.setText("Password is incorrect");
        }
    }

    @FXML
    private void handleNewUserAction() {
        errorMessageLabel.setText("");
        mainController.switchToNewUserSetup();
    }

    @FXML
    private void handleExitAction() {
        var stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}