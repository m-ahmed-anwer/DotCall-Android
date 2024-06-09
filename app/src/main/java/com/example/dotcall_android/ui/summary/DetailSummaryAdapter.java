package com.example.dotcall_android.ui.summary;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;

import java.util.List;

public class DetailSummaryAdapter extends RecyclerView.Adapter<DetailSummaryAdapter.DetailSummaryViewHolder> {

    private List<String> summaries;

    public DetailSummaryAdapter(List<String> summaries) {
        this.summaries = summaries;
    }

    @NonNull
    @Override
    public DetailSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_summary, parent, false);
        return new DetailSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailSummaryViewHolder holder, int position) {
        String summary = summaries.get(position);
        holder.summaryTitle.setText(summary);
        holder.summaryTime.setText("summary"); // Example, you can customize this
    }

    @Override
    public int getItemCount() {
        return summaries.size();
    }

    public static class DetailSummaryViewHolder extends RecyclerView.ViewHolder {
        TextView summaryTitle;
        TextView summaryTime;

        public DetailSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            summaryTitle = itemView.findViewById(R.id.cdetail_summary_title);
            summaryTime = itemView.findViewById(R.id.detail_summary_time);
        }
    }
}
