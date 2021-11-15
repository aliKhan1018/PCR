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
    ArrayList<String> _ids;
    Context _context;


    public FacultyAdapter(ArrayList<Faculty> _data, ArrayList<String> _ids, Context _context) {
        this._data = _data;
        this._ids = _ids;
        this._context = _context;
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

//        txtvw_facultyId.setText(_ids.get(position));
        txtvw_facultyName.setText("Name: " + _data.get(position).getFullName());
        txtvw_facultyEmail.setText("Email: " + _data.get(position).email);
        txtvw_facultyEmail.setText("Contact: " + _data.get(position).contact);

        //TODO status

        return convertView;
    }
}
