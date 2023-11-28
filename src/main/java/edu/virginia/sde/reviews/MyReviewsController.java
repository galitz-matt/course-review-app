package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MyReviewsController {
    private MainController mainController;
    private ReviewService reviewService;
    private User user;
    @FXML
    private Label userLabel;
    @FXML
    private ListView<Review> reviewListView;
    private final ObservableList<Review> reviews = FXCollections.observableArrayList();
    private boolean initialized = false;

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
        reviewListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !reviewListView.getSelectionModel().isEmpty()) {
                Review selectedReview = reviewListView.getSelectionModel().getSelectedItem();
                mainController.switchToCourseReviews(user, reviewService.getCourseByID(selectedReview.getCourseId()));
            }
        });
        initialized = true;
    }

    public boolean isReviewListViewInitialized() {
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

    public void refreshReviewList() {
        reviews.clear();
        reviews.addAll(reviewService.getReviewsByUserID(user.getId()));
    }

    public void handleGoBackAction() {
        mainController.switchToCourseSearch(user);
    }

}
