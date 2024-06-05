package com.example.dotcall_android.manager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthManager {
    private static AuthManager instance;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private AuthManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            // Handle authentication state change
        };
    }

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public void startListening() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void stopListening() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
