package com.durzoflint.remoteupload.Agent.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.durzoflint.remoteupload.R;

import java.lang.ref.WeakReference;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    LinearLayout layout;
    TextView address;
    TextView shopName;
    TextView details;
    ImageView image;

    WeakReference<ClickListener> weakReference;

    public ViewHolder(@NonNull View itemView, ClickListener clickListener) {
        super(itemView);
        layout = itemView.findViewById(R.id.item);
        address = itemView.findViewById(R.id.address);
        shopName = itemView.findViewById(R.id.shop_name);
        details = itemView.findViewById(R.id.details);
        image = itemView.findViewById(R.id.image);

        weakReference = new WeakReference<>(clickListener);

        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        weakReference.get().onCLick(getAdapterPosition());
    }
}
