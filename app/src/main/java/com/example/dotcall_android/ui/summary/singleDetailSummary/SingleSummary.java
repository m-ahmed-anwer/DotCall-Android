package com.example.dotcall_android.ui.summary.singleDetailSummary;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.dotcall_android.R;

public class SingleSummary extends Fragment {

    private TextView textSummaryTitle;
    private TextView textSummary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_summary, container, false);

        textSummaryTitle = view.findViewById(R.id.textSummarySpeaker);
        textSummary = view.findViewById(R.id.textSummary);

        // Set onClickListeners for share and copy buttons
        Button buttonShare = view.findViewById(R.id.buttonShare);
        Button buttonCopy = view.findViewById(R.id.buttonCopy);

        buttonShare.setOnClickListener(v -> performShare());
        buttonCopy.setOnClickListener(v -> performCopy());

        // Set OnLongClickListener for textSummaryTitle and textSummary
        textSummaryTitle.setOnLongClickListener(v -> {
            copyToClipboard(textSummaryTitle.getText().toString());
            return true;
        });

        textSummary.setOnLongClickListener(v -> {
            copyToClipboard(textSummary.getText().toString());
            return true;
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sumary, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_summary);

        if (item.getItemId() == R.id.actionTranscription) {
            navController.navigate(R.id.action_nav_single_summary_to_nav_single_transcription);
            return true;
        } else if (item.getItemId() == R.id.actionRecording) {
            navController.navigate(R.id.action_nav_single_summary_to_nav_single_recording);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void performShare() {
        String title = textSummaryTitle.getText().toString();
        String summary = textSummary.getText().toString();
        String shareText = title + "\n" + summary;

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void performCopy() {
        String title = textSummaryTitle.getText().toString();
        String summary = textSummary.getText().toString();
        String copyText = title + "\n" + summary;

        copyToClipboard(copyText);
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);

    }
}
