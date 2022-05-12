package com.example.haystreethealthyhub;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class PatientHomeMenuActivity04 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    //Patient Navigation View Name
    TextView textView;

    // User identity
    int userID;
    String userName;

    PatientProfileFragment patientProfileFragment = new PatientProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_menu04);

        // Users ID and Name
        Intent thisIntent = getIntent();
        userID = thisIntent.getIntExtra("ID", 0);
        userName = thisIntent.getStringExtra("Name");

        // Set User ID to profile fragment
        Bundle bundle = new Bundle();
        bundle.putInt("userID", userID);
        patientProfileFragment.setArguments(bundle);

        //Patient Navigation View Name
        NavigationView navigationView = findViewById(R.id.PatientHomeNavView);
        View headerLayout = navigationView.getHeaderView(0);
        TextView textView = headerLayout.findViewById(R.id.Main_Menu_Username);
        textView.setText("Welcome " + userName + " !");
        // ======================================================
        // Hooks
        toolbar = findViewById(R.id.PatientHomeToolbar);
        navigationView = findViewById(R.id.PatientHomeNavView);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, patientProfileFragment).commit();
                break;
            case R.id.Nav_menu_appointment:
                break;
            case R.id.Nav_menu_logout:
                confirmLogout();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // Logout Dialog
    public void confirmLogout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout")
                .setMessage("Do you want to logout!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(PatientHomeMenuActivity04.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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