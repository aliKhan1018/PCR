package com.mak.pcr.ui.faculty.batchattendence;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class BatchAttendenceFragment extends Fragment {

    ListView lstvw_batch;
    ArrayList<Student> data;

    DatabaseConnection db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_batch_attendence, container, false);

        lstvw_batch = root.findViewById(R.id.lstvw_batch);

        db = new DatabaseConnection();

        data = new ArrayList<Student>();

        String _f_id = db.get_firebaseAuth().getUid();


        // TODO fetch batchcode
        db.get_dbReference("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()){
                    Student s = dss.getValue(Student.class);
                    data.add(s);
                }
                BatchAttendenceAdapter adapter = new BatchAttendenceAdapter(data, container.getContext());
                lstvw_batch.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

}
