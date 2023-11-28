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
    private boolean initialized = false;
    @FXML
    private Label userLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private ListView<Review> reviewListView;
    private final ObservableList<Review> reviews = FXCollections.observableArrayList();
    private Review selectedReview;

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
        // TODO: check if user has not submitted review
        // If user has submitted review, display appropriate message e.g. already submitted review for this course
        mainController.switchToSubmitReview(user, course);
    }

    public void handleEditReviewAction() {
        // TODO: check if user has submitted review
        // If user has not submitted review, display appropriate message e.g. no review to edit for this course
        mainController.switchToSubmitReview(user, course);
    }

    public void handleGoBackAction() {
        mainController.switchToCourseSelection(user);
    }
}
