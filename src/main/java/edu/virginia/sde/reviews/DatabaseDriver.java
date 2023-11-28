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
            COURSES_NUMBER = "Number",
            Courses_TITLE = "Title",
            COURSES_AVG_RATING = "AvgRating";

    /* Routes Column Names */
    public final String REVIEWS_TABLE = "Reviews",
            REVIEWS_ID = "ID",
            REVIEWS_COURSEID = "CourseID",
            REVIEWS_USERID = "UserID",
            REVIEWS_RATING = "Rating";

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
        var query = "INSERT INTO Users(Username, Password) VALUES (?, ?);";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            rollback();
            throw e;
        }
    }
    public void addCourse(Course course) throws SQLException {
        checkConnection();
        var query = "INSERT INTO Courses(Subject, Number, Title, AvgRating) VALUES (?, ?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, course.getSubject());
            preparedStatement.setInt(2, course.getNumber());
            preparedStatement.setString(3, course.getTitle());
            preparedStatement.setDouble(4, course.getAvgRating());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            rollback();
            throw e;
        }
    }
    public void addReview(Review review) throws SQLException{
        checkConnection();
        var query = "INSERT INTO Reviews (CourseID, UserID, Rating) VALUES (?, ?, ?);";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, review.getCourseId());
            preparedStatement.setInt(2, review.getUserId());
            preparedStatement.setDouble(3, review.getRating());
            preparedStatement.executeUpdate();
            //updateCourseAverageRating(review.getCourseId());
        } catch (SQLException e) {
            rollback();
            throw e;
        }
    }

    public List<User> getAllUsers() throws SQLException{
        checkConnection();
        var users = new ArrayList<User>();
        var query = "SELECT * FROM Users";
        try (var statement = connection.prepareStatement(query)) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(buildUser(resultSet));
                }
            }
        }
        return users;
    }

    public List<Course> getAllCourses() throws SQLException{
        checkConnection();
        var courses = new ArrayList<Course>();
        var query = "SELECT * FROM Courses";
        try (var preparedStatement = connection.prepareStatement(query)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(buildCourse(resultSet));
                }
            }
        }
        return courses;
    }

    public List<Review> getAllReviews() throws SQLException {
        //TODO: get reviews by id
        checkConnection();
        var reviews = new ArrayList<Review>();
        var query = "SELECT * FROM Reviews";
        try (var statement = connection.prepareStatement(query)) {
            try(var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(buildReview(resultSet));
                }
            }
        }
        return reviews;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt(USER_ID);
        var username = resultSet.getString(USER_USERNAME);
        var password = resultSet.getString(USER_PASSWORD);
        return new User(id, username,password);
    }
    private Course buildCourse(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt(COURSES_ID);
        var subject = resultSet.getString(COURSES_SUBJECT);
        var number = resultSet.getInt(COURSES_NUMBER);
        var title = resultSet.getString(Courses_TITLE);
        var avgRating = resultSet.getDouble(COURSES_AVG_RATING);
        return new Course(id, subject, number, title, avgRating);
    }

    private Review buildReview(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt(REVIEWS_ID);
        var courseID = resultSet.getInt(REVIEWS_COURSEID);
        var userID = resultSet.getInt(REVIEWS_USERID);
        var rating = resultSet.getDouble(REVIEWS_RATING);
        return new Review(id, courseID,userID,rating);
    }

    public User getUser(String username) throws SQLException {
        String query = "SELECT * FROM USERS WHERE Username = ?;";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildUser(resultSet);
                }
            }
        }
        return null;
    }

    public void updateCourseAverageRating(int courseId) throws SQLException{
        try {
            checkConnection();
            var selectSql = "SELECT AVG(Rating) as AverageRating FROM Reviews WHERE CourseID = ?;";
            var updateSql = "UPDATE Courses SET AvgRating = ? WHERE ID = ?;";
            // TODO: optimize this, more robust statement initialization (try w/ resources),
            var statement1 = connection.prepareStatement(selectSql);
            var statement2 = connection.prepareStatement(updateSql);
            statement1.setInt(1, courseId);
            ResultSet resultSet = statement1.executeQuery();
            if (resultSet.next()) {
                double averageRating = resultSet.getDouble(REVIEWS_RATING);
                statement2.setDouble(1, averageRating);
                statement2.setInt(2, courseId);
                statement2.executeUpdate();
            }
        }
        catch (SQLException e){
            rollback();
            throw e;
        }
    }

    public boolean isUserInDatabase(String username) throws SQLException {
        String query = "SELECT EXISTS(SELECT 1 FROM Users WHERE Username = ?);";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        }
        return false;
    }

    public boolean isCourseInDatabase(Course course) throws SQLException {
        var query = "SELECT 1 FROM Courses WHERE Subject = ? AND Number = ? AND Title = ?;";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, course.getSubject());
            preparedStatement.setInt(2, course.getNumber());
            preparedStatement.setString(3, course.getTitle().strip());
            try(var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        }
        return false;
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