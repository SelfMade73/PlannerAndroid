package com.example.myapplication.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;

public class Reminder  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"RemindNotifications")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_bucket)
                .setContentTitle("TITLE")
                .setContentText("Random description")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(200,builder.build());
    }
}

