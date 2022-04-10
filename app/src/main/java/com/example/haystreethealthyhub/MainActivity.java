package com.example.haystreethealthyhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText email;
    private EditText password;
    private Button buttonLogin;
    private Button buttonJoinNow;
    private Switch switchDoctor;
    private Button buttonContactUs;

    // current user ID and name
    private int userID;
    private String userName;
    // =======================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize elements on first activity=============
        email = findViewById(R.id.LoginEmailInput);
        password = findViewById(R.id.LoginPasswordInput);
        buttonLogin = findViewById(R.id.ButtonLogin);
        buttonJoinNow = findViewById(R.id.ButtonJoinNow);
        switchDoctor = findViewById(R.id.LoginDoctorSwitch);
        buttonContactUs = findViewById(R.id.ButtonContactUs);

        //===================================================

        // DB Connection
        dbHelper = new DBHelper(MainActivity.this);
        //===================================================

        // Button Listener===================================
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get username from user input
                String loginEmail = email.getText().toString().trim();
                // Get password from user input
                String loginPassword = password.getText().toString().trim();

                // Email and Password can not be empty
                if( !TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword) )
                {
                    // Get all users' login
                    ArrayList<? extends User> usersLogin;


                    if(switchDoctor.isChecked())
                    {
                        // User is a Doctor
                         usersLogin = dbHelper.getAllDoctorsLogin();
                    }
                    else
                    {   // User is a Patient
                        usersLogin = dbHelper.getAllPatientsLogin();
                    }

                    // is login success or not
                    boolean isLoginSuccess = false;

                    // loop all login
                    for (int i = 0; i < usersLogin.size(); i ++)
                    {
                        String tempEmail = usersLogin.get(i).getEmail();
                        String tempPassword = usersLogin.get(i).getPassword();

                        if(loginEmail.equals(usersLogin.get(i).getEmail()) && loginPassword.equals(usersLogin.get(i).getPassword()))
                        {
                            userID = usersLogin.get(i).getId();
                            userName = usersLogin.get(i).getFirstName();
                            isLoginSuccess = true;
                            i = usersLogin.size();

                        }
                        else
                        {
                            isLoginSuccess = false;
                        }
                    }

                    // after trying to log in
                    // Success
                    if(isLoginSuccess)
                    {
                        Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();


                        if(switchDoctor.isChecked())
                        {
                            // User is a Doctor
                        }
                        else
                        {
                            // User is a Patient
                            Intent intent = new Intent(MainActivity.this, PatientHomeMenuActivity04.class);
                            intent.putExtra("ID", userID);
                            intent.putExtra("Name", userName);
                            startActivity(intent);
                        }


                    }
                    else
                    {
                        // Email or Password incorrect
                        Toast.makeText(MainActivity.this,"Email or password incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Email or password can not be empty！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonJoinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity02.class);
                startActivity(intent);
            }
        });

        // Contatct Us dial a number
        buttonContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:123456789");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        //==========================================================

    }
}