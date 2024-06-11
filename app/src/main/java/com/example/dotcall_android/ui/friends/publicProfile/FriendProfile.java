package com.example.dotcall_android.ui.friends.publicProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dotcall_android.CallActivity;
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

        Button summaryButton = view.findViewById(R.id.summary_button);
        Button callButton = view.findViewById(R.id.call_button);

        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action for summary button click
                Toast.makeText(getActivity(), "Summary button clicked", Toast.LENGTH_SHORT).show();
                // Add any other actions you want to perform here
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CallActivity.class));
            }
        });

        return view;
    }
}
