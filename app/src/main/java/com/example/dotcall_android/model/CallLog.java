package com.example.dotcall_android.model;

public class CallLog {
    private String name;
    private String time;
    private String duration;
    private String type;
    private String status;

    public CallLog(String name, String time, String duration, String type, String status) {
        this.name = name;
        this.time = time;
        this.duration = duration;
        this.type = type;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
