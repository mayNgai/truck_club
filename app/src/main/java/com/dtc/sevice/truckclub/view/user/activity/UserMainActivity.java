package com.dtc.sevice.truckclub.view.user.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.presenters.user.UserMainPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.BaseActivity;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class UserMainActivity extends BaseActivity implements View.OnClickListener,OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , com.google.android.gms.location.LocationListener{
    private Button btn_now,btn_booking;
    private EditText edt_start,edt_des,edt_iden;
    private CheckBox chk_cash,chk_credit;
    private ImageView img_start,img_des,img_del_des,img_del_iden;
    private LinearLayout linear_detail;
    //Map
    private GoogleApiClient client;
    private GoogleApiClient mGoogleApiClient;
    private Location locationUserRealTime;
    private SupportMapFragment dMapFragment2;
    private GoogleMap gMap;
    private Marker markerDriverRealTime;
    private Marker markerDes;
    private double longitude;
    private double latitude;
    private Marker[] markers = new Marker[10000];
    private int subLastText = 25;

    private TaskController taskController;
    public List<TblMember> members;
    private Activity _activity;
    private ApiService mApiService;
    private UserMainPresenter mUserMainPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        init();
    }

    private void init(){
        taskController = new TaskController();
        members = new ArrayList<TblMember>();
        _activity = new UserMainActivity();
        linear_detail = (LinearLayout)findViewById(R.id.linear_detail);
        btn_now = (Button)findViewById(R.id.btn_now);
        btn_booking = (Button)findViewById(R.id.btn_booking);
        edt_start = (EditText)findViewById(R.id.edt_start);
        edt_des = (EditText)findViewById(R.id.edt_des);
        edt_iden = (EditText)findViewById(R.id.edt_iden);
        chk_cash = (CheckBox)findViewById(R.id.chk_cash);
        chk_credit = (CheckBox)findViewById(R.id.chk_credit);
        img_start = (ImageView)findViewById(R.id.img_start);
        img_des = (ImageView)findViewById(R.id.img_des);
        img_del_des = (ImageView)findViewById(R.id.img_del_des);
        img_del_iden = (ImageView)findViewById(R.id.img_del_iden);

        members = taskController.getMember();
        img_start.setOnClickListener(this);
        img_des.setOnClickListener(this);
        img_del_des.setOnClickListener(this);
        btn_now.setOnClickListener(this);
        btn_booking.setOnClickListener(this);
        img_del_iden.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        dMapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.UserMainActivity);
        dMapFragment2.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setChangeTextDestination();
        setChangeTextIden();
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
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));

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
        rlp.setMargins(0, 1100, 0, 0);

        getDestinationByFinger();

    }

    //Getting current location
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            //moving the map to location
            moveMap(latitude ,longitude);
            String strAddress = getAddressByLatLng(latitude,longitude);
            edt_start.setText((strAddress.length()>25) ? strAddress.substring(0,25) + ".." : strAddress);
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
                public void onMapClick(LatLng latlon) {
                    try {
                        if(latlon != null) {
                            addMarkerDestination(latlon);
                            moveMap(latlon.latitude,latlon.longitude);
                            String strAddress = getAddressByLatLng(latlon.latitude,latlon.longitude);
                            edt_des.setText((strAddress.length()>40) ? strAddress.substring(0,40) + ".." : strAddress);
                            linear_detail.setVisibility(View.VISIBLE);
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
    private void getDestinationBySearch() {
        try {
            starDistination = edt_des.getText().toString().trim();
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
                    addMarkerDestination(latLng);
                    moveMap(address.getLatitude(),address.getLongitude());
                    String strAddress = getAddressByLatLng(address.getLatitude(),address.getLongitude());
                    edt_des.setText((strAddress.length()>40) ? strAddress.substring(0,40) + ".." : strAddress);
                    linear_detail.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMarkerDestination(LatLng latlon){
        removeMarkerDestination();

        markerDes = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)).title("Your Destination"));

    }

    private void moveMap(double lat ,double lon) {
        LatLng latLng = new LatLng(lat, lon);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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
            case R.id.img_start:
                getCurrentLocation();
                break;
            case R.id.img_des:
                getDestinationBySearch();
                break;
            case R.id.img_del_des:
                edt_des.setText("");
                removeMarkerDestination();
                linear_detail.setVisibility(View.GONE);
                break;

            case R.id.img_del_iden:
                edt_iden.setText("");
                break;
            case R.id.btn_now:

                break;
            case R.id.btn_booking:

                break;

        }
    }

    private void setChangeTextDestination(){
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
                    img_del_des.setVisibility(View.VISIBLE);
                } else {
                    img_del_des.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setChangeTextIden(){
        ((EditText) findViewById(R.id.edt_iden)).addTextChangedListener(new TextWatcher() {
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
                    img_del_iden.setVisibility(View.VISIBLE);
                } else {
                    img_del_iden.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void callDriverInScope(){
        try {
//            mApiService = new ApiService();
//            mUserMainPresenter = new UserMainPresenter(UserMainActivity.this,mApiService);
//            mUserMainPresenter.loadDriverInScope();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateMarkerDriverInScope(List<TblMember> m){
        try {
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
