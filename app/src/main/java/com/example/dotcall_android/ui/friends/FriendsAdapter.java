package com.example.dotcall_android.ui.friends;

import com.example.dotcall_android.model.Friend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;

import java.util.List;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {

    private List<Friend> friendsList;

    public FriendsAdapter(List<Friend> friendsList) {
        this.friendsList = friendsList;
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
            Navigation.findNavController(v).navigate(R.id.action_friendFragment_to_friendProfile);
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_name);
        }
    }
}
