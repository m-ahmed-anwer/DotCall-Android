package com.example.dotcall_android.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.databinding.FragmentSummaryBinding;
import com.example.dotcall_android.model.Summary;

import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SummaryViewModel summaryViewModel =
                new ViewModelProvider(this).get(SummaryViewModel.class);

        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView summary = binding.summary;

        List<Summary> summaries = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            summaries.add(new Summary("Call Receiver Name " + i, "Call Receiver Username " + i, "Recent Summary " + i, "Recent Time " + i));
        }

        SummaryAdapter adapter = new SummaryAdapter(summaries);
        summary.setLayoutManager(new LinearLayoutManager(getContext()));
        summary.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
