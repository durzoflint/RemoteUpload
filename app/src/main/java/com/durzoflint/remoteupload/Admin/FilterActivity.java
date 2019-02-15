package com.durzoflint.remoteupload.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.durzoflint.remoteupload.R;

public class FilterActivity extends AppCompatActivity {
    String email;
    String fromDate = "", toDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        email = getIntent().getStringExtra("email");

        final TextView from = findViewById(R.id.from);
        final TextView to = findViewById(R.id.to);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(FilterActivity.this);
                final View datePicker = inflater.inflate(R.layout.view_datepicker, null);
                new AlertDialog.Builder(FilterActivity.this)
                        .setTitle("Add Record")
                        .setView(datePicker)
                        .setMessage("\nEnter Date, Item Name and Cost\n")
                        .setIcon(android.R.drawable.ic_menu_agenda)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatePicker picker = datePicker.findViewById(R.id.pickdate);
                                        String year = "" + picker.getYear();
                                        String month = "" + (picker.getMonth() + 1);
                                        String day = "" + picker.getDayOfMonth();
                                        if (month.length() == 1)
                                            month = "0" + month;
                                        if (day.length() == 1)
                                            day = "0" + day;
                                        from.setText(day + "/" + month + "/" + year);
                                        fromDate = year + "-" + month + "-" + day;
                                    }
                                })
                        .create().show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(FilterActivity.this);
                final View datePicker = inflater.inflate(R.layout.view_datepicker, null);
                new AlertDialog.Builder(FilterActivity.this)
                        .setTitle("Add Record")
                        .setView(datePicker)
                        .setMessage("\nEnter Date, Item Name and Cost\n")
                        .setIcon(android.R.drawable.ic_menu_agenda)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatePicker picker = datePicker.findViewById(R.id.pickdate);
                                        String year = "" + picker.getYear();
                                        String month = "" + (picker.getMonth() + 1);
                                        String day = "" + picker.getDayOfMonth();
                                        if (month.length() == 1)
                                            month = "0" + month;
                                        if (day.length() == 1)
                                            day = "0" + day;
                                        to.setText(day + "/" + month + "/" + year);
                                        toDate = year + "-" + month + "-" + day;
                                    }
                                })
                        .create().show();
            }
        });

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText agentNameET = findViewById(R.id.agent_name);
                EditText phoneET = findViewById(R.id.phone);
                String agentName = agentNameET.getText().toString();
                String phone = phoneET.getText().toString();

                if (fromDate.isEmpty() || toDate.isEmpty() || agentName.isEmpty() || phone.isEmpty())
                    Toast.makeText(FilterActivity.this, "Please enter the values before " +
                            "submitting", Toast.LENGTH_SHORT).show();
                else if (phone.length() != 10)
                    Toast.makeText(FilterActivity.this, "Enter a 10 digit phone number",
                            Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(FilterActivity.this, ListImagesActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("fromDate", fromDate);
                    intent.putExtra("toDate", toDate);
                    intent.putExtra("agentName", agentName);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                }
            }
        });
    }
}