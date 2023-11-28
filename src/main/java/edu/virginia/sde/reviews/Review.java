package edu.virginia.sde.reviews;

public class Review {
    private int id;
    private final int courseId;
    private final int userId;
    private final double rating;

    public Review(int courseId, int userId, double rating) {
        this.courseId = courseId;
        this.userId = userId;
        this.rating = rating;
    }

    public Review(int id, int courseId, int userId, double rating) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getUserId() {
        return userId;
    }
}
