package com.example.dotcall_android.singleton;

import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.model.SummaryUser;

public class DataManager {
    private static SummaryUser selectedSummaryUser;

    private static Summary selectedSummary;

    public static void setSelectedSummaryUser(SummaryUser summaryUser) {
        selectedSummaryUser = summaryUser;
    }

    public static SummaryUser getSelectedSummaryUser() {
        return selectedSummaryUser;
    }


    public static void setSelectedSummary(Summary summary) {
        selectedSummary = summary;
    }

    public static Summary getSelectedSummary() {
        return selectedSummary;
    }


}
