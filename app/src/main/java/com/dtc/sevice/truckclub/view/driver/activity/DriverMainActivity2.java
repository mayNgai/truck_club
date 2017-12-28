package com.dtc.sevice.truckclub.view.driver.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.TaskListAdapter;
import com.dtc.sevice.truckclub.broadcasts.DriverBackgroundService;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.presenters.driver.DriverMainPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.BaseActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by May on 9/27/2017.
 */

public class DriverMainActivity2 extends BaseActivity implements View.OnClickListener,OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , com.google.android.gms.location.LocationListener{

    //Map
    private GoogleApiClient client;
    private GoogleApiClient mGoogleApiClient;
    private Location locationUserRealTime;
    private SupportMapFragment dMapFragment2;
    private GoogleMap gMap;
    private Marker markerDriverRealTime;
    private Marker markerStart , markerDes;
    private double longitude;
    private double latitude;
    private Marker[] markers = new Marker[10000];
    private int subLastText = 25;

    private static TaskController taskController;
    public static List<TblMember> members;
    public static TblTask tblTask;
    public static TblTask tblTaskBooking;
    public static List<TblTask> listTasks;
    private Activity _activity;
    public static LinearLayout linear_select_type,linear_head,linear_main2;
    public static ProgressBar progressBarCircle;
    private static EditText edt_time;
    private static BaseActivity baseActivity;
    private ApiService mForum;
    private DriverMainPresenter driverMainPresenter;
    private static TaskListAdapter adapter;
    private static RecyclerView recycler_view;
    private SwipeRefreshLayout swipe_refresh;
    private static RelativeLayout linear_title;
    private static ImageButton img_step1,img_step2,img_step3;
    public static TextView txt_step1,txt_step2,txt_step3,txt_name,txt_last,txt_wait_time;
    private static Button btn_done,btn_arrive,btn_go;
    private LinearLayout linear_data_task,linear_bottom;
    private static ImageView img_profile,img_cancel;
    private static CountDownTimer countDownTimer ;
    private int countTime = 10;
    public static Timer timer = new Timer();
    private static DateController dateController;
    private static DialogController dialog;

    /////
    final String PREF_NAME = "user";
    final String KEY_TASK_ID = "task_id";
    final String KEY_MEMBER_ID = "member_id";
    final String KEY_TIME_OUT = "time_out";
    final String KEY_TIME_END = "time_end";
    final String KEY_DATE_CREATE = "date_create";
    final String KEY_TIME_COUNT = "time_count";
    final String KEY_TIME_WAIT = "time_wait";
    final String KEY_DESTROY = "destroy_app";

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    private static DriverBackgroundService backgroundService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_main_driver2);
        init();
        initListeners();
        getSchedulesDriver();
        //setCountDownTime();
    }

    private void init(){
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        tblTaskBooking = new TblTask();
        dateController = new DateController();
        dialog = new DialogController();
        ApplicationController.setAppActivity(DriverMainActivity2.this);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        linear_select_type = (LinearLayout)findViewById(R.id.linear_select_type);
        swipe_refresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        linear_title = (RelativeLayout)findViewById(R.id.linear_title);
        img_step1 = (ImageButton)findViewById(R.id.img_step1);
        img_step2 = (ImageButton)findViewById(R.id.img_step2);
        img_step3 = (ImageButton)findViewById(R.id.img_step3);
        txt_step1 = (TextView)findViewById(R.id.txt_step1);
        txt_step2 = (TextView)findViewById(R.id.txt_step2);
        txt_step3 = (TextView)findViewById(R.id.txt_step3);
        btn_done = (Button)findViewById(R.id.btn_done);
        btn_arrive = (Button)findViewById(R.id.btn_arrive);
        btn_go = (Button)findViewById(R.id.btn_go);
        linear_data_task = (LinearLayout)findViewById(R.id.linear_data_task);
        linear_bottom = (LinearLayout)findViewById(R.id.linear_bottom);
        linear_head = (LinearLayout)findViewById(R.id.linear_head);
        linear_main2 = (LinearLayout)findViewById(R.id.linear_main2);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_last = (TextView)findViewById(R.id.txt_last);
        edt_time = (EditText) findViewById(R.id.edt_time);
        txt_wait_time = (TextView)findViewById(R.id.txt_wait_time);
        img_cancel = (ImageView)findViewById(R.id.img_cancel);
        progressBarCircle = (ProgressBar)findViewById(R.id.progressBarCircle);
        taskController =new TaskController();
        baseActivity = new BaseActivity();
        members = new ArrayList<TblMember>();
        members = baseActivity.getMember();
        _activity = new DriverMainActivity2();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        dMapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.DriverMap);
        dMapFragment2.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setShowSelectList();
        setTitle();
        if(members.get(0).getStatus_id()>=3){
            getTaskByID();
        }
        if(members.get(0).getStatus_id() != 33){
            timeCountInMilliSeconds = 0;
            tblTask2 = new TblTask();
            if(countDownTimer != null){
                countDownTimer.cancel();
            }

        }
        setRefreshList();

