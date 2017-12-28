package com.dtc.sevice.truckclub.view.driver.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.RegisterDriverAdapter;
import com.dtc.sevice.truckclub.model.TblCarDetail;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblPicture;
import com.dtc.sevice.truckclub.model.TblProvince;
import com.dtc.sevice.truckclub.presenters.driver.DriverRegisterPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.dtc.sevice.truckclub.view.driver.fragment.RegisterDriverFragmentFirst;
import com.dtc.sevice.truckclub.view.driver.fragment.RegisterDriverFragmentSecond;
import com.dtc.sevice.truckclub.view.driver.fragment.RegisterDriverFragmentThird;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class DriverRegisterActivity extends FragmentActivity {
    static final Integer READ_EXST = 0x4;
    private static Activity _activity;
    private static ViewPager pager;
    private ApiService mApiService;
    private DriverRegisterPresenter mDriverRegisterPresenter;
    public List<TblProvince> provinces;
    private RegisterDriverFragmentFirst mFirst;
    private RegisterDriverFragmentSecond mSecond;
    private RegisterDriverFragmentThird mThird;
    private DialogController dialogController;
    private int limit = 3;
    public static TblMember member;
    public static List<TblCarDetail> carDetail;
    public static List<TblPicture> tblPicture;
    private Intent extra;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setIntentFromLogin();
        intit();
    }

    private void intit() {
        _activity = DriverRegisterActivity.this;
        ApplicationController.setAppActivity(_activity);
        mFirst = new RegisterDriverFragmentFirst();
        mSecond = new RegisterDriverFragmentSecond();
        mThird = new RegisterDriverFragmentThird();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RegisterDriverAdapter adapter = new RegisterDriverAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setOffscreenPageLimit(limit);
        pager.setAdapter(adapter);

        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        //getData();
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
    }

    private void setIntentFromLogin(){
        try {
            extra = getIntent();
            if (extra != null) {
                member = new TblMember();
                if(extra.getStringExtra("authen")!=null)
                    member.setAuthority(extra.getStringExtra("authen"));
                if(extra.getStringExtra("member_type")!=null)
                    member.setMember_type(Integer.parseInt(extra.getStringExtra("member_type")));
                if(extra.getStringExtra("id")!=null)
                    member.setFace_book_id(extra.getStringExtra("id"));
                if(extra.getStringExtra("first_name")!=null)
                    member.setFirst_name(extra.getStringExtra("first_name"));
                if(extra.getStringExtra("last_name")!=null)
                    member.setLast_name(extra.getStringExtra("last_name"));
                if(extra.getStringExtra("email")!=null)
                    member.setEmail(extra.getStringExtra("email"));
                if(extra.getStringExtra("gender")!=null)
                    member.setSex(extra.getStringExtra("gender"));
                if(extra.getStringExtra("birthday")!=null)
                    member.setBirth_date(extra.getStringExtra("birthday"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getData(){
        try {
            mApiService = new ApiService();
            mDriverRegisterPresenter = new DriverRegisterPresenter(this,mApiService);
            mDriverRegisterPresenter.loadProvince();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProvince(List<TblProvince> list){
        provinces = new ArrayList<TblProvince>();
        provinces.addAll(list);
        mSecond.addProvince(provinces);
    }

    public void setPre(int i) {
        pager.setCurrentItem(pager.getCurrentItem()- i, true);
    }


    private void backButton() {
        Intent i = new Intent(_activity, LoginSecondActivity.class);
        i.putExtra("authen",R.string.txtDriver);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //backButton();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void setComplete(){
        try {
            mFirst = new RegisterDriverFragmentFirst();
            mSecond = new RegisterDriverFragmentSecond();
            mThird = new RegisterDriverFragmentThird();
            dialogController = new DialogController();
            boolean success = false;
            boolean successAll = false;
            if(!mFirst.setCompletePageFirst(_activity)){
                setPre(2);
            }else {
                if(isDateBirthDay(mFirst.txt_date_of_birth.getText().toString().trim())){
                    success = true;
                    member = new TblMember();
                    member = mFirst.tblMember;
                }
            }

            if(success){
                success = false;
                if(!mSecond.setCompletePageSecond(_activity)){
                    setPre(1);
                }else {
                    if(!mSecond.flagSelectOption){
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
                        alertDialogBuilder.setTitle(_activity.getResources().getString(R.string.txtWarning));
                        alertDialogBuilder.setMessage(_activity.getResources().getString(R.string.error_invalid_type_car));
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        setPre(1);
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }else {
                        if(!mSecond.setOptionCompletePageSecond(_activity)){
                            setPre(1);
                        }else {
                            if(mSecond.positionOptionCar == 3){
                                if(!mSecond.flagSelectCarTow){
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
                                    alertDialogBuilder.setTitle(_activity.getResources().getString(R.string.txtWarning));
                                    alertDialogBuilder.setMessage(_activity.getResources().getString(R.string.error_invalid_total_tow));
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    setPre(1);
                                                }
                                            });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }else {
                                    success = true;
//                                    carDetail = new ArrayList<>();
//                                    carDetail.add(mSecond.carDetail);

                                }
                            }else {
                                success = true;
//                                carDetail = new ArrayList<>();
//                                carDetail.add(mSecond.carDetail);
                            }

                        }

                    }
                }

            }

            if(success){
                if(!mThird.setCompleteThird(_activity)){
                    success = false;
                    successAll = false;
                }else{
                    success = true;
                    successAll = true;
                    carDetail = new ArrayList<>();
                    tblPicture = new ArrayList<TblPicture>();
                    tblPicture = mThird.imagePaths;
                    mSecond.carDetail.setPicture(tblPicture);
                    carDetail.add(mSecond.carDetail);
                    member.setCar_detail(carDetail);
                    mApiService = new ApiService();
                    mDriverRegisterPresenter = new DriverRegisterPresenter(this,mApiService);
                    mDriverRegisterPresenter.loadRegisterAndCar();
                }
            }

//            if(successAll){
//                carDetail = new ArrayList<>();
//                tblPicture = new ArrayList<TblPicture>();
//                tblPicture = mThird.imagePaths;
//                mSecond.carDetail.setPicture(tblPicture);
//                carDetail.add(mSecond.carDetail);
//                member.setCar_detail(carDetail);
//                mApiService = new ApiService();
//                mDriverRegisterPresenter = new DriverRegisterPresenter(this,mApiService);
//                mDriverRegisterPresenter.loadRegisterAndCar();
//
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isDateBirthDay(String date){
        boolean success = true;
        if(date.length()==0) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
            alertDialogBuilder.setTitle(_activity.getResources().getString(R.string.txtWarning));
            alertDialogBuilder.setMessage(_activity.getResources().getString(R.string.error_invalid_birth_date));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            mFirst.txt_date_of_birth.setTextColor(Color.RED);
                            setPre(2);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            success = false;
        }
        return success;
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(DriverRegisterActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DriverRegisterActivity.this, permission)) {
                ActivityCompat.requestPermissions(DriverRegisterActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(DriverRegisterActivity.this, new String[]{permission}, requestCode);
            }
        } else {
//            Toast.makeText(getActivity(), "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


}
