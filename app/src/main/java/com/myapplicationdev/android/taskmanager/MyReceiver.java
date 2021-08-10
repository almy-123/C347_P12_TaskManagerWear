package com.myapplicationdev.android.taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

public class MyReceiver extends BroadcastReceiver {

    int requestCode = 54321;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Bundle bundle = intent.getBundleExtra("bundle");
        Task task = (Task) bundle.getSerializable("task");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is for default notification");
            nm.createNotificationChannel(channel);
        }
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, requestCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

        // watch (Launch Task Manager)
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Launch Task Manager",
                pi
        ).build();

        // watch (Reply)
        Intent intentReply = new Intent(context, MainActivity.class);
        intentReply.putExtra("taskID", task.getId());
        PendingIntent pi2 = PendingIntent.getActivity(context, requestCode,
                intentReply, PendingIntent.FLAG_CANCEL_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[]{"Completed", "Not yet"})
                .build();

        NotificationCompat.Action action1 = new NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pi2)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action1);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task");
        builder.setContentText(task.getName() + "\n" + task.getDescription());
        builder.setSmallIcon(android.R.drawable.btn_star);
        builder.setAutoCancel(true);
        builder.setContentIntent(pi);

        // for picture
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ducky)).build();

        // sound
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();

        // vibrate
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        // lights
        builder.setLights(0xff00ff00, 300, 100);

        // for watch
        builder.extend(extender);

        Notification n = builder.build();
        nm.notify(123, n);
    }
}