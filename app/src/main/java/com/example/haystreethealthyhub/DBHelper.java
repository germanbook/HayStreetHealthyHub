package com.example.haystreethealthyhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context)
    {
        super(context,"UserDB",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create Doctor Table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Doctor (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "email TEXT," +
                "password TEXT )");


        // Create Patient Table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Patient (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "email TEXT," +
                "password TEXT," +
                "gender INTEGER," +
                "dob TEXT," +
                "height INTEGER," +
                "weight INTEGER," +
                "gp INTEGER," +
                "FOREIGN KEY(gp) REFERENCES Doctor(ID))"
                );



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Get all patients login info to check username and password
    public ArrayList<Patient> getAllPatientsLogin()
    {
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Patient> usersLogin = new ArrayList<Patient>();

        // All patients
        Cursor cursor = db.query("Patient",null,null,null,null,null,"firstName");
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
            String userName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            usersLogin.add(new Patient(id, userName, email, password));
        }

        return usersLogin;

    }

    // Get all Doctors login info to check username and password
    public ArrayList<Doctor> getAllDoctorsLogin()
    {
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Doctor> doctorsLogin = new ArrayList<Doctor>();

        // All patients
        Cursor cursor = db.query("Doctor",null,null,null,null,null,"firstName");
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            doctorsLogin.add(new Doctor(id, firstName, email, password));
        }

        return doctorsLogin;
    }

    // Patient Registration
    public boolean patientRegistration(Patient patient)
    {
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", patient.getFirstName());
        values.put("lastName", patient.getLastName());
        values.put("email", patient.getEmail());
        values.put("password", patient.getPassword());
        values.put("gender", patient.getGender());
        values.put("dob", patient.getDob());
        values.put("height", patient.getHeight());
        values.put("weight", patient.getWeight());
        values.put("gp", patient.getGp());

        long count = db.insert("Patient", null, values);

        // Insert
        if( count == -1)
        {
            return false;
        }
        else
        {
            return true;
        }



    }

    // Fetch all the doctor's name to populate GP Spinner in Registration page
    public ArrayList<String> getDoctorsName()
    {
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> doctorNames = new ArrayList<String>();

        String sql = "SELECT firstName FROM Doctor";

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor != null)
        {
            while(cursor.moveToNext())
            {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
                doctorNames.add(name);
            }
        }
        else
        {
            doctorNames.add("No Data, ask admin to add Doctor.");
        }

        return doctorNames;

    }


    // Fetch Doctor's ID
    public int getDoctorID(String name)
    {
        int id = 0;
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT ID FROM Doctor WHERE firstName = " + "'" + name + "'";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            //for testing getInt
            id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
        }
        return id;
    }

    // Check whether email address is available
    public boolean checkEmailAvailable(String email)
    {
        // True: email address available
        boolean returnValue = true;
        String sql = "SELECT ID FROM Patient WHERE email = " + "'" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if( cursor != null && cursor.moveToNext() )
        {
            // Email address occupied
            returnValue = false;
        }
        return returnValue;
    }

}
