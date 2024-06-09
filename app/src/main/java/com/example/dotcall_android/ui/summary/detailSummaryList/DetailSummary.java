package com.example.dotcall_android.ui.summary.detailSummaryList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dotcall_android.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class DetailSummary extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary_detail, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.summary_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> summaries = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            summaries.add("Detail Summary " + i);
        }

        DetailSummaryAdapter adapter = new DetailSummaryAdapter(summaries);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

