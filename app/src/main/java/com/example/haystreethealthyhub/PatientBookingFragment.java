package com.example.haystreethealthyhub;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class PatientBookingFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    Spinner gp;
    EditText visitReason;
    TextView visitDate;
    TextView visitTime;
    View view;
    DBHelper dbHelper;
    int doctorID;
    Button appointmentBooking;
    int userID = 0;
    String userType;

    AppointmentListFragment appointmentListFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.patient_booking_fragment, container,false);

        appointmentListFragment = new AppointmentListFragment();
        gp = view.findViewById(R.id.Booking_GP);
        visitReason = view.findViewById(R.id.Booking_VisitReason);
        visitDate = view.findViewById(R.id.Booking_Date);
        visitTime = view.findViewById(R.id.Booking_Time);
        appointmentBooking = view.findViewById(R.id.Booking_Booking);

        // Get userID
        userID = getArguments().getInt("userID");
        userType = getArguments().getString("userType");

        // DB Connection
        dbHelper = new DBHelper(getActivity());

        //===================================================

        // Populate items for GP spinner
        RegistrationActivity.Tools.setGpSpinner(dbHelper, gp, getActivity());
        // ===================================================

        // Fetch Doctor's ID =================================
        gp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doctorID = dbHelper.getDoctorID(gp.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Date
        visitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Time
        visitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
            }
        });

        // Booking button
        appointmentBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check VisitReason Date and Time
                if(!visitReason.getText().toString().matches("") &&
                        !visitDate.getText().toString().matches("") &&
                        !visitTime.getText().toString().matches(""))
                {
                    // Set status to 4 : Incoming
                    Appointment appointment = new Appointment(
                            userID,
                            doctorID,
                            4,
                            visitDate.getText().toString().trim(),
                            visitTime.getText().toString().trim(),
                            visitReason.getText().toString().trim()
                    );


                    if( dbHelper.appointmentBooking(appointment) )
                    {
                        // Booked!
                        confirmBooking();

                    }
                    else
                    {
                        // Fail!
                        Toast.makeText(getActivity(), "App Error!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    bookingFail();
                }

            }
        });

        // Input validation =================================



        return view;
    }

    // Show Date picker dialog
    private void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
        visitDate.setText(date);

    }

    // ===================================================

    public void confirmBooking()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Congratulations ！")
                .setMessage("You have booked successfully, return to appointments.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Pass data to booking page
                        Bundle bundle = new Bundle();
                        bundle.putInt("userID", userID);
                        bundle.putString("userType", userType);
                        appointmentListFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, appointmentListFragment)
                                .commit();
                    }
                });

        builder.create().show();
    }

    // Booking confirmed Dialog
    public void bookingFail()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Booking Fail ！")
                .setMessage("All fields required, and check format!")
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.create().show();
    }
}
