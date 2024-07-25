package com.example.dotcall_android.manager;

import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.model.SummaryUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SummaryManager {

    private static SummaryManager instance;

    private List<Summary> summaries;
    private List<SummaryUser> summaryUsers;

    public static synchronized SummaryManager getInstance() {
        if (instance == null) {
            instance = new SummaryManager();
        }
        return instance;
    }

    public SummaryManager() {
        summaries = new ArrayList<>();
        summaryUsers = new ArrayList<>();
    }

    public void addSummaryUser(SummaryUser summaryUser) {
        Optional<SummaryUser> existingUser = getSummaryUserByEmail(summaryUser.getCallReceiverEmail());

        if (existingUser.isPresent()) {
            SummaryUser user = existingUser.get();
            List<Summary> updatedSummaries = new ArrayList<>(user.getSummary());
            updatedSummaries.addAll(summaryUser.getSummary());
            user.setRecentTime(summaryUser.getRecentTime());
            user.setRecentSummary(summaryUser.getRecentSummary());
            user.setSummary(updatedSummaries);
        } else {
            summaryUsers.add(summaryUser);
        }
    }


    public List<Summary> getSummariesByCallMakerEmail(String email) {
        List<Summary> userSummaries = new ArrayList<>();
        for (Summary summary : summaries) {
            if (summary.getCallMakerEmail().equals(email)) {
                userSummaries.add(summary);
            }
        }
        return userSummaries;
    }

    public boolean deleteSummary(String id) {
        return summaries.removeIf(summary -> summary.getId().equals(id));
    }

    // Retrieve all SummaryUser objects
    public List<SummaryUser> getAllSummaryUsers() {
        return new ArrayList<>(summaryUsers);
    }

    // Retrieve a specific SummaryUser by email
    public Optional<SummaryUser> getSummaryUserByEmail(String email) {
        return summaryUsers.stream()
                .filter(summaryUser -> summaryUser.getCallReceiverEmail().equals(email))
                .findFirst();
    }

    // Update a SummaryUser
    public boolean updateSummaryUser(String email, SummaryUser updatedSummaryUser) {
        for (int i = 0; i < summaryUsers.size(); i++) {
            if (summaryUsers.get(i).getCallReceiverEmail().equals(email)) {
                summaryUsers.set(i, updatedSummaryUser);
                return true;
            }
        }
        return false;
    }

    // Delete a SummaryUser
    public boolean deleteSummaryUser(String email) {
        return summaryUsers.removeIf(summaryUser -> summaryUser.getCallReceiverEmail().equals(email));
    }
}
