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

        import java.util.List;


public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    private List<Summary> summaries;

    public SummaryAdapter(List<Summary> summaries) {
        this.summaries = summaries;
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

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView summaryTitle;
        TextView summaryDetail;
        TextView summaryTime;

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            summaryTitle = itemView.findViewById(R.id.summary_title);
            summaryTime = itemView.findViewById(R.id.summary_time);
        }
    }
}

