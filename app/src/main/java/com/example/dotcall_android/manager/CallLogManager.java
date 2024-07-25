package com.example.dotcall_android.manager;

import com.example.dotcall_android.model.CallLog;

import java.util.ArrayList;
import java.util.List;

public class CallLogManager {

    private static CallLogManager instance;
    private List<CallLog> callLogs;

    private CallLogManager() {
        callLogs = new ArrayList<>();
    }

    public static synchronized CallLogManager getInstance() {
        if (instance == null) {
            instance = new CallLogManager();
        }
        return instance;
    }

    public List<CallLog> getCallLogs() {
        return new ArrayList<>(callLogs);
    }

    public void addCallLog(CallLog callLog) {
        callLogs.add(callLog);
    }

    public void clearCallLogs() {
        callLogs.clear();
    }
}
