package com.example.rxandroid_rxjava_tutorial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxandroid_rxjava_tutorial.model.ObjectData;
import com.example.rxandroid_rxjava_tutorial.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private List<ObjectData> mListData;

    public DataAdapter(List<ObjectData> mListData) {
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        ObjectData objectData = mListData.get(position);
        if (objectData == null) {
            return;
        }

        holder.tvName.setText(objectData.getName());
        holder.tvPrice.setText(String.valueOf(objectData.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPrice;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
