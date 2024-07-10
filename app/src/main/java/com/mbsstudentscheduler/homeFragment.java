package com.mbsstudentscheduler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class homeFragment extends Fragment {
    private final int millisInWeek = 569940000;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        Button mute = view.findViewById(R.id.home_button_muteUnmute);
        TextView homeClass = view.findViewById(R.id.home_text_classID);
        TextView homeRoom = view.findViewById(R.id.home_fragment_roomID);
        TextView timer = view.findViewById(R.id.home_text_timer);

        dbloader();

        if (scheduleElement.SCArraylist.isEmpty()){
            homeClass.setText("No classes set");
            homeRoom.setText("No class set");
            timer.setText("00:00");
        }
        else {

        }


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
}
