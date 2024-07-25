package com.example.dotcall_android.ui.friends.acceptFriend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dotcall_android.R;
import com.example.dotcall_android.model.Friend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AcceptFriend extends Fragment {

    private AcceptFriendAdapter adapter;
    private List<Friend> friendsList;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accept_friend, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.accept_friend_recycler);

        friendsList = new ArrayList<>();

        if (user != null) {
            String userEmail = user.getEmail();
            fetchAcceptFriends(userEmail);
        }

        adapter = new AcceptFriendAdapter(getContext(),getActivity(),friendsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void fetchAcceptFriends(String email) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/friends/getFriendsToAccept/" + email,null, new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            JSONArray friendsArray = response.getJSONArray("friendsToAccept");
                            friendsList.clear();
                            for (int i = 0; i < friendsArray.length(); i++) {
                                JSONObject friendObject = friendsArray.getJSONObject(i);
                                String name = friendObject.getString("name");
                                String username = friendObject.getString("username");
                                String email = friendObject.getString("email");
                                friendsList.add(new Friend(name, email, username));
                            }
                            Toast.makeText(getActivity(), "Friends Updated", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
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
                                new JSONObject(errorResponse);
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }

}
