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
import com.example.dotcall_android.singleton.DataManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailSummary extends Fragment {

    List<Summary> summaries;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary_detail, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.time_transcription);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        SummaryUser summaryUser = DataManager.getSelectedSummaryUser();
        if (summaryUser != null) {
            summaries = summaryUser.getSummary();
        }

        DetailSummaryAdapter adapter = new DetailSummaryAdapter(summaries);
        recyclerView.setAdapter(adapter);
        return view;
    }
}

