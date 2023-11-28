package edu.virginia.sde.reviews;

public class Review {
    private int id;
    private final int courseId;
    private final int userId;
    private final int rating;
    private final String comment;

    public Review(int courseId, int userId, int rating, String comment) {
        this.courseId = courseId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(int id, int courseId, int userId, int rating, String comment) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
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
}
