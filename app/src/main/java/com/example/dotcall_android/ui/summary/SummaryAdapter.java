package com.example.dotcall_android.ui.summary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotcall_android.R;
import com.example.dotcall_android.model.SummaryUser;
import com.example.dotcall_android.singleton.DataManager;

import java.util.ArrayList;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;


public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryUserViewHolder> implements Filterable {

    private final List<SummaryUser> summaryUsers;
    private final List<SummaryUser> summaryUsersFull;

    public SummaryAdapter(List<SummaryUser> summaryUsers) {
        this.summaryUsers = summaryUsers;
        this.summaryUsersFull = new ArrayList<>(summaryUsers); // Copy of the full list
    }

    @NonNull
    @Override
    public SummaryUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);
        return new SummaryUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryUserViewHolder holder, int position) {
        SummaryUser summaryUser = summaryUsers.get(position);
        holder.callReceiverName.setText(summaryUser.getCallReceiverName());
        holder.recentSummary.setText(summaryUser.getRecentSummary());
        holder.timeSummary.setText(summaryUser.getRecentTime());
        holder.itemView.setOnClickListener(v -> {
            DataManager.setSelectedSummaryUser(summaryUser);
            Navigation.findNavController(v).navigate(R.id.action_summaryFragment_to_summaryDetail);
        });
    }

    @Override
    public int getItemCount() {
        return summaryUsers.size();
    }


    @Override
    public Filter getFilter() {
        return summaryFilter;
    }

    private Filter summaryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SummaryUser> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(summaryUsersFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SummaryUser item : summaryUsersFull) {
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
            summaryUsers.clear();
            summaryUsers.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public static class SummaryUserViewHolder extends RecyclerView.ViewHolder {
        TextView callReceiverName;
        TextView recentSummary;
        TextView timeSummary;

        public SummaryUserViewHolder(@NonNull View itemView) {
            super(itemView);
            callReceiverName = itemView.findViewById(R.id.content_transcription);
            recentSummary = itemView.findViewById(R.id.detail_summary);
            timeSummary = itemView.findViewById(R.id.time_transcription_summary);

        }
    }
}



