package com.example.haystreethealthyhub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PatientProfileFragment extends Fragment {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Spinner gender;
    private TextView dob;
    private EditText height;
    private EditText weight;
    private Spinner gp;

    private Patient patient;

    private int userID;
    DBHelper dbHelper;

    Button profileEditIcon;

    Context _context;

    // Inner tool class
    RegistrationActivity.Tools tools = new RegistrationActivity.Tools();

    // Validation marker for First name, Last nme
    private boolean firstNameMarker = false;
    private boolean lastNameMarker = false;
    // ========================================================================

    public PatientProfileFragment(Context context)
    {
        _context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.patient_profile_fragment, container,false);

        // Get userID from home page
        userID = getArguments().getInt("userID");

        // Get patient details to populate profile page
        dbHelper = new DBHelper(this.getActivity());
        patient = dbHelper.getPatient(userID);

        // initialize fields=======================================
        firstName = view.findViewById(R.id.Registration_FirstName);
        lastName = view.findViewById(R.id.Registration_LastName);
        email = view.findViewById(R.id.Registration_Email);
        password = view.findViewById(R.id.Registration_Password);
        confirmPassword = view.findViewById(R.id.Registration_ConfirmPassword);
        gender = view.findViewById(R.id.PersonalInfo_Gender);
        dob = view.findViewById(R.id.PersonalInfo_Dob);
        height = view.findViewById(R.id.PersonalInfo_Height);
        weight = view.findViewById(R.id.PersonalInfo_Weight);
        gp = view.findViewById(R.id.PersonalInfo_GP);

        // profile edit icon
        // TODO set this icon invisible when open other fragment
        profileEditIcon = getActivity().findViewById(R.id.profile_edit_icon);
        profileEditIcon.setVisibility(View.VISIBLE);

        // Set ConfirmPassword invisible
        confirmPassword.setVisibility(View.INVISIBLE);
        // ========================================================

        // Populate profile page
        populateProfilePage();

        // Set fields to not editable
        setFieldsUnEditable();

        // Click on icon and open ActionMode bar
        profileEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                profileEditButton();
            }
        });

        // Edit validation =================================
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

        // Password
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                tools.checkPassword(hasFocus, password, confirmPassword);
            }
        });

        // Confirm Password
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                tools.checkPassword(hasFocus, password, confirmPassword);
            }
        });

        //End of Validation ===================================================

        return view;

    }


    // Set fields to not editable
    // For profile page display
    public void setFieldsUnEditable()
    {
        firstName.setEnabled(false);
        //firstName.setFocusable(false);
        //firstName.setClickable(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);
        confirmPassword.setEnabled(false);
        // Set ConfirmPassword invisible
        confirmPassword.setVisibility(View.INVISIBLE);
        gender.setEnabled(false);
        dob.setEnabled(false);
        height.setEnabled(false);
        weight.setEnabled(false);
        gp.setEnabled(false);
    }

    // Populate profile page
    public void populateProfilePage()
    {
        firstName.setText(patient.getFirstName());
        lastName.setText(patient.getLastName());
        email.setText(patient.getEmail());
        password.setText(patient.getPassword());
        // Gender
        switch (patient.getGender())
        {
            case 0:
                gender.setSelection(0);
                break;
            case 1:
                gender.setSelection(1);
                break;
            case 2:
                gender.setSelection(2);
                break;
        }
        // dob
        dob.setText(patient.getDob());
        // height
        height.setText(Integer.toString(patient.getHeight()));
        // weight
        weight.setText(Integer.toString(patient.getWeight()));
        // GP
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray = dbHelper.getDoctorsName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gp.setAdapter(adapter);

        String gpName = dbHelper.getDoctorName(patient.getGp());
        for(int i=0; i<gp.getAdapter().getCount(); i++)
        {
            if(gp.getItemAtPosition(i).toString() == gpName );
            gp.setSelection(i);
        }
    }

    // ActionMode Menu for profile page
    public void profileEditButton()
    {
        // ActionMode Menu
        getActivity().startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.profile_action_mode_menu, menu);
                actionMode.setTitle("Edit your profile");
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
                    case R.id.option_edit:
                        Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
                        actionMode.finish();
                        setFieldsEditable();
                        return true;

                    case R.id.option_save:
                        actionMode.finish();
                        profileEditSave();

                        return true;

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

    // Set fields editable for profile edit
    public void setFieldsEditable()
    {
        firstName.setEnabled(true);
        lastName.setEnabled(true);
        password.setEnabled(true);
        gender.setEnabled(true);
        height.setEnabled(true);
        weight.setEnabled(true);
        gp.setEnabled(true);

        confirmPassword.setVisibility(View.VISIBLE);
        confirmPassword.setEnabled(true);
    }

    // EditSave
    public void profileEditSave()
    {
        // Check password changed or not
        if(password.getText().toString().trim().equals(patient.getPassword()) &&
                confirmPassword.getText().toString().trim().equals(""))
        {
            // user didn't changed password than set the confirmPassword automatically
            confirmPassword.setText(password.getText().toString().trim());
        }
        // =============================

        tools.performClick(firstName, lastName, email);
        if(tools.checkEmpty(firstName, lastName, email, height, weight, dob) &&
                tools.checkPassword(false, password, confirmPassword) &&
                firstNameMarker &&
                lastNameMarker
        )
        {
            patient.setFirstName(firstName.getText().toString().trim());
            patient.setLastName(lastName.getText().toString().trim());
            patient.setPassword(password.getText().toString().trim());
            patient.setGender(gender.getSelectedItemPosition());
            patient.setHeight(Integer.parseInt(height.getText().toString().trim()));
            patient.setWeight(Integer.parseInt(weight.getText().toString().trim()));
            patient.setGp(gp.getSelectedItemPosition());


            if( dbHelper.updatePatient(patient) )
            {
                // Saved!
                confirmUpdateSaved();
                setFieldsUnEditable();
            }
            else
            {
                // Fail!
                Toast.makeText(getActivity(), "App Error!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            updateFail();
        }

    }

    // Profile update Dialog
    public void confirmUpdateSaved()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Profile Updated ！")
                .setMessage("You have updated successfully.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.create().show();
    }

    // Profile update Dialog
    public void updateFail()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error ！")
                .setMessage("All fields required, and check input format please.")
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.create().show();
    }

}
