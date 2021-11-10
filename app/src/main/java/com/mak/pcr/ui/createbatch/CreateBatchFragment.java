package com.mak.pcr.ui.createbatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.Utility;
import com.mak.pcr.dbentities.Batch;

public class CreateBatchFragment extends Fragment {
    TextInputLayout inpLayout_batchCode;
    EditText edt_batchCode;
    Spinner spner_timings;
    RadioGroup radiogrp_days;
    Button btn_create, btn_clear;

    DatabaseConnection db;
    DatabaseReference dbRefBatch;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View _root = inflater.inflate(R.layout.fragment_create_batch, container, false);

        db = new DatabaseConnection();
        dbRefBatch = db.get_dbReference("Batch");

        inpLayout_batchCode = _root.findViewById(R.id.inpLayout_batchCode);
        edt_batchCode = _root.findViewById(R.id.edt_batchCode);
        spner_timings = _root.findViewById(R.id.spner_timings);
        radiogrp_days = _root.findViewById(R.id.radiogrp_days);
        btn_create = _root.findViewById(R.id.btn_assign);
        btn_clear = _root.findViewById(R.id.btn_clear);

        ArrayAdapter<CharSequence> _arrayAdapter = ArrayAdapter.createFromResource(container.getContext(), R.array.array_timings,
                R.layout.spinner_item);
        _arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spner_timings.setAdapter(_arrayAdapter);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_batchCode.setText("");
                spner_timings.setSelection(0);

                inpLayout_batchCode.setError(null);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _sBatchCode, _sTiming, _sDays;

                _sBatchCode = edt_batchCode.getText().toString().trim();
                _sTiming = spner_timings.getSelectedItem().toString();

                int _iSelectedDaysId = radiogrp_days.getCheckedRadioButtonId();
                RadioButton radiobtn_selectedDays = _root.findViewById(_iSelectedDaysId);
                _sDays = radiobtn_selectedDays.getText().toString();

//                _sDays = ((RadioButton)_root.findViewById( radiogrp_days.getCheckedRadioButtonId() )).getText().toString();

                if(_sBatchCode.matches("")){
                    edt_batchCode.requestFocus();
                    inpLayout_batchCode.setError("Batch code can't be empty");
                    return;
                }

                if(_sTiming.matches("Select a time slot…")){
                    Utility.MakeToast(container.getContext(), "Time slot not selected", 0);
                    return;
                }

                db.get_dbReference("Batch").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String _batchCodeWithDays = _sBatchCode + " (" + _sDays + ")";
                        if (snapshot.hasChild(_batchCodeWithDays)){
                            Utility.MakeToast(container.getContext(), "Batch already exists", 0);
                        }
                        else{
                            db.addToDbReference("Batch", _batchCodeWithDays, new Batch(_batchCodeWithDays, _sTiming, _sDays)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Utility.MakeToast(container.getContext(), "Batch Created", 0);
                                    }
                                    else{
                                        Utility.MakeToast(container.getContext(), task.getException().getMessage(),1);
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Utility.MakeToast(container.getContext(), error.getMessage(), 1);

                    }
                });

            }
        });

        return _root;
    }
}
