package com.example.dotcall_android.model;

import java.util.List;

public class SummaryUser {

    private String callReceiverEmail;
    private String callReceiverName;
    private String recentSummary;
    private String recentTime;
    private List<Summary> summary;

    public SummaryUser() {
    }

    public SummaryUser(String callReceiverEmail, String callReceiverName, String recentSummary, String recentTime,List<Summary> summary) {
        this.callReceiverEmail = callReceiverEmail;
        this.callReceiverName = callReceiverName;
        this.recentSummary = recentSummary;
        this.recentTime = recentTime;
        this.summary = summary;
    }

    public String getCallReceiverEmail() {
        return callReceiverEmail;
    }

    public void setCallReceiverEmail(String callReceiverEmail) {
        this.callReceiverEmail = callReceiverEmail;
    }

    public String getCallReceiverName() {
        return callReceiverName;
    }

    public void setCallReceiverName(String callReceiverName) {
        this.callReceiverName = callReceiverName;
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

    public List<Summary> getSummary() {
        return summary;
    }
    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }
}
