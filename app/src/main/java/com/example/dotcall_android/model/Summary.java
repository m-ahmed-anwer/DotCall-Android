package com.example.dotcall_android.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Summary extends RealmObject {

    @PrimaryKey
    @Required
    private String id;


    @NonNull
    private String callMakerEmail;

    private String callMakerName;
    private String callMakerUsername;
    private String callReciverName;
    private String callReciverUsername;
    private String summaryDetail;
    private String summaryTopic;
    private String time;
    private String transcription;

    private List<SummaryUser> summaryUser;


    public Summary(@NonNull String callMakerEmail, String callMakerName, String callMakerUsername,
                   String callReciverName, String callReciverUsername, String summaryDetail,
                   String summaryTopic, String time, String transcription,List<SummaryUser> summaryUser) {
        this.callMakerEmail = callMakerEmail;
        this.callMakerName = callMakerName;
        this.callMakerUsername = callMakerUsername;
        this.callReciverName = callReciverName;
        this.callReciverUsername = callReciverUsername;
        this.summaryDetail = summaryDetail;
        this.summaryTopic = summaryTopic;
        this.time = time;
        this.transcription = transcription;
        this.summaryUser = summaryUser;
    }

    public Summary() {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
    }


    @NonNull
    public String getCallMakerEmail() {
        return callMakerEmail;
    }

    public void setCallMakerEmail(@NonNull String callMakerEmail) {
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

    public String getSummaryDetail() {
        return summaryDetail;
    }

    public void setSummaryDetail(String summaryDetail) {
        this.summaryDetail = summaryDetail;
    }

    public String getSummaryTopic() {
        return summaryTopic;
    }

    public void setSummaryTopic(String summaryTopic) {
        this.summaryTopic = summaryTopic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public List<SummaryUser> getSummaryUser() {
        return summaryUser;
    }

    public void setSummaries(RealmList<SummaryUser> summaryUser) {
        this.summaryUser = summaryUser;
    }
}
