package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private OneTimeWorkRequest workRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance(this).enqueue(workRequest);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(
                MainActivity.this, workInfo -> {

                    Log.d("RRR","Status: "+workInfo.getState());
                    String message = workInfo.getOutputData().getString("key1");
                    int x = workInfo.getOutputData().getInt("key2",0);
                    Log.d("RRR","Message: " + message + ", x: "+x);

                }
        );
    }

    public void getDate() throws IOException {
        String str = "Строка к API";
        HttpsURLConnection connection;
        URL url = new URL(str);
        connection = (HttpsURLConnection) url.openConnection();
        connection.setConnectTimeout(10000);
        connection.connect();

        // connection.getResponseCode() => 200
        Scanner scanner = new Scanner(connection.getInputStream());

    }
}