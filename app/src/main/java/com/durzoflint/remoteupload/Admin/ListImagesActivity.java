package com.durzoflint.remoteupload.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.durzoflint.remoteupload.Admin.Adapters.Adapter;
import com.durzoflint.remoteupload.Admin.Adapters.Data;
import com.durzoflint.remoteupload.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListImagesActivity extends AppCompatActivity {
    String email;
    List<Data> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_images);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        String from = intent.getStringExtra("fromDate");
        String to = intent.getStringExtra("toDate");
        String agentName = intent.getStringExtra("agentName");
        String phone = intent.getStringExtra("phone");

        list = new ArrayList<>();

        new FetchData().execute(agentName, phone, from, to);
    }

    void setupRecyclerView(List<Data> list) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Adapter adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    class FetchData extends AsyncTask<String, Void, Void> {
        String baseUrl = "http://www.remoteupload.co.in/api/", webPage = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ListImagesActivity.this, "Fetching Data",
                    "Please " +
                            "Wait");
        }

        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                String myURL =
                        baseUrl + "filter.php?name=" + strings[0] + "&phone=" + strings[1] +
                                "&from=" + strings[2] + "&to=" + strings[3];
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
                Log.d("Abhinav", myURL);
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
                String measurementImage = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);
                brI = webPage.indexOf("<br>");
                String shopName = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);
                brI = webPage.indexOf("<br>");
                String details = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);
                brI = webPage.indexOf("<br>");
                String installationImage = webPage.substring(0, brI);
                webPage = webPage.substring(brI + 4);

                list.add(new Data(id, address, measurementImage, installationImage, shopName,
                        details));
                setupRecyclerView(list);
            }
        }
    }
}