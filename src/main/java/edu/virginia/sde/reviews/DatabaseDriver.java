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
        return new Course(id, subject,  number,title,avgRating);
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
                        Num INTEGER NOT NULL,
                        Title TEXT NOT NULL
                        AverageRating Real);
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

    public void addUser(User user) throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        PreparedStatement statement = connection.prepareStatement(
                """
                        INSERT INTO Users(Id, Username, Password) values 
                            (?, ?, ?)
                        """);
        statement.setInt(1, user.getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());

        statement.executeUpdate();

        statement.close();
    }
    public void addCourse(Course course) throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        PreparedStatement statement = connection.prepareStatement(
                """
                        INSERT INTO Courses(Id, Subject, Num, Title,AverageRating) values 
                            (?, ?, ?, ?,?)
                        """);
        statement.setInt(1, course.getId());
        statement.setString(2, course.getSubject());
        statement.setInt(3, course.getNumber());
        statement.setString(4,course.getTitle());
        statement.setDouble(5,course.getAverageRating());

        statement.executeUpdate();

        statement.close();
    }
    public void addReview(Review review) throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Reviews (Id,CourseID, UserID, Rating) VALUES (?, ?, ?,?)");
            pstmt.setInt(1, review.getId());
            pstmt.setInt(2, review.getCourseId());
            pstmt.setInt(3, review.getUserId());
            pstmt.setDouble(4, review.getRating());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Review was not added.");
            }
            else{
                updateCourseAverageRating(review.getCourseId());
            }
            pstmt.close();
        /** need to implement the changes to course.AvgRating after adding a rating to the rating table**/
    }

    public void updateCourseAverageRating(int courseId) throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        String selectSql = "SELECT AVG(Rating) as AverageRating FROM Reviews WHERE CourseID = ?";
        String updateSql = "UPDATE Courses SET AverageRating = ? WHERE ID = ?";
        PreparedStatement statement1 = connection.prepareStatement("SELECT AVG(Rating) as AverageRating FROM Reviews WHERE CourseID = ?");
        PreparedStatement statement2 = connection.prepareStatement("UPDATE Courses SET AverageRating = ? WHERE ID = ?");
        statement1.setInt(1,courseId);
        ResultSet resultSet = statement1.executeQuery();
        if (resultSet.next()) {
            double averageRating = resultSet.getDouble("AverageRating");

            // Update the Courses table
            statement2.setDouble(1, averageRating);
            statement2.setInt(2, courseId);
            int affectedRows = statement2.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Average rating updated successfully for course ID " + courseId);
            } else {
                System.out.println("Course not found or no reviews available for course ID " + courseId);
            }
        }
    }


    public List<User> getUsers() throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM Users";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            users.add(getUser(resultSet));
        }
        statement.close();
        return users;
    }
    public List<Course> getCourses() throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        List<Course> courses = new ArrayList<>();

        String query = "SELECT * FROM Courses";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            courses.add(getCourse(resultSet));
        }
        statement.close();
        return courses;
    }
    public List<Review> getReviews() throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        List<Review> reviews = new ArrayList<>();

        String query = "SELECT * FROM Reviews";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            reviews.add(getReview(resultSet));
        }
        statement.close();
        return reviews;
    }

    public void clearTables() throws SQLException {
        try {
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection is not open");
            }
            String deleteUsers = "DELETE FROM Users;";
            String deleteCourses = "DELETE FROM Courses;";
            String deleteReviews = "DELETE FROM Reviews;";
            PreparedStatement statement1 = connection.prepareStatement(deleteReviews);
            statement1.executeUpdate();
            statement1.close();
            PreparedStatement statement = connection.prepareStatement(deleteUsers);
            statement.executeUpdate();
            statement.close();
            PreparedStatement statement2 = connection.prepareStatement(deleteCourses);
            statement2.executeUpdate();
            statement2.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new IllegalStateException("Failed to delete all tables");
        }

    }

}