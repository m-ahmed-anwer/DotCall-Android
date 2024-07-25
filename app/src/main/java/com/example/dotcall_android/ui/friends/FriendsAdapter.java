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

import com.example.dotcall_android.CallActivity;
import com.example.dotcall_android.R;

import com.example.dotcall_android.manager.CallLogManager;
import com.example.dotcall_android.manager.SummaryManager;
import com.example.dotcall_android.model.CallLog;
import com.example.dotcall_android.model.Friend;
import com.example.dotcall_android.model.Summary;
import com.example.dotcall_android.model.SummaryUser;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

        CallLog newCallLog = new CallLog(friend.getName(), getCurrentTime() , "10 s", "Outgoing", "status");
        CallLogManager.getInstance().addCallLog(newCallLog);

        String summaryText= "This is the summary DW";
        String topicText= "TITLE TITLE";
        String transcriptionText= "This is the transcription of the call";

        Summary summary1 = new Summary(
                "maker@example.com",
                "John Doe",
                friend.getName(),
                friend.getEmail(),
                summaryText,
                topicText,
                getCurrentTime(),
                transcriptionText
        );


        List<Summary> s1 = Arrays.asList(summary1);

        SummaryUser summaryUser = new SummaryUser(
                friend.getEmail(),
                friend.getName(),
                topicText,
                getCurrentTime(),
                s1
        );

        SummaryManager.getInstance().addSummaryUser(summaryUser);

        Toast.makeText(context, "SumarryAdded", Toast.LENGTH_SHORT).show();


        Intent callIntent = new Intent(context, CallActivity.class);
        context.startActivity(callIntent);
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
}
