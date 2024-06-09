package com.example.dotcall_android.ui.friends.acceptFriend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dotcall_android.R;
import com.example.dotcall_android.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class AcceptFriend extends Fragment {

    private AcceptFriendAdapter adapter;
    private List<Friend> friendsList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accept_friend, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.accept_friend_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        friendsList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            friendsList.add(new Friend("Friend " + i, "friend" + i + "@example.com", "username" + i));
        }



        adapter = new AcceptFriendAdapter(friendsList);
        recyclerView.setAdapter(adapter);

        return view;
    }



}
