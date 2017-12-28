package com.dtc.sevice.truckclub.presenters.driver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by may on 10/4/2017.
 */

public class DriverMainPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private DriverMainActivity2 mView;
    private DialogController dialogController;
    private static int task_status = 0;
    private TaskController taskController;
    private Activity activity;

    public DriverMainPresenter(DriverMainActivity2 view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

    public void loadSchedulesDriver(){
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
                                Log.e("loadSchedulesDriver Error", e.getMessage());
                            }

                            @Override
                            public void onNext(List<TblTask> tblTasks) {
                                mView.setUpdateListSchedulesTask(tblTasks);
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
                                Log.e("loagTask Error", e.getMessage());
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

    public void sentUpdateDriver(final TblTask tblTask){
        try {
//            if(!NetworkUtils.isConnected(mView)){
//                dialogController.dialogNolmal(mView,"Wanning","Internet is not stable.");
//            }else {
            dialog = ProgressDialog.show(mView, "Wait", "loading...");
            task_status = tblTask.getTask_status();
            mForum.getApi()
                    .sentUpdateDriver(tblTask)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TblTask>() {
                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadDataMember Error", e.getMessage());
                            dialog.dismiss();
                        }

                        @Override
                        public void onNext(TblTask tblTasks) {
                            dialog.dismiss();
                            if(task_status == 6){
                                taskController.createMember(tblTasks.getMember().get(0));
                                List<TblMember> tblMembers = new ArrayList<TblMember>();
                                tblMembers = taskController.getMember();
                                activity = ApplicationController.getAppActivity();
                                Intent intent = new Intent(activity,DriverMainActivity2.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }else {
                                mView.setTitle();
                            }

                        }
                    });
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sentUpdateLatlon(){
        try {
            mForum.getApi()
                    .sentUpdateLatLon(mView.members.get(0))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TblMember>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadDataMember Error", e.getMessage());
                        }

                        @Override
                        public void onNext(TblMember member) {


                        }
                    });
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadTaskByID(){
        try {
//            if(!NetworkUtils.isConnected(mView)){
//                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
//            }else {
            TblTask tblTask = new TblTask();
            tblTask.setId(mView.members.get(0).getTask_id());
            tblTask.setMember(mView.members);
                mForum.getApi()
                        .getTaskByID(tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadTaskByID Error", e.getMessage());
                            }

                            @Override
                            public void onNext(TblTask tblTasks) {
                                //setDialogBottom(tblTasks);
                                mView.tblTaskBooking = tblTasks;
                                mView.setTitle();
                                Log.i("loadTaskByID","Ok");

                            }
                        });
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadDataMember(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                //dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .getDataMember(mView.listMembers.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblMember>() {
                            @Override
                            public void onCompleted() {
                                // dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadDataMember Error", e.getMessage());
                                //dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblMember member) {
                                taskController.createMember(member);
                                mView.setTitle();
                                //dialog.dismiss();
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
                            if(tblTasks.getService_type().equalsIgnoreCase("Now")){
                                mView.setCountDownTime(tblTasks);
                            }
                           // mView.updateSentOfferPrice();
                        }
                    });
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
