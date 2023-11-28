package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
        ratingField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    ratingField.setText(oldValue);
                } else if (!newValue.isEmpty()) {
                    int value = Integer.parseInt(newValue);
                    if (value < 1 || value > 5) {
                        ratingField.setText(oldValue);
                    }
                }
            }
        });
        if (userReview != null) {
            userReview = reviewService.getReview(user.getId(), course.getId());
            ratingField.setText(String.valueOf(userReview.getRating()));
            commentField.setText(userReview.getComment());
        }
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
        // TODO: add review, deleting happens only AFTER review is added successfully
        if (userReview != null) {
            reviewService.deleteReview(userReview); // TODO: this has to be implemented
        }
    }

    public void handleGoBackAction() {
        clearFields();
        messageLabel.setText("");
        mainController.switchToCourseReviews(user, course);
    }
}
