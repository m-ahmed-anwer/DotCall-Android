package com.example.dotcall_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpVerification extends AppCompatActivity {

    private ProgressDialog progressDialog;
    String verificationId = null;
    String otp=null;
    String phoneNum =null;
    String email=null;
    String fullName=null;
    String password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_verification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        if (intent.hasExtra("phoneNum")) {
            phoneNum = intent.getStringExtra("phoneNum");
            String text = "Enter the code sent to " + phoneNum;
            ((TextView) findViewById(R.id.instructionotp)).setText(text);
        }

        if (intent.hasExtra("name")) {
            fullName = intent.getStringExtra("name");
        }
        if (intent.hasExtra("email")) {
            email = intent.getStringExtra("email");
        }
        if (intent.hasExtra("password")) {
            password = intent.getStringExtra("password");
        }
        if (intent.hasExtra("verificationId")) {
            verificationId = intent.getStringExtra("verificationId");
        }


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



    public void performVerification(View v) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        otp = ((EditText) findViewById(R.id.otpText)).getText().toString().trim();

        //Intent i = new Intent(this, OtpVerification.class);
        if (otp.isEmpty()) {
            progressDialog.dismiss();
            toast("OTP cannot be empty");
            return;
        }
        if (otp.length() != 6) {
            progressDialog.dismiss();
            toast("OTP must be 6 digits long");
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

        if (email==null && fullName==null && password==null ){
            signInWithPhoneAuthCredential(credential);
        }else{
            createAccount(fullName,phoneNum,email,password,credential);
        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            toast("Verification done");
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                toast("Invalid OTP");
                            } else {
                                toast("Verification failed: " + task.getException().getMessage());
                            }
                        }
                    }
                });
    }

    private void createAccount(String name, String phoneNum, String email, String password, PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            signUp(name, phoneNum, email, password);
                        } else {
                            progressDialog.dismiss();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                toast("Invalid OTP");
                            } else {
                                toast("Verification failed: " + task.getException().getMessage());
                            }
                        }
                    }
                });
    }

    private void signUp(String name, String phoneNum,String  email, String password){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("phoneNumber", phoneNum);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/users/signup", jsonObject, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                toast("Account created successfully");
//                                startActivity(new Intent(OtpVerification.this, Home.class));
//                                finish();
                            } else {
                                String message = response.getString("message");
                                toast("Failed to create account: " + message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toast("Failed to create account: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        toast("Failed to create account: " + error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }


    public void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(OtpVerification.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
}