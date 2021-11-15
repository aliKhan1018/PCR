package com.mak.pcr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.dbentities.Student;

import java.util.ArrayList;

public class BatchAttendanceActivity extends AppCompatActivity {

    TextView txtvw_batchCode;
    ListView lstvw_students;
    ArrayList<Student> data;
    DatabaseConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_attendance);

        lstvw_students = findViewById(R.id.lstvw_students);
        txtvw_batchCode = findViewById(R.id.txtvw_batchCode);

        db = new DatabaseConnection();

        data = new ArrayList<Student>();

        String _f_id = db.get_firebaseAuth().getUid();

        Intent i = getIntent();
        String batchCode = i.getStringExtra("batchCode");

        txtvw_batchCode.setText(batchCode);


        // TODO fetch batchcode
        db.get_dbReference("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()){
                    Student s = dss.getValue(Student.class);
                    if(batchCode.equals(s.getBatch_id())){
                        data.add(s);
                    }
                }
                BatchAttendenceAdapter adapter = new BatchAttendenceAdapter(data, BatchAttendanceActivity.this);
                lstvw_students.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}