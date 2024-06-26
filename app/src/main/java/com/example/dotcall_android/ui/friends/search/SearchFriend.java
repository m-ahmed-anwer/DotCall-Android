package com.example.dotcall_android.ui.friends.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentSearchFriendBinding;
import com.example.dotcall_android.model.Friend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SearchFriend extends Fragment {

    private SearchFriendsAdapter adapter;
    private FragmentSearchFriendBinding binding;
    private List<Friend> friendsList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_friend, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.search_friends);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        friendsList = new ArrayList<>();

        adapter = new SearchFriendsAdapter( friendsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_friend, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_friends);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFriends(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    searchFriends(newText);
                } else {
                    friendsList.clear();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }



    private void searchFriends(String text) {
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            Toast.makeText(getActivity(), "User not Authenticated", Toast.LENGTH_SHORT).show();
            return;
        }
        String userEmail = user.getEmail();
        try {
            jsonObject.put("email", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/friends/getAllFriends/" + text, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            JSONArray friendsArray = response.getJSONArray("matchingUsers");
                            friendsList.clear(); // Clear the list before adding new friends
                            for (int i = 0; i < friendsArray.length(); i++) {
                                JSONObject friendObject = friendsArray.getJSONObject(i);
                                String name = friendObject.getString("name");
                                String username = friendObject.getString("username");
                                String email = friendObject.getString("email");
                                friendsList.add(new Friend(name, email, username));
                            }
                            adapter.notifyDataSetChanged(); // Notify adapter of data change
                            Toast.makeText(getActivity(), "Friends Updated", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show(); // Show error message to user
                    }

                });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }


}












