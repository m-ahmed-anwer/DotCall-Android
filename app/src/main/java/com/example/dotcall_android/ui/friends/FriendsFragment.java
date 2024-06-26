package com.example.dotcall_android.ui.friends;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentFriendsBinding;
import com.example.dotcall_android.manager.UserManager;
import com.example.dotcall_android.model.Friend;
import com.example.dotcall_android.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;
    private FriendsAdapter adapter;
    private RequestQueue requestQueue;
    private List<Friend> friendsList;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        requestQueue = Volley.newRequestQueue(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView friendsRecyclerView = binding.friends;
        friendsList = new ArrayList<>();


        if (user != null) {
            String userEmail = user.getEmail();
            fetchFriends(userEmail);
        }

        adapter = new FriendsAdapter(getContext(), friendsList);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendsRecyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_friends, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_summary);

        if (item.getItemId() == R.id.action_find_friends) {
            navController.navigate(R.id.action_friendFragment_to_friendSearch);
            return true;
        }else if(item.getItemId() == R.id.action_accept_request){
            navController.navigate(R.id.action_friendFragment_to_friendAccept);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }


    private void fetchFriends(String email) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/friends/getFriends/" + email,null, new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            JSONArray friendsArray = response.getJSONArray("friends");
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
