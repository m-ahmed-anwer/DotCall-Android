package com.example.dotcall_android.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentSummaryBinding;
import com.example.dotcall_android.manager.CallLogManager;
import com.example.dotcall_android.manager.SummaryManager;
import com.example.dotcall_android.model.CallLog;
import com.example.dotcall_android.model.SummaryUser;
import java.util.Arrays;
import java.util.List;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;
    private SummaryAdapter adapter;
    private List<SummaryUser> summaryUsers;
    private TextView noSummaryTextView;

    RecyclerView summaryRecyclerView ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        summaryRecyclerView = binding.summary;
        noSummaryTextView = root.findViewById(R.id.no_summary);

        summaryUsers = SummaryManager.getInstance().getAllSummaryUsers();
        adapter = new SummaryAdapter(summaryUsers);

        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        summaryRecyclerView.setAdapter(adapter);


        updateEmptyState();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_sumary);
        SearchView searchView = (SearchView) searchItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateEmptyState() {
        if (summaryUsers.isEmpty()) {
            noSummaryTextView.setVisibility(View.VISIBLE);
            summaryRecyclerView.setVisibility(View.GONE);
        } else {
            noSummaryTextView.setVisibility(View.GONE);
            summaryRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
