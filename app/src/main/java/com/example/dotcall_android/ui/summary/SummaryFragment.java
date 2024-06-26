package com.example.dotcall_android.ui.summary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentSummaryBinding;
import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.model.SummaryUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;
    private SummaryAdapter adapter;
    private List<SummaryUser> summaryUsers;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView summaryRecyclerView = binding.summary;

        // Create 3 different SummaryUser instances
        SummaryUser user1 = new SummaryUser("ahmed11","Ahmed Anwer", "User 1", "user1");
        SummaryUser user2 = new SummaryUser("ahmed22","Ahmed", "User 2", "user2");
        SummaryUser user3 = new SummaryUser("ahmed33","Anwer", "User 3", "user3");

        // Create 9 Summary instances (3 for each user)
        List<Summary> summaries = new ArrayList<>();
        summaries.add(new Summary("email1", "name1", "username1", "recivername1", "reciverusername1", "detail1", "topic1", "time1", "transcription1", Arrays.asList(user1, user2, user3)));
        summaries.add(new Summary("email2", "name2", "username2", "recivername2", "reciverusername2", "detail2", "topic2", "time2", "transcription2", Arrays.asList(user1, user2, user3)));
        summaries.add(new Summary("email3", "name3", "username3", "recivername3", "reciverusername3", "detail3", "topic3", "time3", "transcription3", Arrays.asList(user1, user2, user3)));


        adapter = new SummaryAdapter(Arrays.asList(user1, user2, user3));

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
