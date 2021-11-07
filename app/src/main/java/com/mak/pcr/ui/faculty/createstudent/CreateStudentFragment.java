package com.mak.pcr.ui.faculty.createstudent;

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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.dbentities.Batch;
import com.mak.pcr.dbentities.Student;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreateStudentFragment extends Fragment {

    TextInputLayout inputLayout_fname, inputLayout_lname, inputLayout_email, inputLayout_phone;
    EditText edt_fname, edt_lname, edt_email, edt_phone;
    RadioGroup radiogrp_genders;
    Spinner spner_batch;
    Button btn_create, btn_clear;

    DatabaseConnection db;
    DatabaseReference dbRefFaculty;
    FirebaseAuth firebaseAuth;

    ArrayList<String> batches;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get signup fragment
        View _root = inflater.inflate(R.layout.fragment_create_student, container, false);

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
        spner_batch = _root.findViewById(R.id.spner_batch);
        btn_create = _root.findViewById(R.id.btn_assign);
        btn_clear = _root.findViewById(R.id.btn_clear);

        db = new DatabaseConnection();
        firebaseAuth = db.get_firebaseAuth();

        batches = new ArrayList<>();
        batches.add("Select a Batch...");

        String _f_id = db.get_firebaseAuth().getUid();

        db.get_dbReference("Batch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()){
                    Batch _b = dss.getValue( Batch.class );
                    if(_b.faculty_id.matches(_f_id)){
                        batches.add( _b.batchCode );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _studentId, _fName, _lName, _email, _contact,  _gender, _batch_id;
                _fName = edt_fname.getText().toString();
                _lName = edt_lname.getText().toString();
                _email = edt_email.getText().toString();
                _contact = edt_phone.getText().toString();



                int _iSelectedRadioBtnId = radiogrp_genders.getCheckedRadioButtonId();
                RadioButton radiobtn_selectedGender = _root.findViewById(_iSelectedRadioBtnId);
                _gender = radiobtn_selectedGender.getText().toString();

                if (TextUtils.isEmpty(_fName)) {
                    inputLayout_fname.setError("First Name is Required");
                    inputLayout_fname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(_lName)) {
                    inputLayout_lname.setError("Last Name is Required");
                    inputLayout_lname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(_email)) {
                    inputLayout_email.setError("Email is Required");
                    inputLayout_email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
                    inputLayout_email.setError("Enter a Valid Email");
                    inputLayout_email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(_contact)) {
                    inputLayout_phone.setError("Contact Number is Required");
                    inputLayout_phone.requestFocus();
                    return;
                }
                if (_contact.length() != 11) {
                    inputLayout_phone.setError("Contact Number must be 11 digits");
                    inputLayout_phone.requestFocus();
                    return;
                }

                _studentId = "";
                char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
                for (int i = 0; i < 7; i++){
                    int randomInt = (int) Math.ceil(Math.random() * 9);
                    _studentId += numbers[randomInt];
                }
                db.addToDbReference("Student", _studentId, new Student(_studentId, _fName, _lName,
                        _gender, _contact, _email, _batch_id));


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
