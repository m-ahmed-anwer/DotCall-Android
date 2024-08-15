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
import com.example.dotcall_android.manager.CallLogManager;
import com.example.dotcall_android.manager.SummaryManager;
import com.example.dotcall_android.model.CallLog;
import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.model.SummaryUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;

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
                CallLog newCallLog = new CallLog(friendName, getCurrentTime() , "10 s", "Outgoing", "status");
                CallLogManager.getInstance().addCallLog(newCallLog);

                String summaryText= "This is the summary DW";
                String topicText= "TITLE TITLE";
                String transcriptionText= "This is the transcription of the call";

                Summary summary1 = new Summary(
                        "maker@example.com",
                        "John Doe",
                        friendName,
                        friendEmail,
                        summaryText,
                        topicText,
                        getCurrentTime(),
                        transcriptionText
                );


                List<Summary> s1 = Arrays.asList(summary1);

                SummaryUser summaryUser = new SummaryUser(
                        friendEmail,
                        friendName,
                        topicText,
                        getCurrentTime(),
                        s1
                );

                SummaryManager.getInstance().addSummaryUser(summaryUser);

                Toast.makeText(requireContext(), "SumarryAdded", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), CallActivity.class));
            }
        });

        return view;
    }

    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }

}
