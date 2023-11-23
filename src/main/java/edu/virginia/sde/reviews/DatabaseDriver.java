package edu.virginia.sde.reviews;

import java.sql.*;

public class DatabaseDriver {
    private final String sqliteFilename;
    private Connection connection;

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
                        INSERT INTO Courses(Id, Subject, Num, Title,AvgRating) values 
                            (?, ?, ?, ?,?)
                        """);
        statement.setInt(1, course.getId());
        statement.setString(2, course.getSubject());
        statement.setInt(3, course.getNumber());
        statement.setString(4,course.getTitle());
        statement.setDouble(5,course.getAvgRating());

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
            pstmt.close();
        }

}