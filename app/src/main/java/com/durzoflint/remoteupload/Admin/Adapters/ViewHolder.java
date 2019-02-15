package com.durzoflint.remoteupload.Admin.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.durzoflint.remoteupload.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView name, details, address;
    ImageView installation, measurement;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        details = itemView.findViewById(R.id.details);
        address = itemView.findViewById(R.id.address);
        measurement = itemView.findViewById(R.id.measurement);
        installation = itemView.findViewById(R.id.installation);
    }
}
