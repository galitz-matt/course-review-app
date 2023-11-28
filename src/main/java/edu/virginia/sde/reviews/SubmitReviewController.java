package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SubmitReviewController {
    private MainController mainController;
    private ReviewService reviewService;
    private User user;
    private Course course;
    @FXML
    private Label userLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField commentField;
    @FXML
    private Label messageLabel;

    public void initializeFields() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void setUser(User user) {
        this.user = user;
        userLabel.setText("Logged in as: " + user.getUsername());
    }

    public void setCourse(Course course) {
        this.course = course;
        courseLabel.setText("Reviewing for: " + course.getSubject() + " " + course.getNumber());
    }

    public void handleSubmitAction() {
        // TODO: implement
        // If user already reviewed course, delete current review and submit new one
    }

    public void clearFields() {
        ratingField.clear();
        commentField.clear();
    }

    public void handleGoBackAction() {
        clearFields();
        messageLabel.setText("");
        mainController.switchToCourseReviews(user, course);
    }
}
