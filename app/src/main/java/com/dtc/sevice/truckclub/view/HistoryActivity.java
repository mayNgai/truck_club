package com.dtc.sevice.truckclub.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.HistoryPageAdapter;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.presenters.HistoryPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.TaskController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by May on 10/9/2017.
 */

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioButton rdoNow, rdoBook;
    private static Activity _activity;
    private DialogController dialog;
    private Toolbar toolbar;
    private Spinner spinner;
    private EditText edtStart, edtEnd;
    private ImageButton imgStart, imgEnd;
    private static RecyclerView recycler_view;
    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar newCalendar;
    private int pos = 0;
    private DateController dateController;
    private TaskController taskController;
    private long lStartSelect ,lEndSelect ;
    private boolean flagSelect = false;
    private String[] strSelectDate;
    private int index = 0;
    public List<TblMember> listMembers;
    private ApiService mForum;
    private HistoryPresenter historyPresenter;
    private static List<TblTask> listTask = new ArrayList<TblTask>();
    private static HistoryPageAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dialog = new DialogController();
        _activity = HistoryActivity.this;
        ApplicationController.setAppActivity(_activity);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        dateController = new DateController();
        taskController = new TaskController();
        initAll();
        getMember();
        setSpinner();
        setCheckChang();
        imgStart.setOnClickListener(this);
        imgEnd.setOnClickListener(this);
        setDatePickerDialog();
    }
    private void initAll() {
        rdoNow = (RadioButton) findViewById(R.id.rdoNow);
        rdoBook = (RadioButton) findViewById(R.id.rdoBook);
        spinner = (Spinner) findViewById(R.id.spinner);
        edtStart = (EditText) findViewById(R.id.edtStart);
        edtEnd = (EditText) findViewById(R.id.edtEnd);
        imgStart = (ImageButton) findViewById(R.id.imgStart);
        imgEnd = (ImageButton) findViewById(R.id.imgEnd);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getMember(){
        try {
            listMembers = new ArrayList<TblMember>();
            listMembers = taskController.getMember();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setSpinner() {
        try {
            strSelectDate = getResources().getStringArray(R.array.select_date_array);
            ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, strSelectDate);
            spinner.setAdapter(adapterDate);
            setDateDefault();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    setDate(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDateDefault() {
        try {
            pos = 0;
            edtStart.setText(GlobalVar.getSystemDateOnly(HistoryActivity.this));
            edtEnd.setText(GlobalVar.getSystemDateOnly(HistoryActivity.this));
            setClickCalendar(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setClickCalendar(boolean flag) {
        try {
            imgStart.setEnabled(flag);
            imgEnd.setEnabled(flag);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setDate(int position) {
        pos = position;
        try {
            if (pos == 0) {
                edtStart.setText(GlobalVar.getSystemDateOnly(HistoryActivity.this));
                edtEnd.setText(GlobalVar.getSystemDateOnly(HistoryActivity.this));
                flagSelect = true;
                setClickCalendar(false);
            } else if (pos == 1) {
                flagSelect = true;
                setClickCalendar(false);
            } else if (pos == 2) {
                flagSelect = true;
                setClickCalendar(false);
            } else if (pos == 3) {
                flagSelect = true;
                setClickCalendar(false);
            } else {
                flagSelect = false;
                edtStart.setText("");
                edtEnd.setText("");
                edtStart.setHint("Date Start");
                edtEnd.setHint("Date End");
                lStartSelect=0;
                lEndSelect=0;
                clearDefaultChooseYourSelf();
                setClickCalendar(true);
            }

            getDataHistory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setCheckChang() {
        try {

            rdoNow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rdoNow.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        rdoBook.setBackgroundResource(R.drawable.buttonshape_orange);
                        index = 0;
                        getDataHistory();
                    }
                }
            });

            rdoBook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rdoBook.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        rdoNow.setBackgroundResource(R.drawable.buttonshape_orange);
                        index = 1;
                        getDataHistory();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataHistory(){
        try {
            mForum = new ApiService();
            historyPresenter = new HistoryPresenter(this,mForum);
            historyPresenter.loadHistory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateTask(List<TblTask> list){
        try {
            listTask = new ArrayList<TblTask>();
            //listTask = list;
            setListInSelect(list);
            setAdapter();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(){
        try {
            adapter = new HistoryPageAdapter(HistoryActivity.this,listTask);
            recycler_view.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void clearDefaultChooseYourSelf(){
        try {
            edtEnd.setText("");
            edtEnd.setHint("Date End");
            flagSelect = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setDatePickerDialog() {
        try {
            newCalendar = Calendar.getInstance();
            startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    edtStart.setText(dateFormatter.format(newDate.getTime()));
                    clearDefaultChooseYourSelf();
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            endDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    lStartSelect = dateController.dateFormat1Tolong(edtStart.getText().toString());
                    lEndSelect = dateController.dateFormat1Tolong(dateFormatter.format(newDate.getTime()));

                    if(lStartSelect<=lEndSelect) {
                        edtEnd.setText(dateFormatter.format(newDate.getTime()));
                        flagSelect = true;
                        getDataHistory();
                    }else
                        dialog.dialogNolmal(HistoryActivity.this, getResources().getString(R.string.titleLoginFail),
                                getResources().getString(R.string.txtPleaseChooseDateStart));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgStart:
                startDatePickerDialog.show();

                break;

            case R.id.imgEnd:
                if(edtStart.getText().toString().length()>0)
                    endDatePickerDialog.show();
                else
                    dialog.dialogNolmal(HistoryActivity.this, getResources().getString(R.string.titleLoginFail),
                            getResources().getString(R.string.txtPleaseChooseDateStart));

                break;

            default:
        }

    }

    private void setListInSelect(List<TblTask> list) {
        try {
            if ((list != null) && (list.size()>0)) {
                for(TblTask t: list){
                    if(index == 0 && t.getService_type().equalsIgnoreCase("Now")){
                        long longStart = GlobalVar.dateTimeFormatTolong(t.getDate_task_create());
                        long longCurrent = GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemTime(HistoryActivity.this));
                        if (pos == 0) {
                            if (longStart == longCurrent) {
                                listTask.add(t);
                            }

                        } else if (pos == 1) {
                            List<String> listdate = dateController.daysOfWeek();
                            if (listdate.size() > 0) {
                                edtStart.setText(listdate.get(0));
                                edtEnd.setText(listdate.get(1));
                                long startWeek = dateController.dateFormat1Tolong(listdate.get(0));
                                long endWeek = dateController.dateFormat1Tolong(listdate.get(1));

                                if (longStart >= startWeek && longStart <= endWeek) {
                                    listTask.add(t);
                                }
                            }

                        } else if (pos == 2) {
                            List<String> listdate = dateController.daysOfMonth();
                            edtStart.setText(listdate.get(1));
                            edtEnd.setText(listdate.get(2));
                            if (listdate.size() > 0) {
                                long lmonth = dateController.dateTimeFormat5Tolong(listdate.get(0));
                                long startmonth = dateController.dateTimeFormat5Tolong(t.getDate_task_create().substring(0, 7));

                                if (lmonth == startmonth) {
                                    listTask.add(t);
                                }
                            }

                        } else if (pos == 3) {

                            List<String> listdate = dateController.daysOfYear();

                            if (listdate.size() > 0) {
                                edtStart.setText(listdate.get(1));
                                edtEnd.setText(listdate.get(2));
                                long lYear = dateController.dateTimeFormat6Tolong(listdate.get(0));
                                long startYear = dateController.dateTimeFormat6Tolong(t.getDate_task_create().substring(0, 4));

                                if (lYear == startYear) {
                                    listTask.add(t);
                                }
                            }
                        } else {

                            if(lStartSelect!=0&&lEndSelect!=0){
                                if (longStart >= lStartSelect && longStart <= lEndSelect) {
                                    listTask.add(t);
                                }
                            }


                        }
                    }else if(index == 1 && t.getService_type().equalsIgnoreCase("Booking")){
                        long longStart = GlobalVar.dateTimeFormatTolong(t.getDate_task_create());
                        long longCurrent = GlobalVar.dateTimeFormatTolong(GlobalVar.getSystemTime(HistoryActivity.this));
                        if (pos == 0) {
                            if (longStart == longCurrent) {
                                listTask.add(t);
                            }

                        } else if (pos == 1) {
                            List<String> listdate = dateController.daysOfWeek();
                            if (listdate.size() > 0) {
                                edtStart.setText(listdate.get(0));
                                edtEnd.setText(listdate.get(1));
                                long startWeek = dateController.dateFormat1Tolong(listdate.get(0));
                                long endWeek = dateController.dateFormat1Tolong(listdate.get(1));

                                if (longStart >= startWeek && longStart <= endWeek) {
                                    listTask.add(t);
                                }
                            }

                        } else if (pos == 2) {
                            List<String> listdate = dateController.daysOfMonth();
                            edtStart.setText(listdate.get(1));
                            edtEnd.setText(listdate.get(2));
                            if (listdate.size() > 0) {
                                long lmonth = dateController.dateTimeFormat5Tolong(listdate.get(0));
                                long startmonth = dateController.dateTimeFormat5Tolong(t.getDate_task_create().substring(0, 7));

                                if (lmonth == startmonth) {
                                    listTask.add(t);
                                }
                            }

                        } else if (pos == 3) {

                            List<String> listdate = dateController.daysOfYear();

                            if (listdate.size() > 0) {
                                edtStart.setText(listdate.get(1));
                                edtEnd.setText(listdate.get(2));
                                long lYear = dateController.dateTimeFormat6Tolong(listdate.get(0));
                                long startYear = dateController.dateTimeFormat6Tolong(t.getDate_task_create().substring(0, 4));

                                if (lYear == startYear) {
                                    listTask.add(t);
                                }
                            }
                        } else {

                            if(lStartSelect!=0&&lEndSelect!=0){
                                if (longStart >= lStartSelect && longStart <= lEndSelect) {
                                    listTask.add(t);
                                }
                            }


                        }
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
