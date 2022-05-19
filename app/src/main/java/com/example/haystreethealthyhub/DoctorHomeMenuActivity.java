package com.example.haystreethealthyhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DoctorHomeMenuActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    // User identity
    int userID;
    String userName;
    String userType;

    AppointmentListFragment appointmentListFragment = new AppointmentListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_menu);

        // Users ID and Name
        Intent thisIntent = getIntent();
        userID = thisIntent.getIntExtra("ID", 0);
        userName = thisIntent.getStringExtra("Name");
        userType = thisIntent.getStringExtra("UserType");

        // Set User ID to  fragments
        Bundle bundle = new Bundle();
        bundle.putInt("userID", userID);
        bundle.putString("userType", userType);

        // Appointments fragment
        appointmentListFragment.setArguments(bundle);
        // ======================================================


        // Navigation View Name
        NavigationView navigationView = findViewById(R.id.DoctorHomeNavView);
        View headerLayout = navigationView.getHeaderView(0);
        TextView textView = headerLayout.findViewById(R.id.Main_Menu_Username);
        textView.setText("Welcome " + userName + " !");
        // ======================================================
        // Hooks
        toolbar = findViewById(R.id.DoctorHomeToolbar);
        drawerLayout = findViewById(R.id.DoctorHomeDrawerLayout);

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.Patient_Main_Menu_Open, R.string.Patient_Main_Menu_Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.doctor_menu_home:
                drawerLayout.closeDrawers();
                break;
            case R.id.doctor_menu_patients:
                break;
            case R.id.doctor_menu_appointment:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, appointmentListFragment)
                        .commit();
                break;
            case R.id.doctor_menu_logout:
                PatientHomeMenuActivity.Tools.confirmLogout(this);
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}