package com.example.dotcall_android.ui.recent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentRecentsBinding;
import com.example.dotcall_android.model.CallLog;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {

    private FragmentRecentsBinding binding;
    private RecentAdapter adapter;
    private List<CallLog> callLogs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecentViewModel recentViewModel =
                new ViewModelProvider(this).get(RecentViewModel.class);

        binding = FragmentRecentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);

        final RecyclerView recentCallList = binding.recentCall;

        callLogs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            callLogs.add(new CallLog("John Doe", "10:30 AM", "5m 30s", "Incoming", "Missed"));
        }

        // Set up the adapter
        adapter = new RecentAdapter(callLogs, requireContext());
        recentCallList.setLayoutManager(new LinearLayoutManager(getContext()));
        recentCallList.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recents, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Delete")
                .setMessage("Do you want to clear all recent calls?")
                .setPositiveButton("OK", (dialog, which) -> {
                    clearCallLogs();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void clearCallLogs() {
        callLogs.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "All recent calls cleared", Toast.LENGTH_SHORT).show();
    }
}
