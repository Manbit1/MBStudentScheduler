package com.mbsstudentscheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class settingsFragment extends Fragment{
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        
        String[] days = {"English","French","German","Arabic"};
        Spinner daySpinner = view.findViewById(R.id.langSpinner);
        
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item,days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int day = position;
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int day = 0;
            }
        });
        
        return view;
    }
}
