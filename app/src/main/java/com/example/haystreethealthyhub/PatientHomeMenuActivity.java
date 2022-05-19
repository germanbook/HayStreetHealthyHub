package com.example.haystreethealthyhub;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PatientHomeMenuActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        TimePickerDialog.OnTimeSetListener {


    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    //Patient Navigation View Name
    TextView textView;

    // User identity
    int userID;
    String userName;
    String userType;

    // Appointment Booking page Visit Time
    TextView visitTime;

    PatientProfileFragment patientProfileFragment = new PatientProfileFragment(PatientHomeMenuActivity.this);
    AppointmentListFragment appointmentListFragment = new AppointmentListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_menu04);

        // Users ID and Name
        Intent thisIntent = getIntent();
        userID = thisIntent.getIntExtra("ID", 0);
        userName = thisIntent.getStringExtra("Name");
        userType = thisIntent.getStringExtra("UserType");

        // Set User ID to  fragments
        Bundle bundle = new Bundle();
        bundle.putInt("userID", userID);
        bundle.putString("userType", userType);

        // Profile fragment
        patientProfileFragment.setArguments(bundle);

        // Appointments fragment
        appointmentListFragment.setArguments(bundle);
        // ======================================================


        //Patient Navigation View Name
        navigationView = findViewById(R.id.PatientHomeNavView);
        View headerLayout = navigationView.getHeaderView(0);
        TextView textView = headerLayout.findViewById(R.id.Main_Menu_Username);
        textView.setText("Welcome " + userName + " !");
        // ======================================================
        // Hooks
        toolbar = findViewById(R.id.PatientHomeToolbar);
        drawerLayout = findViewById(R.id.PatientHomeDrawerLayout);

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

    // todo
    // override onBackPressed()

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.Nav_menu_home:
                drawerLayout.closeDrawers();
                break;
            case R.id.Nav_menu_profile:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, patientProfileFragment)
                        .commit();
                break;
            case R.id.Nav_menu_appointment:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, appointmentListFragment)
                        .commit();
                break;
            case R.id.Nav_menu_logout:
                PatientHomeMenuActivity.Tools.confirmLogout(this);
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }





    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        visitTime = findViewById(R.id.Booking_Time);
        visitTime.setText("Time: " + hourOfDay + ":" + minute);
    }

    public static class Tools
    {
        // Logout
        public static void confirmLogout(Activity currentContext)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(currentContext);
            builder.setTitle("Logout")
                    .setMessage("Do you want to logout!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(currentContext, MainActivity.class);
                            currentContext.startActivity(intent);
                            currentContext.finish();
                        }
                    });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.create().show();
        }
    }
}