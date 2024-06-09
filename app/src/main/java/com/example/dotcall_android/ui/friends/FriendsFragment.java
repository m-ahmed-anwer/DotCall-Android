package com.example.dotcall_android.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.databinding.FragmentFriendsBinding;
import com.example.dotcall_android.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FriendsViewModel contactViewModel =
                new ViewModelProvider(this).get(FriendsViewModel.class);

        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final RecyclerView friendsList = binding.friends;


        // Temporary list of friends
        List<Friend> friends = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            friends.add(new Friend("Friend " + i, "friend" + i + "@example.com", "username" + i));
        }

        // Set up the RecyclerView
        FriendsAdapter adapter = new FriendsAdapter(friends);
        friendsList.setLayoutManager(new LinearLayoutManager(getContext()));
        friendsList.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
