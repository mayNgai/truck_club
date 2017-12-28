package com.dtc.sevice.truckclub.view.user.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.AddImageAdapter;
import com.dtc.sevice.truckclub.adapter.ItemOffsetDecoration;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.presenters.user.UserTaskPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
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
import com.squareup.picasso.Picasso;

/**
 * Created by May on 10/18/2017.
 */

public class UserTaskActivity extends AppCompatActivity implements View.OnClickListener,OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , com.google.android.gms.location.LocationListener{
    public static TblTask tblTask;
    private Toolbar toolbar;
    private ImageView img_profile;
    private TextView txt_name,txt_last,txt_step1,txt_step2,txt_step3;
    private LinearLayout linear_select_detail,linear_finish;
    private ImageButton img_step1,img_step2,img_step3;
    private Activity _activity;

    private GoogleApiClient client;
    private GoogleApiClient mGoogleApiClient;
    private Location locationUserRealTime;
    private SupportMapFragment dMapFragment2;
    private GoogleMap gMap;
    private Marker markerDriverRealTime;
    private static Marker markerStart,markerDes,markerDriver;
    private double longitude;
    private double latitude;
    private Marker[] markers = new Marker[10000];
    private DialogController dialogController;
    private ApiService apiService;
    private UserTaskPresenter userTaskPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_user);
        Intent extra = getIntent();
        tblTask = new TblTask();
        if (extra != null) {
            if(extra.hasExtra("select_task"))
                tblTask = (TblTask) extra.getSerializableExtra("select_task");
        }
        init();
        setStatusTitle();
        setInfo();
    }

    private void init(){
        try {
            ApplicationController.setAppActivity(UserTaskActivity.this);
            dialogController = new DialogController();
            _activity = UserTaskActivity.this;
            img_profile = (ImageView)findViewById(R.id.img_profile);
            txt_name = (TextView)findViewById(R.id.txt_name);
            txt_last = (TextView)findViewById(R.id.txt_last);
            linear_select_detail = (LinearLayout)findViewById(R.id.linear_select_detail);
            linear_finish = (LinearLayout)findViewById(R.id.linear_finish);
            img_step1 = (ImageButton)findViewById(R.id.img_step1);
            img_step2 = (ImageButton)findViewById(R.id.img_step2);
            img_step3 = (ImageButton)findViewById(R.id.img_step3);
            txt_step1 = (TextView)findViewById(R.id.txt_step1);
            txt_step2 = (TextView)findViewById(R.id.txt_step2);
            txt_step3 = (TextView)findViewById(R.id.txt_step3);
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

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            dMapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.UserMainActivity);
            dMapFragment2.getMapAsync(this);

            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
            linear_select_detail.setOnClickListener(this);
            linear_finish.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setInfo(){
        try {
            Picasso.with(_activity).load(GlobalVar.url_up_pic + tblTask.getMember().get(0).getName_pic_path())
                    .placeholder( R.drawable.progress_animation )
                    .fit().centerCrop().error( R.drawable.no_images ).into(img_profile);
            txt_name.setText(tblTask.getMember().get(0).getFirst_name());
            txt_last.setText(tblTask.getMember().get(0).getLast_name());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setStatusTitle(){
        try {
            if(tblTask.getTask_status() == 3){
                img_step1.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                img_step2.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                img_step3.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                txt_step1.setTextColor(getResources().getColor(R.color.LightGray));
                txt_step2.setTextColor(getResources().getColor(R.color.LightGray));
                txt_step3.setTextColor(getResources().getColor(R.color.LightGray));
            }else if(tblTask.getTask_status() == 4){
                img_step1.setBackgroundResource(R.drawable.button_cricle_orange);
                img_step2.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                img_step3.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                txt_step1.setTextColor(getResources().getColor(R.color.black_1));
                txt_step2.setTextColor(getResources().getColor(R.color.LightGray));
                txt_step3.setTextColor(getResources().getColor(R.color.LightGray));
            }else if(tblTask.getTask_status() == 5){
                img_step1.setBackgroundResource(R.drawable.button_cricle_orange);
                img_step2.setBackgroundResource(R.drawable.button_cricle_orange);
                img_step3.setBackgroundResource(R.drawable.button_cricle_orange_stock);
                txt_step1.setTextColor(getResources().getColor(R.color.LightGray));
                txt_step2.setTextColor(getResources().getColor(R.color.black_1));
                txt_step3.setTextColor(getResources().getColor(R.color.LightGray));
            }else if(tblTask.getTask_status() == 6){
                img_step1.setBackgroundResource(R.drawable.button_cricle_orange);
                img_step2.setBackgroundResource(R.drawable.button_cricle_orange);
                img_step3.setBackgroundResource(R.drawable.button_cricle_orange);
                txt_step1.setTextColor(getResources().getColor(R.color.LightGray));
                txt_step2.setTextColor(getResources().getColor(R.color.LightGray));
                txt_step3.setTextColor(getResources().getColor(R.color.black_1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
//        locationUserRealTime = location;
//        if (location == null) {
//            return;
//        }
//        longitude = location.getLatitude();
//        longitude= location.getLongitude();
//
//        LatLng coordinate = new LatLng(longitude, longitude);
//        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap = googleMap;
//        markerDriverRealTime = gMap.addMarker(new MarkerOptions().position(new LatLng(13.977207, 100.5833366)).title("Your Driver"));
//        markerDes = gMap.addMarker(new MarkerOptions().position(new LatLng(13.977207, 100.5833366)).title("Your Destination"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0,40, 0, 0);
        if(tblTask!=null){
            if(tblTask.getTask_status() > 3){
                LatLng latLng = new LatLng(tblTask.getMember().get(0).getLat(), tblTask.getMember().get(0).getLon());
                addMarkerDriver(latLng);
            }
            LatLng latLngStart = new LatLng(tblTask.getStart_lat(), tblTask.getStart_lon());
            LatLng latLngDes = new LatLng(tblTask.getDes_lat(), tblTask.getDes_lon());
            addMarkerStart(latLngStart);
            addMarkerDestination(latLngDes);
            moveMap(Double.parseDouble(String.valueOf(tblTask.getDes_lat())),Double.parseDouble(String.valueOf(tblTask.getDes_lon())));
        }


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
            //moveMap(latitude,longitude);

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

    private void moveMap(double lat ,double lon) {
        LatLng latLng = new LatLng(lat, lon);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(9));
        gMap.getUiSettings().setZoomControlsEnabled(true);


    }

    private void addMarkerStart(LatLng latlon){
        removeMarkerStart();
        markerStart = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_24dp)).title("Your Start Position"));

    }

    private void addMarkerDestination(LatLng latlon){
        removeMarkerDestination();
        markerDes = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)).title("Your Destination"));

    }

    private void addMarkerDriver(LatLng latlon){
        removeMarkerDriver();
        markerDriver = gMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_truck_marker)).title("Position Driver"));

    }

    private void removeMarkerStart(){
        if (markerStart != null)
            markerStart.remove();
    }

    private void removeMarkerDestination(){
        if (markerDes != null)
            markerDes.remove();
    }

    private void removeMarkerDriver(){
        if (markerDriver != null)
            markerDriver.remove();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_select_detail:
                setDialogBottom();
                break;
            case R.id.linear_finish:
                if(tblTask.getTask_status() == 5){
                    setDialogRating();
                }else {
                    dialogController.dialogNolmal(this,"Warning","คนขับยังไม่กดจบงาน");
                }

                break;
        }
    }

    private void setFinishTask(){
        try {
            tblTask.setTask_status(6);
            tblTask.setRating(count_rating);
            apiService = new ApiService();
            userTaskPresenter = new UserTaskPresenter(this,apiService);
            userTaskPresenter.sendFinish();

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

    private void setDialogBottom(){
        try {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_driver_detail, null);
            ImageView img_back = (ImageView)view.findViewById( R.id.img_back);
            ImageView img_profile = (ImageView)view.findViewById( R.id.img_profile);
            TextView txt_driver_name = (TextView)view.findViewById(R.id.txt_driver_name);
            TextView txt_driver_surname = (TextView)view.findViewById(R.id.txt_driver_surname);
            TextView txt_license = (TextView)view.findViewById(R.id.txt_license);
            TextView txt_province = (TextView)view.findViewById(R.id.txt_province);
            TextView txt_brand = (TextView)view.findViewById(R.id.txt_brand);
            TextView txt_model = (TextView)view.findViewById(R.id.txt_model);
            TextView txt_price = (TextView)view.findViewById(R.id.txt_price);
            Button btn_accept = (Button)view.findViewById(R.id.btn_accept);
            LinearLayout linear_head = (LinearLayout)view.findViewById(R.id.linear_head);
            RecyclerView recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
            final Dialog mBottomSheetDialog = new Dialog (this,R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (false);
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 4);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_offset));
            AddImageAdapter adapter = new AddImageAdapter(this,tblTask.getMember().get(0).getCar_detail().get(0).getPicture());
            recycler_view.setAdapter(adapter);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_driver_name.setText(tblTask.getMember().get(0).getFirst_name());
            txt_driver_surname.setText(tblTask.getMember().get(0).getLast_name());
            txt_license.setText(tblTask.getMember().get(0).getCar_detail().get(0).getLicense_plate());
            txt_province.setText(tblTask.getMember().get(0).getCar_detail().get(0).getProvince());
            txt_brand.setText(tblTask.getMember().get(0).getCar_detail().get(0).getCar_brand());
            txt_model.setText(tblTask.getMember().get(0).getCar_detail().get(0).getCar_model());
            txt_price.setText(tblTask.getPrice());
            linear_head.setVisibility(View.GONE);
            Picasso.with(this).load(GlobalVar.url_up_pic + tblTask.getMember().get(0).getName_pic_path())
                    .placeholder( R.drawable.progress_animation )
                    .fit().centerCrop().error( R.drawable.no_images ).into(img_profile);
            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
