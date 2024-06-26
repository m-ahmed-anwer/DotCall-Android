






package com.example.dotcall_android.ui.friends.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;
import com.example.dotcall_android.model.Friend;
import com.example.dotcall_android.ui.friends.FriendsAdapter;
import com.example.dotcall_android.ui.friends.acceptFriend.AcceptFriendAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFriendsAdapter extends RecyclerView.Adapter<SearchFriendsAdapter.ViewHolder> {
    private Context context;
    private List<Friend> friendsList;

    public SearchFriendsAdapter( List<Friend> friendsList) {
        this.friendsList = friendsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = friendsList.get(position);
        holder.name.setText(friend.getName());
        holder.username.setText(friend.getUsername());
        holder.addButton.setOnClickListener(v -> performAccept(holder, friend));
    }

    private void performAccept(SearchFriendsAdapter.ViewHolder holder, Friend friend) {
        Toast.makeText(holder.itemView.getContext(), "Searching " + friend.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView username;
        public Button addButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.public_friend_name);
            username = itemView.findViewById(R.id.public_friend_username);
            addButton = itemView.findViewById(R.id.public_friend_add);
        }
    }

    private void performAddFriend(Friend friend) {
        // Implement your add friend logic here, e.g., send a friend request to the server
        //Toast.makeText(SearchFriendsAdapter.this, "Friend request sent to " + friend.getName(), Toast.LENGTH_SHORT).show();
    }
}
