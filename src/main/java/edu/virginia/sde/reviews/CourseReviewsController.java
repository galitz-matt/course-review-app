package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CourseReviewsController {
    private MainController mainController;
    private ReviewService reviewService;
    private User user;
    private Course course;
    private Review userReview;
    private boolean initialized = false;
    @FXML
    private Label userLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private ListView<Review> reviewListView;
    private final ObservableList<Review> reviews = FXCollections.observableArrayList();
    @FXML
    private Label messageLabel;

    public void initializeReviewListView() {
        refreshReviewList();
        reviewListView.setItems(reviews);
        reviewListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Review review, boolean empty) {
                super.updateItem(review, empty);
                setText(empty ? null : reviewService.getReviewString(review));
            }
        });
        userReview = reviewService.getReview(user.getId(), course.getId());
        // TODO: consider deleting code below, verify if these need to be clicked
//        reviewListView.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2 && !reviewListView.getSelectionModel().isEmpty()) {
//                selectedReview = reviewListView.getSelectionModel().getSelectedItem();
//            }
//        });
        initialized = true;
    }

    public boolean isReviewListInitialized() {
        return initialized;
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
        courseLabel.setText("Reviews for " + course.getSubject() + " " + course.getNumber() + ": ");
    }

    public void refreshReviewList() {
        reviews.clear();
        reviews.addAll(reviewService.getReviewsByCourseID(course.getId()));
    }

    public void handleAddReviewAction() {
        // TODO: check if user has not submitted review, (is userReview null)
        // If user has submitted review, display appropriate message e.g. already submitted review for this course
        // ONLY SWITCH SCREENS IF has not submitted review
        mainController.switchToSubmitReview(user, course, userReview);
    }

    public void handleEditReviewAction() {
        // TODO: check if user has submitted review, (is userReview not null)
        // If user has not submitted review, display appropriate message e.g. no review to edit for this course
        // ONLY SWITCH SCREENS IF HAS SUBMITTED REVIEW
        mainController.switchToSubmitReview(user, course, userReview);
    }

    public void handleDeleteReviewAction() {
        if (userReview != null) {
            reviewService.deleteReview(userReview);
            userReview = null;
            messageLabel.setText("Your review was deleted successfully");
        } else {
            messageLabel.setText("You have not reviewed this course");
        }
    }

    public void handleGoBackAction() {
        mainController.switchToCourseSelection(user);
    }
}
