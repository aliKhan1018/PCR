package com.mak.pcr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mak.pcr.dbentities.Faculty;
import com.mak.pcr.dbentities.Student;

import java.util.ArrayList;

public class BatchAttendenceAdapter extends BaseAdapter {

    ArrayList<Student> _data;
    Context _context;


    public BatchAttendenceAdapter(ArrayList<Student> _data, Context _context) {
        this._data = _data;
        this._context = _context;
    }


    @Override
    public int getCount() {
        return this._data.size();
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
        txtvw_studentName.setText("Name: " + _data.get(position).getFname());

        return convertView;
    }
}
