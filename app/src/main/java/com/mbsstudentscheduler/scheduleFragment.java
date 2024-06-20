package com.mbsstudentscheduler;


import android.content.Context;
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

public class scheduleFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
 
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        
        scheduleElement.SCArraylist.clear();
        dbloader();
        
        FloatingActionButton fab = view.findViewById(R.id.addScheduleElement);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newScheduleItemDialogue item = new newScheduleItemDialogue();
                item.showDialog(getContext());
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.scheduleRecy);
        recyclerViewAdapt adapter = new recyclerViewAdapt(getContext(),scheduleElement.SCArraylist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        
        swipeRefreshLayout = view.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshScheduleFragment(getContext(), recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstances){
        super.onViewCreated(view, savedInstances);
        

    }
    private void dbloader() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(requireContext());
        sqLiteManager.putDataInArray();
    }
    
    public static void refreshScheduleFragment(Context context, RecyclerView recyclerView){
        scheduleElement.SCArraylist.clear();
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
        sqLiteManager.putDataInArray();
        sort();
        recyclerViewAdapt adapter = new recyclerViewAdapt(context,scheduleElement.SCArraylist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
    public static void sort(){
        scheduleElement.SCArraylist = algorithms.quicksort(scheduleElement.SCArraylist);
    }
}
