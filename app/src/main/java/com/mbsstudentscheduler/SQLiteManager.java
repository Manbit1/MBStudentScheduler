package com.mbsstudentscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.util.ArrayList;


public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqmanager;
    private static final String DBNAME = "elementDB";
    private static final String TABLE = "elements";
    public SQLiteManager(Context context) {
        super(context, DBNAME, null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table "+TABLE+" (startTime INT primary key, endTime INT, classID TEXT, roomID TEXT, alarmStatus INT)");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists "+TABLE);
    }
    
    public boolean insertElement(scheduleElement element){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("startTime",element.getStartTime());
        cv.put("endTime",element.getEndTime());
        cv.put("classID",element.getClassID());
        cv.put("roomID",element.getRoomID());
        int alarmstate;
        if (element.isAlarm())
            alarmstate = 1;
        else
            alarmstate=0;
        cv.put("alarmStatus", alarmstate);
        
        long result = db.insert(TABLE,null,cv);
        return result != -1;
    }
    public boolean updateElement(scheduleElement element){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("startTime",element.getStartTime());
        cv.put("endTime",element.getEndTime());
        cv.put("classID",element.getClassID());
        cv.put("roomID",element.getRoomID());
       int alarmstate;
        if (element.isAlarm())
            alarmstate = 1;
        else
            alarmstate=0;
        cv.put("alarmStatus", alarmstate);
        Cursor cursor = db.rawQuery("select * from "+TABLE+" where startTime=?",new String[]{String.valueOf(element.getStartTime())});
        long result = db.update(TABLE,cv,"startTime=?",new String[]{String.valueOf(element.getStartTime())});
        
        if (cursor.getCount()>0){
            return result != -1;
        }
        else {
            return false;
        }
    }
    public boolean updateElement(scheduleElement originalElement,scheduleElement newElement){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("startTime",newElement.getStartTime());
        cv.put("endTime",newElement.getEndTime());
        cv.put("classID",newElement.getClassID());
        cv.put("roomID",newElement.getRoomID());
        int alarmstate;
        if (newElement.isAlarm())
            alarmstate = 1;
        else
            alarmstate=0;
        cv.put("alarmStatus", alarmstate);
        Cursor cursor = db.rawQuery("select * from "+TABLE+" where startTime=?",new String[]{String.valueOf(newElement.getStartTime())});
        long result = db.update(TABLE,cv,"startTime=?",new String[]{String.valueOf(originalElement.getStartTime())});
        
        if (cursor.getCount()>0){
            return result != -1;
        }
        else {
            return false;
        }
    }
    public boolean deleteElement(scheduleElement element){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "+TABLE+" where startTime=?",new String[]{String.valueOf(element.getStartTime())});
        long result = db.delete(TABLE,"startTime=?",new String[]{String.valueOf(element.getStartTime())});
        
        if (cursor.getCount()>0){
            return result != -1;
        }
        else {
            return false;
        }
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE+" where startTime=?", null);
        return cursor;
    }
    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqmanager==null)
            sqmanager = new SQLiteManager(context);
        return sqmanager;
        
    }
    public void putDataInArray(){
        SQLiteDatabase db = this.getReadableDatabase();
        try(Cursor result = db.rawQuery("select * from "+TABLE,null)){
            if (result.getCount()!=0){
                while (result.moveToNext()){
                    int startTime=result.getInt(0);
                    int endTime = result.getInt(1);
                    String classID=result.getString(2);
                    String roomID = result.getString(3);
                    scheduleElement element = new scheduleElement(classID,roomID,startTime,endTime);
                    scheduleElement.SCArraylist.add(element);

                }
            }
        }
    }
}
