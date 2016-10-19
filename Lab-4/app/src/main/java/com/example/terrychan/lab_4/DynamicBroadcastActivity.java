package com.example.terrychan.lab_4;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by terrychan on 18/10/2016.
 */

public class DynamicBroadcastActivity extends AppCompatActivity {
    boolean isRegistered = false;
    String action = "com.example.terrychan.DYNAMIC";
    Button regButton, sendButton;
    EditText input;
    IntentFilter intentFilter;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_broadcast);
        setView();
    }

    void setView() {
        regButton = (Button) findViewById(R.id.register);
        sendButton = (Button) findViewById(R.id.send);
        input = (EditText) findViewById(R.id.message);
        intentFilter = new IntentFilter(action);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("msg");
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle("动态广播")
                        .setContentText(msg)
                        .setTicker("动态通知")
                        .setSmallIcon(R.mipmap.dynamic)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.dynamic))
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));
                notificationManager.notify(0, builder.build());
            }
        };
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegistered) {
                    regButton.setText("Register Broadcast");
                    unregisterReceiver(broadcastReceiver);
                } else {
                    regButton.setText("Unregister Broadcast");
                    registerReceiver(broadcastReceiver, intentFilter);
                }
                isRegistered = !isRegistered;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = input.getText().toString();
                Intent intent = new Intent();
                intent.setAction(action);
                intent.putExtra("msg", inputText);
                sendBroadcast(intent);
            }
        });
    }
}

