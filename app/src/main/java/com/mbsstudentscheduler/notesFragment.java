package com.mbsstudentscheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class notesFragment extends Fragment {
    
    SwipeRefreshLayout refreshLayout;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes,container,false);
        NoteItem.list.clear();
        refreshLayout = view.findViewById(R.id.notesRefreshLayout);
        FloatingActionButton fab = view.findViewById(R.id.add_note_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            newNoteItemDialogue dialogue = new newNoteItemDialogue();
            dialogue.showDialogue(getContext());
            }
        });
        dbloader();
        
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstances){
        super.onViewCreated(view, savedInstances);
        RecyclerView recyclerView = view.findViewById(R.id.notesRecyclerView);
        recyclerViewAdapterNotes adapter = new recyclerViewAdapterNotes(getContext(),NoteItem.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NoteItem.list.clear();
                dbloader();
                recyclerViewAdapterNotes adapter = new recyclerViewAdapterNotes(getContext(),NoteItem.list);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                refreshLayout.setRefreshing(false);
            }
        });
    }
    public void dbloader(){
        SQLitemanagerNotes sqLitemanagerNotes = SQLitemanagerNotes.instanceOfDB(getContext());
        sqLitemanagerNotes.populateArray();
    }
}
