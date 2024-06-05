package com.example.dotcall_android.model;

public class User {
    private String id;
    private String name;
    private String email;
    private String username;
    private String createdAt;
    private String notification;
    private String faceId;
    private String haptic;

    public User(String id, String name, String email, String username, String createdAt, String notification, String faceId, String haptic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.createdAt = createdAt;
        this.notification = notification;
        this.faceId = faceId;
        this.haptic = haptic;
    }

    public String getId() {
        return id;
    }

    public void set_id(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String isNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String isFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String isHaptic() {
        return haptic;
    }

    public void setHaptic(String haptic) {
        this.haptic = haptic;
    }


}
