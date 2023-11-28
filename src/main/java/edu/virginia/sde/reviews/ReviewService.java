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

    public void deleteReview(Review review) {
        // TODO: implement, gonna need to create new database driver methods
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

    public boolean hasUserReviewedCourse(User user, Course course) {
        var dummyReview = new Review(user.getId(), course.getId(), 5, "N/A", 0);
        try {
            databaseDriver.connect();
            var hasReviewed = databaseDriver.hasUserReviewedCourse(dummyReview);
            databaseDriver.disconnect();
            return hasReviewed;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
