package com.mak.pcr.ui.faculty.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.BatchAttendanceActivity;
import com.mak.pcr.DatabaseConnection;
import com.mak.pcr.DateTimeManager;
import com.mak.pcr.R;
import com.mak.pcr.dbentities.Batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    TextView txtvw_batchInSession, txtvw_nextSession, txtvw_nextSessionTime;

    DatabaseConnection db;

    ArrayList<Batch> batches;

    MaterialCardView batchCodeCard;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_faculty_home, container, false);

        db = new DatabaseConnection();

        batches = new ArrayList<Batch>();

        Intent i = new Intent(container.getContext(), BatchAttendanceActivity.class);

        txtvw_batchInSession = root.findViewById(R.id.txtvw_batchInSession);
//        txtvw_nextSession = root.findViewById(R.id.txtvw_nextSession);
//        txtvw_nextSessionTime = root.findViewById(R.id.txtvw_nextSessionTime);
        batchCodeCard = root.findViewById(R.id.batchCode);

//        SimpleDateFormat _dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date _date = null;
//        try {
//            _date = _dateFormat.parse(_dateFormat.format(System.currentTimeMillis()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        SimpleDateFormat _dayFormat = new SimpleDateFormat("EEEE");
//        String _day = _dayFormat.format(_date);
//
//        String _days = (_day.matches("Monday") || _day.matches("Wednesday") || _day.matches("Friday"))
//                && !_day.matches("Sunday") ?
//                "MWF" : "TTS";
        String _days = DateTimeManager.GetDays();

        _days = "Monday";
        String _f_id = db.get_firebaseAuth().getUid();
        txtvw_batchInSession.setText("N/A");
        db.get_dbReference("Batch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()){
                    Batch _b = dss.getValue( Batch.class );
                    if (_b.faculty_id == null || !_b.faculty_id.equals(_f_id)) {
                        continue;
                    }
                    String _startTime, _endTime;
                    String[] _splitTime = _b.timings.split(" ");

                    _startTime = _splitTime[0];
                    _endTime = _splitTime[3];

                    _startTime = _startTime.substring(0, 5) + ":00";
                    _endTime = _endTime.substring(0, 5) + ":00";

                    if(DateTimeManager.IsBetween(_startTime, _endTime)){
                        txtvw_batchInSession.setText(_b.batchCode);
                        i.putExtra("batchCode", _b.batchCode);
                        batchCodeCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(i);
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

}
