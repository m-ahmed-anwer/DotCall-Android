package com.example.dotcall_android.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dotcall_android.LaunchScreen;
import com.example.dotcall_android.R;
import com.example.dotcall_android.databinding.FragmentSettingsBinding;
import com.example.dotcall_android.manager.UserManager;
import com.example.dotcall_android.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        User currentUser = UserManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            ((TextView) binding.getRoot().findViewById(R.id.fullName)).setText(currentUser.getName());
            ((TextView) binding.getRoot().findViewById(R.id.email)).setText(currentUser.getEmail());
        } else {
            // User is not logged in
        }

        binding.logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout(view);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void signout(View v){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        UserManager.getInstance().clearCurrentUser();
        Toast.makeText(getContext(), "You have been Signed Out", Toast.LENGTH_LONG).show();
        Intent i= new Intent(getContext(), LaunchScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


}