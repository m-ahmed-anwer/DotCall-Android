package com.example.dotcall_android.model;

public class Summary {
    private String callReciverName;
    private String callReciverUsername;
    private String recentSummary;
    private String recentTime;

    public Summary(String callReciverName, String callReciverUsername, String recentSummary, String recentTime) {
        this.callReciverName = callReciverName;
        this.callReciverUsername = callReciverUsername;
        this.recentSummary = recentSummary;
        this.recentTime = recentTime;
    }

    // Getters and setters
    public String getCallReciverName() {
        return callReciverName;
    }

    public void setCallReciverName(String callReciverName) {
        this.callReciverName = callReciverName;
    }

    public String getCallReciverUsername() {
        return callReciverUsername;
    }

    public void setCallReciverUsername(String callReciverUsername) {
        this.callReciverUsername = callReciverUsername;
    }

    public String getRecentSummary() {
        return recentSummary;
    }

    public void setRecentSummary(String recentSummary) {
        this.recentSummary = recentSummary;
    }

    public String getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(String recentTime) {
        this.recentTime = recentTime;
    }
}
