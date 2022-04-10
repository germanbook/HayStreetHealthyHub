package com.example.haystreethealthyhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class PatientHomeMenuActivity04 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    //Patient Navigation View Name
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_menu04);

        // Users ID and Name
        Intent thisIntent = getIntent();
        int userID = thisIntent.getIntExtra("ID", 0);
        String userName = thisIntent.getStringExtra("Name");

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.Nav_menu_home:
                drawerLayout.closeDrawers();
                break;
            case R.id.Nav_menu_profile:
                break;
            case R.id.Nav_menu_appointment:
                break;
            case R.id.Nav_menu_logout:
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}