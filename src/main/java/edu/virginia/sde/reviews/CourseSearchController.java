package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class CourseSearchController {
    private MainController mainController;
    //TODO: create CourseService for interacting w/ database
    private User user;
    @FXML
    private Label userIDLabel;
    @FXML
    private TextField subjectField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField titleField;

    public void initialize() {
        subjectField.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(4)));
        numberField.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(4)));
        titleField.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(50)));
    }

    private UnaryOperator<TextFormatter.Change> createLimitingUnaryOperator(int limit) {
        return change -> {
            if (change.getControlNewText().length() <= limit) {
                return change;
            } else {
                return null;
            }
        };
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(User user) {
        this.user = user;
        this.userIDLabel.setText("Logged in as:" + user.getUsername());
    }
}
