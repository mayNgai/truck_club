package com.dtc.sevice.truckclub.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by May on 10/10/2017.
 */

public class CalendarAdapter extends BaseAdapter {
    private Context context;

    private java.util.Calendar month;
    public GregorianCalendar pmonth;
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;

    private ArrayList<String> items;
    public static List<String> day_string;
    private View previousView;
    public ArrayList<CalendarCollection> date_collection_arr;
    //private boolean flagSetting;
    private List<String> stringArrFreeTime;
    private CheckBox chk_date = null;
    private boolean flagNoFreedate = false;
    private String action="";

    public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<CalendarCollection> date_collection_arr, String _action, List<String> _stringArrFreeTime) {
        this.date_collection_arr = date_collection_arr;
        CalendarAdapter.day_string = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        refreshDays();
        this.action = _action;
        this.stringArrFreeTime = _stringArrFreeTime;

        if(stringArrFreeTime == null || stringArrFreeTime.size()==0)
            flagNoFreedate = true;

    }

    public void setItems(ArrayList<String> items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    public int getCount() {
        return day_string.size();
    }

    public Object getItem(int position) {
        return day_string.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;

        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register"))
                v = vi.inflate(R.layout.cal_item_check_box, null);
            else
                v = vi.inflate(R.layout.cal_item, null);
        }

        dayView = (TextView) v.findViewById(R.id.date);
        String[] separatedTime = day_string.get(position).split("-");
        if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
            chk_date = (CheckBox) v.findViewById(R.id.chk_date);
            chk_date.setVisibility(View.INVISIBLE);
        }

        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        // create date string for comparison
        String date = day_string.get(position);

        long dateBook=0 ;//= GlobalVar.dateTimeFormatTolong(date);
        long dateCurrent=0 ;//= GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemDateOnlyformat(context));

        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);

        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);

        } else {
            // setting curent month's days in blue color.
            dayView.setTextColor(Color.BLACK);
            if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
                dayView.setTextColor(Color.GRAY);
                chk_date.setEnabled(false);
                if(dateBook>=dateCurrent){
                    chk_date.setVisibility(View.VISIBLE);
                    chk_date.setChecked(true);
                    chk_date.setEnabled(true);
                    if(stringArrFreeTime.indexOf(date)==-1) {
                        stringArrFreeTime.add(date);
                        //Driver_Book_SetBookTime.listFreeDate = stringArrFreeTime;
                    }
                }
//                }

            }
        }

        if (day_string.get(position).equals(curentDateString)) {

            //v.setBackgroundColor(Color.CYAN);
        } else {

            //if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
                v.setBackgroundColor(Color.WHITE);
            //}else {
                //v.setBackgroundColor(Color.parseColor("#343434"));
            //}

        }


        dayView.setText(gridvalue);



        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        // show icon if date is not empty and it exists in the items array
        /*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.GONE);
		}
		*/

        setEventView(v, position, dayView);
        setCheckBox(position , date);

        return v;
    }

    private void setCheckBox(int pos, final String date){
        try {
            chk_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        if(stringArrFreeTime.indexOf(date)==-1) {
                            stringArrFreeTime.add(date);
                            //Driver_Book_SetBookTime.listFreeDate = stringArrFreeTime;
                        }
                    }else {
                        if(stringArrFreeTime.indexOf(date)==-1){

                        }else {
                            for (String a : stringArrFreeTime){
                                if(a.equalsIgnoreCase(date)) {
                                    stringArrFreeTime.remove(a);
                                    //Driver_Book_SetBookTime.listFreeDate = stringArrFreeTime;
                                    break;
                                }
                            }
                        }
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public View setSelected(View view, int pos) {
        if (previousView != null) {
            //previousView.setBackgroundColor(Color.parseColor("#343434"));
        }

        int len = day_string.size();
        if (len > pos) {
            if (day_string.get(pos).equals(curentDateString)) {

            } else {
                previousView = view;
            }
        }

        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        int x1 = month.get(GregorianCalendar.MONTH);
        int x2 = month.getActualMinimum(GregorianCalendar.MONTH);
        if (x1 == month.getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

    public void setEventView(View v, int pos, TextView txt) {

        try {
            int len = CalendarCollection.date_collection_arr.size();
            long dateString = GlobalVar.dateTimeFormatTolong(day_string.get(pos));
            long dateCurrent= GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemDateOnlyformat(context));

            if(dateString==dateCurrent)
                v.setBackgroundColor(Color.CYAN);

            if(len<1){
                for (String a : stringArrFreeTime){
                    if (day_string.get(pos).equalsIgnoreCase(a)) {
                        if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
                            chk_date.setVisibility(View.VISIBLE);
                            chk_date.setChecked(true);
                            //v.setBackgroundColor(Color.WHITE);
//                            v.setBackgroundResource(R.drawable.calender_item_free_time);
                            //stringArrFreeTime.remove(a);
                        }else
                            v.setBackgroundResource(R.drawable.rounded_calender_item_gray);


                    }
                }

            }else {
                for (String a : stringArrFreeTime){
                    if (day_string.get(pos).equalsIgnoreCase(a)) {
                        if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
                            chk_date.setVisibility(View.VISIBLE);
                            chk_date.setChecked(true);
//                            v.setBackgroundResource(R.drawable.calender_item_free_time);
                            v.setBackgroundColor(Color.WHITE);
                            //stringArrFreeTime.remove(a);
                        }else
                            v.setBackgroundResource(R.drawable.rounded_calender_item_gray);

                    }
                }



                for (int i = 0; i < len; i++) {
                    CalendarCollection cal_obj = CalendarCollection.date_collection_arr.get(i);
                    String date = cal_obj.date;
                    int status = cal_obj.status;
                    int len1 = day_string.size();
                    if (len1 > pos) {


                        if (day_string.get(pos).equalsIgnoreCase(date)) {
                            if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")){
                                chk_date.setVisibility(View.INVISIBLE);
                                txt.setTextColor(Color.CYAN);
                            }
                            long dateBook = GlobalVar.dateTimeFormatTolong(date);
                            //long dateCurrent = GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemDateOnly(context));
                            //long dateCurrent = GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemDateOnlyformat(context));

                            //
                            if(txt!=null)
                                txt.setTextColor(Color.BLACK);
                                //txt.setTextColor(Color.WHITE);
                            if(dateBook==dateCurrent) {
                                if (action.equalsIgnoreCase("setting") || action.equalsIgnoreCase("register")) {
                                    txt.setTextColor(Color.GREEN);
                                } else {
                                    v.setBackgroundResource(R.drawable.rounded_calender_item);
                                }

                            }else if(dateBook>dateCurrent) {
                                if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
                                    txt.setTextColor(Color.YELLOW);
                                }else {
                                    v.setBackgroundResource(R.drawable.rounded_calender_item_yellow);
                                    txt.setTextColor(Color.GRAY);
                                }

                            }else if(dateBook<dateCurrent) {
                                if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
                                    txt.setTextColor(Color.RED);
                                }else {
                                    v.setBackgroundResource(R.drawable.rounded_calender_item_red);

                                }
                            }

                            if(status>=6){
                                txt.setTextColor(Color.BLACK);
                                v.setBackgroundResource(R.drawable.rounded_calender_item_green_dask);
                            }
//                            else {
//                                if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {
//                                    txt.setTextColor(Color.CYAN);
//                                }else {
//                                    v.setBackgroundResource(R.drawable.rounded_calender_item_blue);
//                                }
//                                //R.drawable.calender_item_free_time
//                                //v.setBackgroundResource(R.drawable.rounded_calender_item);
//                            }

                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public View getPositionList(String date, final Activity act, View view, int pos) {
        try {
            int len = CalendarCollection.date_collection_arr.size();
            for (int i = 0; i < len; i++) {
                CalendarCollection cal_collection = CalendarCollection.date_collection_arr.get(i);
                String event_date = cal_collection.date;
                int status = cal_collection.status;

                String event_message = cal_collection.event_message;
                long dateBook = GlobalVar.dateTimeFormatTolong(date);
                //long dateCurrent = GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemDateOnly(context));
                long dateCurrent = GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemDateOnlyformat(context));

                if (date.equals(event_date)) {

                    //v.setBackgroundColor(Color.parseColor("#343434"));
                    if(dateBook==dateCurrent) {
                        if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {

                        }else {
                            view.setBackgroundResource(R.drawable.rounded_calender_item);
                        }

                    }else if(dateBook>dateCurrent) {
                        if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {

                        }else {
                            view.setBackgroundResource(R.drawable.rounded_calender_item_yellow);
                        }

                    }else if(dateBook<dateCurrent) {
                        if(action.equalsIgnoreCase("setting")||action.equalsIgnoreCase("register")) {

                        }else {
                            if(status <6){
                                view.setBackgroundResource(R.drawable.rounded_calender_item_red);
                            }else {
                                view.setBackgroundResource(R.drawable.rounded_calender_item_green_dask);
                            }

                        }

                    }
//                    Toast.makeText(context, "You have event on this date: " + event_date, Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Date: " + date)
                            .setMessage("Event: " + event_message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    previousView = null;
                                    //act.finish();
                                }
                            }).show();
                    break;
                } else {


                    if (previousView != null) {
                        //previousView.setBackgroundColor(Color.parseColor("#343434"));
                    }

                    //int len = day_string.size();
                    if (len > pos) {
                        if (day_string.get(pos).equals(curentDateString)) {

                        } else {
                            previousView = view;
                        }
                    }

//                    view.setBackgroundColor(Color.CYAN);
                }
            }
            long dateBook = GlobalVar.dateTimeFormatTolong(date);
            long dateCurrent = GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemDateOnlyformat(context));

            //boolean alert =false;
            if(dateBook==dateCurrent) {
                //alert = true;
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Date: " + date)
                        .setMessage("Event: " + context.getResources().getString(R.string.txtToday) )
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                previousView = null;
                                //act.finish();
                            }
                        }).show();
            }
//            else {
//                    for (String s: stringArrFreeTime){
//                        if (date.equalsIgnoreCase(s)) {
//                            new AlertDialog.Builder(context)
//                                    .setIcon(android.R.drawable.ic_dialog_alert)
//                                    .setTitle("Date: " + date)
//                                    .setMessage("Event: " + "วันว่าง")
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                            previousView = null;
//                                            //act.finish();
//                                        }
//                                    }).show();
//                            break;
//                        }
//                    }
//
//            }


        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return view;
    }
}
