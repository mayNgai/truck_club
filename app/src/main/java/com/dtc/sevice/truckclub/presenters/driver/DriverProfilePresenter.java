package com.dtc.sevice.truckclub.presenters.driver;

import android.app.ProgressDialog;
import android.util.Log;

import com.dtc.sevice.truckclub.model.TblCarDetail;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.driver.activity.DriverProfileActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 10/9/2017.
 */

public class DriverProfilePresenter {
    private DriverProfileActivity mView;
    private ApiService mForum;
    private DialogController dialogController;
    private ProgressDialog dialog;
    private TaskController taskController;
    public DriverProfilePresenter(DriverProfileActivity view , ApiService forum){
        mView = view;
        mForum = forum;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

    public void loadCarDetail(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,"Warning","Internet is not stable.");
            }else {
                mForum.getApi()
                        .getCarDetail(mView.listMembers.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblCarDetail>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadCarDetail Error", e.getMessage());
                            }

                            @Override
                            public void onNext(TblCarDetail carDetail) {
                                mView.updateCarDetail(carDetail);
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProfile(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,"Warning","Internet is not stable.");
            }else {
                dialog = ProgressDialog.show(mView, "Wait", "loading...");
                mForum.getApi()
                        .updateProfile(mView.listMembers.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblMember>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("updateProfile Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblMember member) {
                                updateProfile(member);
                                dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProfile(TblMember member){
        try {
            if(member.getSuccess().equalsIgnoreCase("1")){
                member.setGuid(mView.listMembers.get(0).getGuid());
                taskController.updateMember(member);
                mView.setDefault();
            }else if(member.getSuccess().equalsIgnoreCase("0")){
                dialogController.dialogNolmal(mView,"Wanning",member.getMessage());
                Log.i("message", member.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
