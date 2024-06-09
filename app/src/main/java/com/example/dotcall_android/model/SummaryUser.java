package com.example.dotcall_android.model;

import java.util.List;

public class SummaryUser {
    private String callMakerEmail;
    private String callMakerName;
    private String callMakerUsername;
    private List<Summary> summaries;

    public SummaryUser(String callMakerEmail, String callMakerName, String callMakerUsername, List<Summary> summaries) {
        this.callMakerEmail = callMakerEmail;
        this.callMakerName = callMakerName;
        this.callMakerUsername = callMakerUsername;
        this.summaries = summaries;
    }

    // Getters and setters
    public String getCallMakerEmail() {
        return callMakerEmail;
    }

    public void setCallMakerEmail(String callMakerEmail) {
        this.callMakerEmail = callMakerEmail;
    }

    public String getCallMakerName() {
        return callMakerName;
    }

    public void setCallMakerName(String callMakerName) {
        this.callMakerName = callMakerName;
    }

    public String getCallMakerUsername() {
        return callMakerUsername;
    }

    public void setCallMakerUsername(String callMakerUsername) {
        this.callMakerUsername = callMakerUsername;
    }

    public List<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<Summary> summaries) {
        this.summaries = summaries;
    }
}
