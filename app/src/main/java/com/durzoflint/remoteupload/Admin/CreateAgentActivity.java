package com.durzoflint.remoteupload.Admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.durzoflint.remoteupload.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateAgentActivity extends AppCompatActivity {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_agent);

        email = getIntent().getStringExtra("email");
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameET = findViewById(R.id.name);
                EditText emailET = findViewById(R.id.email);
                EditText phoneET = findViewById(R.id.phone);
                EditText passwordET = findViewById(R.id.password);
                EditText companyET = findViewById(R.id.company);
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String phone = phoneET.getText().toString();
                String password = passwordET.getText().toString();
                String company = companyET.getText().toString();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || company.isEmpty())
                    Toast.makeText(CreateAgentActivity.this, "Enter the details and then submit",
                            Toast.LENGTH_SHORT).show();
                else if (phone.length() != 10)
                    Toast.makeText(CreateAgentActivity.this, "Enter a 10 digit phone number",
                            Toast.LENGTH_SHORT).show();
                else
                    new CreateAgent().execute(name, email, company, password, phone);
            }
        });
    }

    class CreateAgent extends AsyncTask<String, Void, Void> {
        String baseUrl = "http://www.remoteupload.co.in/api/", webPage = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CreateAgentActivity.this, "Sending Data",
                    "Please " +
                    "Wait");
        }

        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                String myURL =
                        baseUrl + "addagent.php?name=" + strings[0] + "&email=" + strings[1]
                                + "&company=" + strings[2] + "&password=" + strings[3] + "&phone" +
                                "=" + strings[4];
                myURL = myURL.replaceAll(" ", "%20");
                myURL = myURL.replaceAll("\'", "%27");
                myURL = myURL.replaceAll("\'", "%22");
                myURL = myURL.replaceAll("\\+'", "%2B");
                myURL = myURL.replaceAll("\\(", "%28");
                myURL = myURL.replaceAll("\\)", "%29");
                myURL = myURL.replaceAll("\\{", "%7B");
                myURL = myURL.replaceAll("\\}", "%7B");
                myURL = myURL.replaceAll("\\]", "%22");
                myURL = myURL.replaceAll("\\[", "%22");
                url = new URL(myURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection
                        .getInputStream()));
                String data;
                while ((data = br.readLine()) != null)
                    webPage = webPage + data;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            progressDialog.dismiss();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (webPage.equals("success")) {
                Toast.makeText(CreateAgentActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Toast.makeText(CreateAgentActivity.this, "Failed",
                        Toast.LENGTH_SHORT).show();
        }
    }
}