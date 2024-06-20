package com.mbsstudentscheduler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerViewAdapt extends RecyclerView.Adapter<recyclerViewAdapt.myViewHolder> {
    static Context context;
    ArrayList<scheduleElement> elements;
    public recyclerViewAdapt(Context context, ArrayList<scheduleElement> elements){
        this.context = context;
        this.elements = elements;
    }
    @NonNull
    @Override
    public recyclerViewAdapt.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_schedule_element,parent,false);
        return new recyclerViewAdapt.myViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapt.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.classIdStr.setText(elements.get(position).getClassID());
        holder.roomIdStr.setText(elements.get(position).getRoomID());
        holder.startTimeStr.setText(elements.get(position).getStartTimeFormat());
        holder.endTimeStr.setText(elements.get(position).getEndTimeFormat());
        holder.editBtn.setText("edit");
        holder.deleteBtn.setText("delete");
        
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleElement elm = elements.get(position);
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
                sqLiteManager.deleteElement(elm);
                Toast.makeText(context,"please update the list again",Toast.LENGTH_SHORT).show();
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newScheduleItemDialogue item = new newScheduleItemDialogue();
                item.showDialog(context,elements.get(position));
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return elements.size();
    }
    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView classIdStr, roomIdStr, startTimeStr,endTimeStr;
        Button editBtn,deleteBtn;

        public myViewHolder(@NonNull View itemView) {
            
            super(itemView);
            classIdStr = itemView.findViewById(R.id.classIdStr);
            roomIdStr = itemView.findViewById(R.id.roomIdStr);
            startTimeStr= itemView.findViewById(R.id.startTimeStr);
            endTimeStr= itemView.findViewById(R.id.endTimeStr);
            editBtn= itemView.findViewById(R.id.editBtn);
            deleteBtn= itemView.findViewById(R.id.deleteBtn);
        }
    }
}
