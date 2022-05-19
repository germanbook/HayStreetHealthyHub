package com.example.haystreethealthyhub;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentListViewAdapter extends RecyclerView.Adapter<AppointmentListViewAdapter.ViewHolder> {

    private static Object ViewHolder;
    // List item
    // AppointmentID
    ArrayList appointmentID = new ArrayList();
    // note
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

    // Selected position
    int selectedPosition = -1;
    RadioButtonClickListener radioButtonClickListener;

    Context context;

    AppointmentDetailFragment patientAppointmentFragment;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;



    // ********************************************************************************************
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void setOnRadioButtonClickListener(RadioButtonClickListener radioButtonClickListener)
    {
        this.radioButtonClickListener = radioButtonClickListener;
    }


    public AppointmentListViewAdapter(Context context, ArrayList appointmentDoctorName,
                                      ArrayList appointmentStatus, ArrayList appointmentDate,
                                      ArrayList appointmentTime, ArrayList appointmentVisitReason,
                                      ArrayList appointmentNote, ArrayList appointmentID)
    {
        this.context = context;
        this.appointmentID = appointmentID;
        this.appointmentNote = appointmentNote;
        this.appointmentDoctorName = appointmentDoctorName;
        this.appointmentStatus = appointmentStatus;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentVisitReason = appointmentVisitReason;
    }


    @NonNull
    @Override
    public AppointmentListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        AppointmentListViewAdapter.ViewHolder viewHolder = new AppointmentListViewAdapter.ViewHolder(view);
        patientAppointmentFragment = new AppointmentDetailFragment();

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull AppointmentListViewAdapter.ViewHolder holder, int position) {

        // ID for patient cancel appointment use
        holder.appointmentID = (Integer) appointmentID.get(position);

        holder.appointmentName.setText((String)appointmentDoctorName.get(position));
        //holder.appointmentStatus.setText((String)appointmentStatus.get(position));
        holder.appointmentDate.setText((String)appointmentDate.get(position));
        holder.appointmentTime.setText((String)appointmentTime.get(position));
        holder.appointmentVisitReason.setText((String)appointmentVisitReason.get(position));
        holder.appointmentListEditIcon.setVisibility(View.VISIBLE);


        // Status
        switch ((String) appointmentStatus.get(position))
        {
            case "1":
                // Canceled
                holder.appointmentStatus.setText("Canceled");
                holder.appointmentStatus.setTextColor(Color.parseColor("#E4414343"));
                break;
            case "2":
                // Missed
                holder.appointmentStatus.setText("Missed");
                holder.appointmentStatus.setTextColor(Color.parseColor("#DACA0E00"));
                break;
            case "3":
                // Attended
                holder.appointmentStatus.setText("Attended");
                holder.appointmentStatus.setTextColor(Color.parseColor("#FFFFFFFF"));
                break;
            case "4":
                // Incoming
                holder.appointmentStatus.setText("Incoming");
                holder.appointmentStatus.setTextColor(Color.parseColor("#FFFFFFFF"));
                break;
        }




        // Checked selected radio button
        holder.appointmentListEditIcon.setChecked(position == selectedPosition);

        // set listener on radio button
        holder.appointmentListEditIcon.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                    {
                        // check condition
                        if (b)
                        {
                            // When checked
                            // update selected position
                            selectedPosition = holder.getAdapterPosition();
                            // Call listener
                            radioButtonClickListener.onClick(holder.appointmentID);
                        }
                    }
                });

        if(mOnItemClickListener != null){
            //set listener for ItemView
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2

                    Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show();
                    // Set doctor name and note to detail fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("doctorName", (String) appointmentDoctorName.get(position));
                    bundle.putString("note", (String) appointmentNote.get(position));

                    // pass data
                    patientAppointmentFragment.setArguments(bundle);
                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, patientAppointmentFragment).addToBackStack(null)
                            .commit();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return appointmentDoctorName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        int appointmentID;
        TextView appointmentName;
        TextView appointmentStatus;
        TextView appointmentDate;
        TextView appointmentTime;
        TextView appointmentVisitReason;
        // Apppointment list page edit icon
        RadioButton appointmentListEditIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentID = 0;
            appointmentName = (TextView) itemView.findViewById(R.id.appointment_name);
            appointmentStatus = (TextView) itemView.findViewById(R.id.appointment_status);
            appointmentDate = (TextView) itemView.findViewById(R.id.appointment_date);
            appointmentTime = (TextView) itemView.findViewById(R.id.appointment_time);
            appointmentVisitReason = (TextView) itemView.findViewById(R.id.appointment_visitReason);
            appointmentListEditIcon = (RadioButton) itemView.findViewById(R.id.appointment_edit_radiobutton);
        }

    }

    // Inner interface for Recyclerview item click listener
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    public interface RadioButtonClickListener {
        // Create method
        void onClick(int appointmentID);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    // Clear all data
    public void clearData()
    {
        this.appointmentID.clear();
        this.appointmentNote.clear();
        this.appointmentDoctorName.clear();
        this.appointmentStatus.clear();
        this.appointmentDate.clear();
        this.appointmentTime.clear();
        this.appointmentVisitReason.clear();
    }

}
