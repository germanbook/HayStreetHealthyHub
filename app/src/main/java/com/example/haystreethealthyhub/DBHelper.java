package com.example.haystreethealthyhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context)
    {
        super(context,"UserDB",null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create Doctor Table
        sqLiteDatabase.execSQL("CREATE TABLE Doctor (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "email TEXT," +
                "password TEXT )");


        // Create Patient Table
        sqLiteDatabase.execSQL("CREATE TABLE Patient (" +
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


        // TODO add a new column called 'visitReason'
        // Create Appointment Table
        sqLiteDatabase.execSQL("CREATE TABLE Appointment (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "patient_ID INTEGER," +
                "doctor_ID INTEGER," +
                "status INTEGER," +
                "note TEXT," +
                "date TEXT," +
                "time TEXT," +
                "visitReason TEXT," +
                "FOREIGN KEY(patient_ID) REFERENCES Patient(ID)," +
                "FOREIGN KEY(doctor_ID) REFERENCES Doctor(ID))"
                );

        // Initialize tables

        sqLiteDatabase.execSQL("INSERT INTO Doctor (firstName, lastName, email, password)" +
                "VALUES ('Alex', 'Miller', 'alex@alex.com', 'alex' )," +
                "('Tom', 'Smith', 'tom@tom.com', 'tom')");
        sqLiteDatabase.execSQL("INSERT INTO Patient (firstName, lastName, email, password, gender," +
                " dob, height, weight, gp)" +
                "VALUES ('Jack', 'Johnson', 'jack@jack.com', 'jack', '1', '10-10-1985', '185', '70', '1')," +
                "('Bowen', 'Zhang', 'bowen@bowen.com', 'bowen', '1', '25-10-2000', '182', '95', '2')");
        sqLiteDatabase.execSQL("INSERT INTO Appointment (patient_ID, doctor_ID, status, note, date, " +
                " time, visitReason)" +
                "VALUES ('1', '2', '4', 'No big problem, just take some Panadol.', '12-5-2022', " +
                "'14:30', 'Intermittent stomach pains, accompanied by vomiting, have been going on for a week.')");
        sqLiteDatabase.execSQL("INSERT INTO Appointment (patient_ID, doctor_ID, status, note, date, " +
                " time, visitReason)" +
                "VALUES ('1', '1', '4', '', '12-6-2022', " +
                "'11:30', 'Head Spining')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Drop older table if exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "Doctor");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "Patient");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "Appointment");

        // Create tables again
        onCreate(sqLiteDatabase);

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
        if( count == 0)
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
    // Fetch Doctor's Name
    public String getDoctorName(int id)
    {
        String firstName = "";
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT firstName FROM Doctor WHERE ID = " + "'" + id + "'";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            //for testing getInt
            firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
        }
        return firstName;
    }

    // Fetch Doctor's Name
    public String getPatientName(int id)
    {
        String firstName = "";
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT firstName FROM Patient WHERE ID = " + "'" + id + "'";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            //for testing getInt
            firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
        }
        return firstName;
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

    // Get user details to populate profile page
    public Patient getPatient(int id)
    {
        Patient patient = new Patient();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Patient WHERE ID = " + id;

        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            patient.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            patient.firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
            patient.lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
            patient.email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            patient.password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            patient.setGender(cursor.getInt(cursor.getColumnIndexOrThrow("gender")));
            patient.setDob(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
            patient.setHeight(cursor.getInt(cursor.getColumnIndexOrThrow("height")));
            patient.setWeight(cursor.getInt(cursor.getColumnIndexOrThrow("weight")));
            patient.setGp(cursor.getInt(cursor.getColumnIndexOrThrow("gp")));

        }

        return patient;
    }

    public boolean updatePatient(Patient patient)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", patient.getFirstName());
        values.put("lastName", patient.getLastName());
        values.put("password", patient.getPassword());
        values.put("gender", patient.getGender());
        values.put("height", patient.getHeight());
        values.put("weight", patient.getWeight());
        values.put("gp", patient.getGp());

        int tempId = patient.getId();

        long count = db.update("Patient", values, "ID=?",
                                new String[]{Integer.toString(patient.getId())}
                );

        // update
        if( count == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // Get all appointments by ID for specific user
    public ArrayList<Appointment> getAllAppointment(int id, String userType)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        String query;
        if(userType.equals("P"))
        {
            query = "SELECT * FROM Appointment WHERE patient_ID = " + id;
        }
        else
        {
            query = "SELECT * FROM Appointment WHERE doctor_ID = " + id;
        }

        Cursor cursor = db.rawQuery(query, null);

        while(cursor != null && cursor.moveToNext())
        {
            int ID = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
            int patient_ID = cursor.getInt(cursor.getColumnIndexOrThrow("patient_ID"));
            int doctor_ID = cursor.getInt(cursor.getColumnIndexOrThrow("doctor_ID"));
            int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
            String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String visitReason = cursor.getString(cursor.getColumnIndexOrThrow("visitReason"));

            appointments.add(new Appointment(ID, patient_ID, doctor_ID, status, note, date,
                                            time, visitReason));
        }

        return appointments;
    }

    // Appointment cancel
    public boolean appointmentCancel(int appointmentID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "UPDATE Appointment SET status = 1 WHERE ID = " + appointmentID;

        ContentValues values = new ContentValues();
        values.put("status", 1);

        long count = db.update("Appointment", values, "ID=?",
                new String[]{Integer.toString(appointmentID)});

        // update
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    // Patient Registration
    public boolean appointmentBooking(Appointment appointment)
    {
        // db
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("patient_ID", appointment.getPatientID());
        values.put("doctor_ID", appointment.getDoctorID());
        values.put("status", appointment.getStatus());
        values.put("date", appointment.getDate());
        values.put("time", appointment.getTime());
        values.put("visitReason", appointment.getVisitReason());

        long count = db.insert("Appointment", null, values);

        // Insert
        if( count == 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public int getAppointmentStatus(int appointmentID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int status = -1;

        String query = "SELECT status FROM Appointment WHERE ID = " + appointmentID;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor != null && cursor.moveToNext())
        {
            status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
        }

        return status;
    }

    // Appointment Remove
    public boolean appointmentRemove(int appointmentID) {
        SQLiteDatabase db = this.getReadableDatabase();

        long count = db.delete("Appointment", "ID=?", new String[]{Integer.toString(appointmentID)});

        // update
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }
}
