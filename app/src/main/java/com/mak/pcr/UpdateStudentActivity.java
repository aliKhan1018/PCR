package com.mak.pcr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.dbentities.Student;

public class UpdateStudentActivity extends AppCompatActivity {

    EditText edt_fname, edt_lname, edt_email, edt_phone;
    RadioGroup radiogrp_genders;
    RadioButton radiobtn_male, radiobtn_female;
    Button btn_update, btn_reset;

    DatabaseConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        db = new DatabaseConnection();

        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        radiogrp_genders = findViewById(R.id.radiogrp_genders);
        radiobtn_male = findViewById(R.id.radiobtn_male);
        radiobtn_female = findViewById(R.id.radiobtn_female);
        btn_reset = findViewById(R.id.btn_reset);
        btn_update = findViewById(R.id.btn_update);

        Intent i = getIntent();
        String studentId = i.getStringExtra("studentid");

        db.get_dbReference("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student s = snapshot.child(studentId).getValue(Student.class);

                edt_fname.setText(s.fname);
                edt_lname.setText(s.lname);
                edt_email.setText(s.parentEmail);
                edt_phone.setText(s.parentContact);

                radiogrp_genders.clearCheck();
                String gender = s.gender;
                if (gender.equals("Male")){
                    radiobtn_male.setChecked(true);
                }
                else if (gender.equals("Female")){
                    radiobtn_female.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = edt_fname.getText().toString();
                String lname = edt_lname.getText().toString();
                String email = edt_email.getText().toString();
                String phone = edt_phone.getText().toString();

                String gender = ((RadioButton)findViewById( radiogrp_genders.getCheckedRadioButtonId() )).getText().toString();

                db.get_dbReference("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String batch = snapshot.child(studentId).child("batch_id").getValue().toString();
                        Student updatedStudent = new Student(studentId, fname, lname, gender, phone, email, batch);
                        db.addToDbReference("Student", studentId, updatedStudent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.get_dbReference("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Student s = snapshot.child(studentId).getValue(Student.class);

                        edt_fname.setText(s.fname);
                        edt_lname.setText(s.lname);
                        edt_email.setText(s.parentEmail);
                        edt_phone.setText(s.parentContact);

                        radiogrp_genders.clearCheck();
                        String gender = s.gender;
                        if (gender.equals("Male")){
                            radiobtn_male.setChecked(true);
                        }
                        else if (gender.equals("Female")){
                            radiobtn_female.setChecked(true);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}