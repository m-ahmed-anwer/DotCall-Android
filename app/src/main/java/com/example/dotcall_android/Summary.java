package com.example.dotcall_android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.dotcall_android.manager.UserManager;
import com.example.dotcall_android.model.User;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotcall_android.databinding.ActivitySummaryBinding;

public class Summary extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivitySummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarSummary.toolbar);
        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_recents, R.id.nav_contacts)
                .setOpenableLayout(drawer)
                .build();


//        View mainView = findViewById(R.id.container);
//        mainView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    hideKeyboard();
//                }
//                return false;
//            }
//        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_summary);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Update header text
        View headerView = navigationView.getHeaderView(0);
        TextView nameTextView = headerView.findViewById(R.id.name);
        TextView emailTextView = headerView.findViewById(R.id.email);

        User user = new User("id","name","email","username","createdAt","notification","f","haptic");
        UserManager.getInstance().setCurrentUser(user);
        user = UserManager.getInstance().getCurrentUser();
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.summary, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_summary);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Summary.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
}