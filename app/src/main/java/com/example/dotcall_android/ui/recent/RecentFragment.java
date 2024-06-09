package com.example.dotcall_android.ui.recent;

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

import com.example.dotcall_android.databinding.FragmentRecentsBinding;
import com.example.dotcall_android.model.CallLog;
import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.ui.summary.SummaryAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {

    private FragmentRecentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecentViewModel recentViewModel =
                new ViewModelProvider(this).get(RecentViewModel.class);

        binding = FragmentRecentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recentCallList = binding.recentCall;


        List<CallLog> callLogs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            callLogs.add(new CallLog("John Doe", "10:30 AM", "5m 30s", "Incoming", "Missed"));

        }

        // Set up the adapter
        RecentAdapter adapter = new RecentAdapter(callLogs, requireContext());
        recentCallList.setLayoutManager(new LinearLayoutManager(getContext()));
        recentCallList.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}