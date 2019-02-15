package com.durzoflint.remoteupload.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.durzoflint.remoteupload.R;

public class AdminDashboardActivity extends AppCompatActivity {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        email = getIntent().getStringExtra("email");

        Button next = findViewById(R.id.next);
        Button createAgent = findViewById(R.id.create_agent);
        createAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, CreateAgentActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}