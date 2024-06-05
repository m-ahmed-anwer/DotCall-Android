package com.example.dotcall_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dotcall_android.manager.AuthManager;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AuthManager authManager = AuthManager.getInstance();
//        if (authManager.isLoggedIn()) {
//            startActivity(new Intent(LaunchScreen.this, Summary.class));
//            finish();
//        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void createAccount(View v){
        Intent i = new Intent(this, CreateAccount.class);
        startActivity(i);
    }

    public void login(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

}