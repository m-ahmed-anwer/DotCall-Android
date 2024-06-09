package com.example.dotcall_android.ui.summary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotcall_android.R;

import com.example.dotcall_android.model.Summary;

import java.util.ArrayList;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> implements Filterable {

    private List<Summary> summaries;
    private List<Summary> summariesFull;

    public SummaryAdapter(List<Summary> summaries) {
        this.summaries = summaries;
        this.summariesFull = new ArrayList<>(summaries); // Copy of the full list
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        Summary summary = summaries.get(position);
        holder.summaryTitle.setText(summary.getRecentSummary());
        holder.summaryTime.setText(summary.getRecentTime());
        holder.itemView.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_summaryFragment_to_summaryDetail);
        });
    }

    @Override
    public int getItemCount() {
        return summaries.size();
    }

    @Override
    public Filter getFilter() {
        return summaryFilter;
    }

    private Filter summaryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Summary> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(summariesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Summary item : summariesFull) {
                    if (item.getRecentSummary().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            summaries.clear();
            summaries.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView summaryTitle;
        TextView summaryTime;

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            summaryTitle = itemView.findViewById(R.id.summary_title);
            summaryTime = itemView.findViewById(R.id.summary_time);
        }
    }
}
