package com.durzoflint.remoteupload.Admin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.durzoflint.remoteupload.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Data> list;
    private Context context;

    public Adapter(Context context, List<Data> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_data, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).shopName);
        holder.details.setText(list.get(position).details);
        holder.address.setText(list.get(position).address);
        Glide.with(context).load(list.get(position).measurementImage).into(holder.measurement);
        Glide.with(context).load(list.get(position).installationImage).into(holder.installation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
