package com.durzoflint.remoteupload.Agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.durzoflint.remoteupload.R;

public class AgentDashboardActivity extends AppCompatActivity {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_dashboard);

        email = getIntent().getStringExtra("email");

        TextView measurment = findViewById(R.id.measurement);
        measurment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgentDashboardActivity.this, AddPictureActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        TextView installation = findViewById(R.id.installation);
    }
}