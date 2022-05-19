package com.example.haystreethealthyhub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentListFragment extends Fragment {

    RecyclerView recyclerView;


    // List item

    // AppointmentID
    ArrayList appointmentID = new ArrayList();
    // user note
    ArrayList appointmentNote = new ArrayList();
    // doctor's name
    ArrayList appointmentDoctorName = new ArrayList();
    // status
    ArrayList appointmentStatus = new ArrayList();
    // date
    ArrayList appointmentDate = new ArrayList();
    // time
    ArrayList appointmentTime = new ArrayList();
    // visit reason
    ArrayList appointmentVisitReason = new ArrayList();

    // Database
    DBHelper dbHelper;

    // User id
    private int userID;
    private String userType;
    ArrayList<Appointment> appointments =new ArrayList<>();

    View view;

    Button appointmentListEditIcon;

    int appointmentCancelID = 0;
    int _selectedPosition = -1;

    // Booking button
    Button appointmentBooking;

    AppointmentListViewAdapter adapter;
    PatientBookingFragment patientBookingFragment;

    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Get userID from home page
        userID = getArguments().getInt("userID");
        userType = getArguments().getString("userType");

        // Reuse the same icon used in profil edit
        if(userType.equals("D"))
        {
            appointmentListEditIcon = getActivity().findViewById(R.id.action_bar_edit_icon);
        }
        else
        {
            appointmentListEditIcon = getActivity().findViewById(R.id.profile_edit_icon);
        }

        appointmentListEditIcon.setVisibility(View.VISIBLE);



        // initialize if view is null
        // Avoid showing duplicate view item when turn back this fragment
        if(view == null)
        {

            view=inflater.inflate(R.layout.patient_appointment_list_fragment, container,false);


            // Get all appointments of this user
            dbHelper = new DBHelper(this.getActivity());
            appointments = dbHelper.getAllAppointment(userID, userType);

            // Appointment ListView
            recyclerView = view.findViewById(R.id.appointment_listView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            // Fill all ArrayLists
            fillListItem();

            adapter = new AppointmentListViewAdapter(getActivity(), appointmentDoctorName,
                    appointmentStatus, appointmentDate, appointmentTime, appointmentVisitReason, appointmentNote,
                    appointmentID);
            adapter.setHasStableIds(true);

            appointmentBooking = view.findViewById(R.id.appointment_list_booking);
            // Make booking button
            if(userType.equals("D"))
            {
                appointmentBooking.setVisibility(View.GONE);
                linearLayout = view.findViewById(R.id.bookingButtonLayout);
                linearLayout.setVisibility(View.GONE);
            }



            patientBookingFragment = new PatientBookingFragment();

            recyclerView.setAdapter(adapter);

        }

        // Click on icon and open ActionMode bar
        appointmentListEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                cancelAppointment();
            }
        });

        // Booking click
        appointmentBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass data to booking page
                Bundle bundle = new Bundle();
                bundle.putInt("userID", userID);
                bundle.putString("userType", userType);
                patientBookingFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, patientBookingFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Click listener for recyclerview item
        adapter.setOnItemClickListener(new AppointmentListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(), "List item click ", Toast.LENGTH_SHORT).show();
                // Leave here blank, click call back in Adapter
            }
        });


        // Initialize listener for list item radio button
        adapter.setOnRadioButtonClickListener(new AppointmentListViewAdapter.RadioButtonClickListener() {
            @Override
            public void onClick(int appointmentID) {

                // Display toast
                Toast.makeText(getActivity(), "AppointmentID : "
                        + appointmentID, Toast.LENGTH_SHORT).show();

                appointmentCancelID = appointmentID;
                // keep this for item single selection
                recyclerView.post(new Runnable() {
                    @Override public void run()
                    {
                        adapter.notifyDataSetChanged();
                    }
                });



            }

        });
        if(userType.equals("P"))
        {

        }
        return view;
    }

    // Fill all ArrayLists
    public void fillListItem()
    {
        for(int i=0; i<appointments.size(); i++)
        {
            appointmentID.add(appointments.get(i).getID());
            appointmentNote.add(appointments.get(i).getNote());
            if(userType.equals("P"))
            {
                // Get doctor name for patient
                appointmentDoctorName.add("Dr. " + dbHelper.getDoctorName(appointments.get(i).getDoctorID()));
            }
            else
            {
                // Get patient name for doctor
                appointmentDoctorName.add(dbHelper.getPatientName(appointments.get(i).getPatientID()));
            }

            appointmentStatus.add(Integer.toString(appointments.get(i).getStatus()));
            appointmentDate.add(appointments.get(i).getDate());
            appointmentTime.add(appointments.get(i).getTime());
            appointmentVisitReason.add(appointments.get(i).getVisitReason());

        }
    }


    // ActionMode Menu for profile page
    public void cancelAppointment()
    {
        // ActionMode Menu
        getActivity().startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                if(userType.equals("P"))
                {
                    actionMode.getMenuInflater().inflate(R.menu.appointment_booking_action_mode_menu, menu);
                    actionMode.setTitle("Cancel your booking?");
                }
                else
                {
                    actionMode.getMenuInflater().inflate(R.menu.doctor_booking_delete_action_mode_menu, menu);
                    actionMode.setTitle("Remove selected booking?");
                }


                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.option_cancel:
                        // no item selected
                        if(appointmentCancelID == 0)
                        {
                            appointmentCancelFail();
                        }
                        else
                        {
                            if(dbHelper.getAppointmentStatus(appointmentCancelID) != 4)
                            {
                                Toast.makeText(getActivity(),
                                        "Can not cancel appointment!",
                                        Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                confirmAppointmentCancel();
                            }

                        }

                        actionMode.finish();
                        return true;

                    case R.id.option_remove:
                        confirmAppointmentRemove();
                        actionMode.finish();
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                actionMode = null;

            }

        });
    }

    // Appointment cancel
    public void confirmAppointmentCancel()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cancel Appointment!")
                .setMessage("You are gonna cancel your appointment!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
                        // Appointment cancel
                        boolean cancelValue = dbHelper.appointmentCancel(appointmentCancelID);
                        // Canceled
                        if(cancelValue)
                        {
                            Toast.makeText(getActivity(), "Appointment Canceled!", Toast.LENGTH_SHORT).show();

                            // ========================================================
                            // 1.Clear current view
                            // Clear the Dataset in adapter
                            adapter.clearData();

                            // 2.Update appointment status ========================
                            appointments.clear();
                            appointments = dbHelper.getAllAppointment(userID, userType);
                            fillListItem();

                            // Notify adapter
                            recyclerView.post(new Runnable() {
                                @Override public void run()
                                {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            // ==================================================
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
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

    // Doctor remove appointment
    public void confirmAppointmentRemove()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove Appointment!")
                .setMessage("You are gonna remove selected appointment!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
                        // Appointment Remove
                        boolean removeValue = dbHelper.appointmentRemove(appointmentCancelID);
                        // Canceled
                        if(removeValue)
                        {
                            Toast.makeText(getActivity(), "Appointment Removed!", Toast.LENGTH_SHORT).show();

                            // ========================================================
                            // 1.Clear current view
                            // Clear the Dataset in adapter
                            adapter.clearData();

                            // 2.Update appointment status ========================
                            appointments.clear();
                            appointments = dbHelper.getAllAppointment(userID, userType);
                            fillListItem();

                            // Notify adapter
                            recyclerView.post(new Runnable() {
                                @Override public void run()
                                {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            // ==================================================
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
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

    // Appointment cancel fail
    public void appointmentCancelFail()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cancel Error ！")
                .setMessage("No appointment selected!")
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.create().show();
    }

}
