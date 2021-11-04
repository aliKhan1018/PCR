package com.mak.pcr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mak.pcr.dbentities.Batch;

import java.util.ArrayList;

public class BatchAdapter extends BaseAdapter {

    ArrayList<Batch> data;
    Context context;

    public BatchAdapter(ArrayList<Batch> data, Context context){
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
        convertView = layoutInflater.inflate(R.layout.vw_batch, null);

        TextView txtvw_batchCode = convertView.findViewById(R.id.txtvw_batchCode);
        TextView txtvw_batchTiming = convertView.findViewById(R.id.txtvw_batchTiming);
        TextView txtvw_batchFaculty = convertView.findViewById(R.id.txtvw_batchFaculty);

        Batch _currentBatch = this.data.get(position);

        txtvw_batchCode.setText("Batch Code: " + _currentBatch.batchCode);
        txtvw_batchTiming.setText("Time Slot: " + _currentBatch.timings + " (" + _currentBatch.days + ")");

        return convertView;
    }
}
