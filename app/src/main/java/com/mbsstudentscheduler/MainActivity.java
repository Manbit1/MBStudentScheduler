package com.mbsstudentscheduler;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
        }


        //set toolbar as actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        //initialize drawer
        drawer = findViewById(R.id.drawer_layout);
        
        
        NavigationView navigationView = findViewById(R.id.nav_container);
        navigationView.setNavigationItemSelectedListener(this);
        
        //links the actionbar with the drawer activity and enables a button in said activity to appear
        //this button opens up the menu drawer
        //enables animation for the button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new homeFragment()).commit();
        navigationView.setCheckedItem(R.id.fragment_home);
        
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        item.setChecked(true);
        drawer.closeDrawers();
        if (itemid == R.id.menu_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
            
        } else if (itemid == R.id.menu_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new settingsFragment()).commit();
            
        } else if (itemid == R.id.menu_notes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new notesFragment()).commit();
            
        } else if (itemid == R.id.menu_schedule) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new scheduleFragment()).commit();
        }
        
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    
    
    //sets the back button to close the drawer, not the whole activity
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}