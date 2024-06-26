package com.example.dotcall_android.ui.summary.transcription;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotcall_android.R;
import java.util.List;

public class TranscriptionAdapter extends RecyclerView.Adapter<TranscriptionAdapter.TranscriptionViewHolder> {

    private List<String> transcriptionItems;
    private Context context;

    public TranscriptionAdapter(List<String> transcriptionItems, Context context) {
        this.transcriptionItems = transcriptionItems;
        this.context = context;
    }

    @NonNull
    @Override
    public TranscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transcription, parent, false);
        return new TranscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TranscriptionViewHolder holder, int position) {
        String item = transcriptionItems.get(position);
        holder.contentTranscription.setText(item); // Assuming the item is the transcription content
        holder.timeTranscription.setText("Time " + (position + 1)); // Example time, you can adjust as needed
        holder.transcriptionSpeaker.setText("00:0" + (position + 1)); // Example time format, adjust as needed

        holder.contentTranscription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", holder.contentTranscription.getText().toString());
                clipboard.setPrimaryClip(clip);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return transcriptionItems.size();
    }

    public static class TranscriptionViewHolder extends RecyclerView.ViewHolder {
        TextView contentTranscription;
        TextView timeTranscription;
        TextView transcriptionSpeaker;

        public TranscriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTranscription = itemView.findViewById(R.id.content_transcription);
            timeTranscription = itemView.findViewById(R.id.time_transcription);
            transcriptionSpeaker = itemView.findViewById(R.id.transcription_speaker);
        }
    }
}
