package com.example.dotcall_android.ui.summary.recording;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.widget.Button;
import com.example.dotcall_android.R;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.Locale;

public class RecordingFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    private ProgressBar progressBar;
    private TextView tvTimer;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recording, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        progressBar = view.findViewById(R.id.progressBar);
        tvTimer = view.findViewById(R.id.tvTimer);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
        mediaPlayer.setLooping(true); // Set looping to true

        try {
            AssetFileDescriptor afd = requireContext().getResources().openRawResourceFd(R.raw.test_audio); // Replace with your MP3 resource
            if (afd != null) {
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                mediaPlayer.prepare();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set initial state
        isPlaying = false;
        handler = new Handler(Looper.getMainLooper());

        // Handle button clicks
        view.findViewById(R.id.btnPlayPause).setOnClickListener(v -> playPause());
        view.findViewById(R.id.btnSkipBack).setOnClickListener(v -> skipBack());
        view.findViewById(R.id.btnSkipForward).setOnClickListener(v -> skipForward());

        // Update progress bar and timer
        updateProgressBar();

        // Handle media player completion
        mediaPlayer.setOnCompletionListener(mp -> {
            // Restart playback if looping
            if (mediaPlayer.isLooping()) {
                mediaPlayer.start();
            } else {
                isPlaying = false;
                // Update UI (if needed)
            }
        });
    }

    private void playPause() {
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            ImageButton btnPlayPause = requireView().findViewById(R.id.btnPlayPause);
            btnPlayPause.setImageResource(R.drawable.play_circle);

        } else {
            mediaPlayer.start();
            isPlaying = true;
            ImageButton btnPlayPause = requireView().findViewById(R.id.btnPlayPause);
            btnPlayPause.setImageResource(R.drawable.pause_circle);
            updateProgressBar();
        }
    }

    private void skipBack() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int newPosition = currentPosition - 10000; // 10 seconds backward

        if (newPosition < 0) {
            newPosition = 0;
        }

        mediaPlayer.seekTo(newPosition);
        updateProgressBar();
    }

    private void skipForward() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int newPosition = currentPosition + 10000; // 10 seconds forward

        if (newPosition > mediaPlayer.getDuration()) {
            newPosition = mediaPlayer.getDuration();
        }

        mediaPlayer.seekTo(newPosition);
        updateProgressBar();
    }

    private void updateProgressBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && isPlaying) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int duration = mediaPlayer.getDuration();

                    // Update progress bar
                    if (duration > 0) {
                        long progress = (long) currentPosition * 100 / duration;
                        progressBar.setProgress((int) progress);
                    }

                    // Update timer
                    tvTimer.setText(String.format(Locale.getDefault(), "%s / %s",
                            millisecondsToTimer(currentPosition), millisecondsToTimer(duration)));

                    // Schedule the next update
                    handler.postDelayed(this, 1000); // Update every second
                }
            }
        }, 0); // Start immediately
    }

    private String millisecondsToTimer(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release resources when fragment is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacksAndMessages(null); // Remove all callbacks to avoid memory leaks
    }
}
