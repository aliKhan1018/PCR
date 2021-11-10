package com.mak.pcr.ui.faculty.batchattendence;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.BatchAdapter;
import com.mak.pcr.BatchAttendenceAdapter;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.Utility;
import com.mak.pcr.dbentities.Batch;
import com.mak.pcr.dbentities.Student;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BatchAttendenceFragment extends AppCompatActivity {

    ListView lstvw_batch;
    ArrayList<Student> data;

    DatabaseConnection db;

    @Nullable
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_batch_attendence);

        lstvw_batch = findViewById(R.id.lstvw_batch);

        db = new DatabaseConnection();

        data = new ArrayList<Student>();

        String _f_id = db.get_firebaseAuth().getUid();

        Intent i = getIntent();
        String batchCode = i.getStringExtra("batchcode");


    }
}
