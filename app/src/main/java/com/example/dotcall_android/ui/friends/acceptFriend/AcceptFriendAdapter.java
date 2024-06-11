// AcceptFriendAdapter.java

package com.example.dotcall_android.ui.friends.acceptFriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;
import com.example.dotcall_android.model.Friend;
import com.example.dotcall_android.ui.friends.FriendsAdapter;

import java.util.List;

public class AcceptFriendAdapter extends RecyclerView.Adapter<AcceptFriendAdapter.ViewHolder> {

    private List<Friend> friendsList;

    public AcceptFriendAdapter(List<Friend> friendsList) {
        this.friendsList = friendsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accept_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = friendsList.get(position);
        holder.name.setText(friend.getName());
        holder.username.setText(friend.getUsername());
        holder.acceptButton.setOnClickListener(v -> performAccept(holder, friend));
    }

    private void performAccept(AcceptFriendAdapter.ViewHolder holder, Friend friend) {
        Toast.makeText(holder.itemView.getContext(), "Accepting " + friend.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView username;
        public Button acceptButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.friend_name);
            username = itemView.findViewById(R.id.friend_username);
            acceptButton = itemView.findViewById(R.id.accept_friend);
        }
    }
}
