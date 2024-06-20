package com.mbsstudentscheduler;

import java.util.ArrayList;

public class algorithms {
    public static ArrayList<scheduleElement> quicksort(ArrayList<scheduleElement> array){
        if (array.isEmpty()) return array;
        ArrayList<scheduleElement> sorted;
        ArrayList<scheduleElement> smaller = new ArrayList<scheduleElement>();
        ArrayList<scheduleElement> greater = new ArrayList<scheduleElement>();
        scheduleElement pivot = array.get(0);
        
        
        int i;
        scheduleElement j;
        for (i=1;i<array.size();i++){
            j=array.get(i);
            if (j.timeCompare(pivot)<0) smaller.add(j);
            else greater.add(j);
        }
        
        
        smaller=quicksort(smaller);
        greater = quicksort(greater);
        smaller.add(pivot);
        smaller.addAll(greater);
        sorted = smaller;
        
        return sorted;
    }
    
}
