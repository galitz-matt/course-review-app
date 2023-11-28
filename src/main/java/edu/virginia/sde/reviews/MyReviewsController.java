package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MyReviewsController {
    private MainController mainController;
    private CourseService courseService;
    private User user;
    @FXML
    private Label userLabel;

    public void initialize() {
        //TODO: display all user reviews via ListView, make reviews clickable
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(User user) {
        this.user = user;
        userLabel.setText("Logged in as: " + user);
    }

    public void handleGoBackAction() {
        mainController.switchToCourseSelection(user);
    }

}
