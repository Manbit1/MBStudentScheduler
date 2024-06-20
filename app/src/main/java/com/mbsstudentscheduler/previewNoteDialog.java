package com.mbsstudentscheduler;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class previewNoteDialog {
    
    public void showDialog(Context context, NoteItem item){

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.preview_note);
        TextView title = dialog.findViewById(R.id.titleTextView);
        TextView body = dialog.findViewById(R.id.bodyTextView);
        Button delete = dialog.findViewById(R.id.noteDeleteBtn);
        Button edit = dialog.findViewById(R.id.noteEditBtn);
        
        title.setText(item.getTitle());
        body.setText(item.getBody());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLitemanagerNotes sql = SQLitemanagerNotes.instanceOfDB(context);
                sql.deleteItem(item);
                Toast.makeText(context,"refresh the page",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNoteItemDialogue dialogue = new newNoteItemDialogue();
                dialogue.showDialogue(context,item);
            }
        });
        dialog.show();
    }
}
