package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class LoginController {
    @FXML
    private Label messageLabel;

    public void handleButton() {
        messageLabel.setText("does this work?");
    }
}
