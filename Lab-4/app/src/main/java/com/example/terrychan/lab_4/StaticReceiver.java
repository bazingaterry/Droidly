package com.example.terrychan.lab_4;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * Created by terrychan on 18/10/2016.
 */

public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Fruit fruit = (Fruit) intent.getSerializableExtra("fruit");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("静态广播")
                .setContentText(fruit.getName())
                .setTicker("静态通知")
                .setSmallIcon(fruit.getImg())
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), fruit.getImg()))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));
        notificationManager.notify(0, builder.build());
    }
}
