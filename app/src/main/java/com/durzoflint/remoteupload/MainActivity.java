package com.durzoflint.remoteupload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.durzoflint.remoteupload.Agent.AgentDashboardActivity;
import com.durzoflint.remoteupload.Agent.AgentLoginActivity;

public class MainActivity extends AppCompatActivity {
    int AGENT_LOGIN_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView admin = findViewById(R.id.admin);
        TextView agent = findViewById(R.id.agent);
        agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AgentLoginActivity.class),
                        AGENT_LOGIN_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AGENT_LOGIN_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, AgentDashboardActivity.class);
            intent.putExtra("email", data.getStringExtra("email"));
            startActivity(intent);
            finish();
        }
    }
}