//        setClickList();
    }

    private void initListeners(){
        btn_go.setOnClickListener(this);
        btn_arrive.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        linear_data_task.setOnClickListener(this);
        img_cancel.setOnClickListener(this);
    }

    public void setTitle(){
        try {
            if(members == null ||members.size()==0){
                members = baseActivity.getMember();
            }
            if(members.get(0).getStatus_id()>=3){
                if(tblTaskBooking!=null){
                    LatLng latLngStart = new LatLng(tblTaskBooking.getStart_lat(), tblTaskBooking.getStart_lon());
                    LatLng latLngDes = new LatLng(tblTaskBooking.getDes_lat(), tblTaskBooking.getDes_lon());
                    if(tblTaskBooking.getDes_lat()!= 0.0 || tblTaskBooking.getDes_lon() != 0.0 ){
                        addMarkerDestination(latLngDes);
                        addMarkerStart(latLngStart);
                    }
                }
                linear_bottom.setVisibility(View.VISIBLE);
                linear_title.setVisibility(View.VISIBLE);
                linear_select_type.setVisibility(View.GONE);
                if(members.get(0).getStatus_id()==3){
                    btn_go.setVisibility(View.VISIBLE);
                    btn_arrive.setVisibility(View.GONE);
                    btn_done.setVisibility(View.GONE);
                }else if(members.get(0).getStatus_id()==4){
                    img_step1.setBackgroundResource(R.drawable.button_cricle_orange);
                    img_step2.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                    img_step3.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                    txt_step1.setTextColor(getResources().getColor(R.color.black_1));
                    txt_step2.setTextColor(getResources().getColor(R.color.LightGray));
                    txt_step3.setTextColor(getResources().getColor(R.color.LightGray));
                    btn_go.setVisibility(View.GONE);
                    btn_arrive.setVisibility(View.VISIBLE);
                    btn_done.setVisibility(View.GONE);
                }else if(members.get(0).getStatus_id()==5){
                    img_step1.setBackgroundResource(R.drawable.button_cricle_orange);
                    img_step2.setBackgroundResource(R.drawable.button_cricle_orange);
                    img_step3.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                    txt_step1.setTextColor(getResources().getColor(R.color.LightGray));
                    txt_step2.setTextColor(getResources().getColor(R.color.black_1));
                    txt_step3.setTextColor(getResources().getColor(R.color.LightGray));
                    btn_go.setVisibility(View.GONE);
                    btn_arrive.setVisibility(View.GONE);
                    btn_done.setVisibility(View.VISIBLE);
                }else if(members.get(0).getStatus_id()==33){
                    setCountDownTime(tblTaskBooking);
                }
            }else {
                linear_bottom.setVisibility(View.GONE);
                linear_title.setVisibility(View.GONE);


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setShowSelectList(){
        try {
            members = baseActivity.getMember();
            if(members.get(0).getStatus_id()==2){
                linear_select_type.setVisibility(View.VISIBLE);
                getTask();
            }else {
                linear_select_type.setVisibility(View.GONE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getTask(){
        try {
            tblTask = new TblTask();
            tblTask.setService_type("Now");
            tblTask.setTask_status(1);
            tblTask.setMember(members);
            mForum = new ApiService();
            driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
            driverMainPresenter.loadTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getTaskByID(){
        try {
            mForum = new ApiService();
            driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
            driverMainPresenter.loadTaskByID();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setListTask(List<TblTask> lists){
        try {
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            listTasks = new ArrayList<TblTask>();
            listTasks = lists;
            adapter = new TaskListAdapter(DriverMainActivity2.this,listTasks,0);
            recycler_view.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getSchedulesDriver(){
        try {
            mForum = new ApiService();
            driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
            driverMainPresenter.loadSchedulesDriver();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setUpdateListSchedulesTask(List<TblTask> lists){
        try {
            taskController.createTask(lists);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setRefreshList(){
        try {
            swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getTask();
                    swipe_refresh.setRefreshing(false);
                }
            });
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
            tblTask.setService_type("Now");
            tblTask.setMember(members);
            mForum = new ApiService();
            driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
            driverMainPresenter.sentOfferPrice(tblTask);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void setClickList(){
//        try {
//            recycler_view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    setDialogBottom();
//                }
//            });
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    private static Dialog mBottomSheetDialog;
//    private void setDialogBottom(){
//        try {
//            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate (R.layout.dialog_driver_offer, null);
//            ImageView img_cancel = (ImageView)view.findViewById( R.id.img_cancel);
//            mBottomSheetDialog = new Dialog (this,R.style.MaterialDialogSheet);
//            mBottomSheetDialog.setContentView (view);
//            mBottomSheetDialog.setCancelable (true);
//            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
//            mBottomSheetDialog.show ();
//            img_cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mBottomSheetDialog.dismiss();
//
//                }
//            });
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("DriverMainActivity2 Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onLocationChanged(Location location) {
        locationUserRealTime = location;
        if (location == null) {
            return;
        }
        latitude = location.getLatitude();
        longitude= location.getLongitude();

        LatLng coordinate = new LatLng(longitude, longitude);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 9));
        updateLatLon(latitude,longitude);

    }

    private void updateLatLon(double latitude,double longitude){
        try {
            members.get(0).setLat(Float.parseFloat(String.valueOf(latitude)));
            members.get(0).setLon(Float.parseFloat(String.valueOf(longitude)));
            mForum = new ApiService();
            driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
            driverMainPresenter.sentUpdateLatlon();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap = googleMap;
        markerDriverRealTime = gMap.addMarker(new MarkerOptions().position(new LatLng(1, 1)).title("Your Driver"));
        markerDes = gMap.addMarker(new MarkerOptions().position(new LatLng(1, 1)).title("Your Destination"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//        rlp.setMargins(0, 1100, 0, 0);

//        getDestinationByFinger();


    }

    //Getting current location
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            //moving the map to location
            moveMap(latitude ,longitude);
            //String strAddress = getAddressByLatLng(latitude,longitude);
            //edt_start.setText((strAddress.length()>25) ? strAddress.substring(0,25) + ".." : strAddress);
            if(members == null || members.size() == 0)
                members = baseActivity.getMember();
            members.get(0).setLat(Float.valueOf(String.valueOf(latitude)));
            members.get(0).setLon(Float.valueOf(String.valueOf(longitude)));
            members.get(0).setRadius(0.05);
            //callDriverInScope();
            updateLatLon(latitude,longitude);
        }
    }
    private void moveMap(double lat ,double lon) {
        LatLng latLng = new LatLng(lat, lon);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(9));
        gMap.getUiSettings().setZoomControlsEnabled(true);


    }

    private void addMarkerStart(LatLng latlon){
        removeMarkerStart();
        markerStart = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_24dp)).title("Start Position"));

    }

    private void addMarkerDestination(LatLng latlon){
        removeMarkerDestination();
        markerDes = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)).title("Destination"));

    }

    private void removeMarkerStart(){
        if (markerStart != null)
            markerStart.remove();
    }

    private void removeMarkerDestination(){
        if (markerDes != null)
            markerDes.remove();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (lastLocation != null) {
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
            getCurrentLocation();

        } else {
            Toast.makeText(this, "ไม่สามารถระบุตำแหน่งได้", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
        mGoogleApiClient.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                setDialogRating();
                break;
            case R.id.btn_arrive:
                members.get(0).setStatus_id(5);
                tblTaskBooking.setTask_status(5);
                mForum = new ApiService();
                driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
                driverMainPresenter.sentUpdateDriver(tblTaskBooking);
                break;
            case R.id.linear_data_task:

                break;
            case R.id.img_cancel:
                tblTask2 = new TblTask();
                timeCountInMilliSeconds = 0;
                stopCountDownTimer();
                linear_main2.setVisibility(View.GONE);
                break;
            case R.id.btn_go:
                members.get(0).setStatus_id(4);
                tblTaskBooking.setTask_status(4);
                mForum = new ApiService();
                driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
                driverMainPresenter.sentUpdateDriver(tblTaskBooking);
                break;

        }
    }

    private void setFinishTask(){
        try {
            members.get(0).setStatus_id(6);
            tblTaskBooking.setTask_status(6);
            tblTaskBooking.setRating(count_rating);
            mForum = new ApiService();
            driverMainPresenter = new DriverMainPresenter(DriverMainActivity2.this , mForum);
            driverMainPresenter.sentUpdateDriver(tblTaskBooking);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    float count_rating = 5;
    private void setDialogRating(){
        try {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_rating, null);
            final Dialog mBottomSheetDialog = new Dialog (this,R.style.MaterialDialogSheet);
            Button submitRateBtn = (Button)view.findViewById(R.id.submitRateBtn);
            RatingBar ratingsBar = (RatingBar) view.findViewById(R.id.ratingsBar);
            final TextView txt_rate = (TextView) view.findViewById(R.id.txt_rate);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.CENTER);
            mBottomSheetDialog.show ();
            ratingsBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                    count_rating = rating;
                    txt_rate.setText(String.valueOf(rating));
                }
            });
            submitRateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();
                    setFinishTask();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static TblTask tblTask2;
    public static long timeCountInMilliSeconds = 1 * 60000;
    public void setCountDownTime(final TblTask t){
        try {
            tblTask2 = new TblTask();
            tblTask2 = t;
            edt_time.setText(String.valueOf(tblTask2.getTime_wait()));
            dateController = new DateController();
            long current = dateController.dateTimeFormat9Tolong(dateController.getSystemTimeFull(ApplicationController.getAppActivity()));
            timeCountInMilliSeconds = dateController.dateTimeFormat9Tolong(tblTask2.getDate_task_create()) + tblTask2.getTime_wait() * 60000;
            if(editor == null){
                sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                editor = sp.edit();
            }
            editor.putLong(KEY_TIME_END,timeCountInMilliSeconds);
            editor.putString(KEY_DATE_CREATE,tblTask2.getDate_task_create());
            if(timeCountInMilliSeconds > current){
                //stopService();
                timeCountInMilliSeconds = timeCountInMilliSeconds - current;
                editor.putInt(KEY_MEMBER_ID,members.get(0).getMember_id());
                editor.putString(KEY_TASK_ID,tblTask2.getId());
                editor.putLong(KEY_TIME_COUNT,timeCountInMilliSeconds);
                editor.putInt(KEY_TIME_WAIT,tblTask2.getTime_wait());
                editor.putBoolean(KEY_DESTROY, false);
                editor.commit();
                linear_main2.setVisibility(View.VISIBLE);
//                backgroundService = new DriverBackgroundService();
//                startService();
                countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        try {
                            if(txt_wait_time != null){

                                txt_wait_time.setText(hmsTimeFormatter(millisUntilFinished));
                                progressBarCircle.setProgress(60 - (int) ((timeCountInMilliSeconds - millisUntilFinished)/ (1000 * tblTask2.getTime_wait())));
                                Log.i("onTick","test");
                            }

                            Log.i("wait_time",hmsTimeFormatter(millisUntilFinished));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFinish() {
                        Log.i("wait_time","Time out...");
                        setProgressBarValues();
                        stopCountDownTimer();
                        linear_main2.setVisibility(View.GONE);
                        dialog.dialogNolmal(ApplicationController.getAppActivity(),"Warning","Time out...");
                    }

                }.start();
                countDownTimer.start();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    public String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

    public void startService() {
        startService(new Intent(ApplicationController.getAppActivity(), backgroundService.getClass()));
    }

    public void stopService() {
        stopService(new Intent(ApplicationController.getAppActivity(), backgroundService.getClass()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent i = new Intent(DriverMainActivity2.this, LoginSecondActivity.class);
//            startActivity(i);
//            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
