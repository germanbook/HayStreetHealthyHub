package com.example.haystreethealthyhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppointmentDetailFragment extends Fragment {


    TextView doctorName;
    TextView appointmentNote;
    String tempDoctorName;
    String tempAppointmentNote;
    AppointmentListFragment appointmentListFragment;
    View view;

    Button btnBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.patient_appointment_detail_fragment, container,false);
        appointmentListFragment = new AppointmentListFragment();

        doctorName = view.findViewById(R.id.appointment_details_docName);
        appointmentNote = view.findViewById(R.id.appointment_details_note);
        btnBack = view.findViewById(R.id.appointment_details_btnback);

        // Get Doctor name and note
        tempDoctorName = getArguments().getString("doctorName");
        tempAppointmentNote = getArguments().getString("note");

        doctorName.setText("Dr. " + tempDoctorName);
        if(tempAppointmentNote != null)
        {
            appointmentNote.setText(tempAppointmentNote);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, patientAppointmentListFragment).commit();
                getActivity().onBackPressed();
            }
        });


        return view;
    }
}
