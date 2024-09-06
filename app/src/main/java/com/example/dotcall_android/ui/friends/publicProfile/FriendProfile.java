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

                String summaryText= "Jordan attended an Ehta lecture on AI advancements and their application in various industries. Key points included the integration of AI in healthcare, diagnostics, and personalized treatment plans. Ethical concerns were discussed, including data privacy and bias. Notable guest speakers included Dr. Patel, who discussed AI in education. The lecture was recorded and available on the university's website.";
                String topicText= "Analysis: What's next for AI? Here's how it's being applied in different industries";
                String transcriptionText= "Hey, Jordan! How's it going? Hey, Alex! I'm good, thanks. How about you? I'm doing well. Did you attend the Ehta lecture yesterday? Yeah, I did. It was pretty interesting. Did you make it? Unfortunately, no. I had a prior commitment. What did I miss? Well, Ehta talked about the latest advancements in AI and how it's being applied in different industries. It was fascinating. That sounds awesome! Can you give me some highlights? Sure! One of the key points was about the integration of AI in healthcare, particularly in diagnostics and personalized treatment plans. Ehta showed some case studies where AI significantly improved patient outcomes. Wow, that’s impressive. Did they discuss any ethical concerns? Yes, Ehta emphasized the importance of addressing ethical issues, especially regarding data privacy and the potential for bias in AI systems. They also talked about the need for transparent and explainable AI. That's crucial. Were there any notable guest speakers? Yes, there were a couple of industry experts who shared their experiences and insights. One of them was Dr. Patel, who spoke about AI in education and how it's transforming the learning process. Nice! Did they mention any resources or papers we can check out? Definitely. They provided a list of recommended readings and some online courses to deepen our understanding of AI applications. I took some notes and can share them with you. That would be great, thanks! Do you know if the lecture was recorded? Yes, it was. They said the recording would be available on the university's website by the end of the week. Perfect, I’ll make sure to watch it. Thanks for filling me in, Jordan! No problem, Alex. Happy to help! Let’s catch up more about it once you’ve seen the lecture. Sounds like a plan. Talk to you later! Bye!";

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
