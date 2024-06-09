package com.example.dotcall_android.ui.friends.search;

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
import com.example.dotcall_android.databinding.FragmentSearchFriendBinding;
import com.example.dotcall_android.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class SearchFriend extends Fragment {

    private SearchFriendsAdapter adapter;
    private FragmentSearchFriendBinding binding;
    private List<Friend> friendsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_friend, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.search_friends);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        friendsList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            friendsList.add(new Friend("Friend " + i, "friend" + i + "@example.com", "username" + i));
        }



        adapter = new SearchFriendsAdapter( friendsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_friend, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_friends);
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












