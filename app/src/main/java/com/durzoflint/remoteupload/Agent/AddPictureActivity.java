package com.durzoflint.remoteupload.Agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.durzoflint.remoteupload.R;

public class AddPictureActivity extends AppCompatActivity {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

        email = getIntent().getStringExtra("email");

        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phone = findViewById(R.id.phone);
                String phoneNumber = phone.getText().toString();
                if (phoneNumber.length() != 10)
                    Toast.makeText(AddPictureActivity.this, "Invalid Phone Number. Enter a 10 " +
                                    "digit phone number",
                            Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(AddPictureActivity.this,
                            UploadPictureActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }
}