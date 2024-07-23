package com.mbsstudentscheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

//TODO: make the counter automatically update and implement a notification system

public class homeFragment extends Fragment {
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
        setUpCounter(homeClass,homeRoom,timer);

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
    private void setUpCounter(TextView homeClass, TextView homeRoom, TextView timer){
        if (scheduleElement.SCArraylist.isEmpty()){
            homeClass.setText("No classes set");
            homeRoom.setText("No class set");
            timer.setText("00:00");
        }
        else {
            int i = 0;
            if (getTimeMills() > scheduleElement.SCArraylist.get(scheduleElement.SCArraylist.size() - 1).getStartTime()) { //if the last start time in the array is less than the current time
                homeClass.setText(scheduleElement.SCArraylist.get(0).getClassID());
                homeRoom.setText(scheduleElement.SCArraylist.get(0).getRoomID());
                setFormatTime(timer, (scheduleElement.SCArraylist.get(0).getStartTime() + millisInWeek) - getTimeMills());
            } else {
                while (getTimeMills() > scheduleElement.SCArraylist.get(i).getStartTime()) {
                    i++;
                }
                homeClass.setText(scheduleElement.SCArraylist.get(i).getClassID());
                homeRoom.setText(scheduleElement.SCArraylist.get(i).getRoomID());
                setFormatTime(timer, scheduleElement.SCArraylist.get(i).getStartTime() - getTimeMills());
            }
            if (getTimeMills()-scheduleElement.SCArraylist.get(i).getStartTime()==300000){
            }
            refresh(homeClass, homeRoom, timer);
        }
    }

    private void refresh(TextView homeClass, TextView homeRoom, TextView timer){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setUpCounter(homeClass,homeRoom,timer);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

}
