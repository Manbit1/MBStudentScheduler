package com.mbsstudentscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLitemanagerNotes extends SQLiteOpenHelper {
    public static SQLitemanagerNotes sqLitemanagerNotes;
    private static String dbName = "itemDB";
    private static String table = "itemTable";
    public SQLitemanagerNotes(Context context) {
        super(context,dbName,null,1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table+" (noteTitle TEXT primary key, noteBody TEXT)");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+table);
    }
    
    public void insertItem(NoteItem item){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("noteTitle",item.getTitle());
        cv.put("noteBody",item.getBody());
        db.insert(table,null,cv);
    }
    
    public void updateItem(NoteItem oldItem, NoteItem newItem){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("noteTitle",newItem.getTitle());
        cv.put("noteBody",newItem.getBody());
        Cursor cursor = db.rawQuery("select * from "+table+" where noteTitle=?",new String[]{newItem.getTitle()});
        db.update(table,cv,"noteTitle=?",new String[]{oldItem.getTitle()});
        
    }
    public void deleteItem(NoteItem item){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+table+" where noteTitle=?",null);
        db.delete(table,"noteTitle=?",new String[]{item.getTitle()});
    }
    public static SQLitemanagerNotes instanceOfDB(Context context){
        if (sqLitemanagerNotes==null){
            sqLitemanagerNotes = new SQLitemanagerNotes(context);
        }
        return sqLitemanagerNotes;
    }
    public void populateArray(){
        SQLiteDatabase db = this.getReadableDatabase();
        try(Cursor result = db.rawQuery("select * from "+table,null)){
            if (result.getCount()!=0){
                while (result.moveToNext()){
                    String notetitle = result.getString(0);
                    String notebody = result.getString(1);
                    NoteItem item = new NoteItem(notetitle,notebody);
                    NoteItem.list.add(item);
                }
            }
        }
    }
}
