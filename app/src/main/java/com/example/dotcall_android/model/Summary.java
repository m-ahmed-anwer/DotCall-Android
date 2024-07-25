package com.example.dotcall_android.model;

import java.util.UUID;

public class Summary {

    private String id;
    private String callMakerEmail;
    private String callMakerName;
    private String callReceiverName;
    private String callReceiverEmail;
    private String summaryDetail;
    private String summaryTopic;
    private String time;
    private String transcription;


    public Summary(String callMakerEmail, String callMakerName,
                   String callReceiverName, String callReceiverEmail, String summaryDetail,
                   String summaryTopic, String time, String transcription) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.callMakerEmail = callMakerEmail;
        this.callMakerName = callMakerName;
        this.callReceiverName = callReceiverName;
        this.callReceiverEmail = callReceiverEmail;
        this.summaryDetail = summaryDetail;
        this.summaryTopic = summaryTopic;
        this.time = time;
        this.transcription = transcription;

    }

    public Summary() {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
    }

    public String getId() {
        return id;
    }

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



    public String getCallReceiverName() {
        return callReceiverName;
    }

    public void setCallReceiverName(String callReceiverName) {
        this.callReceiverName = callReceiverName;
    }

    public String getCallReceiverEmail() {
        return callReceiverEmail;
    }

    public void setCallReceiverEmail(String callReceiverEmail) {
        this.callReceiverEmail = callReceiverEmail;
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


}
