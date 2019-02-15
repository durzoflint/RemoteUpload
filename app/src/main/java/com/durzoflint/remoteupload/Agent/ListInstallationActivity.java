package com.durzoflint.remoteupload.Agent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.durzoflint.remoteupload.Agent.Adapters.Adapter;
import com.durzoflint.remoteupload.Agent.Adapters.ClickListener;
import com.durzoflint.remoteupload.Agent.Adapters.Data;
import com.durzoflint.remoteupload.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListInstallationActivity extends AppCompatActivity {
    static final int TAKE_PIC = 1;
    List<Data> list;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_installation);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        list = new ArrayList<>();
        new FetchMeasurment().execute(email);
    }

    void setupRecyclerView(final List<Data> list) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Adapter adapter = new Adapter(this, list, new ClickListener() {
            @Override
            public void onCLick(int position) {
                Intent intent = new Intent(ListInstallationActivity.this,
                        UploadInstallationImageActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("id", list.get(position).id);
                startActivityForResult(intent, TAKE_PIC);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PIC && resultCode == RESULT_OK) {
            finish();
        }
    }

    class FetchMeasurment extends AsyncTask<String, Void, Void> {
        String baseUrl = "http://www.remoteupload.co.in/api/", webPage = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ListInstallationActivity.this, "Please Wait",
                    "Fetching Data");
        }

        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                String myURL =
                        baseUrl + "fetchmeasurement.php?email=" + strings[0];
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
            while (webPage.contains("<br>")) {
                int brI = webPage.indexOf("<br>");
                String id = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);
                brI = webPage.indexOf("<br>");
                String address = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);
                brI = webPage.indexOf("<br>");
                String image = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);
                brI = webPage.indexOf("<br>");
                String shopName = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);
                brI = webPage.indexOf("<br>");
                String details = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);

                list.add(new Data(id, image, address, shopName, details));
            }
            setupRecyclerView(list);
        }
    }
}