package com.mak.pcr.ui.createstudent;

import android.os.Bundle;
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

public class CreateStudentFragment extends Fragment {

    TextInputLayout inputLayout_fname, inputLayout_lname, inputLayout_email, inputLayout_phone;
    EditText edt_fname, edt_lname, edt_email, edt_phone;
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
        inputLayout_phone = _root.findViewById(R.id.inpLayout_phone);
        edt_fname = _root.findViewById(R.id.edt_fname);
        edt_lname = _root.findViewById(R.id.edt_lname);
        edt_email = _root.findViewById(R.id.edt_email);
        edt_phone = _root.findViewById(R.id.edt_phone);
        radiogrp_genders = _root.findViewById(R.id.radiogrp_genders);
        btn_create = _root.findViewById(R.id.btn_assign);
        btn_clear = _root.findViewById(R.id.btn_clear);

        db = new DatabaseConnection();
        firebaseAuth = db.get_firebaseAuth();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _studentId, _sFName, _sLName, _sEmail, _sContact,  _sGender;
                _sFName = edt_fname.getText().toString();
                _sLName = edt_lname.getText().toString();
                _sEmail = edt_email.getText().toString();
                _sContact = edt_phone.getText().toString();

                int _iSelectedRadioBtnId = radiogrp_genders.getCheckedRadioButtonId();
                RadioButton radiobtn_selectedGender = _root.findViewById(_iSelectedRadioBtnId);
                _sGender = radiobtn_selectedGender.getText().toString();

                if (TextUtils.isEmpty(_sFName)) {
                    edt_fname.setError("First Name is Required");
                    edt_fname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(_sLName)) {
                    edt_lname.setError("Last Name is Required");
                    edt_lname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(_sEmail)) {
                    edt_email.setError("Email is Required");
                    edt_email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(_sEmail).matches()) {
                    edt_email.setError("Enter a Valid Email");
                    edt_email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(_sContact)) {
                    edt_pswd.setError("Contact Number is Required");
                    edt_pswd.requestFocus();
                    return;
                }   


            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_fname.setText("");
                edt_lname.setText("");
                edt_email.setText("");

                inputLayout_email.setError(null);
                inputLayout_fname.setError(null);
                inputLayout_lname.setError(null);

                edt_fname.requestFocus();
            }
        });

        return _root;
    }
}
