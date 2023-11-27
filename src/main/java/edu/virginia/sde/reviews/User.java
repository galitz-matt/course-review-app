package edu.virginia.sde.reviews;

import java.util.Optional;

public class User {
    private int id;
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.id = -1;
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username= username;
        this.password = password;
    }

    public Optional<Integer> getId() {
        return id == -1 ? Optional.empty() : Optional.of(id);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) { this.id = id; }
}
