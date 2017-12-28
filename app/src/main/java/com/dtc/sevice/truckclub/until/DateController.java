package com.dtc.sevice.truckclub.until;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class DateController {

    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat dateFormat6 = new SimpleDateFormat("yyyy");
    SimpleDateFormat dateFormat7 = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dateFormat8 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormat9 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getSystemTime(Context mContext) {
        long time;
        String timeStr = "1993-10-27 07:05";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 0)
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            else {
                time = System.currentTimeMillis();
                Date resultDate = new Date(time);
                timeStr = dateFormat4.format(resultDate);
            }
        }

        return timeStr;
    }

    public String getSystemTimeFull(Context mContext) {
        long time;
        String timeStr = "1993-10-27 07:05:00";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 0)
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            else {
                time = System.currentTimeMillis();
                Date resultDate = new Date(time);
                timeStr = dateFormat9.format(resultDate);
            }
        }

        return timeStr;
    }

    public long dateFormat1Tolong(String date) {
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat1.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public long dateFormat2Tolong(String date) {
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat2.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public long dateTimeFormat1Tolong(String date) {
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat3.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public long dateTimeFormat2Tolong(String date) {
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat4.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public long dateTimeFormat5Tolong(String date) {
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat5.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public long dateTimeFormat6Tolong(String date) {
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat6.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public long dateTimeFormat9Tolong(String date) {
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat9.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public String dateTimeToStringFormat5(Date datetime) {
        String dateFormatted;
//        Date dateUtil = new Date(datetime);
        dateFormatted = dateFormat5.format(datetime.getTime());
        return dateFormatted;
    }
    public String convertDateFormat7To1(String date1){
        String date = date1;
        Date testDate = null;
        try {
            testDate = dateFormat7.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        String newFormat = dateFormat1.format(testDate);
        return newFormat;
    }

    public String convertDateFormat2To1(String date1){
        String date = date1;
        Date testDate = null;
        try {
            testDate = dateFormat2.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        String newFormat = dateFormat1.format(testDate);
        return newFormat;
    }

    public String convertDateFormat1To2(String date1){
        String date = date1;
        Date testDate = null;
        try {
            testDate = dateFormat1.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        String newFormat = dateFormat2.format(testDate);
        return newFormat;
    }

    public String convertDateFormat2To8(String date1){
        String date = date1;
        Date testDate = null;
        try {
            testDate = dateFormat8.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        String newFormat = dateFormat1.format(testDate);
        return newFormat;
    }

    public int daysBetween(long t1, long t2) {
        return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
    }

    public List<String> daysOfWeek(){
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            if(i==1||i==7){
                String formatedDate = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
                list.add(formatedDate);
            }

        }

        return list;
    }

    public List<String> daysOfMonth(){
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int first = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1);
        list.add(formatedDate);
        list.add(first + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR));
        list.add(last + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR));
        return list;
    }

    public List<String> daysOfYear(){
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        String formatedDate = String.valueOf(cal.get(Calendar.YEAR));
        list.add(formatedDate);
        list.add("01" + "-" + "01" + "-" + cal.get(Calendar.YEAR));
        list.add("31" + "-" + "12" + "-" + cal.get(Calendar.YEAR));

        return list;
    }
}
