package com.example.project2group10real.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project2group10real.R;

public class SpendingLogViewHolder extends RecyclerView.ViewHolder {
    private final TextView spendingLogViewItem;
    private SpendingLogViewHolder(View spendingLogView ) {
        super (spendingLogView);
        spendingLogViewItem = spendingLogView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind(String text) {
        spendingLogViewItem.setText(text);
    }

    static SpendingLogViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spendinglog_recycler_item, parent, false);
        return new SpendingLogViewHolder(view);
    }
}
