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
import com.mak.pcr.dbentities.Faculty;

import java.util.ArrayList;

public class FacultyAdapter extends BaseAdapter {

    ArrayList<Faculty> _data;
    ArrayList<String> _ids;
    Context _context;

    DatabaseConnection db;
    DatabaseReference dbRef;


    public FacultyAdapter(ArrayList<Faculty> _data, ArrayList<String> _ids, Context _context) {
        this._data = _data;
        this._ids = _ids;
        this._context = _context;

        db = new DatabaseConnection();
        dbRef = db.get_dbReference("Faculty");
    }

    @Override
    public int getCount() {
        return _data.size();
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
        LayoutInflater layoutInflater = LayoutInflater.from(_context);
        convertView = layoutInflater.inflate(R.layout.vw_faculty,null);

//        TextView txtvw_facultyId = convertView.findViewById(R.id.txtvw_facultyId);
        TextView txtvw_facultyName = convertView.findViewById(R.id.txtvw_facultyName);
        TextView txtvw_facultyEmail = convertView.findViewById(R.id.txtvw_facultyEmail);
        TextView txtvw_facultyContact = convertView.findViewById(R.id.txtvw_facultyContact);
        Button btn_update = convertView.findViewById(R.id.btn_update);
        Button btn_delete = convertView.findViewById(R.id.btn_delete);

//        txtvw_facultyId.setText(_ids.get(position));
        txtvw_facultyName.setText("Name: " + _data.get(position).getFullName());
        txtvw_facultyEmail.setText("Email: " + _data.get(position).email);
        txtvw_facultyEmail.setText("Contact: " + _data.get(position).contact);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(_context, UpdateStudentActivity.class);
                i.putExtra("facultyemail", _data.get(position).email);
                _context.startActivity(i);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(_context)
                        .setTitle("Are you sure?")
                        .setMessage("This action cannot be reversed! Are you sure you want to delete " + _data.get(position).getFullName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().child(_data.get(position).getFullName()).removeValue();
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

        //TODO status

        return convertView;
    }
}
