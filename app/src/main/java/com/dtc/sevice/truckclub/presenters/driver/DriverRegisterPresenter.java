package com.dtc.sevice.truckclub.presenters.driver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblProvince;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverRegisterActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class DriverRegisterPresenter {
    private static ProgressDialog dialog;
    private ApiService mForum;
    private DialogController dialogController;
    private static DriverRegisterActivity mView;
    private TaskController taskController;
    private static Activity _activity;

    public DriverRegisterPresenter(DriverRegisterActivity view , ApiService forum){
        mView = view;
        mForum = forum;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

    public void loadRegisterAndCar(){
        try {
            _activity = ApplicationController.getAppActivity();
            if(!NetworkUtils.isConnected(_activity)){
                dialogController.dialogNolmal(_activity,"Warning","Internet is not stable.");
            }else {
                dialog = ProgressDialog.show(_activity, "Wait", "loading...");
                mForum.getApi()
                        .createMemberAndCar(mView.member)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblMember>() {
                            @Override
                            public void onCompleted() {
                                 dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadRegister Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblMember member) {
                                Log.i("loadRegisterAndCar", "OK");
                                updateSignUp(member);
                                dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void updateSignUp(TblMember member){
        try {
            _activity = ApplicationController.getAppActivity();
            if(member.getSuccess().equalsIgnoreCase("1")){
                taskController.createMember(member);
                //loadRegisterCar(member.getMember_id());
                Intent i = new Intent(_activity,LoginSecondActivity.class);
                i.putExtra("authen" , member.getAuthority());
                i.putExtra("member_type" , member.getMember_type());
                i.putExtra("face_book_id" , member.getFace_book_id());
                i.putExtra("user_name" , member.getUser_name());
                i.putExtra("password" , member.getPassword());
                _activity.startActivity(i);
                _activity.finish();
            }else if(member.getSuccess().equalsIgnoreCase("0")){
                dialogController.dialogNolmal(_activity,"Warning",member.getMessage());
                Log.i("message", member.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadProvince(){
        try {
            dialog = ProgressDialog.show(mView, "Wait", "loading...");
            mForum.getApi()
                    .getProvince()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<TblProvince>>() {
                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("getProvince Error", e.getMessage());
                            dialog.dismiss();
                        }

                        @Override
                        public void onNext(List<TblProvince> province) {
                            mView.addProvince(province);
                            dialog.dismiss();
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
