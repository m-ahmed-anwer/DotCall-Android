//package com.example.dotcall_android.ui.recent;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.dotcall_android.Summary;
//import com.example.dotcall_android.model.CallLog;
//import com.example.dotcall_android.pkg.CallLogDao;
//
//import java.util.List;
//
//public class RecentViewModel extends ViewModel {
//    private final CallLogDao callLogDao;
//    private final LiveData<List<CallLog>> allCallLogs;
//
//    public RecentViewModel() {
//        callLogDao = Summary.getDatabase().callLogDao();
//        allCallLogs = callLogDao.getAllCallLogs();
//    }
//
//    public LiveData<List<CallLog>> getAllCallLogs() {
//        return allCallLogs;
//    }
//
//    public void clearAllCallLogs() {
//        Summary.getDatabase().callLogDao().clearAllCallLogs();
//    }
//}
