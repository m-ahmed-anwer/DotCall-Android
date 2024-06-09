package com.example.dotcall_android.ui.friends.publicProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dotcall_android.R;


public class FriendProfile extends Fragment {

    String friendName = null;
    String friendUsername = null;
    String friendEmail = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_profile, container, false);

        if (getArguments() != null) {
            friendName = getArguments().getString("friendName");
            friendUsername = getArguments().getString("friendUsername");
            friendEmail = getArguments().getString("friendEmail");


            TextView friendNameTextView = view.findViewById(R.id.friend_name);
            TextView friendUsernameTextView = view.findViewById(R.id.friend_username);

            friendNameTextView.setText(friendName);
            friendUsernameTextView.setText(friendUsername);
        }

        return view;
    }

}