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
    @FXML
    private Label selectedReviewLabel;
    private ObservableList<Review> reviews = FXCollections.observableArrayList();

    public void initialize() {
        reviewService = new ReviewService(new DatabaseDriver(new Configuration()));
        refreshReviewList();
        reviewListView.setItems(reviews);
        reviewListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Review review, boolean empty) {
                super.updateItem(review, empty);
                setText(empty ? null : review.toString());
            }
        });
        reviewListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !reviewListView.getSelectionModel().isEmpty()) {
                Review selectedReview = reviewListView.getSelectionModel().getSelectedItem();
                // TODO: get rid of selected review label, replace w/ commented code
                selectedReviewLabel.setText("You selected: " + selectedReview.toString());
                // TODO: mainController.switchToCourseReviews(reviewService.getCourse(review);
            }
        });
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
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
        mainController.switchToCourseSelection(user);
    }

}
