package com.mbsstudentscheduler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerViewAdapterNotes extends RecyclerView.Adapter<recyclerViewAdapterNotes.myViewHolder> {
    Context context;
    ArrayList<NoteItem> items;
    
    public recyclerViewAdapterNotes(Context context, ArrayList<NoteItem> items){
        this.context = context;
        this.items = items;
    }
    
    @NonNull
    @Override
    public recyclerViewAdapterNotes.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_note_item,parent,false);
        return new recyclerViewAdapterNotes.myViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapterNotes.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.chooseNote.setText(items.get(position).getTitle());
        holder.chooseNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            previewNoteDialog dialog = new previewNoteDialog();
            dialog.showDialog(context,items.get(position));
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class myViewHolder extends RecyclerView.ViewHolder{
        Button chooseNote;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            chooseNote = itemView.findViewById(R.id.chooseNoteBtn);
        }
    }
}
