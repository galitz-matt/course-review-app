package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.List;

public class CourseService {
    private final DatabaseDriver databaseDriver;

    public CourseService(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public List<Course> getCourses() {
        try {
            databaseDriver.connect();
            var courses = databaseDriver.getCourses();
            databaseDriver.disconnect();
            return courses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
