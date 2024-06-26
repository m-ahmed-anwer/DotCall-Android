package com.example.dotcall_android.dao;

import com.example.dotcall_android.model.CallLog;

import io.realm.Realm;
import io.realm.RealmResults;

public class CallLogDao {

    private Realm realm;

    public CallLogDao() {
        realm = Realm.getDefaultInstance();
    }

    public RealmResults<CallLog> getAllCallLogs() {
        return realm.where(CallLog.class).findAll();
    }

    public void addCallLog(CallLog callLog) {
        realm.executeTransaction(realm -> {
            realm.insert(callLog);
        });
    }

    public void deleteAllCallLogs() {
        realm.executeTransaction(realm -> {
            realm.delete(CallLog.class);
        });
    }

    public void close() {
        if (realm != null) {
            realm.close();
        }
    }
}
