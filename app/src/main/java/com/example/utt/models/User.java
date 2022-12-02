package com.example.utt.models;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.utt.database.DatabaseHandler;
import com.google.firebase.database.Exclude;

// We shouldn't generate multiple of these.
public class User {
    private String email;
    private String index;

    @Exclude
    private String id;

    @Exclude
    private String password;
    private boolean isAdmin;

    @Exclude
    private static User currentUser;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        setPassword(password);
        index = email + this.password;
    }

    public User(String email, String password, boolean isAdmin) {
        this(email, password);
        this.isAdmin = isAdmin;
        index = email + this.password;
    }

    public static void logout() {
        // TODO - implement.
    }

    public static void login(User user) {
        if (currentUser != null) logout();
        currentUser = user;
    }

    public static User getInstance() { return currentUser; }

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

    public String getIndex() { return index;}

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    // Redundant with above

    @Exclude
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