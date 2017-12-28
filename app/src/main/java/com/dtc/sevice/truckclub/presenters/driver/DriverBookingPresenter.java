package com.dtc.sevice.truckclub.presenters.driver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.driver.activity.DriverBookingActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 10/10/2017.
 */

public class DriverBookingPresenter {
    private static ProgressDialog dialog;
    private ApiService mForum;
    private DriverBookingActivity mView;
    private DialogController dialogController;
    private Activity activity;
    private TaskController taskController;

    public DriverBookingPresenter(DriverBookingActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        taskController = new TaskController();
    }
    public void loadSchedules(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,"Warning","Internet is not stable.");
            }else {
                mForum.getApi()
                        .getSchedulesDriver(mView.members.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblTask>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadSchedules Error", e.getMessage());
                            }

                            @Override
                            public void onNext(List<TblTask> tblTasks) {
                                mView.setListSchedulesTasks(tblTasks);
                            }
                        });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadTask(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,"Warning","Internet is not stable.");
            }else {
                mForum.getApi()
                        .getTask(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblTask>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadTask Error", e.getMessage());
                            }

                            @Override
                            public void onNext(List<TblTask> tblTasks) {
                                mView.setListTask(tblTasks);
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sentOfferPrice(TblTask tblTask){
        try {
                mForum.getApi()
                        .sentOfferPrice(tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadDataMember Error", e.getMessage());
                            }

                            @Override
                            public void onNext(TblTask tblTasks) {
                                mView.updateSentOfferPrice();
                            }
                        });
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sentUpdateDriver(TblTask tblTask){
        try {
//            if(!NetworkUtils.isConnected(mView)){
//                dialogController.dialogNolmal(mView,"Wanning","Internet is not stable.");
//            }else {
            // dialog = ProgressDialog.show(mView, "Wait", "loading...");
            mForum.getApi()
                    .sentUpdateDriver(tblTask)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TblTask>() {
                        @Override
                        public void onCompleted() {
                            //dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadDataMember Error", e.getMessage());
                            // dialog.dismiss();
                        }

                        @Override
                        public void onNext(TblTask tblTasks) {
                            taskController.createMember(tblTasks.getMember().get(0));
                            mView.updateGoing();
                            activity = ApplicationController.getAppActivity();
                            Intent i = new Intent(activity , DriverMainActivity2.class);
                            activity.startActivity(i);
                            activity.finish();
                            //  dialog.dismiss();
                        }
                    });
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
