package com.dtc.sevice.truckclub.view.driver.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.CalendarAdapter;
import com.dtc.sevice.truckclub.adapter.CalendarCollection;
import com.dtc.sevice.truckclub.adapter.TaskListAdapter;
import com.dtc.sevice.truckclub.model.SheduleTable;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.presenters.driver.DriverBookingPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.TaskController;
import com.j256.ormlite.stmt.query.In;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by May on 10/9/2017.
 */

public class DriverBookingActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private GridView gridview;
    private static RecyclerView recycler_view;
    private CalendarAdapter cal_adapter;
    public GregorianCalendar cal_month;
    private TextView tv_month;
    private ImageButton previous ;
    private ImageButton next;
    private LinearLayout linear_edit,linear_save;
    private Spinner sp_job;
    private String action = "";
    private String strMonth="",strDate="";
    public static List<String> listFreeDate;
    private DateController dateController;
    private List<TblTask> listTasks;
    private List<TblTask> listSchedulesTasks;
    public static List<TblMember> members;
    public static TblTask tblTask;
    private static TaskListAdapter adapter;
    private static ApiService mForum;
    private static DriverBookingPresenter driverBookingPresenter;
    private static String strType = "1";
    private TaskController taskController;
    private static int select_position = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_booking);
        init();
        initListeners();
    }

    private void init(){
        try {
            dateController = new DateController();
            taskController = new TaskController();
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            gridview = (GridView) findViewById(R.id.gv_calendar);
            cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
            tv_month = (TextView) findViewById(R.id.tv_month);
            previous = (ImageButton) findViewById(R.id.ib_prev);
            next = (ImageButton) findViewById(R.id.Ib_next);
            recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
            sp_job = (Spinner) findViewById(R.id.sp_job);
            linear_edit = (LinearLayout)findViewById(R.id.linear_edit);
            linear_save = (LinearLayout)findViewById(R.id.linear_save);
            tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
            toolbar.setTitle("ตารางงาน");
            toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backButton();
                }
            });
            getMember();
            getSchedulesLocal();
            getTask();
            setSelectTask();
            listFreeDate = new ArrayList<>();
            cal_adapter = new CalendarAdapter(DriverBookingActivity.this, cal_month, CalendarCollection.date_collection_arr,action,listFreeDate);
            gridview.setAdapter(cal_adapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    ((CalendarAdapter) parent.getAdapter()).setSelected(v, position);
                    String selectedGridDate = CalendarAdapter.day_string.get(position);
                    //String selectedGridDate = "2016-11-25";

                    String[] separatedTime = selectedGridDate.split("-");
                    String gridvalueString = separatedTime[2].replaceFirst("^0*", "");
                    int gridvalue = Integer.parseInt(gridvalueString);

                    if ((gridvalue > 10) && (position < 8)) {
                        setPreviousMonth();
                        refreshCalendar();
                    } else if ((gridvalue < 7) && (position > 28)) {
                        setNextMonth();
                        refreshCalendar();
                    }
                    ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, DriverBookingActivity.this,v,position);
                    //((CalendarAdapter) parent.getAdapter()).setSelected(v, position);

                    // refreshCalendar();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initListeners(){
        linear_edit.setOnClickListener(this);
        linear_save.setOnClickListener(this);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    private void getMember(){
        try {
            members = new ArrayList<TblMember>();
            members = taskController.getMember();
        }catch (Exception e){

        }
    }

    private void getSchedulesLocal(){
        try {
            listSchedulesTasks = new ArrayList<TblTask>();
            listSchedulesTasks = taskController.getTask();
            setSheduleTable(listSchedulesTasks);
        }catch (Exception e){

        }
    }

    protected void setNextMonth() {
        int x1 = cal_month.get(GregorianCalendar.MONTH);
        if (x1 == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        int x1 = cal_month.get(GregorianCalendar.MONTH);
        if (x1 == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        strMonth = dateController.dateTimeToStringFormat5(cal_month.getTime());
        //setFreeDate();
    }

    private void setSelectTask(){
        try {
            sp_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    select_position = i;
                    if(i == 0){
                        strType = "1";
                    }else if(i == 1){
                        strType = "3";
                    }else if(i == 2){
                        strType = "2";
                    }else if(i == 3){
                        strType = "4";
                    }
                    getTask();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getTask(){
        try {
            tblTask = new TblTask();
            tblTask.setService_type("Booking");
            tblTask.setTask_status(Integer.parseInt(strType));
            tblTask.setMember(members);
            mForum = new ApiService();
            driverBookingPresenter = new DriverBookingPresenter(DriverBookingActivity.this , mForum);
            driverBookingPresenter.loadTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getSchedules(){
        try {
            driverBookingPresenter = new DriverBookingPresenter(DriverBookingActivity.this , mForum);
            driverBookingPresenter.loadSchedules();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sentOfferPrice(int id , int price){
        try {
            TblTask tblTask = new TblTask();
            tblTask.setId(String.valueOf(id));
            tblTask.setPrice_offer(String.valueOf(price));
            tblTask.setTask_status(0);
            tblTask.setService_type("Booking");
            tblTask.setMember(members);
            mForum = new ApiService();
            driverBookingPresenter = new DriverBookingPresenter(DriverBookingActivity.this , mForum);
            driverBookingPresenter.sentOfferPrice(tblTask);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sentGoing(TblTask tblTask){
        try {
            tblTask.setTask_status(4);
            mForum = new ApiService();
            driverBookingPresenter = new DriverBookingPresenter(DriverBookingActivity.this , mForum);
            driverBookingPresenter.sentUpdateDriver(tblTask);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateSentOfferPrice(){
        try {
            adapter.closeDriverOffer();
            getTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateGoing(){
        try {
            adapter.closeDriverOffer();
//            Intent i = new Intent(DriverBookingActivity.this , DriverMainActivity2.class);
//            startActivity(i);
//            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setListTask(List<TblTask> lists){
        try {
            listTasks = new ArrayList<TblTask>();
            if(strType.equalsIgnoreCase("1")){
                List<TblTask> listNewTasks = new ArrayList<TblTask>();
                listTasks = lists;
                if(listSchedulesTasks != null && listSchedulesTasks.size()>0){
                    for(TblTask t : lists){
                        for(TblTask s : listSchedulesTasks){
                            long start_new = dateController.dateFormat2Tolong(t.getStart_date());
                            long end_new = dateController.dateFormat2Tolong(t.getEnd_date());
                            long start_schedules = dateController.dateFormat2Tolong(s.getStart_date());
                            long end_schedules = dateController.dateFormat2Tolong(s.getEnd_date());
                            if((start_new < start_schedules&&start_new > end_schedules)&&(end_new > start_schedules&&end_new < end_schedules)){
                                listNewTasks.add(t);
                            }
                        }
                    }
                    listTasks = listNewTasks;
                    setAdapter();
                }else {
                    listTasks = lists;
                    setAdapter();
                }

            }else {
                listTasks = lists;
                setAdapter();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(){
        try {
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            adapter = new TaskListAdapter(DriverBookingActivity.this,listTasks,select_position);
            recycler_view.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setListSchedulesTasks(List<TblTask> lists){
        try {
            listSchedulesTasks = new ArrayList<TblTask>();
            listSchedulesTasks = lists;
            taskController.createTask(lists);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setSheduleTable(List<TblTask> listSchedulesTasks){
        try {
            CalendarCollection.date_collection_arr = new ArrayList<CalendarCollection>();
            for(TblTask t : listSchedulesTasks){
                SheduleTable sheduleTable = new SheduleTable();
                sheduleTable.setStartDate(t.getStart_date());
                sheduleTable.setUserReserveId(Integer.parseInt(t.getId()));
                sheduleTable.setStartLocation(t.getStart_location());
                sheduleTable.setStartProvicne(t.getStart_province());
                sheduleTable.setDestLocation(t.getDest_location());
                sheduleTable.setDestPorvice(t.getDest_province());
                sheduleTable.setCustomerName("");
                sheduleTable.setCustomerTel("");
                sheduleTable.setDateCount(t.getDate_count());
                sheduleTable.setEndDate(t.getEnd_date());
                if (t.getDate_count() >= 1) {
//                    for (int j = 0; j < t.getDate_count() ; j++) {
                    CalendarCollection.date_collection_arr.add(new CalendarCollection(t.getStart_date().toString().substring(0, 10)
                            , t.getStart_location() +
                            "-" + t.getDest_location(),t.getTask_status()));
                    //CalendarCollection.date_collection_arr.addAll();

                    ArrayList<String> arr = findBetween(t.getStart_date().toString(), t.getDate_count());
                    for(int i = 0 ; i< arr.size() ; i++){
                        CalendarCollection.date_collection_arr.add(new CalendarCollection(arr.get(i)
                                , t.getStart_location() +
                                "-" + t.getDest_location(),t.getTask_status()));

                    }

//                    }
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> findBetween(String startDate, int count) {
        ArrayList<String> dateBetween = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String FirstBetweenDate = "000";
        try {
            c.setTime(sdf.parse(startDate));
            c.add(Calendar.DATE, 1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            FirstBetweenDate = sdf1.format(c.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c2 = Calendar.getInstance();
        for (int i = 0; i <= count - 1; i++) {
            try {
                c2.setTime(sdf.parse(FirstBetweenDate));
                c2.add(Calendar.DATE, i);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                dateBetween.add(sdf2.format(c2.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Log.d("fff", String.valueOf(dateBetween));
        return dateBetween;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        MenuItem save = menu.findItem(R.id.action_save);
        MenuItem edit = menu.findItem(R.id.action_edit);
        MenuItem warning = menu.findItem(R.id.action_warning);

        save.setVisible(false);
        edit.setVisible(false);
        warning.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
//                saveFreeDate();
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                break;
            case R.id.action_warning:
                Dialog d = new BottomSheetDialog(this);
                d.setContentView(R.layout.dialog_status_color_bottom);
                d.setCancelable(true);
                d.show();
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_edit:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder.setMessage("คุณต้องการตั้งค่าวันที่ไม่รับงานใช่ไหม?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                linear_edit.setVisibility(View.GONE);
                                linear_save.setVisibility(View.VISIBLE);
                            }
                        });
                alertDialogBuilder.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.linear_save:
                final AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
                alertDialogBuilder2.setTitle("Warning");
                alertDialogBuilder2.setMessage("คุณต้องการบันทึกข้อมูลใช่ไหม?");
                alertDialogBuilder2.setCancelable(false);
                alertDialogBuilder2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                linear_edit.setVisibility(View.VISIBLE);
                                linear_save.setVisibility(View.GONE);
                            }
                        });
                alertDialogBuilder2.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog2 = alertDialogBuilder2.create();
                alertDialog2.show();
                break;

            case R.id.Ib_next:
                if(action.equalsIgnoreCase("setting")) {
//                        flagNext = true;
//                        saveFreeDate();
                }else {
                    setNextMonth();
                    refreshCalendar();
                }
                break;
            case R.id.ib_prev:
                if(action.equalsIgnoreCase("setting")) {
//                        flagNext = false;
//                        saveFreeDate();
                }else {
                    setPreviousMonth();
                    refreshCalendar();
                }
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backButton();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void backButton(){
        Intent i = new Intent(DriverBookingActivity.this,DriverMainActivity2.class);
        startActivity(i);
        finish();
    }
}
