package edu.virginia.sde.reviews;

import com.sun.javafx.scene.control.IntegerField;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ReviewController {
    private MainController mainController;
    private ReviewService reviewService;
    private User user;
    private Course course;
    @FXML
    private Label userLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private IntegerField ratingField;
    @FXML
    private TextField commentField;
    @FXML
    private Label messageLabel;

    private void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void setReviewService(ReviewService reviewService) {
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
    }

    public void handleGoBackAction() {
        messageLabel.setText("");
        mainController.switchToCourseReviews(user, course);
    }
}
