package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.UnaryOperator;

public class CourseSearchController {
    private MainController mainController;
    private CourseService courseService;
    private User user;
    @FXML
    private Label userLabel;
    @FXML
    private TextField subjectFilter;
    @FXML
    private TextField numberFilter;
    @FXML
    private TextField titleFilter;
    @FXML
    private ListView<Course> courseListView;

    public void initialize() {
        addFilterLengthRestrictions();
    }

    private void addFilterLengthRestrictions() {
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

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setUser(User user) {
        this.user = user;
        this.userLabel.setText("Logged in as: " + user.getUsername());
    }
}
