package com.mak.pcr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeManager {

    public static String GetDays(){
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
        return _days;
    }

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
            timeNow = new SimpleDateFormat("HH:mm:ss").parse("09:30:00");
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
