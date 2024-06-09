package com.example.dotcall_android.model;

public class Friend {
    private String name;
    private String email;
    private String username;

    public Friend(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
