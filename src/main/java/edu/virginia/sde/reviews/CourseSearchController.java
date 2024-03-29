package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private FilteredList<Course> filteredData;

    public void initializeCourseListView() {
        addFilterLengthRestrictions();
        refreshCourseList();
        filteredData = new FilteredList<>(courses, p -> true);
        courseListView.setItems(filteredData);
        addFilterListeners();
        courseListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                setText(empty ? null : course.toString());
            }
        });
        courseListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !courseListView.getSelectionModel().isEmpty()) {
                Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
                mainController.switchToCourseReviews(user, selectedCourse);
            }
        });
    }

    private void addFilterLengthRestrictions() {
        subjectFilter.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(4)));
        numberFilter.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(4)));
        titleFilter.setTextFormatter(new TextFormatter<>(createLimitingUnaryOperator(50)));
    }

    private void addFilterListeners() {
        subjectFilter.textProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        numberFilter.textProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        titleFilter.textProperty().addListener((observable, oldValue, newValue) -> updateFilter());
    }

    private void updateFilter() {
        var subjectFilterText = subjectFilter.getText().toLowerCase();
        var numberFilterText = numberFilter.getText();
        var titleFilterText = titleFilter.getText().toLowerCase();
        filteredData.setPredicate(course -> {
            if (!subjectFilterText.isEmpty() && !course.getSubject().toLowerCase().equals(subjectFilterText)) {
                return false;
            }
            if (!numberFilterText.isEmpty() && !String.valueOf(course.getNumber()).equals(numberFilterText)) {
                return false;
            }
            if (!titleFilterText.isEmpty() && !course.getTitle().toLowerCase().contains(titleFilterText)) {
                return false;
            }
            return true;
        });
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

    public void setCourseService(CourseService courseService) { this.courseService = courseService; }

    public void setUser(User user) {
        this.user = user;
        this.userLabel.setText("Logged in as: " + user.getUsername());
    }

    public void refreshCourseList() {
        courses.clear();
        courses.addAll(courseService.getCourses());
    }

    public void handleAddCourseAction() {
        mainController.switchToAddCourse(user);
    }

    public void handleMyReviewsAction() {
        mainController.switchToMyReviews(user);
    }
    public void handleLogOutAction() {
        mainController.switchToLogin();
    }
}
