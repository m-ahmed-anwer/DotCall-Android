package com.example.dotcall_android.ui.friends;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dotcall_android.CallActivity;
import com.example.dotcall_android.R;

import com.example.dotcall_android.manager.CallLogManager;
import com.example.dotcall_android.manager.SummaryManager;
import com.example.dotcall_android.model.CallLog;
import com.example.dotcall_android.model.Friend;
import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.model.SummaryUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> implements Filterable {

    private final List<Friend> friendsList;
    private final List<Friend> friendsListFull;
    private final Context context;

    public FriendsAdapter(Context context, List<Friend> friendsList) {
        this.context = context;
        this.friendsList = friendsList;
        this.friendsListFull = new ArrayList<>(friendsList); // Copy of the full list
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friendsList.get(position);
        holder.nameTextView.setText(friend.getName());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("friendName", friend.getName());
            bundle.putString("friendUsername", friend.getUsername());
            bundle.putString("friendEmail", friend.getEmail());
            Navigation.findNavController(v).navigate(R.id.action_friendFragment_to_friendProfile, bundle);
        });

        holder.callButton.setOnClickListener(v -> performCall(friend));
    }

    private void performCall(Friend friend) {
        CallLog newCallLog = new CallLog(friend.getName(), getCurrentTime(), "10 s", "Outgoing", "status");
        CallLogManager.getInstance().addCallLog(newCallLog);

        // Transcription to send to the server
        String transcriptionText = "Hey, Jordan! How's it going? Hey, Alex! I'm good, thanks. How about you? I'm doing well. Did you attend the Ehta lecture yesterday? Yeah, I did. It was pretty interesting. Did you make it? Unfortunately, no. I had a prior commitment. What did I miss? Well, Ehta talked about the latest advancements in AI and how it's being applied in different industries. It was fascinating. That sounds awesome! Can you give me some highlights? Sure! One of the key points was about the integration of AI in healthcare, particularly in diagnostics and personalized treatment plans. Ehta showed some case studies where AI significantly improved patient outcomes. Wow, that’s impressive. Did they discuss any ethical concerns? Yes, Ehta emphasized the importance of addressing ethical issues, especially regarding data privacy and the potential for bias in AI systems. They also talked about the need for transparent and explainable AI. That's crucial. Were there any notable guest speakers? Yes, there were a couple of industry experts who shared their experiences and insights. One of them was Dr. Patel, who spoke about AI in education and how it's transforming the learning process. Nice! Did they mention any resources or papers we can check out? Definitely. They provided a list of recommended readings and some online courses to deepen our understanding of AI applications. I took some notes and can share them with you. That would be great, thanks! Do you know if the lecture was recorded? Yes, it was. They said the recording would be available on the university's website by the end of the week. Perfect, I’ll make sure to watch it. Thanks for filling me in, Jordan! No problem, Alex. Happy to help! Let’s catch up more about it once you’ve seen the lecture. Sounds like a plan. Talk to you later! Bye!";

        // Call the uploadTranscription method
        uploadTranscription(transcriptionText, new UploadCallback() {
            @Override
            public void onSuccess(Map<String, String> result) {
                // Handle the response from the server
                String summaryText = result.get("summary");
                String topicText = result.get("topics");
                String transcriptionResponse = result.get("transcription");
                String title = result.get("title");

                Summary summary1 = new Summary(
                        "ahmedanwer0094@gmail.com",
                        "Ahmed Anwer",
                        friend.getName(),
                        friend.getEmail(),
                        summaryText,
                        title,
                        getCurrentTime(),
                        transcriptionResponse
                );

                List<Summary> summaries = Arrays.asList(summary1);

                SummaryUser summaryUser = new SummaryUser(
                        friend.getEmail(),
                        friend.getName(),
                        topicText,
                        getCurrentTime(),
                        summaries
                );

                SummaryManager.getInstance().addSummaryUser(summaryUser);

                Toast.makeText(context, "Summary Added", Toast.LENGTH_SHORT).show();

                // Start the CallActivity
                Intent callIntent = new Intent(context, CallActivity.class);
                context.startActivity(callIntent);
            }

            @Override
            public void onError(Exception error) {
                // Handle error response
                Toast.makeText(context,  error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    @Override
    public Filter getFilter() {
        return friendFilter;
    }

    private Filter friendFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Friend> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(friendsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Friend item : friendsListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            friendsList.clear();
            friendsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button callButton;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_name);
            callButton = itemView.findViewById(R.id.friend_call);
        }
    }
    private void uploadTranscription(String transcription, UploadCallback callback) {
        String url = "http://127.0.0.1:5000/api/summarize/text"; // Your API endpoint

        // Create a new request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Create a JSON object to send with the request
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("transcription", transcription);
        } catch (JSONException e) {
            callback.onError(e);
            return;
        }

        // Create a JSON object request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                response -> {
                    try {
                        // Extract the values from the response
                        String transcriptionResponse = response.getString("transcription");
                        String summary = response.getString("summary");
                        String title = response.getString("title");
                        JSONArray topicsArray = response.getJSONArray("topics");

                        // Convert topics JSON array to a comma-separated string
                        List<String> topics = new ArrayList<>();
                        for (int i = 0; i < topicsArray.length(); i++) {
                            topics.add(topicsArray.getString(i));
                        }
                        String topicsString = String.join(", ", topics);

                        // Callback on success
                        Map<String, String> result = new HashMap<>();
                        result.put("transcription", transcriptionResponse);
                        result.put("summary", summary);
                        result.put("title", title);
                        result.put("topics", topicsString);

                        callback.onSuccess(result);

                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> {
                    // Handle errors
                    callback.onError(new Exception( error.getMessage()));
                }
        );

        // Set request timeout and retry policy
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000, // Timeout in milliseconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Add the request to the queue
        requestQueue.add(jsonObjectRequest);
    }

    // Define a callback interface for success and error handling
    interface UploadCallback {
        void onSuccess(Map<String, String> result);
        void onError(Exception error);
    }


}

