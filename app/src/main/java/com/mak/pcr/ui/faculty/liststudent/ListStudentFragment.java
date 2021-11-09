package com.mak.pcr.ui.faculty.liststudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.StudentAdapter;
import com.mak.pcr.Utility;
import com.mak.pcr.dbentities.Batch;
import com.mak.pcr.dbentities.Student;

import java.util.ArrayList;

public class ListStudentFragment extends Fragment {

    ListView lstvw_student;
    ArrayList<Student> students;

    DatabaseConnection db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View _root = inflater.inflate(R.layout.fragment_list_student, container, false);

        db = new DatabaseConnection();

        lstvw_student = _root.findViewById(R.id.lstvw_student);

        String _f_id = db.get_firebaseAuth().getUid();

        students = new ArrayList<Student>();

        db.get_dbReference("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()){
                    Student _s = dss.getValue( Student.class  );
                    db.get_dbReference("Batch").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot _dss : snapshot.getChildren()){
                                Batch _b = _dss.getValue(Batch.class);
                                if(_dss.getKey().matches(_s.getBatch_id()+" (TTS)")){
                                    if(_f_id.matches(_b.faculty_id)){
                                        students.add(_s);

                                    }
//                                    Utility.MakeToast(container.getContext(), _b.batchCode, 0);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                StudentAdapter adapter = new StudentAdapter(students, container.getContext());
                lstvw_student.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return _root;
    }
}
