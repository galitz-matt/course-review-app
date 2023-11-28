package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.UnaryOperator;

public class AddCourseController {
    private MainController mainController;
    private CourseService courseService;
    private User user;
    @FXML
    private Label userLabel;
    @FXML
    private TextField subjectField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField titleField;

    public void initialize() {
        courseService = new CourseService(new DatabaseDriver(new Configuration()));
        addFieldLengthRestrictions();
    }

    private void addFieldLengthRestrictions() {
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
        this.userLabel.setText("Logged in as: " + user.getUsername());
    }

    public Course buildCourse() {
        var subject = subjectField.getText();
        var number = Integer.parseInt(numberField.getText());
        var title = titleField.getText();
        return new Course(subject, number, title);
    }

    public void handleAddCourseAction() {
        courseService.addCourse(buildCourse());
        clearTextFields();
    }

    private void clearTextFields() {
        subjectField.clear();
        numberField.clear();
        titleField.clear();
    }

    public void handleGoBackAction() {
        mainController.switchToCourseSelection(user);
    }
}
