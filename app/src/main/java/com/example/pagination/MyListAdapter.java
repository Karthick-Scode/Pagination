package com.example.pagination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

    private Context context;
    private List<Model.data> dataList;

    public MyListAdapter(Context context, List<Model.data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cell_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model.data data = dataList.get(position);

        holder.email.setText(data.getEmail());
        holder.first.setText(data.getFirst_name());
        holder.last.setText(data.getLast_name());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email, first, last;
        public ViewHolder(View itemView) {
            super(itemView);

            this.email = (TextView) itemView.findViewById(R.id.tv_email);
            this.first = (TextView) itemView.findViewById(R.id.tv_first);
            this.last = (TextView) itemView.findViewById(R.id.tv_last);
        }
    }
}
