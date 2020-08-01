package com.codersofblvkn.criminaltagging.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static com.codersofblvkn.criminaltagging.Activities.MainActivity.API_URL_FCM;
import static com.codersofblvkn.criminaltagging.Activities.MainActivity.AUTH_KEY_FCM;

public class FCMTask extends AsyncTask<String, Void, Void> {

    final Context context;
    public FCMTask(Context context)
    {
        this.context=context;
    }

    @Override
    protected Void doInBackground(String... voids) {

        try {
            String result = "";
            URL url = new URL(API_URL_FCM);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();

            json.put("to", "/topics/alert");
            JSONObject info = new JSONObject();
            info.put("title", "Alert"); // Notification title
            info.put("body", voids[0]); // Notification
            // body
            json.put("notification", info);
            try {
                OutputStreamWriter wr = new OutputStreamWriter(
                        conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                result = "Success";
            } catch (Exception e) {
                e.printStackTrace();
                result = "Failure";
            }
            Toast.makeText(context,"Alert successful",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("FCMError", Objects.requireNonNull(e.getMessage()));
        }
        return null;
    }
}