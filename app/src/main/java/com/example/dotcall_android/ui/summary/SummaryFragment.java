package com.example.dotcall_android.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentSummaryBinding;
import com.example.dotcall_android.model.Summary;
import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;
    private SummaryAdapter adapter;
    private List<Summary> summaries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView summaryRecyclerView = binding.summary;

        summaries = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            summaries.add(new Summary("Call Receiver Name " + i, "Call Receiver Username " + i, "Recent Summary " + i, "Recent Time " + i));
        }

        adapter = new SummaryAdapter(summaries);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        summaryRecyclerView.setAdapter(adapter);

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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
