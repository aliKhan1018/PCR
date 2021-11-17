package com.mak.pcr.ui.createfaculty;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.Utility;
import com.mak.pcr.dbentities.Faculty;

import org.jetbrains.annotations.NotNull;

public class CreateFacultyFragment extends Fragment {

    TextInputLayout inputLayout_fname, inputLayout_lname, inputLayout_email,
            inputLayout_pswd, inputLayout_confirmPswd, inputLayout_contact;
    EditText edt_fname, edt_lname, edt_email,
            edt_pswd, edt_confirmPswd, edt_contact;
    RadioGroup radiogrp_genders;
    Button btn_create, btn_clear;

    DatabaseConnection db;
    DatabaseReference dbRefFaculty;
    FirebaseAuth firebaseAuth;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get signup fragment
        View _root = inflater.inflate(R.layout.fragment_create_faculty, container, false);

        // Get UI elements
        inputLayout_fname = _root.findViewById(R.id.inpLayout_fname);
        inputLayout_lname = _root.findViewById(R.id.inpLayout_lname);
        inputLayout_email = _root.findViewById(R.id.inpLayout_email);
        inputLayout_pswd = _root.findViewById(R.id.inpLayout_pswd);
        inputLayout_contact = _root.findViewById(R.id.inpLayout_contact);
        inputLayout_confirmPswd = _root.findViewById(R.id.inpLayout_confirmPswd);
        edt_fname = _root.findViewById(R.id.edt_fname);
        edt_lname = _root.findViewById(R.id.edt_lname);
        edt_email = _root.findViewById(R.id.edt_email);
        edt_pswd = _root.findViewById(R.id.edt_pswd);
        edt_confirmPswd = _root.findViewById(R.id.edt_confirmPswd);
        edt_contact = _root.findViewById(R.id.edt_contact);
        radiogrp_genders = _root.findViewById(R.id.radiogrp_genders);
        btn_create = _root.findViewById(R.id.btn_assign);
        btn_clear = _root.findViewById(R.id.btn_clear);

        db = new DatabaseConnection();
        firebaseAuth = db.get_firebaseAuth();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _sFName, _sLName, _sEmail, _sPswd, _sConfirmPswd, _sGender, _sContact;
                _sFName = edt_fname.getText().toString();
                _sLName = edt_lname.getText().toString();
                _sEmail = edt_email.getText().toString();
                _sPswd = edt_pswd.getText().toString();
                _sConfirmPswd = edt_confirmPswd.getText().toString();
                _sContact = edt_contact.getText().toString();

                int _iSelectedRadioBtnId = radiogrp_genders.getCheckedRadioButtonId();
                RadioButton radiobtn_selectedGender = _root.findViewById(_iSelectedRadioBtnId);
                _sGender = radiobtn_selectedGender.getText().toString();

                if (TextUtils.isEmpty(_sFName)) {
                    inputLayout_fname.setError("First Name is Required");
                    edt_fname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(_sLName)) {
                    inputLayout_lname.setError("Last Name is Required");
                    edt_lname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(_sEmail)) {
                    inputLayout_email.setError("Email is Required");
                    edt_email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(_sEmail).matches()) {
                    inputLayout_email.setError("Enter a Valid Email");
                    edt_email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(_sPswd)) {
                    inputLayout_pswd.setError("Password is Required");
                    edt_pswd.requestFocus();
                    return;
                }
                if (_sPswd.length() > 6) {
                    inputLayout_pswd.setError("Password must be less than or equal to 6 characters");
                    edt_pswd.requestFocus();
                    return;
                }

                if(!_sPswd.matches(_sConfirmPswd)){
                    inputLayout_confirmPswd.setError("Passwords do not match!");
                    edt_confirmPswd.requestFocus();
                    return;
                }

                if (_sContact.length() != 11) {
                    inputLayout_contact.setError("Contact number must be 11 digits");
                    edt_contact.requestFocus();
                    return;
                }

                db.CreateUser(_sEmail, _sPswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Utility.MakeToast(container.getContext(), "Creating...", 0);

                            Faculty _f = new Faculty(_sFName, _sLName, _sGender, _sEmail, _sContact);
                            String _id = task.getResult().getUser().getUid();

                            db.addToDbReference("Faculty", _id, _f).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isComplete()){
                                        Utility.MakeToast(container.getContext(), "Created!", 0);
                                    }
                                    else{
                                        Utility.MakeToast(container.getContext(), task.getException().toString(), 0);
                                    }
                                }
                            });
                        }
                        else{
                            Utility.MakeToast(container.getContext(), task.getException().getMessage(), 1);
                        }
                    }
                });

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_fname.setText("");
                edt_lname.setText("");
                edt_email.setText("");
                edt_pswd.setText("");
                edt_confirmPswd.setText("");
                edt_contact.setText("");

                inputLayout_pswd.setError(null);
                inputLayout_email.setError(null);
                inputLayout_confirmPswd.setError(null);
                inputLayout_fname.setError(null);
                inputLayout_lname.setError(null);
                inputLayout_contact.setError(null);

                edt_fname.requestFocus();
            }
        });

        return _root;
    }
}
