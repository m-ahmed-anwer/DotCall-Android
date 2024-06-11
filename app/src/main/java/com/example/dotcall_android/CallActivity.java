package com.example.dotcall_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CallActivity extends AppCompatActivity {

    private boolean isMicMuted = false;
    private boolean isSpeakerOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton endButton = findViewById(R.id.endButton);
        ImageButton micPressButton = findViewById(R.id.micPress);
        ImageButton speakerCheckButton = findViewById(R.id.speakercheck);
        TextView mutedCheckText = findViewById(R.id.mutedcheck);
        ImageView muteButton = findViewById(R.id.muteButton);
        TextView callerNameTextView = findViewById(R.id.callerName);


        endButton.setOnClickListener(v -> {
            Intent summaryIntent = new Intent(CallActivity.this, Summary.class);
            startActivity(summaryIntent);
            finish();
        });

        micPressButton.setOnClickListener(v -> {
            isMicMuted = !isMicMuted;
            if (isMicMuted) {
                micPressButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
                mutedCheckText.setText(callerNameTextView.getText() + " muted this call");
            } else {
                micPressButton.setImageResource(android.R.drawable.ic_btn_speak_now);
                mutedCheckText.setText("");
            }

        });

        speakerCheckButton.setOnClickListener(v -> {
            isSpeakerOn = !isSpeakerOn;
            if (isSpeakerOn) {
                speakerCheckButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
            } else {
                speakerCheckButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
            }
        });
    }
}
