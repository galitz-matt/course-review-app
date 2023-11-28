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
            if (hasUserReviewedCourse(review.getUserId(), review.getCourseId())) {
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

    public void deleteReview(Review review) {
        try {
            databaseDriver.connect();
            databaseDriver.deleteReview(review);
            databaseDriver.commit();
            databaseDriver.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException();
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

    public Course getCourseByID(int id) {
        try {
            databaseDriver.connect();
            var course = databaseDriver.getCourse(id);
            databaseDriver.disconnect();
            return course;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Review getReview(int userId, int courseId) {
        try {
            databaseDriver.connect();
            var review = databaseDriver.getReviewByForeignKeys(userId, courseId);
            databaseDriver.disconnect();
            return review;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasUserReviewedCourse(int userId, int courseId) {
        var dummyReview = new Review(userId, courseId, 5, "N/A", 0);
        try {
            databaseDriver.connect();
            var hasReviewed = databaseDriver.hasUserReviewedCourse(dummyReview);
            databaseDriver.disconnect();
            return hasReviewed;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getReviewString(Review review) {
        var stringBuilder = new StringBuilder();
        var course = getCourseByID(review.getCourseId());
        stringBuilder.append(course.getSubject())
                .append(" ")
                .append(course.getNumber())
                .append("\n Rating: ")
                .append(review.getRating())
                .append("\n Comment: ")
                .append(review.getComment());
        return stringBuilder.toString();
    }
}
