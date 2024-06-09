package com.example.dotcall_android.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.dotcall_android.R;
import com.example.dotcall_android.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> implements Filterable {

    private List<Friend> friendsList;
    private List<Friend> friendsListFull;

    public FriendsAdapter(List<Friend> friendsList) {
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

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_name);
        }
    }
}
