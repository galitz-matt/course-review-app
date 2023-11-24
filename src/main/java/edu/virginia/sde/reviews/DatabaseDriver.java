package edu.virginia.sde.reviews;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDriver {
    private final String sqliteFilename;
    private Connection connection;
    public final String USERS_TABLE = "Users",
            USER_ID = "ID",
            USER_USERNAME = "Username",
            USER_PASSWORD = "Password";
    public final String COURSES_TABLE = "Courses",
            COURSES_ID = "ID",
            COURSES_SUBJECT = "Subject",
            COURSES_NUMBER = "Num",
            Courses_TITLE = "Title",
            COURSES_AVG_RATING = "AvgRating";

    /* Routes Column Names */
    public final String REVIEWS_TABLE = "Reviews",
            REVIEWS_ID = "ID",
            REVIEWS_COURSEID = "CourseID",
            REVIEWS_USERID = "UserID",
            REVIEWS_RATING = "Rating";

    private User getUser(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt(USER_ID);
        var username = resultSet.getString(USER_USERNAME);
        var password = resultSet.getString(USER_PASSWORD);
        return new User(id, username,password);
    }
    private Course getCourse(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt(COURSES_ID);
        var subject = resultSet.getString(COURSES_SUBJECT);
        var number = resultSet.getInt(COURSES_NUMBER);
        var title = resultSet.getString(Courses_TITLE);
        var avgRating = resultSet.getDouble(COURSES_AVG_RATING);
        return new Course(id, subject, number, title, avgRating);
    }
    private Review getReview(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt(REVIEWS_ID);
        var courseID = resultSet.getInt(REVIEWS_COURSEID);
        var userID = resultSet.getInt(REVIEWS_USERID);
        var rating = resultSet.getDouble(REVIEWS_RATING);
        return new Review(id, courseID,userID,rating);
    }

    public DatabaseDriver(Configuration configuration) {
        this.sqliteFilename = configuration.getDatabaseFilename();
    }

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            throw new IllegalStateException("The connection is already opened");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteFilename);
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public void checkConnection() throws SQLException {
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
    }

    public void createTables() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute(
                    """
                        CREATE TABLE IF NOT EXISTS Users(
                        ID INTEGER PRIMARY KEY,
                        Username TEXT UNIQUE NOT NULL,
                        Password TEXT NOT NULL);
                        """
            );
            statement.execute(
                    """
                        CREATE TABLE IF NOT EXISTS Courses(
                        ID INTEGER PRIMARY KEY,
                        Subject TEXT NOT NULL,
                        Number INTEGER NOT NULL,
                        Title TEXT NOT NULL,
                        AvgRating Real);
                        """
            );
            statement.execute(
                    """
                        CREATE TABLE IF NOT EXISTS Reviews(
                        ID INTEGER PRIMARY KEY,
                        CourseID INTEGER NOT NULL,
                        UserID INTEGER NOT NULL,
                        Rating REAL NOT NULL,
                        FOREIGN KEY (CourseID) REFERENCES Courses (ID) ON DELETE CASCADE,
                        FOREIGN KEY (UserID) REFERENCES Users (ID) ON DELETE CASCADE);
                        """
            );
        }
    }

    public void addUser(User user) throws SQLException {
        checkConnection();
        var query = "INSERT INTO Users(Id, Username, Password) VALUES (?, ?, ?);";
        try (var preparedStatement = connection.prepareStatement(query)) {
            var statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            rollback();
            throw e;
        }
    }
    public void addCourse(Course course) throws SQLException{
        checkConnection();
        var query = "INSERT INTO Courses(Id, Subject, Number, Title, AvgRating) VALUES (?, ?, ?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, course.getId());
            preparedStatement.setString(2, course.getSubject());
            preparedStatement.setInt(3, course.getNumber());
            preparedStatement.setString(4, course.getTitle());
            preparedStatement.setDouble(5, course.getAvgRating());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            rollback();
            throw e;
        }
    }
    public void addReview(Review review) throws SQLException{
        checkConnection();
        var query = "INSERT INTO Reviews (Id, CourseID, UserID, Rating) VALUES (?, ?, ?, ?);";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, review.getId());
            preparedStatement.setInt(2, review.getCourseId());
            preparedStatement.setInt(3, review.getUserId());
            preparedStatement.setDouble(4, review.getRating());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            rollback();
            throw e;
        }
        //TODO: Update average rating for course, doesn't need to be done to be done immediately
    }
    public List<User> getUsers() throws SQLException{
        checkConnection();
        var users = new ArrayList<User>();
        var query = "SELECT * FROM Users";
        try (var statement = connection.prepareStatement(query)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
        }
        return users;
    }
    public List<Course> getCourses() throws SQLException{
        checkConnection();
        var courses = new ArrayList<Course>();
        var query = "SELECT * FROM Courses";
        try (var preparedStatement = connection.prepareStatement(query)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(getCourse(resultSet));
            }
        }
        return courses;
    }
    public List<Review> getReviews() throws SQLException{
        checkConnection();
        var reviews = new ArrayList<Review>();
        var query = "SELECT * FROM Reviews";
        try (var statement = connection.prepareStatement(query)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reviews.add(getReview(resultSet));
            }
        }
        return reviews;
    }

    public void clearTables() throws SQLException {
        checkConnection();
        var deleteUsers = "DELETE FROM Users;";
        var deleteCourses = "DELETE FROM Courses;";
        var deleteReviews = "DELETE FROM Reviews;";
        try (var statement = connection.createStatement()) {
            statement.execute(deleteUsers);
            statement.execute(deleteCourses);
            statement.execute(deleteReviews);
        } catch (SQLException e) {
            rollback();
            throw e;
        }

    }
}