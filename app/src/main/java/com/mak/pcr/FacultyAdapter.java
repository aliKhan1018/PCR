package com.mak.pcr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mak.pcr.dbentities.Faculty;

import java.util.ArrayList;

public class FacultyAdapter extends BaseAdapter {

    ArrayList<Faculty> _data;
    Context _context;

    public FacultyAdapter(ArrayList<Faculty> data, Context context) {
        this._data = data;
        this._context = context;
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

        TextView txtvw_facultyName = convertView.findViewById(R.id.txtvw_facultyName);
        TextView txtvw_facultyEmail = convertView.findViewById(R.id.txtvw_facultyEmail);

        txtvw_facultyName.setText("Name: " + _data.get(position).getFname());
        txtvw_facultyEmail.setText("Email: " + _data.get(position).email);
        //TODO status

        return convertView;
    }
}
