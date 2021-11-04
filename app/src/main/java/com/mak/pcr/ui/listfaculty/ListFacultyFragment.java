package com.mak.pcr.ui.listfaculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.FacultyAdapter;
import com.mak.pcr.R;
import com.mak.pcr.Utility;
import com.mak.pcr.dbentities.Batch;
import com.mak.pcr.dbentities.Faculty;

import java.util.ArrayList;

public class ListFacultyFragment extends Fragment {
    ListView lstvw_faculty;
    ArrayList<Faculty> data;

    DatabaseConnection db;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View _root = inflater.inflate(R.layout.fragment_list_faculty, container, false);

        lstvw_faculty = _root.findViewById(R.id.lstvw_faculty);

        db = new DatabaseConnection();

        data = new ArrayList<Faculty>();

        db.get_dbReference("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()){
                    data.add( dss.getValue( Faculty.class ) );
                }
                FacultyAdapter adapter = new FacultyAdapter( data, container.getContext() );
                lstvw_faculty.setAdapter( adapter );
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Utility.MakeToast(container.getContext(), error.getMessage(), 1);
            }
        });

        return _root;

    }
}
