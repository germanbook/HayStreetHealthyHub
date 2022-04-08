package com.example.haystreethealthyhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RegistrationActivity02 extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Spinner gender;
    private DatePicker dob;
    private EditText height;
    private EditText weight;
    private Spinner gp;
    private Button buttonRegister;
    private Button buttonCancel;

    private int doctorID;
    private String dobDate;

    //22222222
    // Assignment 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration02);

        // Initialize elements ==============================
        firstName = findViewById(R.id.Registration_FirstName);
        lastName = findViewById(R.id.Registration_LastName);
        email = findViewById(R.id.Registration_Email);
        password = findViewById(R.id.Registration_Password);
        confirmPassword = findViewById(R.id.Registration_ConfirmPassword);
        gender = findViewById(R.id.PersonalInfo_Gender);
        dob = findViewById(R.id.PersonalInfo_Dob);
        height = findViewById(R.id.PersonalInfo_Height);
        weight = findViewById(R.id.PersonalInfo_Weight);
        gp = findViewById(R.id.PersonalInfo_GP);
        buttonRegister = findViewById(R.id.Registration_ButtonOk);
        buttonCancel = findViewById(R.id.Registration_ButtonCancel);

        // ===================================================

        // DB Connection
        dbHelper = new DBHelper(RegistrationActivity02.this);
        //===================================================

        // Populate items for GP spinner
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray = dbHelper.getDoctorsName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gp.setAdapter(adapter);
        // ===================================================

        // Fetch Doctor's ID =================================

        doctorID = dbHelper.getDoctorID(gp.getSelectedItem().toString());
        gp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doctorID = dbHelper.getDoctorID(gp.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // ==================================================

        // Buttons ==========================================
        // Registration
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get dob from datepicker ====================================
                int day = dob.getDayOfMonth();
                int month = dob.getMonth() + 1;
                int year = dob.getYear();

                dobDate = getStringDate(day, month, year);
                // =============================================================

                Patient patient = new Patient(
                                        firstName.getText().toString(),
                                        lastName.getText().toString(),
                                        email.getText().toString(),
                                        password.getText().toString(),
                                        gender.getSelectedItemPosition(),
                                        dobDate,
                                        Integer.parseInt(height.getText().toString()),
                                        Integer.parseInt(weight.getText().toString()),
                                        doctorID
                                        );


                if( dbHelper.patientRegistration(patient) )
                {
                    // Registered!
                    confirmRegistration();

                    //Toast.makeText(RegistrationActivity02.this, "Registered!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Fail!
                    Toast.makeText(RegistrationActivity02.this, "Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Cancel
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegistrationActivity02.this, MainActivity.class);
                startActivity(intent);

            }
        });

        //===================================================

    }

    // Get a Date in String format
    public String getStringDate(int day, int month, int year)
    {

        String _day;
        String _month;
        //String _year;

        if(month < 10)
        {
            _month = "0" + String.valueOf(month);
        }
        else
        {
            _month = String.valueOf(month);
        }

        if(day < 10){

            _day  = "0" + String.valueOf(day);
        }
        else
        {
            _day = String.valueOf(day);
        }

        String strDate = _day + "-" + _month + "-" + String.valueOf(year);
        return strDate;
    }

    // Registration Dialog
    public void confirmRegistration()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations ！")
                .setMessage("You have registered successfully, please return to login.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RegistrationActivity02.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        builder.create().show();
    }
}