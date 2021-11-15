package com.mak.pcr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.dbentities.Faculty;
import com.mak.pcr.dbentities.Student;

public class UpdateFacultyActivity extends AppCompatActivity {

    TextInputLayout inputLayout_fname, inputLayout_lname, inputLayout_contact;
    EditText edt_fname, edt_lname, edt_contact;
    Button btn_reset, btn_update;

    DatabaseConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        db = new DatabaseConnection();

        Intent i = getIntent();
        String facultyEmail = i.getStringExtra("facultyemail");

        inputLayout_fname = findViewById(R.id.inpLayout_fname);
        inputLayout_lname = findViewById(R.id.inpLayout_lname);
        inputLayout_contact = findViewById(R.id.inpLayout_contact);
        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_contact = findViewById(R.id.edt_contact);
        btn_update = findViewById(R.id.btn_update);
        btn_reset = findViewById(R.id.btn_reset);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.get_dbReference("Faculty").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dss : snapshot.getChildren()){
                            Faculty f = dss.getValue(Faculty.class);
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