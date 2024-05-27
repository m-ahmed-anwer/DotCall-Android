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

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccount extends AppCompatActivity {

    private ProgressDialog progressDialog;
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

        email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        fullName = ((EditText) findViewById(R.id.name)).getText().toString().trim();
        password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
        confirmPassword = ((EditText) findViewById(R.id.confirmpassword)).getText().toString().trim();

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

        checkEmailAvailability(email,fullName,password);

    }

    private void checkEmailAvailability(String email,String fullName,String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
        } catch (JSONException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "https://dot-call-a7ff3d8633ee.herokuapp.com/users/email", jsonObject, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Intent i = new Intent(CreateAccount.this, CreateAccountPhoneNumber.class);
                                i.putExtra("email", email);
                                i.putExtra("name", fullName);
                                i.putExtra("password", password);
                                startActivity(i);
                            } else {
                                toast("User with this email is already registered");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        toast("Error: " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
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