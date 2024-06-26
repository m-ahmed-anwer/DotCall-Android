package com.example.dotcall_android.ui.summary.detailSummaryList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dotcall_android.R;
import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.model.SummaryUser;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailSummary extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary_detail, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.time_transcription);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        SummaryUser user1 = new SummaryUser("ahmed11","Ahmed Anwer", "User 1", "user1");
        SummaryUser user2 = new SummaryUser("ahmed22","Ahmed", "User 2", "user2");
        SummaryUser user3 = new SummaryUser("ahmed33","Anwer", "User 3", "user3");

        // Create 9 Summary instances (3 for each user)
        List<Summary> summaries = new ArrayList<>();
        summaries.add(new Summary("email1", "name1", "username1", "recivername1", "reciverusername1", "detail1", "topic1", "time1", "transcription1", Arrays.asList(user1, user2, user3)));
        summaries.add(new Summary("email2", "name2", "username2", "recivername2", "reciverusername2", "detail2", "topic2", "time2", "transcription2", Arrays.asList(user1, user2, user3)));
        summaries.add(new Summary("email3", "name3", "username3", "recivername3", "reciverusername3", "detail3", "topic3", "time3", "transcription3", Arrays.asList(user1, user2, user3)));


        DetailSummaryAdapter adapter = new DetailSummaryAdapter(summaries);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

