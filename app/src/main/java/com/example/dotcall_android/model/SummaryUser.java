package com.example.dotcall_android.model;

import com.example.dotcall_android.model.Summary;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SummaryUser extends RealmObject {


    private String callReceiverUsername;

    private String callReceiverName;


    private String recentSummary;
    private String recentTime;



    public SummaryUser() {
    }

    public SummaryUser(String callReceiverUsername, String callReceiverName, String recentSummary, String recentTime) {
        this.callReceiverUsername = callReceiverUsername;
        this.callReceiverName = callReceiverName;
        this.recentSummary = recentSummary;
        this.recentTime = recentTime;
    }



    public String getCallReceiverUsername() {
        return callReceiverUsername;
    }

    public void setCallReceiverUsername(String callReceiverUsername) {
        this.callReceiverUsername = callReceiverUsername;
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
}
