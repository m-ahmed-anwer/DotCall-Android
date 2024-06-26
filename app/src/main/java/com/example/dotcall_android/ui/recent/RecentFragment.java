package com.example.dotcall_android.ui.recent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentRecentsBinding;
import com.example.dotcall_android.model.CallLog;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RecentFragment extends Fragment {

    private FragmentRecentsBinding binding;
    private RecentAdapter adapter;
    private List<CallLog> callLogs;
    private TextView noRecentCallsTextView;
    private RecyclerView recentCallList;
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recentCallList = binding.recentCall;
        noRecentCallsTextView = root.findViewById(R.id.no_recent_calls);

        callLogs = new ArrayList<>();
        //fetchCallLogsFromRealm();

        adapter = new RecentAdapter(callLogs, requireContext());
        recentCallList.setLayoutManager(new LinearLayoutManager(getContext()));
        recentCallList.setAdapter(adapter);

        updateEmptyState();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (realm != null) {
            realm.close();
        }
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

        Toast.makeText(getActivity(), "All recent calls cleared", Toast.LENGTH_SHORT).show();
    }

    private void updateEmptyState() {
        if (callLogs.isEmpty()) {
            noRecentCallsTextView.setVisibility(View.VISIBLE);
            recentCallList.setVisibility(View.GONE);
        } else {
            noRecentCallsTextView.setVisibility(View.GONE);
            recentCallList.setVisibility(View.VISIBLE);
        }
    }

    private void fetchCallLogsFromRealm() {
        RealmResults<CallLog> results = realm.where(CallLog.class).findAll();
        callLogs.addAll(realm.copyFromRealm(results));
        adapter.notifyDataSetChanged();
    }
}
