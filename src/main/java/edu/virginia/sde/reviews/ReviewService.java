package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {
    private final DatabaseDriver databaseDriver;

    public ReviewService(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public void addReview(Review review) {
        try {
            databaseDriver.connect();
            if (databaseDriver.hasUserReviewedCourse(review)) {
                databaseDriver.disconnect();
                throw new CourseAlreadyReviewedException();
            }
            databaseDriver.addReview(review);
            databaseDriver.commit();
            databaseDriver.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getReviewsByUserID(int userId) {
        try {
            databaseDriver.connect();
            var reviews = databaseDriver.getAllReviewsByUserID(userId);
            databaseDriver.disconnect();
            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getReviewsByCourseID(int courseId) {
        try {
            databaseDriver.connect();
            var reviews = databaseDriver.getAllReviewsByCourseID(courseId);
            databaseDriver.disconnect();
            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getReviewString(Review review) {
        var stringBuilder = new StringBuilder();
        var course = getCourseByID();
        stringBuilder.append(course.getSubject())
                .append(" ")
                .append(course.getNumber())
                .append(" - ")
                .append(review.getRating());
        return stringBuilder.toString();
    }

    public Course getCourseByID() {

    }
}
