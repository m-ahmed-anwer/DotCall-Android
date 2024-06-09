package com.example.dotcall_android.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dotcall_android.R;
import com.example.dotcall_android.manager.UserManager;
import com.example.dotcall_android.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfile extends Fragment {

    private EditText nameEditText;
    private Button updateButton;
    private ProgressBar loadingView;
    User user= UserManager.getInstance().getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_update_profile, container, false);

        nameEditText = rootView.findViewById(R.id.name);
        updateButton = rootView.findViewById(R.id.createButton);
        loadingView = rootView.findViewById(R.id.loading_view);
        nameEditText.setText(user.getName());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateName();
            }
        });

        // Set up touch listener to hide keyboard when user touches outside the EditText
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideKeyboard();
                }
                return false;
            }
        });

        return rootView;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    private void updateName() {
        String name = nameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            return;
        }

        // Show loading view
        loadingView.setVisibility(View.VISIBLE);

        // Create the JSON object to send in the request
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
            loadingView.setVisibility(View.GONE);
            return;
        }



        // URL for the profile update endpoint
        String url = "https://dot-call-a7ff3d8633ee.herokuapp.com/users/editProfile/"+user.getId();

        // Create a request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Hide loading view
                        loadingView.setVisibility(View.GONE);

                        user.setName(name);
                        UserManager.getInstance().setCurrentUser(user);
                        // Show success message
                        Toast.makeText(getActivity(), "Name updated successfully", Toast.LENGTH_SHORT).show();

                        // Navigate back
                        NavHostFragment.findNavController(UpdateProfile.this).popBackStack();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Hide loading view
                        loadingView.setVisibility(View.GONE);

                        // Show error message
                        Toast.makeText(getActivity(), "Failed to update name", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }
}
