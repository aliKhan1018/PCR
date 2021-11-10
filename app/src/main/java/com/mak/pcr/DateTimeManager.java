package com.mak.pcr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeManager {

    public static boolean IsBetween(String startTime, String endTime){
        Date time1 = null;
        try {
            time1 = new SimpleDateFormat("HH:mm:ss").parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        calendar1.add(Calendar.AM, 1);

        Date time2 = null;
        try {
            time2 = new SimpleDateFormat("HH:mm:ss").parse(endTime);
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

        Date timeNow = null;
        try {
            timeNow = new SimpleDateFormat("HH:mm:ss").parse(_currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar3.setTime(timeNow);
        calendar3.add(Calendar.AM, 1);
        return  (timeNow.after(time1) && timeNow.before(time2));

    }

    public static boolean isAfter(String after, String timeToCheck){
        Date time1 = null;
        try {
            time1 = new SimpleDateFormat("HH:mm:ss").parse(after);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        calendar1.add(Calendar.AM, 1);

        Date time2 = null;
        try {
            time2 = new SimpleDateFormat("HH:mm:ss").parse(timeToCheck);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);
        calendar2.add(Calendar.AM, 1);

        return time2.after(time1);
    }

}
