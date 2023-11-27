package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.UnaryOperator;

public class CourseSearchController {
    private MainController mainController;
    //TODO: create CourseService for interacting w/ database
    private User user;
    @FXML
    private Label userIDLabel;
    @FXML
    private TextField subjectFilter;
    @FXML
    private TextField numberFilter;
    @FXML
    private TextField titleFilter;
    @FXML
    private ListView<Course> courseListView;

    public void initialize() {
        addTextFieldRestrictions();
    }

    private void addTextFieldRestrictions() {
        subjectFilter.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(4)));
        numberFilter.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(4)));
        titleFilter.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(50)));
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
        this.userIDLabel.setText("Logged in as: " + user.getUsername());
    }
}
