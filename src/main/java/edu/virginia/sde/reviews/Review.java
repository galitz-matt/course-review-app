package edu.virginia.sde.reviews;

import java.sql.Timestamp;

public class Review {
    private int id;
    private final int courseId;
    private final int userId;
    private final int rating;
    private final String comment;
    private final Timestamp timeStamp;

    public Review(int courseId, int userId, int rating, String comment, Timestamp timeStamp) {
        this.courseId = courseId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.timeStamp = timeStamp;
    }

    public Review(int id, int courseId, int userId, int rating, String comment, Timestamp timeStamp) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getUserId() {
        return userId;
    }

    public String getComment() {
        if (comment.isEmpty()) {
            return "N/A";
        }
        return comment;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
}
