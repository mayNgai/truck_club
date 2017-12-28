package com.dtc.sevice.truckclub.view.user.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.DriverWaitAcceptAdapter;
import com.dtc.sevice.truckclub.adapter.TaskListAdapter;
import com.dtc.sevice.truckclub.adapter.TypeCarAdapter;
import com.dtc.sevice.truckclub.broadcasts.UserBackgroundService;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblCarGroup;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.presenters.user.UserMainPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.BaseActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class UserMainActivity2 extends BaseActivity implements View.OnClickListener,OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , com.google.android.gms.location.LocationListener{
    private static Button btn_now,btn_booking,btn_call_service,btn_cancel;
    private static EditText edt_start,edt_des,edt_count_date,edt_end_date,edt_start_date,edt_iden,edt_start_time,edt_end_time,edt_time;
    private CheckBox chk_cash,chk_credit;
    private static ImageView img_cancel;
    private static LinearLayout linear_select_type,linear_current,linear_detail,linear_main1,linear_main2,linear_bottom,linear_head,linear_img_start,linear_img_des,linear_del_start,linear_del_des;
    public static TextView txtname_car,txt_wait_time,txt_type_car;
    public static ProgressBar progressBarCircle;
    private static SwipeRefreshLayout swipe_refresh;
    private static RecyclerView recycler_view;
    private static CountDownTimer countDownTimer ;
    private int countTime = 10;
    //private LinearLayout linear_detail;
    //Map
    private GoogleApiClient client;
    private GoogleApiClient mGoogleApiClient;
    private Location locationUserRealTime;
    private SupportMapFragment dMapFragment2;
    private GoogleMap gMap;
    private Marker markerDriverRealTime;
    private static Marker markerDes,markerStart;
    private double longitude;
    private double latitude;
    private Marker[] markers = new Marker[10000];
    private int subLastText = 25;

    private TaskController taskController;
    public List<TblMember> members;
    private Activity _activity;
    private static ApiService mApiService;
    private static UserMainPresenter mUserMainPresenter;
    private static List<TblCarGroup> listCarGroups;
    public static TblTask tblTask;
    private DateController dateController;
    private static String service_type = "";
    private DialogController dialog;
    private boolean flagEndDate =  false;
    private boolean clickableOnMap = true;
    private static BaseActivity baseActivity;
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
    final String KEY_CANCEL = "cancel";

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    public static Timer timer = new Timer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user4);
        init();
        initListeners();
       // setCountDownTime();
    }

    private void init(){
        baseActivity = new BaseActivity();
        taskController = new TaskController();
        members = new ArrayList<TblMember>();
        tblTask = new TblTask();
        _activity = new UserMainActivity2();
        dateController = new DateController();
        dialog = new DialogController();
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        //linear_detail = (LinearLayout)findViewById(R.id.linear_detail);
        btn_now = (Button)findViewById(R.id.btn_now);
        btn_booking = (Button)findViewById(R.id.btn_booking);
        btn_call_service = (Button)findViewById(R.id.btn_call_service);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        edt_start = (EditText)findViewById(R.id.edt_start);
        edt_des = (EditText)findViewById(R.id.edt_des);
        edt_count_date = (EditText)findViewById(R.id.edt_count_date);
        edt_end_date = (EditText)findViewById(R.id.edt_end_date);
        edt_start_date = (EditText)findViewById(R.id.edt_start_date);
        edt_end_time = (EditText)findViewById(R.id.edt_end_time);
        edt_start_time = (EditText)findViewById(R.id.edt_start_time);
        edt_iden = (EditText)findViewById(R.id.edt_iden);
        chk_cash = (CheckBox)findViewById(R.id.chk_cash);
        linear_img_start = (LinearLayout)findViewById(R.id.linear_img_start);
        linear_img_des = (LinearLayout)findViewById(R.id.linear_img_des);
        linear_del_start = (LinearLayout)findViewById(R.id.linear_del_start);
        linear_del_des = (LinearLayout)findViewById(R.id.linear_del_des);
        linear_current = (LinearLayout)findViewById(R.id.linear_current);
        linear_select_type = (LinearLayout)findViewById(R.id.linear_select_type);
        linear_detail = (LinearLayout)findViewById(R.id.linear_detail);
        linear_main1 = (LinearLayout)findViewById(R.id.linear_main1);
        linear_main2 = (LinearLayout)findViewById(R.id.linear_main2);
        linear_bottom = (LinearLayout)findViewById(R.id.linear_bottom);
        linear_head = (LinearLayout)findViewById(R.id.linear_head);
        txtname_car = (TextView)findViewById(R.id.txtname_car);
        txt_type_car = (TextView)findViewById(R.id.txt_type_car);
        edt_time = (EditText)findViewById(R.id.edt_time);
        img_cancel = (ImageView)findViewById(R.id.img_cancel);
        progressBarCircle = (ProgressBar)findViewById(R.id.progressBarCircle);
        txt_wait_time = (TextView)findViewById(R.id.txt_wait_time);
        swipe_refresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        //img_del_iden = (ImageView)findViewById(R.id.img_del_iden);
        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        members = taskController.getMember();

        //img_del_iden.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        dMapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.UserMainActivity);
        dMapFragment2.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setChangeTextStart();
        setChangeTextDes();
        setCarGroup();
        setTimeOut();
        //setChangeTextIden();
    }

    private void initListeners() {
        linear_select_type.setOnClickListener(this);
        linear_current.setOnClickListener(this);
        linear_img_start.setOnClickListener(this);
        linear_img_des.setOnClickListener(this);
        linear_del_start.setOnClickListener(this);
        linear_del_des.setOnClickListener(this);
        btn_now.setOnClickListener(this);
        btn_booking.setOnClickListener(this);
        btn_call_service.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        edt_end_date.setOnClickListener(this);
        edt_start_date.setOnClickListener(this);
        edt_start_time.setOnClickListener(this);
        edt_end_time.setOnClickListener(this);
        img_cancel.setOnClickListener(this);
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("UserMainActivity Page")
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
        longitude = location.getLatitude();
        longitude= location.getLongitude();

        LatLng coordinate = new LatLng(longitude, longitude);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 9));

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
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 140, 10, 0);

        getDestinationByFinger();

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
                members = taskController.getMember();
            members.get(0).setLat(Float.valueOf(String.valueOf(latitude)));
            members.get(0).setLon(Float.valueOf(String.valueOf(longitude)));
            members.get(0).setRadius(0.05);
            callDriverInScope();
        }
    }

    private void getDestinationByFinger() {
        try {
            gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(final LatLng latlon) {
                    try {
                        if(latlon != null) {
                            if(clickableOnMap){
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserMainActivity2.this);
                                alertDialogBuilder.setTitle("Warning");
                                alertDialogBuilder.setMessage("กรุณาเลือกจุดปักตำแหน่ง..");
                                alertDialogBuilder.setCancelable(true);
                                alertDialogBuilder.setPositiveButton("ส่ง",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                addMarkerDestination(latlon);
                                                moveMap(latlon.latitude,latlon.longitude);
                                                String strAddress = getAddressByLatLng(latlon.latitude,latlon.longitude);
                                                edt_des.setText((strAddress.length()>50) ? strAddress.substring(0,50) + ".." : strAddress);
                                                String strProvince = getProvinceByLatLng(latlon.latitude,latlon.longitude);
                                                setDestination(latlon.latitude,latlon.longitude,strProvince,strAddress);
                                            }
                                        });
                                alertDialogBuilder.setNegativeButton("รับ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        addMarkerStart(latlon);
                                        moveMap(latlon.latitude,latlon.longitude);
                                        String strAddress = getAddressByLatLng(latlon.latitude,latlon.longitude);
                                        edt_start.setText((strAddress.length()>50) ? strAddress.substring(0,50) + ".." : strAddress);
                                        String strProvince = getProvinceByLatLng(latlon.latitude,latlon.longitude);
                                        setStartLocation(latlon.latitude,latlon.longitude,strProvince,strAddress);
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String starDistination = "";
    private void getLocationBySearch(String txt_position) {
        try {
            if(txt_position.equalsIgnoreCase("start")){
                starDistination = edt_start.getText().toString().trim();
            }else if(txt_position.equalsIgnoreCase("des")){
                starDistination = edt_des.getText().toString().trim();
            }

            List<Address> addressList = null;
            if(starDistination.length()>0){
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(starDistination, 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address address;

                if (addressList != null && addressList.size() != 0) {
                    address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    if(txt_position.equalsIgnoreCase("start")){
                        addMarkerStart(latLng);
                        String strAddress = getAddressByLatLng(address.getLatitude(),address.getLongitude());
                        edt_start.setText((strAddress.length()>50) ? strAddress.substring(0,50) + ".." : strAddress);
                        setStartLocation(address.getLatitude(),address.getLongitude(),address.getAdminArea(),strAddress);
                    }else if(txt_position.equalsIgnoreCase("des")){
                        addMarkerDestination(latLng);
                        String strAddress = getAddressByLatLng(address.getLatitude(),address.getLongitude());
                        edt_des.setText((strAddress.length()>50) ? strAddress.substring(0,50) + ".." : strAddress);
                        setDestination(address.getLatitude(),address.getLongitude(),address.getAdminArea(),strAddress);
                    }

                    moveMap(address.getLatitude(),address.getLongitude());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStartLocation(double lat , double lon , String province ,String location){
        try {
            tblTask.setStart_lat(Float.valueOf(String.valueOf(lat)));
            tblTask.setStart_lon(Float.valueOf(String.valueOf(lon)));
            tblTask.setStart_province(province);
            tblTask.setStart_location(location);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDestination(double lat , double lon , String province ,String location){
        try {
            tblTask.setDes_lat(Float.valueOf(String.valueOf(lat)));
            tblTask.setDes_lon(Float.valueOf(String.valueOf(lon)));
            tblTask.setDest_province(province);
            tblTask.setDest_location(location);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addMarkerStart(LatLng latlon){
        removeMarkerStart();
        markerStart = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_24dp)).title("Your Start"));

    }

    private void addMarkerDestination(LatLng latlon){
        removeMarkerDestination();
        markerDes = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)).title("Your Destination"));

    }

    private void moveMap(double lat ,double lon) {
        LatLng latLng = new LatLng(lat, lon);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(9));
        gMap.getUiSettings().setZoomControlsEnabled(true);


    }

    public String getAddressByLatLng(double lat ,double lon) {
        String strAddress = "";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            strAddress = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAddress;
    }

    public String getProvinceByLatLng(double lat ,double lon) {
        String strAddress = "",city = "";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            strAddress = addresses.get(0).getAddressLine(0);
//            city = addresses.get(0).getLocality();
            city = addresses.get(0).getAdminArea();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
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
            case R.id.linear_current:
                getCurrentLocation();
                break;
            case R.id.linear_img_start:
                if(edt_start.getText().toString().length()>0){
                    getLocationBySearch("start");
                }else {
                    dialog.dialogNolmal(UserMainActivity2.this,"Warning","กรุณากรอกสถานที่ก่อน");
                }

                break;
            case R.id.linear_img_des:
                if(edt_des.getText().toString().length()>0){
                    getLocationBySearch("des");
                }else {
                    dialog.dialogNolmal(UserMainActivity2.this,"Warning","กรุณากรอกสถานที่ก่อน");
                }

                break;
            case R.id.linear_del_start:
                edt_start.setText("");
                removeMarkerStart();
                //setDefault();
                break;
            case R.id.linear_del_des:
                edt_des.setText("");
                removeMarkerDestination();
                //setDefault();
                break;
            case R.id.btn_now:
                if(edt_start.getText().toString().length()>0){
                    clickableOnMap = false;
                    edt_start.setEnabled(false);
                    edt_des.setEnabled(false);
                    linear_del_des.setEnabled(false);
                    linear_del_start.setVisibility(View.INVISIBLE);
                    linear_img_start.setVisibility(View.INVISIBLE);
                    linear_img_des.setEnabled(false);
                    edt_start_date.setText(GlobalVar.getSystemDateOnly(UserMainActivity2.this));
                    edt_start_time.setText(GlobalVar.getSystemTimeOnly(UserMainActivity2.this));
                    edt_start_date.setEnabled(false);
                    edt_start_time.setEnabled(false);
                    txt_type_car.setText(txtname_car.getText().toString());
                    linear_detail.setVisibility(View.VISIBLE);
                    linear_select_type.setVisibility(View.INVISIBLE);
                    btn_now.setTextColor(getResources().getColor(R.color.colorPrimary));
                    btn_booking.setTextColor(getResources().getColor(R.color.black_1));
                    service_type = "Now";
                }
                break;
            case R.id.btn_booking:
                if(edt_start.getText().toString().length()>0){
                    clickableOnMap = false;
                    edt_start.setEnabled(false);
                    edt_des.setEnabled(false);
                    linear_del_des.setVisibility(View.INVISIBLE);
                    linear_del_start.setVisibility(View.INVISIBLE);
                    linear_img_start.setEnabled(false);
                    linear_img_des.setEnabled(false);
                    edt_start_date.setText("");
                    edt_start_time.setText("");
                    edt_start_date.setEnabled(true);
                    edt_start_time.setEnabled(true);
                    txt_type_car.setText(txtname_car.getText().toString());
                    linear_detail.setVisibility(View.VISIBLE);
                    linear_select_type.setVisibility(View.INVISIBLE);
                    btn_now.setTextColor(getResources().getColor(R.color.black_1));
                    btn_booking.setTextColor(getResources().getColor(R.color.colorPrimary));
                    service_type = "Booking";
                }

                break;
            case R.id.linear_select_type:
                setDialogBottom();
                break;

            case R.id.btn_call_service:
                if(edt_start.getText().toString().length()>0){
                    setData();

                }

                break;
            case R.id.btn_cancel:
                setDefault();
                break;
            case R.id.edt_start_date:
                flagEndDate = false;
                edt_end_date.setText("");
                edt_start_time.setText("");
                edt_end_time.setText("");
                edt_count_date.setText("");
                setDatePicker(edt_start_date);
                break;
            case R.id.edt_end_date:
                if(edt_start_date.getText().toString().length()>0&&edt_start_time.getText().toString().length()>0) {
                    flagEndDate = true;
                    setDatePicker(edt_end_date);
                }else
                    dialog.dialogNolmal(UserMainActivity2.this, getResources().getString(R.string.txtWarning),getResources().getString(R.string.txtPleaseChooseDateStart));
                break;
            case R.id.edt_start_time:
                if(edt_start_date.getText().toString().length()>0)
                    setTimePicker(edt_start_time);
                else
                    dialog.dialogNolmal(UserMainActivity2.this, getResources().getString(R.string.txtWarning),getResources().getString(R.string.txtPleaseChooseDateStart));
                break;
            case R.id.edt_end_time:
                if(edt_start_date.getText().toString().length()>0&&edt_start_time.getText().toString().length()>0&&edt_end_date.getText().toString().length()>0)
                    setTimePicker(edt_end_time);
                else
                    dialog.dialogNolmal(UserMainActivity2.this, getResources().getString(R.string.txtWarning),getResources().getString(R.string.txtPleaseChooseDateEnd));
                break;
            case R.id.img_cancel:
                //stopCountDownTimer();
//                Intent s = new Intent(UserMainActivity2.this, UserBackgroundService.class);
//                stopService(s);
                editor.putInt(KEY_MEMBER_ID,0);
                editor.putString(KEY_TASK_ID,"");
                editor.putLong(KEY_TIME_COUNT,0);
                editor.putInt(KEY_TIME_WAIT,0);
                editor.putBoolean(KEY_DESTROY, false);
                editor.putBoolean(KEY_CANCEL, true);
                editor.commit();
                timeCountInMilliSeconds = 0;
                UserBackgroundService.time_count = 0;
                UserBackgroundService.cancel = true;
                tblTask2.setTask_status(-1);
                tblTask2.setMember(members);
                callUpdateTask(tblTask2,null,-1);
                linear_main1.setVisibility(View.VISIBLE);
                linear_main2.setVisibility(View.GONE);
                stopService();
                break;

        }
    }

    private void setData(){
        try {
            boolean cancel = false;
            String txt_error = "";
            if(edt_start_date.getText().toString().length()==0){
                cancel = true;
                txt_error = getResources().getString(R.string.txtPleaseChooseDateStart);
            }else if(edt_start_time.getText().toString().length()==0){
                cancel = true;
                txt_error = getResources().getString(R.string.txtPleaseChooseTimeStart);
            }else if(edt_end_date.getText().toString().length()==0){
                cancel = true;
                txt_error = getResources().getString(R.string.txtPleaseChooseDateEnd);
            }else if(edt_end_time.getText().toString().length()==0){
                cancel = true;
                txt_error = getResources().getString(R.string.txtPleaseChooseTimeEnd);
            }

            if(!cancel){
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserMainActivity2.this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder.setMessage("ยืนยัน..");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                tblTask.setUser_id(members.get(0).getMember_id());
                                tblTask.setGroup_id(position);
                                tblTask.setService_type(service_type);
                                tblTask.setDate_count(Integer.parseInt(edt_count_date.getText().toString()));
                                tblTask.setType_create("1");
                                tblTask.setIdentify(edt_iden.getText().toString());
                                if(service_type.equalsIgnoreCase("Now"))
                                    tblTask.setTime_wait(10);
                                tblTask.setStart_date(dateController.convertDateFormat1To2(edt_start_date.getText().toString()) + " " + edt_start_time.getText().toString());
                                tblTask.setEnd_date(dateController.convertDateFormat1To2(edt_end_date.getText().toString()) + " " + edt_end_time.getText().toString());
                                members.get(0).setStatus_id(33);
                                members.get(0).setStatus("wait offer");
                                taskController.updateMember(members.get(0));
                                mApiService = new ApiService();
                                mUserMainPresenter = new UserMainPresenter(UserMainActivity2.this,mApiService);
                                mUserMainPresenter.sentCreateTask();

                            }
                        });
                alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else {
                dialog.dialogNolmal(UserMainActivity2.this, getResources().getString(R.string.txtWarning),txt_error);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setDatePicker(final EditText editText) {
        String txtDate = "";
        int mYear,mMonth,mDay;
        if(edt_start_date.getText().toString().length()>0){
            String date = dateController.convertDateFormat1To2(edt_start_date.getText().toString());
            mYear = Integer.parseInt(date.substring(0,4));
            mMonth = Integer.parseInt(date.substring(5,7)) - 1;
            mDay = Integer.parseInt(date.substring(8,10));
        }else {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(UserMainActivity2.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String aa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                if (monthOfYear + 1 < 10 || dayOfMonth < 10) {
                    if (monthOfYear + 1 < 10)
                        aa = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                    if (dayOfMonth < 10)
                        aa = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    if (monthOfYear + 1 < 10 && dayOfMonth < 10)
                        aa = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                }
                editText.setText(dateController.convertDateFormat2To1(aa));
                if(flagEndDate){
                    long StartSelect = dateController.dateFormat1Tolong(edt_start_date.getText().toString());
                    long EndSelect = dateController.dateFormat1Tolong(edt_end_date.getText().toString());
                    if(StartSelect<EndSelect) {
                        int days = dateController.daysBetween(dateController.dateFormat1Tolong(edt_start_date.getText().toString()),dateController.dateFormat2Tolong(aa));
                        edt_count_date.setText(String.valueOf(days));
                    }else {
                        edt_end_date.setText("");
                        dialog.dialogNolmal(UserMainActivity2.this, getResources().getString(R.string.titleLoginFail),
                                getResources().getString(R.string.txtPleaseChooseDateStart));
                    }

                }else {
                    long current = dateController.dateFormat1Tolong(GlobalVar.getSystemDateOnly(UserMainActivity2.this));
                    long StartSelect = dateController.dateFormat1Tolong(edt_start_date.getText().toString());
                    if(StartSelect<current) {
                        edt_start_date.setText("");
                        dialog.dialogNolmal(UserMainActivity2.this, getResources().getString(R.string.titleLoginFail),
                                getResources().getString(R.string.txtPleaseChooseDateStart));
                    }
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void setTimePicker(final EditText editText){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if(minute<10) {
                            String time = "0" + minute;
                            //minute = Integer.parseInt(time);
                            editText.setText(hourOfDay + ":" + time);
                        }else {
                            editText.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }
    private void setCarGroup(){
        try {
            listCarGroups = taskController.getCarGroup();
            updateNameCar();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateNameCar(){
        for(TblCarGroup c : listCarGroups){
            if(c.getIsSelect() == 1){
                txtname_car.setText(c.getName_group());
            }
        }

    }
    private static Dialog mBottomSheetDialog;
    private void setDialogBottom(){
        try {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.bottom_type_car, null);
            RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
//            TextView txt_head = (TextView)view.findViewById( R.id.txt_head);
            ImageView img_cancel = (ImageView)view.findViewById( R.id.img_cancel);
            mBottomSheetDialog = new Dialog (this,R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            TypeCarAdapter adapter = new TypeCarAdapter(this,listCarGroups);
            mRecyclerView.setAdapter(adapter);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
//            txt_head.setText(txtHead);
            img_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    static int position = 1;
    public void setSelectCar(int i){
        position = i + 1;
        for(int a = 0 ; a<listCarGroups.size();a++){
            if(a == i){
                listCarGroups.get(a).setIsSelect(1);
                txtname_car.setText(listCarGroups.get(a).getName_group());

            }else {
                listCarGroups.get(a).setIsSelect(0);
            }
        }
        mBottomSheetDialog.dismiss();
    }

    private void setChangeTextStart(){
        ((EditText) findViewById(R.id.edt_start)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    //dp something
                    linear_del_start.setVisibility(View.VISIBLE);
                } else {
                    linear_del_start.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private void setChangeTextDes(){
        ((EditText) findViewById(R.id.edt_des)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    //dp something
                    linear_del_des.setVisibility(View.VISIBLE);
                } else {
                    linear_del_des.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void callDriverInScope(){
        try {
            mApiService = new ApiService();
            mUserMainPresenter = new UserMainPresenter(UserMainActivity2.this,mApiService);
            mUserMainPresenter.loadDriverInScope();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callUpdateTask(TblTask task,TblMember list,int status_id){
        try {
            if(list!= null){
                List<TblMember> tblMembers = new ArrayList<TblMember>();
                tblMembers.add(list);
                task.setMember(tblMembers);
            }
            task.setTask_status(status_id);
            editor.putInt(KEY_MEMBER_ID,0);
            editor.putString(KEY_TASK_ID,"");
            editor.putLong(KEY_TIME_COUNT,0);
            editor.putInt(KEY_TIME_WAIT,0);
            editor.putBoolean(KEY_DESTROY, false);
            editor.putBoolean(KEY_CANCEL, true);
            editor.commit();
            timeCountInMilliSeconds = 0;
            UserBackgroundService.time_count = 0;
            UserBackgroundService.cancel = true;
           // stopService();
            mApiService = new ApiService();
            mUserMainPresenter = new UserMainPresenter(UserMainActivity2.this,mApiService);
            mUserMainPresenter.updateMain(task,status_id);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateMarkerDriverInScope(List<TblMember> m){
        try {
            markers = new Marker[10000];
            for (int i = 0; i < m.size(); i++) {
                markers[i] = gMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_truck_marker))
                        .position(new LatLng(Double.parseDouble(String.valueOf(m.get(i).getLat())), Double.parseDouble(String.valueOf(m.get(i).getLon()))))
                        .title("คุณ" + " " + m.get(i).getFirst_name()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setDefault(){
        try {
            flagEndDate = false;
            clickableOnMap = true;
            edt_start.setEnabled(true);
            edt_des.setEnabled(true);
            linear_img_start.setEnabled(true);
            linear_img_des.setEnabled(true);
            edt_start.setText("");
            edt_des.setText("");
            edt_start_date.setText("");
            edt_start_time.setText("");
            edt_end_date.setText("");
            edt_end_time.setText("");
            edt_count_date.setText("");
            edt_iden.setText("");
            txt_type_car.setText("");
            linear_detail.setVisibility(View.GONE);
            btn_now.setTextColor(getResources().getColor(R.color.black_1));
            btn_booking.setTextColor(getResources().getColor(R.color.black_1));
            removeMarkerStart();
            removeMarkerDestination();
            linear_select_type.setVisibility(View.VISIBLE);
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
            long current = dateController.dateTimeFormat9Tolong(dateController.getSystemTimeFull(UserMainActivity2.this));
            timeCountInMilliSeconds = dateController.dateTimeFormat9Tolong(tblTask2.getDate_task_create()) + tblTask2.getTime_wait() * 60000;
            editor.putLong(KEY_TIME_END,timeCountInMilliSeconds);
            editor.putString(KEY_DATE_CREATE,tblTask2.getDate_task_create());
            if(timeCountInMilliSeconds > current){
                stopService();
                timeCountInMilliSeconds = timeCountInMilliSeconds - current;
                editor.putInt(KEY_MEMBER_ID,members.get(0).getMember_id());
                editor.putString(KEY_TASK_ID,tblTask2.getId());
                editor.putLong(KEY_TIME_COUNT,timeCountInMilliSeconds);
                editor.putInt(KEY_TIME_WAIT,tblTask2.getTime_wait());
                editor.putBoolean(KEY_DESTROY, false);
                editor.putBoolean(KEY_CANCEL,false);
                editor.commit();
                linear_main1.setVisibility(View.GONE);
                linear_main2.setVisibility(View.VISIBLE);
                startService();
                if(t.getMember()!=null&&t.getMember().size()>0){
                    setListTask(t.getMember(),Integer.parseInt(tblTask2.getId()));
                }else {
                    List<TblMember> list = new ArrayList<TblMember>();
                    setListTask(list,Integer.parseInt(tblTask2.getId()));
                }
                setRefreshList();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setListTask(List<TblMember> lists,int id){
        try {
            DriverWaitAcceptAdapter adapter = new DriverWaitAcceptAdapter(UserMainActivity2.this,lists,tblTask2,"Now");
            recycler_view.setAdapter(adapter);
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

    private void getTask(){
        try {
            tblTask.setId(members.get(0).getTask_id());
            tblTask.setMember(members);
            mApiService = new ApiService();
            mUserMainPresenter = new UserMainPresenter(UserMainActivity2.this,mApiService);
            mUserMainPresenter.loadCreateTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTimeOut(){
        try {
            if(sp.getBoolean(KEY_TIME_OUT,false)){
                editor.putBoolean(KEY_TIME_OUT,false);
                editor.commit();
                dialog.dialogNolmal(UserMainActivity2.this,"Warning","Time out...");
            }
            members = baseActivity.getMember();
            if(members.get(0).getStatus_id() == 33){
                getTask();
            }
//
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
        startService(new Intent(UserMainActivity2.this, UserBackgroundService.class));
    }

    public void stopService() {
        stopService(new Intent(UserMainActivity2.this, UserBackgroundService.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onResume","-- ON RESUME --");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onPause","-- ON PAUSE --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editor.putBoolean(KEY_DESTROY, true);
        editor.commit();
        Log.i("onDestroy","-- ON Destroy --");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent i = new Intent(UserMainActivity.this, LoginSecondActivity.class);
//            startActivity(i);
//            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
