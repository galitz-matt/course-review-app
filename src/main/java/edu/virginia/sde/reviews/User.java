package edu.virginia.sde.reviews;

public class User {
    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        verifyInfo(username, password); // TODO: remove; see line 15
        this.id = id;
        this.username= username;
        this.password = password;
    }

    //TODO: handle this in the business logic i.e. button handlers
    private void verifyInfo(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("User information cannot be empty");
        }
        if (password.length() >= 8) {
            throw new IllegalArgumentException("Password must be less than 8 characters");
        }
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
