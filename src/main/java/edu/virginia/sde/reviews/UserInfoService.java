package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class UserInfoService {
    private final DatabaseDriver databaseDriver;

    public UserInfoService(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public User getUser(String username, String password){
        try {
            databaseDriver.connect();
            var user = databaseDriver.getUser(username);
            databaseDriver.disconnect();
            verifyUserInfo(user, password);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  boolean isUsernameAvailable(String username) {
        try {
            databaseDriver.connect();
            var isAvailable = !databaseDriver.isUserInDatabase(username);
            databaseDriver.disconnect();
            return isAvailable;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void verifyUserInfo(User user, String password) {
        if (user == null) {
            throw new InvalidUsernameException();
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }
}
