// AcceptFriendAdapter.java

package com.example.dotcall_android.ui.friends.acceptFriend;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dotcall_android.R;
import com.example.dotcall_android.model.Friend;
import com.example.dotcall_android.ui.friends.FriendsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AcceptFriendAdapter extends RecyclerView.Adapter<AcceptFriendAdapter.ViewHolder> {

    private List<Friend> friendsList;
    private final Context context;
    private final Activity activity;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    public AcceptFriendAdapter(Context context,Activity activity,List<Friend> friendsList) {
        this.context = context;
        this.activity = activity;
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
        addFriend(friend.getEmail());
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

    private void addFriend(String email) {
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            return;
        }
        String userEmail = user.getEmail();
        try {
            jsonObject.put("email", email);
            jsonObject.put("acceptingUserEmail", userEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/friends/acceptFriend", jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(context, "Friend Accepted, Please go back", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Error Accepting Friend", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error occurred";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String errorResponse = new String(error.networkResponse.data, "UTF-8");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show(); // Show error message to user
                    }

                });

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
