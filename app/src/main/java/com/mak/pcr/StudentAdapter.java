package com.mak.pcr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.mak.pcr.dbentities.Student;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {

    ArrayList<Student> data;
    Context context;

    DatabaseConnection db;
    DatabaseReference dbRef;

    public StudentAdapter(ArrayList<Student> data, Context context) {
        this.data = data;
        this.context = context;

        db = new DatabaseConnection();
        dbRef = db.get_dbReference("Student");
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
        convertView = layoutInflater.inflate(R.layout.vw_student,null);

        TextView txtvw_studentId = convertView.findViewById(R.id.txtvw_studentId);
        TextView txtvw_studentName = convertView.findViewById(R.id.txtvw_studentName);
        TextView txtvw_parentEmail = convertView.findViewById(R.id.txtvw_parentEmail);
        TextView txtvw_studentBatch = convertView.findViewById(R.id.txtvw_studentBatch);
        TextView txtvw_parentContact = convertView.findViewById(R.id.txtvw_parentContact);

        txtvw_studentId.setText("Student ID: " + data.get(position).getStudentId());
        txtvw_studentName.setText("Name: " + data.get(position).getFname() + " " + data.get(position).getLname());
        txtvw_studentBatch.setText("Batch Code: " + data.get(position).getBatch_id());
        txtvw_parentEmail.setText("Parent's Email: " + data.get(position).getParentEmail());
        txtvw_parentContact.setText("Parent's Contact: " + data.get(position).getParentContact());

        Button btn_update = convertView.findViewById(R.id.btn_update);
        Button btn_delete = convertView.findViewById(R.id.btn_delete);

        txtvw_parentEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + data.get(position).parentEmail));
                context.startActivity(intent);
            }
        });

        txtvw_parentContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + data.get(position).parentContact));
                context.startActivity(sendIntent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UpdateStudentActivity.class);
                i.putExtra("studentid", data.get(position).getStudentId());
                context.startActivity(i);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Are you sure?")
                        .setMessage("This action cannot be reversed! Are you sure you want to delete student " + data.get(position).getStudentId() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().child(data.get(position).getStudentId()).removeValue();
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
