package com.dtc.sevice.truckclub.presenters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.SettingActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 11/2/2017.
 */

public class SettingPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private SettingActivity mView;
    private DialogController dialogController;
    private TaskController taskController;
    private Activity activity;

    public SettingPresenter(SettingActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

    public void updateSetting(){
        try {
            activity = ApplicationController.getAppActivity();
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogNolmal(activity,"Warning","Internet is not stable.");
            }else {
                dialog = ProgressDialog.show(activity, "Wait", "loading...");
                mForum.getApi()
                        .updateSetting(mView.members.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblMember>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("updateSetting Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblMember member) {
                                updateSetting(member);
                                dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateSetting(TblMember member){
        try {
            if(member.getSuccess().equalsIgnoreCase("1")){
//                member.setGuid(mView.listMembers.get(0).getGuid());
                taskController.updateMember(member);
//                mView.setDefault();
            }else if(member.getSuccess().equalsIgnoreCase("0")){
                dialogController.dialogNolmal(mView,"Wanning",member.getMessage());
                Log.i("message", member.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
