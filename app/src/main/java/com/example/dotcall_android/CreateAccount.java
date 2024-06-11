package com.example.dotcall_android;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CreateAccount extends AppCompatActivity {

    private ProgressDialog progressDialog;

    String username=null;
    String email=null;
    String fullName=null;
    String password=null;
    String confirmPassword=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
    public void performCreateAccount(View v){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        username = ((EditText) findViewById(R.id.username)).getText().toString().trim();
        email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        fullName = ((EditText) findViewById(R.id.name)).getText().toString().trim();
        password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
        confirmPassword = ((EditText) findViewById(R.id.confirmpassword)).getText().toString().trim();

        if (username.isEmpty()) {
            progressDialog.dismiss();
            toast("Username cannot be empty");
            return;
        }
        if (email.isEmpty()) {
            progressDialog.dismiss();
            toast("Email cannot be empty");
            return;
        }
        if (fullName.isEmpty()) {
            progressDialog.dismiss();
            toast("Full Name cannot be empty");
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
        if (username.length() < 6) {
            progressDialog.dismiss();
            toast("Username must be at least 6 characters long");
            return;
        }
        if (password.length() < 6) {
            progressDialog.dismiss();
            toast("Password must be at least 6 characters long");
            return;
        }
        if (!password.equals(confirmPassword)) {
            progressDialog.dismiss();
            toast("Passwords do not match");
            return;
        }

        signupUpser(fullName,email,username,password);

    }

    private void signupUpser(String name,String emailPas,String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("email", emailPas);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/users/signup", jsonObject, new Response.Listener<JSONObject>(){

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
                                signUpFirebase(emailPas,password);
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

    private void signUpFirebase(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CreateAccount.this, "Verification email sent.",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(CreateAccount.this, Login.class));
                                            }
                                        }
                                    });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(CreateAccount.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(CreateAccount.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
}