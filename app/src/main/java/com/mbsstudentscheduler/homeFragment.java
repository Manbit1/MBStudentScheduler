package com.mbsstudentscheduler;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;


public class homeFragment extends Fragment {
    scheduleElement element;
    private final int millisInWeek = 604740000;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        Button mute = view.findViewById(R.id.home_button_muteUnmute);
        TextView homeClass = view.findViewById(R.id.home_text_classID);
        TextView homeRoom = view.findViewById(R.id.home_fragment_roomID);
        TextView timer = view.findViewById(R.id.home_text_timer);

        dbloader();
        setUpCounter(homeClass,homeRoom,timer,getContext());
        if (element.isMuteState()==true)
            mute.setText("unmute");
        else
            mute.setText("mute");

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (element.isMuteState()==true) {
                    element.setMuteState(false);
                    mute.setText("mute");
                }
                else{
                    element.setMuteState(true);
                    mute.setText("unmute");
                }
            }
        });

        return view;
    }


    public int getTimeMills(){
        int time;
        int day;
        int hour;
        int minute;
        Calendar calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_WEEK) -1;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        time = ((((((day * 24) + hour) * 60) + minute) * 60) * 1000);

        return time;
    }
    private void dbloader() {
        scheduleElement.SCArraylist.clear();
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(requireContext());
        sqLiteManager.putDataInArray();
    }

    private void setFormatTime(TextView timer, int time){
        int minutes = ((time / (1000*60)) % 60);
        int hours   = ((time / (1000*60*60)) % 24);
        int days = (time / (1000*60*60*24));
        if(days<1){
            if (hours<10){
                if (minutes<10){
                    timer.setText("0"+hours+":0"+minutes);
                }
                else {
                    timer.setText("0"+hours+":"+minutes);
                }
            }
            else {
                if (minutes<10){
                    timer.setText(hours+":0"+minutes);
                }
                else {
                    timer.setText(hours+":"+minutes);
                }
            }
        }
        else {
            if (hours<10){
                if (minutes<10){
                    timer.setText(days+":0"+hours+":0"+minutes);
                }
                else {
                    timer.setText(days+":0"+hours+":"+minutes);
                }
            }
            else {
                if (minutes<10){
                    timer.setText(days+":"+hours+":0"+minutes);
                }
                else {
                    timer.setText(days+":"+hours+":"+minutes);
                }
            }
        }
    }
    private void setUpCounter(TextView homeClass, TextView homeRoom, TextView timer, Context context){
        scheduleElement element;

        if (scheduleElement.SCArraylist.isEmpty()){
            homeClass.setText("No classes set");
            homeRoom.setText("No class set");
            timer.setText("00:00");
        }
        else {
            int i = 0;
            if (getTimeMills() > scheduleElement.SCArraylist.get(scheduleElement.SCArraylist.size() - 1).getStartTime()) { //if the last start time in the array is less than the current time
                element = scheduleElement.SCArraylist.get(i);
                homeClass.setText(element.getClassID());
                homeRoom.setText(element.getRoomID());
                setFormatTime(timer, (element.getStartTime() + millisInWeek) - getTimeMills());
            } else {
                while (getTimeMills() > scheduleElement.SCArraylist.get(i).getStartTime()) {
                    i++;
                }
                element = scheduleElement.SCArraylist.get(i);
                homeClass.setText(element.getClassID());
                homeRoom.setText(element.getRoomID());
                setFormatTime(timer, element.getStartTime() - getTimeMills());
            }
            this.element = element;
            notification(getTimeMills(),scheduleElement.SCArraylist.get(i),context);
            refresh(homeClass, homeRoom, timer,context);
        }
    }

    private void refresh(TextView homeClass, TextView homeRoom, TextView timer,Context context){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setUpCounter(homeClass,homeRoom,timer,context);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void notification(int currentTime, scheduleElement element, Context context){
        if (!element.isMuteState()){
            if (currentTime==element.getStartTime()-300000){

                String channelID = "STUDENT-SCHEDULER-APP";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelID)
                        .setSmallIcon(R.drawable.ic_start_time)
                        .setContentTitle(element.getClassID())
                        .setContentText("Class "+element.getClassID()+ " in room "+element.getRoomID()+" in 5 minutes")
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                Intent intent = new Intent(context, homeFragment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_MUTABLE);
                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);
                if (notificationChannel == null){
                    int importance = notificationManager.IMPORTANCE_HIGH;
                    notificationChannel = new NotificationChannel(channelID,"some description", importance);
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                notificationManager.notify(0,builder.build());
                element.setMuteState(true);
            }
            else if (currentTime<element.getStartTime()-300000){
                element.setMuteState(false);
            }
        }
    }

}
