package com.mbsstudentscheduler;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class serviceClass extends Service {
    private Context context = this;
    scheduleElement element;
    private static final int NOTIFID = 1;
    private static final String NOTIF_CHANNEL_ID = "channel ID";
    
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        
        if (element != null) {
        
        }
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            setUpCounter();
                            Log.d("Thread run status", "run: the thread is running");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        ).start();

        
        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, NOTIF_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 232, intent1, PendingIntent.FLAG_IMMUTABLE);
        
        NotificationCompat.Builder notifcation = new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
                .setContentTitle("MB's student scheduler is running").setSmallIcon(R.drawable.ic_notif).setOngoing(true);
        notifcation.setContentIntent(pendingIntent);
        

        
        startForeground(2, notifcation.build());
        return START_STICKY;
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    
    private void singleNotif(int currentTime, scheduleElement element) {
        Log.d("notification thingy", "second one is working");
        if (element != null) {
            
            if (!element.isMuteState()) {
                if (currentTime == element.getStartTime() - 300000) {
                    
                    String channelID = "STUDENT-SCHEDULER-APP";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                            .setSmallIcon(R.drawable.ic_start_time)
                            .setContentTitle(element.getClassID())
                            .setContentText("Class " + element.getClassID() + " in room " + element.getRoomID() + " in 5 minutes")
                            .setPriority(NotificationCompat.PRIORITY_MAX);
                    
                    Intent intent = new Intent(context, homeFragment.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
                    builder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    
                    NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);
                    if (notificationChannel == null) {
                        int importance = notificationManager.IMPORTANCE_HIGH;
                        notificationChannel = new NotificationChannel(channelID, "some description", importance);
                        notificationChannel.enableVibration(true);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                    notificationManager.notify(0, builder.build());
                    element.setMuteState(true);
                } else if (currentTime < element.getStartTime() - 300000) {
                    element.setMuteState(false);
                }
            }
        }
    }
    
    public int getTimeMills() {
        int time;
        int day;
        int hour;
        int minute;
        Calendar calendar = Calendar.getInstance();
        
        day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        time = ((((((day * 24) + hour) * 60) + minute) * 60) * 1000);
        
        return time;
    }
    
    private void setUpCounter() {
            Log.d("counter","counter set up is working as intended");
            int i = 0;
            if (scheduleElement.SCArraylist.isEmpty()) {
                element = null;
            } else {
                if (getTimeMills() > scheduleElement.SCArraylist.get(scheduleElement.SCArraylist.size() - 1).getStartTime()) { //if the last start time in the array is less than the current time
                    element = scheduleElement.SCArraylist.get(i);
                } else { //in case largest time in array is more than current time
                    while (getTimeMills() > scheduleElement.SCArraylist.get(i).getStartTime()) {
                        i++;
                    }
                    element = scheduleElement.SCArraylist.get(i);
                }
            }
            singleNotif(getTimeMills(), element);

    }
}