package com.mbsstudentscheduler;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;

public class newScheduleItemDialogue{
    
    private int startTimeMil, endTimeMil;
    private String classID,roomID;
    private boolean alarmbool;
    private Button confirm,cancel,startTime,endTime;
    private CheckBox alarm;
    private EditText classNameInput, roomNameInput;
    private returner starter = new returner();
    private returner finisher = new returner();
    private int day;
    private scheduleElement element;
    private scheduleElement newElement;
    
    public void showDialog(Context context){
        //start the dialogue window and assignt to a res
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_schedule_dialog);
        
        //declare the views
        confirm = dialog.findViewById(R.id.done);
        cancel = dialog.findViewById(R.id.cancel);
        startTime = dialog.findViewById(R.id.startTime);
        endTime = dialog.findViewById(R.id.endTime);
        classNameInput = dialog.findViewById(R.id.classNameInput);
        roomNameInput= dialog.findViewById(R.id.roomNameInput);
        alarm = dialog.findViewById(R.id.alarmCheck);
        
        
        //the spinner and its adapter
        String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        Spinner daySpinner = dialog.findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = position;
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                day = 0;
            }
        });
            
            //the done button
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //changes the given times into milliseconds
                    startTimeMil = ((((((day * 24) + starter.getHourss()) * 60) + starter.minutess) * 60) * 1000);
                    endTimeMil = ((((((day * 24) + finisher.hourss) * 60) + finisher.minutess) * 60) * 1000);
                    //changes the edittexts into normal strings
                    classID = classNameInput.getText().toString();
                    roomID = roomNameInput.getText().toString();
                    if (alarm.isChecked())
                        alarmbool=true;
                    else if (!alarm.isChecked())
                        alarmbool=false;
                    
                    //tests a bunch of cases and gives errors accordingly
                    if (classID.length() == 0) {
                        Toast.makeText(context, "Class can't be nothing", Toast.LENGTH_SHORT).show();
                    }
                    else if (startTimeMil>=endTimeMil){
                        Toast.makeText(context, "start time and end time contradict", Toast.LENGTH_SHORT).show();
                    }
                    else if (startTimeMil==0){
                        Toast.makeText(context, "Please fill out the times", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        element = new scheduleElement (getClassID(),getRoomID(),getStartTimeMil(),getEndTimeMil(),alarmbool);
                        SQLiteManager manager = new SQLiteManager(context);
                        manager.insertElement(element);
                        Toast.makeText(context,"refresh the page",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }
            });
            //cancel button, self explanatory.
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    
                }
            });
            
            //pressing the start time button and assigning a number changes the starter's values into the desirable attributes
            startTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starter = popTimeListener(startTime, context);
                    
                }
            });
            //same thing as start button
            endTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finisher = popTimeListener(endTime, context);
                    
                }
            });
            dialog.show();
    }
    public void showDialog(Context context, scheduleElement elem){
        element=elem;
        //start the dialogue window and assignt to a res
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_schedule_dialog);
        
        //declare the views
        confirm = dialog.findViewById(R.id.done);
        cancel = dialog.findViewById(R.id.cancel);
        startTime = dialog.findViewById(R.id.startTime);
        endTime = dialog.findViewById(R.id.endTime);
        classNameInput = dialog.findViewById(R.id.classNameInput);
        roomNameInput= dialog.findViewById(R.id.roomNameInput);
        alarm = dialog.findViewById(R.id.alarmCheck);

        
        
        //the spinner and its adapter
        String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        Spinner daySpinner = dialog.findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = position;
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
        
        //the done button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changes the given times into milliseconds
                startTimeMil = ((((((day * 24) + starter.getHourss()) * 60) + starter.minutess) * 60) * 1000);
                endTimeMil = ((((((day * 24) + finisher.hourss) * 60) + finisher.minutess) * 60) * 1000);
                //changes the edittexts into normal strings
                classID = classNameInput.getText().toString();
                roomID = roomNameInput.getText().toString();
                if (alarm.isChecked())
                    alarmbool=true;
                else if (!alarm.isChecked())
                    alarmbool=false;
                
                //tests a bunch of cases and gives errors accordingly
                if (classID.length() == 0) {
                    Toast.makeText(context, "Class can't be nothing", Toast.LENGTH_SHORT).show();
                }
                else if (startTimeMil>=endTimeMil){
                    Toast.makeText(context, "start time and end time contradict", Toast.LENGTH_SHORT).show();
                }
                else if (startTimeMil==0){
                    Toast.makeText(context, "Please fill out the times", Toast.LENGTH_SHORT).show();
                }
                else {
                    newElement = new scheduleElement (getClassID(),getRoomID(),getStartTimeMil(),getEndTimeMil(),alarmbool);
                    SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
                    sqLiteManager.updateElement(element,newElement);
                    Toast.makeText(context,"refresh the page",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
        //cancel button, self explanatory
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                
            }
        });
        
        //pressing the start time button and assigning a number changes the starter's values into the desirable attributes
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starter = popTimeListener(startTime, context);
                
            }
        });
        //same thing as start button
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finisher = popTimeListener(endTime, context);
                
            }
        });
        dialog.show();
    }
    public scheduleElement returnElement(){
        return element;
    }

    // the clock interface that pops up when you press the start or end buttons
    private static returner popTimeListener(Button button, Context context){
        returner x = new returner();
        
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                x.setHourss(hourOfDay);
                x.setMinutess(minute);
                button.setText(String.format(Locale.getDefault(),"%02d:%02d",x.getHourss(),x.getMinutess()));
                
            }
        };
        
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,onTimeSetListener,x.getHourss(), x.getMinutess(),true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();
        return x;
    }
    
    public int getStartTimeMil() {
        return startTimeMil;
    }
    
    public int getEndTimeMil() {
        return endTimeMil;
    }
    
    public String getClassID() {
        return classID;
    }
    
    public String getRoomID() {
        return roomID;
    }
    
    public boolean isAlarmbool() {
        return alarmbool;
    }
    
    //returner class to adapt everything together
    private static class returner{
        private int minutess, hourss;
        
        private int getMinutess() {
            return minutess;
        }
        
        private void setMinutess(int minutess) {
            this.minutess = minutess;
        }
        
        private int getHourss() {
            return hourss;
        }
        
        private void setHourss(int hourss) {
            this.hourss = hourss;
        }
    }
}
