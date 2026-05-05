package com.example.project2group10real.viewHolders;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.project2group10real.database.entities.SpendingLog;

public class SpendingLogAdapter extends ListAdapter<SpendingLog, SpendingLogViewHolder> {

    public SpendingLogAdapter(@NonNull DiffUtil.ItemCallback<SpendingLog> difCallBack) {
        super(difCallBack);
    }

    @NonNull
    @Override
    public SpendingLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SpendingLogViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingLogViewHolder holder, int position) {
        SpendingLog current = getItem(position);
        holder.bind(current.toString());
    }

    public static class SpendingLogDiff extends DiffUtil.ItemCallback<SpendingLog> {
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull SpendingLog oldItem, @NonNull SpendingLog newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areItemsTheSame(@NonNull SpendingLog oldItem, @NonNull SpendingLog newItem) {
            return oldItem.equals(newItem);
        }
    }
}
