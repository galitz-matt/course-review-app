package edu.virginia.sde.reviews;

import javax.xml.crypto.Data;

public class ReviewService {
    private DatabaseDriver databaseDriver;

    public ReviewService(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    // TODO: addReview method, add review to DB, update avgRating
    // TODO: getReviewsByUserID method
    // TODO: getReviewsByCourseID method
}
