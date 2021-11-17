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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.dbentities.Faculty;
import com.mak.pcr.dbentities.Student;

public class UpdateFacultyActivity extends AppCompatActivity {

    TextInputLayout inputLayout_fname, inputLayout_lname, inputLayout_contact;
    EditText edt_fname, edt_lname, edt_contact;
    RadioGroup radiogrp_genders;
    RadioButton radiobtn_male, radiobtn_female;
    Button btn_reset, btn_update;

    DatabaseConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        db = new DatabaseConnection();

        Intent i = getIntent();
        String facultyId = i.getStringExtra("facultyid");

        inputLayout_fname = findViewById(R.id.inpLayout_fname);
        inputLayout_lname = findViewById(R.id.inpLayout_lname);
        inputLayout_contact = findViewById(R.id.inpLayout_contact);
        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_contact = findViewById(R.id.edt_contact);
        radiogrp_genders = findViewById(R.id.radiogrp_genders);
        radiobtn_male = findViewById(R.id.radiobtn_male);
        radiobtn_female = findViewById(R.id.radiobtn_female);
        btn_update = findViewById(R.id.btn_update);
        btn_reset = findViewById(R.id.btn_reset);

        db.get_dbReference("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Faculty f = snapshot.child(facultyId).getValue(Faculty.class);
                edt_fname.setText(f.fname);
                edt_lname.setText(f.lname);
                edt_contact.setText(f.contact);

                radiogrp_genders.clearCheck();
                String gender = f.gender;
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
                String fname, lname, gender, contact;
                fname = edt_fname.getText().toString();
                lname = edt_lname.getText().toString();
                contact = edt_contact.getText().toString();
                gender = ((RadioButton)findViewById( radiogrp_genders.getCheckedRadioButtonId() )).getText().toString();

                db.get_dbReference("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child(facultyId).child("fname").setValue(fname);
                        snapshot.getRef().child(facultyId).child("lname").setValue(lname);
                        snapshot.getRef().child(facultyId).child("contact").setValue(contact);
                        snapshot.getRef().child(facultyId).child("gender").setValue(gender);
                        Utility.MakeToast(UpdateFacultyActivity.this, "Updated!", 0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Utility.MakeToast(UpdateFacultyActivity.this, error.getMessage().toString(), 1);
                    }
                });
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.get_dbReference("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Faculty f = snapshot.child(facultyId).getValue(Faculty.class);

                        edt_fname.setText(f.getFname());
                        edt_lname.setText(f.getLname());
                        edt_contact.setText(f.contact);

                        radiogrp_genders.clearCheck();
                        String gender = f.gender;
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