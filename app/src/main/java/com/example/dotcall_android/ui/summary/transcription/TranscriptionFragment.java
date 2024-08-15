package com.example.dotcall_android.ui.summary.transcription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotcall_android.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranscriptionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transcription, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.transcription_recycler);
        List<String> transcriptionItems = generateSampleData(); // Replace with your data
        TranscriptionAdapter adapter = new TranscriptionAdapter(transcriptionItems, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private List<String> generateSampleData() {
        // Generate or fetch your data here
        return Arrays.asList(
                "Transcription 1"
        );
    }
}

