package com.example.dotcall_android.ui.recent;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotcall_android.R;

import com.example.dotcall_android.model.CallLog;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {

    private List<CallLog> callLogs;
    private Context context;

    public RecentAdapter(List<CallLog> callLogs, Context context) {
        this.callLogs = callLogs;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_call, parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        CallLog callLog = callLogs.get(position);

        holder.callName.setText(callLog.getName());
        holder.callTime.setText(callLog.getTime());
        holder.callType.setText(callLog.getType());
    }

    @Override
    public int getItemCount() {
        return callLogs.size();
    }

    public static class RecentViewHolder extends RecyclerView.ViewHolder {
        TextView callName, callTime, callType;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);
            callName = itemView.findViewById(R.id.call_name);
            callTime = itemView.findViewById(R.id.call_time);
            callType = itemView.findViewById(R.id.call_type);
        }
    }
}
