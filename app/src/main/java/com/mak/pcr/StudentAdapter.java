package com.mak.pcr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mak.pcr.dbentities.Student;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {

    ArrayList<Student> data;
    Context context;

    public StudentAdapter(ArrayList<Student> data, Context context) {
        this.data = data;
        this.context = context;
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
        txtvw_parentEmail.setText("Email: " + data.get(position).getParentEmail());
        txtvw_parentContact.setText("Contact: " + data.get(position).getParentContact());

        return convertView;
    }
}
