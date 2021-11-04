package com.mak.pcr.ui.assignfaculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.dbentities.Batch;
import com.mak.pcr.dbentities.Faculty;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AssignFacultyFragment extends Fragment {

    Spinner spner_faculty, spner_batch;
    Button btn_create, btn_clear;

    ArrayList<String> availableBatches;
    ArrayList<String> availableFaculty;
    ArrayList<String> availableFacultyId;

    ArrayAdapter<String> stringArrayAdapter;

    DatabaseConnection db;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View _root = inflater.inflate(R.layout.fragment_assign_faculty, container, false);
        db = new DatabaseConnection();

        spner_batch = _root.findViewById(R.id.spner_batch);
        spner_faculty = _root.findViewById(R.id.spner_faculty);
        btn_create = _root.findViewById(R.id.btn_assign);
        btn_clear = _root.findViewById(R.id.btn_clear);

        availableBatches = new ArrayList<>();
        availableBatches.add("Select a batch...");

        availableFaculty = new ArrayList<>();
        availableFaculty.add("Faculty to Assign...");

        availableFacultyId = new ArrayList<>();


        db.get_dbReference("Batch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()){
                    Batch _b = dss.getValue( Batch.class );
                    if(_b.faculty_id == null || _b.faculty_id.matches("")){
                        availableBatches.add(dss.getKey().toString());
                    }
                }
                stringArrayAdapter = new ArrayAdapter<String>(container.getContext(), R.layout.spinner_item, availableBatches);
                spner_batch.setAdapter(stringArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        db.get_dbReference("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()){
                    Faculty _f = dss.getValue( Faculty.class );
                    String _genderPrefix = _f.gender.matches( "Male" ) ? "Sir" : "Miss";
                    availableFaculty.add(_genderPrefix + " " + _f.fname);
                    availableFacultyId.add(dss.getKey().toString());
                }
                stringArrayAdapter = new ArrayAdapter<String>(container.getContext(), R.layout.spinner_item, availableFaculty);
                spner_faculty.setAdapter(stringArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.get_dbReference("Batch").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        int _correctedPosition = (int)spner_faculty.getSelectedItemId() - 1;
                        String _faculty_id = availableFacultyId.get(_correctedPosition);
                        snapshot.getRef().child(spner_batch.getSelectedItem().toString()).child("faculty_id").setValue(_faculty_id);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        stringArrayAdapter = new ArrayAdapter<String>(container.getContext(), R.layout.spinner_item, availableBatches);
        spner_batch.setAdapter(stringArrayAdapter);

        stringArrayAdapter = new ArrayAdapter<String>(container.getContext(), R.layout.spinner_item, availableFaculty);
        spner_faculty.setAdapter(stringArrayAdapter);

        return _root;
    }
}
