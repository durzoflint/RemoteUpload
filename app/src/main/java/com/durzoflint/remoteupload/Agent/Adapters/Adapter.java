package com.durzoflint.remoteupload.Agent.Adapters;

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

    private Context context;
    private ClickListener clickListener;
    private List<Data> list;

    public Adapter(Context context, List<Data> list, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_measurement
                , viewGroup, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(list.get(position).address);
        Glide.with(context).load(list.get(position).imageUrl).into(holder.image);
        holder.shopName.setText(list.get(position).shopName);
        holder.details.setText(list.get(position).details);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
