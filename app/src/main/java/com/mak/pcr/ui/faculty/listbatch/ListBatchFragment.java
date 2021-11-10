package com.mak.pcr.ui.faculty.listbatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.BatchAdapter;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.Utility;
import com.mak.pcr.dbentities.Batch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListBatchFragment extends Fragment {

    ListView lstvw_batch;
    ArrayList<Batch> data;

    DatabaseConnection db;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View _root = inflater.inflate(R.layout.fragment_faculty_list_batch, container, false);

        lstvw_batch = _root.findViewById(R.id.lstvw_batch);

        db = new DatabaseConnection();

        data = new ArrayList<Batch>();

        String _f_id = db.get_firebaseAuth().getUid();

        db.get_dbReference("Batch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()){
                    Batch _b = dss.getValue( Batch.class );
                    if (_b.faculty_id.equals(_f_id)) {
                        data.add(_b);
                    }
                }
                BatchAdapter adapter = new BatchAdapter( data, container.getContext() );
                lstvw_batch.setAdapter( adapter );
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Utility.MakeToast(container.getContext(), error.getMessage(), 1);
            }
        });

        return _root;
    }
}
