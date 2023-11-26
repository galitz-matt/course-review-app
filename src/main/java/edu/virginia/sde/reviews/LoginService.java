package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class LoginService {
    private final DatabaseDriver databaseDriver;

    public LoginService(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public boolean isUsernameValid(String username) {
        if (!isUsernameInDatabase(username)) {
            throw new InvalidUsernameException();
        }
        return true;
    }

    public boolean isUsernameAvailable(String username) {
        if (isUsernameInDatabase(username)) {
            throw new UsernameNotAvailableException();
        }
        return true;
    }

    public boolean isUsernameInDatabase(String username) {
        try {
            databaseDriver.connect();
            var inDatabase = databaseDriver.isUserInDatabase(username);
            databaseDriver.disconnect();
            return inDatabase;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
