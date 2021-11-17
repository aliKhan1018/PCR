package com.mak.pcr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.dbentities.Batch;
import com.mak.pcr.dbentities.Faculty;
import com.mak.pcr.dbentities.Student;

import java.util.ArrayList;

public class BatchAdapter extends BaseAdapter {

    ArrayList<Batch> data;
    Context context;

    String faculty;

    DatabaseConnection db;
    DatabaseReference dbRef;

    public BatchAdapter(ArrayList<Batch> data, Context context){
        this.data = data;
        this.context = context;

        db = new DatabaseConnection();
        dbRef = db.get_dbReference("Batch");
    }

    public BatchAdapter(ArrayList<Batch> data, Context context, String faculty){
        this.data = data;
        this.context = context;
        this.faculty = faculty;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.vw_batch, null);

        TextView txtvw_batchCode = convertView.findViewById(R.id.txtvw_batchCode);
        TextView txtvw_batchTiming = convertView.findViewById(R.id.txtvw_batchTiming);
        TextView txtvw_batchFaculty = convertView.findViewById(R.id.txtvw_batchFaculty);
        Button btn_update = convertView.findViewById(R.id.btn_update);
        Button btn_delete = convertView.findViewById(R.id.btn_delete);

        Batch _currentBatch = this.data.get(position);

        txtvw_batchCode.setText("Batch Code: " + _currentBatch.batchCode);
        txtvw_batchTiming.setText("Time Slot: " + _currentBatch.timings + " (" + _currentBatch.days + ")");

        if(this.faculty != null){
            txtvw_batchFaculty.setText("Faculty's Name: " + faculty);
        }
        else {
            db.get_dbReference("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(_currentBatch.faculty_id != null && !_currentBatch.faculty_id.equals("")){
                        String facultyName = snapshot.child(_currentBatch.faculty_id).getValue(Faculty.class).getFullName();
                        txtvw_batchFaculty.setText("Faculty's Name: " + facultyName);
                    }
                    else{
                        txtvw_batchFaculty.setText("Faculty's Name: Not Assigned");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UpdateStudentActivity.class);
                i.putExtra("batchcode", data.get(position).batchCode);
                context.startActivity(i);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Are you sure?")
                        .setMessage("This action cannot be reversed! Are you sure you want to delete Batch " + data.get(position).batchCode + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().child(data.get(position).batchCode).removeValue();
                                        db.get_dbReference("Student").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dss : snapshot.getChildren()){
                                                    Student s = dss.getValue(Student.class);
                                                    if(s.getBatch_id().equals(data.get(position).batchCode)){
                                                        dss.getRef().child("batch_id").setValue("");
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        return convertView;
    }
}
