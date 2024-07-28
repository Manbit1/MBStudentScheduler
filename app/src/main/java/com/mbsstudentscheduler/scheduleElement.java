package com.mbsstudentscheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class scheduleElement {
    private String roomID;
    private String classID;
    private int startTime;
    private int endTime;
    private boolean muteState;
    private boolean isAlarm;
    public static ArrayList<scheduleElement> SCArraylist = new ArrayList<>();
    
    public scheduleElement(String classID, String roomID, int startTime, int endTime, boolean muteState, boolean isAlarm){
        this.classID = classID;
        this.roomID = roomID;
        this.startTime = startTime;
        this.endTime=endTime;
        this.muteState=muteState;
        this.isAlarm=isAlarm;
    }
    public scheduleElement(String classID, int startTime, int endTime, boolean muteState, boolean isAlarm){
        this.classID = classID;
        this.startTime = startTime;
        this.endTime=endTime;
        this.muteState=muteState;
        this.isAlarm=isAlarm;
    }
    public scheduleElement(String classID, String roomID, int startTime, int endTime, boolean isAlarm){
        this.classID = classID;
        this.roomID = roomID;
        this.startTime = startTime;
        this.endTime=endTime;
        this.muteState= false;
        this.isAlarm=isAlarm;
    }
    public scheduleElement(String classID, String roomID, int startTime, int endTime){
        this.classID = classID;
        this.roomID = roomID;
        this.startTime = startTime;
        this.endTime=endTime;
        this.muteState=false;
        this.isAlarm=false;
    }
    public scheduleElement(){}
    
    
    public String getRoomID() {
        return roomID;
    }
    
    public String getClassID() {
        return classID;
    }
    
    public int getStartTime() {
        return startTime;
    }
    
    public int getEndTime() {
        return endTime;
    }
    public String getStartTimeFormat(){
        int minutes = ((startTime / (1000*60)) % 60);
        int hours   = ((startTime / (1000*60*60)) % 24);
        int days = (startTime / (1000*60*60*24));
        String weekday="";
        if (days==0)
            weekday="sun";
        else if (days==1)
            weekday="mon";
        else if (days==2)
            weekday="tue";
        else if (days==3)
            weekday="wed";
        else if (days==4)
            weekday="thu";
        else if (days==5)
            weekday="fri";
        else if (days==6)
            weekday="sat";
        else
            weekday="something weird is happening";
        
        if (minutes<10)
            return (weekday +" "+ hours+":0"+minutes);
        else
            return (weekday +" "+ hours+":"+minutes);
    }
    public String getEndTimeFormat(){
        int minutes = ((endTime / (1000*60)) % 60);
        int hours   = ((endTime / (1000*60*60)) % 24);
        int days = (endTime / (1000*60*60*24));
        String weekday="";
        if (days==0)
            weekday="sun";
        else if (days==1)
            weekday="mon";
        else if (days==2)
            weekday="tue";
        else if (days==3)
            weekday="wed";
        else if (days==4)
            weekday="thu";
        else if (days==5)
            weekday="fri";
        else if (days==6)
            weekday="sat";
        else
            weekday="something weird is happening";
            
        if (minutes<10)
            return (weekday +" "+ hours+":0"+minutes);
        else
            return (weekday +" "+ hours+":"+minutes);
    }
    
    public boolean isMuteState() {
        return muteState;
    }
    public void setMuteState(boolean state){
        muteState = state;
    }
    
    public boolean isAlarm() {
        return isAlarm;
    }
    public int timeCompare(scheduleElement element){
        return startTime-element.getStartTime();
    }
    
}