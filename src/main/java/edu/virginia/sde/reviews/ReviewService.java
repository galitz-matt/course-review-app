package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {
    private DatabaseDriver databaseDriver;

    public ReviewService(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    // TODO: addReview method, add review to DB, update avgRating

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
}
