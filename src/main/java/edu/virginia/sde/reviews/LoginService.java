package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class LoginService {
    private final DatabaseDriver databaseDriver;

    public LoginService(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public User getUser(String username) throws SQLException {
        databaseDriver.connect();
        var user = databaseDriver.getUser(username);
        databaseDriver.disconnect();
        if (user == null) {
            throw new InvalidUsernameException();
        }
        return user;
    }
}
