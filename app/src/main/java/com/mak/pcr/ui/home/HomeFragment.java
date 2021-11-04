package com.mak.pcr.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mak.pcr.dbentities.Batch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.R;
import com.mak.pcr.Utility;
import com.mak.pcr.databinding.FragmentHomeBinding;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView txtvw_batchesInSession;

    DatabaseConnection db;
    ArrayList<Batch> batches;
    int batchesInSession;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        db = new DatabaseConnection();
        batches = new ArrayList<Batch>();
        batchesInSession = 0;

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txtvw_batchesInSession = root.findViewById(R.id.txtvw_batchesInSession);

        SimpleDateFormat _dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date _date = null;
        try {
            _date = _dateFormat.parse(_dateFormat.format(System.currentTimeMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat _dayFormat = new SimpleDateFormat("EEEE");
        String _day = _dayFormat.format(_date);

        String _days = (_day.matches("Monday") || _day.matches("Wednesday") || _day.matches("Friday"))
                && !_day.matches("Sunday") ?
                "MWF" : "TTS";

        Log.println(Log.DEBUG, "Time", _days);


        db.get_dbReference("Batch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()){
                    Batch _b = dss.getValue( Batch.class );

                    Log.println(Log.DEBUG, "Time", _b.batchCode + " Days: " + _b.days);

                    if (!_b.days.matches(_days) || _b.faculty_id == null || _b.faculty_id.matches("")) {
                        continue;
                    }
                    String _startTime, _endTime;
                    String[] _splitTime = _b.timings.split(" ");

                    _startTime = _splitTime[0];
                    _endTime = _splitTime[3];

                    _startTime = _startTime.substring(0, 5) + ":00";
                    _endTime = _endTime.substring(0, 5) + ":00";

                    Log.println(Log.DEBUG, "Time", _startTime + " " + _endTime);

                    Date time1 = null;
                    try {
                        time1 = new SimpleDateFormat("HH:mm:ss").parse(_startTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.AM, 1);

                    Date time2 = null;
                    try {
                        time2 = new SimpleDateFormat("HH:mm:ss").parse(_endTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.AM, 1);

                    Calendar calendar3 = Calendar.getInstance();
                    Date d = calendar3.getTime();
                    String[] _splitDate = d.toString().split(" ");
                    String _currentTime = _splitDate[3];
                    Log.println(Log.DEBUG, "Time", _currentTime + " current time");

                    Date timeNow = null;
                    try {
                        timeNow = new SimpleDateFormat("HH:mm:ss").parse(_currentTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    calendar3.setTime(timeNow);
                    calendar3.add(Calendar.AM, 1);

                    Log.println(Log.DEBUG, "Time", timeNow.toString());

                    if (timeNow.after(time1) && timeNow.before(time2)) {
                        batchesInSession++;
                    }

                }
                txtvw_batchesInSession.setText(batchesInSession + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}