package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SubmitReviewController {
    private MainController mainController;
    private ReviewService reviewService;
    private User user;
    private Course course;
    private Review userReview;
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
        ratingField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ratingField.setText(oldValue);
            } else if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);
                if (value < 1 || value > 5) {
                    ratingField.setText(oldValue);
                }
            }
        });
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

    public void setUserReview(Review userReview) {
        this.userReview = userReview;
    }

    public void clearFields() {
        ratingField.clear();
        commentField.clear();
    }

    public void handleSubmitAction() {
        var timeStamp = System.currentTimeMillis();
        var rating = Integer.parseInt(ratingField.getText());
        var comment = commentField.getText().isEmpty() ? "N/A" : commentField.getText();
        var newReview = new Review(course.getId(), user.getId(), rating, comment, timeStamp);
        if (userReview != null) {
            reviewService.deleteReview(userReview);
        }
        reviewService.addReview(newReview);
        userReview = newReview;
        clearFields();
    }

    public void handleGoBackAction() {
        clearFields();
        messageLabel.setText("");
        mainController.switchToCourseReviews(user, course);
    }
}
