package com.mak.pcr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.dbentities.Faculty;
import com.mak.pcr.dbentities.Student;

import java.util.ArrayList;

public class BatchAttendenceAdapter extends BaseAdapter {

    ArrayList<Student> _data;
    Context _context;
    DatabaseConnection db;
    DatabaseReference dbRef;

    public BatchAttendenceAdapter(ArrayList<Student> data, Context context) {
        this._data = data;
        this._context = context;

        db = new DatabaseConnection();
        dbRef = db.get_dbReference("Attendance");
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
        convertView = layoutInflater.inflate(R.layout.vw_student_attendence,null);

        TextView txtvw_studentId = convertView.findViewById(R.id.txtvw_studentId);
        TextView txtvw_studentName = convertView.findViewById(R.id.txtvw_studentName);

        txtvw_studentId.setText("Student Id: " + _data.get(position).studentId);
        txtvw_studentName.setText("Name: " + _data.get(position).getFname() + " " + _data.get(position).getLname());

        Button btn_present = convertView.findViewById(R.id.btn_present);
        Button btn_absent = convertView.findViewById(R.id.btn_absent);

        LinearLayout linlay_btns = convertView.findViewById(R.id.linlay_btns);
        CardView card_marked = convertView.findViewById(R.id.cardvw_marked);

        db.get_dbReference("Attendance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(DateTimeManager.getDate()).hasChild(_data.get(position).getStudentId())){
                    linlay_btns.setVisibility(View.GONE);
                    card_marked.setVisibility(View.VISIBLE);
                }
                else{
                    linlay_btns.setVisibility(View.VISIBLE);
                    card_marked.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utility.MakeToast(_context, DateTimeManager.getDate(), 1);
                db.get_dbReference("Attendance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String _date = DateTimeManager.getDate();
                        snapshot.getRef().child(_date).child(_data.get(position).getStudentId()).child("isPresent").setValue(true);
                        linlay_btns.setVisibility(View.GONE);
                        card_marked.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.get_dbReference("Attendance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String _date = DateTimeManager.getDate();
                        snapshot.getRef().child(_date).child(_data.get(position).getStudentId()).child("isPresent").setValue(false);
                        linlay_btns.setVisibility(View.GONE);
                        card_marked.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        return convertView;
    }
}
