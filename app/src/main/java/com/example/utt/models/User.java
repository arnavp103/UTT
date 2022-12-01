package com.example.utt.models;


import androidx.annotation.NonNull;

import com.example.utt.database.DatabaseHandler;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

// We shouldn't generate multiple of these.
public class User {
    private String email;

    @Exclude
    private String id;

    @Exclude
    private String password;
    private boolean isAdmin;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        setPassword(password);
    }

    public User(String email, String password, boolean isAdmin) {
        this(email, password);
        this.isAdmin = isAdmin;
    }

    @Exclude
    public String getId() { return id; }

    @Exclude
    public void setId(String id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    // Redundant with above
    public Boolean isStudent() { return !isAdmin; }

    public void setPassword(String password) {
        this.password = DatabaseHandler.hashString(password);
    }

    public void setIsAdmin(boolean val) {
        this.isAdmin = val;
    }

    @NonNull
    public String toString() {
        return email + " is admin?: " + isAdmin;
    }
}
