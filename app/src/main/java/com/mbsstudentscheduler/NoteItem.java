package com.mbsstudentscheduler;

import java.util.ArrayList;

public class NoteItem {
    private String title;
    private String body;
    public static ArrayList<NoteItem> list = new ArrayList<>();
    public NoteItem(String title, String body){
        this.title = title;
        this.body = body;
    }
    
    public String getTitle() {
        return title;
    }
    
    public  void setTitle(String title) {
        this.title = title;
    }
    
    public String getBody() {
        return body;
    }
    
    public  void setBody(String body) {
        this.body = body;
    }
}
