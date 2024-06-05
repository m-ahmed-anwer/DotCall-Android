package com.example.dotcall_android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dotcall_android.manager.UserManager;
import com.example.dotcall_android.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;


public class Login extends AppCompatActivity {

    private  ProgressDialog progressDialog;
    String email =null;
    String password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseApp.initializeApp(this);

//        EditText phoneEditText = findViewById(R.id.phoneNum);
//        EditText passwordEditText = findViewById(R.id.password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        View mainView = findViewById(R.id.container);
        mainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideKeyboard();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void performLogin(View v){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        email =((EditText)findViewById(R.id.email)).getText().toString().trim();
        password= ((EditText)findViewById(R.id.password)).getText().toString().trim();

        if (email.isEmpty()) {
            progressDialog.dismiss();
            toast("Email cannot be empty");
            return;
        }
        if (password.isEmpty()) {
            progressDialog.dismiss();
            toast("Password cannot be empty");
            return;
        }

        if (!isValidEmail(email)) {
            progressDialog.dismiss();
            toast("Invalid email format");
            return;
        }
        if (password.length() < 6) {
            progressDialog.dismiss();
            toast("Password must be at least 6 characters long");
            return;
        }

        loginUser(email,password);
//        progressDialog.dismiss();


    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loginUser(String phoneNumber, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/users/login", jsonObject, new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                            JSONObject userObj = response.getJSONObject("user");
                            String id = userObj.getString("_id");
                            String name = userObj.getString("name");
                            String email = userObj.getString("email");
                            String username = userObj.getString("username");
                            String createdAt = userObj.getString("createdAt");
                            JSONObject generalSettingsObj = userObj.getJSONObject("generalSettings");
                            String notification = generalSettingsObj.getString("notification");
                            String faceId = generalSettingsObj.getString("faceId");
                            String haptic = generalSettingsObj.getString("haptic");

                            User user = new User(id,name,email,username,createdAt,notification,faceId,haptic);
                            UserManager.getInstance().setCurrentUser(user);
                            signInFirebase(email,password);
                            } else {
                                String message = response.getString("message");
                                progressDialog.dismiss();
                                toast(message);
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
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
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                errorMessage = jsonObject.getString("message");
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();
                        toast(errorMessage);
                    }

                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void signInFirebase(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                new AlertDialog.Builder(Login.this)
                                        .setTitle("Email not verified")
                                        .setMessage("Please verify your email before Login.")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    public void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Login.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

}