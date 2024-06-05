package com.example.dotcall_android.manager;

import com.example.dotcall_android.model.User;

public class UserManager {
    private static UserManager instance;
    private User currentUser;

    private UserManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void clearCurrentUser() {
        currentUser = null;
    }
}
