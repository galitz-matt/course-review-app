package edu.virginia.sde.reviews;

public class Review {
    private int id;
    private int courseId;
    private int userId;
    private double rating;

    public Review(int id, int courseId, int userId, double rating) {
        this.id=id;
        this.courseId = courseId;
        this.userId = userId;
        this.rating= rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
