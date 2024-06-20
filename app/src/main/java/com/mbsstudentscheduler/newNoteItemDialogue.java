package com.mbsstudentscheduler;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newNoteItemDialogue {
    private String title;
    private String body;

    private EditText bodyEditText,TitleEditText;
    private NoteItem item;
    private SQLitemanagerNotes sqlmn;
    public void showDialogue(Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_note_dialogue);
        
        sqlmn = SQLitemanagerNotes.instanceOfDB(context);
        Button confirm = dialog.findViewById(R.id.confirmButtonNote);
        Button cancel = dialog.findViewById(R.id.cancelButtonNote);
        TitleEditText = dialog.findViewById(R.id.inputTitle);
        bodyEditText = dialog.findViewById(R.id.inputBody);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = TitleEditText.getText().toString();
                body = bodyEditText.getText().toString();
                
                if (title.length()==0 || body.length()==0){
                    Toast.makeText(context,"Note title and body can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    item = new NoteItem(title,body);
                    sqlmn.insertItem(item);
                    Toast.makeText(context,"refresh the page",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        dialog.show();
    }
    public void showDialogue(Context context,NoteItem oldItem){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_note_dialogue);
        
        sqlmn = SQLitemanagerNotes.instanceOfDB(context);
        Button confirm = dialog.findViewById(R.id.confirmButtonNote);
        Button cancel = dialog.findViewById(R.id.cancelButtonNote);
        TitleEditText = dialog.findViewById(R.id.inputTitle);
        bodyEditText = dialog.findViewById(R.id.inputBody);
        
        TitleEditText.setText(oldItem.getTitle());
        bodyEditText.setText(oldItem.getBody());
        
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = TitleEditText.getText().toString();
                body = bodyEditText.getText().toString();
                
                if (title.length()==0 || body.length()==0){
                    Toast.makeText(context,"Note title and body can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    item = new NoteItem(title,body);
                    sqlmn.updateItem(oldItem,item);
                    Toast.makeText(context,"refresh the page",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        dialog.show();
    }
}
