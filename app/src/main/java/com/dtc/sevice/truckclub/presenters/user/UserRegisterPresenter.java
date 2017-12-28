package com.dtc.sevice.truckclub.presenters.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;
import com.dtc.sevice.truckclub.view.user.activity.UserRegisterActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class UserRegisterPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private DialogController dialogController;
    private UserRegisterActivity mView;
    private TaskController taskController;

    public UserRegisterPresenter(UserRegisterActivity view ,ApiService forum){
        mForum = forum;
        mView =view;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

//    public void loadRegister(){
//        try {
//            if(!NetworkUtils.isConnected(mView)){
//                dialogController.dialogNolmal(mView,"Wanning","Internet is not stable.");
//            }else {
//                dialog = ProgressDialog.show(mView, "Wait", "loading...");
//                mForum.getApi()
//                        .createMember(mView.member.getFirst_name(),mView.member.getLast_name(),mView.member.getEmail(),
//                                mView.member.getUser_name(),mView.member.getTel(),mView.member.getBirth_date(),mView.member.getSex(),mView.member.getPassword()
//                                ,mView.member.getMember_type(),mView.member.getAuthority(),mView.member.getDevice_id(),mView.member.getFace_book_id(),mView.member.getName_pic_path())
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<TblMember>() {
//                            @Override
//                            public void onCompleted() {
//                                dialog.dismiss();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e("userRegisterCall Error", e.getMessage());
//                                dialog.dismiss();
//                            }
//
//                            @Override
//                            public void onNext(TblMember member) {
//                                updateSignUp(member);
//                                dialog.dismiss();
//                            }
//                        });
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    public void loadRegister(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,"Warning","Internet is not stable.");
            }else {
                dialog = ProgressDialog.show(mView, "Wait", "loading...");
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
                                Log.e("userRegisterCall Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblMember member) {
                                updateSignUp(member);
                                dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateSignUp(TblMember members){
        try {
            if(members.getSuccess().equalsIgnoreCase("1")){
                taskController.createMember(members);
                Intent i = new Intent(mView, LoginSecondActivity.class);
                i.putExtra("authen" , members.getAuthority());
                i.putExtra("member_type" , members.getMember_type());
                i.putExtra("face_book_id" , members.getFace_book_id());
                i.putExtra("user_name" , members.getUser_name());
                i.putExtra("password" , members.getPassword());
                mView.startActivity(i);
                mView.finish();
                Log.i("message", members.getMessage());
            }else if(members.getSuccess().equalsIgnoreCase("0")){
                dialogController.dialogNolmal(mView,"Wanning",members.getMessage());
                Log.i("message", members.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
