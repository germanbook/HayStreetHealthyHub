package com.example.haystreethealthyhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

    // Validation marker for First name, Last nme, E-mail
    // Whether they are empty or not can be checked by registration button
    // but can not find any method to check whether EditText has error or not.
    private boolean firstNameMarker = false;
    private boolean lastNameMarker = false;
    private boolean emailMarker = false;
    // ========================================================================


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
                // Perform click before submit
                firstName.requestFocus();
                lastName.requestFocus();
                email.requestFocus();

                firstName.clearFocus();
                lastName.clearFocus();
                email.clearFocus();
                // ============================

                // Check all input fields
                // Make sure they are not empty
                if(!firstName.getText().toString().matches("") &&
                        !lastName.getText().toString().matches("") &&
                        !email.getText().toString().matches("") &&
                        !height.getText().toString().matches("") &&
                        !weight.getText().toString().matches("") &&
                        checkPassword(false) &&
                        dbHelper.checkEmailAvailable(email.getText().toString().trim()) &&
                        firstNameMarker &&
                        lastNameMarker &&
                        emailMarker
                )
                {
                    // get dob from datepicker ====================================
                    int day = dob.getDayOfMonth();
                    int month = dob.getMonth() + 1;
                    int year = dob.getYear();

                    dobDate = getStringDate(day, month, year);
                    // =============================================================

                    Patient patient = new Patient(
                            firstName.getText().toString().trim(),
                            lastName.getText().toString().trim(),
                            email.getText().toString().trim(),
                            password.getText().toString().trim(),
                            gender.getSelectedItemPosition(),
                            dobDate,
                            Integer.parseInt(height.getText().toString().trim()),
                            Integer.parseInt(weight.getText().toString().trim()),
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
                else
                {
                    Toast.makeText(RegistrationActivity02.this, "All fields required, and check format!", Toast.LENGTH_SHORT).show();
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


        // Input validation =================================
        // First Name
        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus && !firstName.getText().toString().trim().matches(""))
                {
                    if(! firstName.getText().toString().trim().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))
                    {
                        firstName.setError("Invalid format!");
                        firstNameMarker = false;
                    }
                    else
                    {
                        firstNameMarker = true;
                    }
                }
            }
        });

        // Last Name
        lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus && !lastName.getText().toString().trim().matches(""))
                {
                    if(! lastName.getText().toString().trim().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))
                    {
                        lastName.setError("Invalid format!");
                        lastNameMarker = false;
                    }
                    else
                    {
                        lastNameMarker = true;
                    }
                }
            }
        });

        // Email
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !email.getText().toString().trim().matches("")) {
                    if(! email.getText().toString().trim().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$"))
                    {
                        email.setError("Invalid format!");
                        emailMarker = false;
                    }
                    else
                    {
                        if(!dbHelper.checkEmailAvailable(email.getText().toString().trim()))
                        {
                            email.setError("Email occupied! Please try another address!");
                            emailMarker = false;
                        }
                        else
                        {
                            emailMarker = true;
                        }

                    }

                }
            }
        });

        // Password
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                checkPassword(hasFocus);
            }
        });

        // Confirm Password
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                checkPassword(hasFocus);
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

    // Password and Confirm fields validation
    public boolean checkPassword(boolean hasFocus)
    {
        boolean returnValue = false;
        // Passwords fields
        String passwordValue = password.getText().toString().trim();
        String confirmValue = confirmPassword.getText().toString().trim();

        // Password loses focus
        if(hasFocus == false)
        {
            // Password and Confirm both filled
            // Check whether they are match
            if(!passwordValue.matches("") && !confirmValue.matches(""))
            {
                // Not equal
                if(!passwordValue.equals(confirmValue))
                {
                    confirmPassword.setError("Password did not match!");
                    returnValue = false;
                } // equal
                else
                {
                    confirmPassword.setError(null);
                    returnValue = true;
                }
            }

        }

        // Password is empty but Confirm filled
        if(passwordValue.matches("") && !confirmValue.matches(""))
        {
            password.setError("Please fill password!");
            returnValue = false;
        }

        // Password filled but Confirm is empty
        if(!passwordValue.matches("") && confirmValue.matches(""))
        {
            confirmPassword.setError("Please confirm password!");
            returnValue = false;
        }

        // Both empty
        if(passwordValue.matches("") && confirmValue.matches(""))
        {
            password.setError(null);
            confirmPassword.setError(null);
            returnValue = false;
        }

        return returnValue;
    }

}