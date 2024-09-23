package com.headless;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AlarmForegroundService extends Service {
    private static final String CHANNEL_ID = "AlarmServiceChannel";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Display the notification and run the task
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Alarm Running")
                .setContentText("Background task is running")
                .setSmallIcon(com.facebook.react.R.drawable.ic_resume)
                .setContentIntent(pendingIntent)
                .build();

        // Start the service in the foreground
        startForeground(1, notification);

        // Run the task
        performTask();
        startHeadlessJsTask();

        // Stop the service once the task is done
        stopForeground(true);
        stopSelf();

        return START_NOT_STICKY;
    }

    private void performTask() {
        // Your background task logic here, e.g., fetching data from API
        // This runs in the foreground service
        try {
            // Simulating some task or fetching data
            Thread.sleep(2000); // Simulate a 2-second task
            System.out.println("Task completed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startHeadlessJsTask() {
        // Start your headless task here
        Log.d("AlarmForegroundService", "Starting headless JS task");
        Intent headlessIntent = new Intent(this, AlarmTaskService.class);
        headlessIntent.putExtra("foo", "bar");
        startService(headlessIntent);
    }

//    private void startHeadlessJsTask(Context context) {
//        Intent service = new Intent(context.getApplicationContext(), AlarmTaskService.class);
//        Bundle bundle = new Bundle();
//
//        bundle.putString("foo", "bar");
//        service.putExtras(bundle);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.getApplicationContext().startForegroundService(service);
//        }
//    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
