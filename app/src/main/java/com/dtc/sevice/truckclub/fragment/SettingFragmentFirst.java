package com.dtc.sevice.truckclub.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.SettingAdapter;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblSetting;
import com.dtc.sevice.truckclub.presenters.SettingPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.dtc.sevice.truckclub.view.SettingActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverBookingActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;
import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by May on 10/31/2017.
 */

public class SettingFragmentFirst extends Fragment {
    private View rootView;
    private static RecyclerView recycler_view;
    private Toolbar toolbar;
    private static SettingActivity mView;
    private List<TblSetting> settingArray;
    private SettingAdapter adapter;
    private TaskController taskController;
    private static List<TblMember> members;
    private ApiService mApiService;
    private SettingPresenter mSettingPresenter;
    private Activity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting_first, container, false);
        init();
        return rootView;
    }

    private void init(){
        mView = new SettingActivity();
        taskController = new TaskController();
        recycler_view = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.setting);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mView.authen.equalsIgnoreCase("Driver")){
                    Intent i = new Intent(getActivity(), DriverMainActivity2.class);
                    getActivity().startActivity(i);
                    getActivity().finish();
                }else if(mView.authen.equalsIgnoreCase("User")){
                    Intent i = new Intent(getActivity(), UserMainActivity2.class);
                    getActivity().startActivity(i);
                    getActivity().finish();
                }

            }
        });

        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);
        getMember();
        setDataSetting();
    }

    private void getMember(){
        try {
            members = mView.members;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDataSetting(){
        try {
            activity = ApplicationController.getAppActivity();
            settingArray = new ArrayList<TblSetting>();
            TblSetting tblSetting = new TblSetting();
            tblSetting.setName(activity.getResources().getString(R.string.txt_language));
            tblSetting.setValue1(members.get(0).getLanguage());
            tblSetting.setValue2("");
            settingArray.add(tblSetting);
            if(mView.authen.equalsIgnoreCase("User")){
                TblSetting tblSetting2 = new TblSetting();
                tblSetting2.setName(activity.getResources().getString(R.string.waitAccept));
                tblSetting2.setValue1(String.valueOf(members.get(0).getTime_wait()));
                tblSetting2.setValue2("นาที");
                settingArray.add(tblSetting2);
            }
            setAdapter();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(){
        try {
            adapter = new SettingAdapter(getContext(),settingArray);
            recycler_view.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDialog(View v,TblSetting tblSetting){
        try {
            LayoutInflater inflater = (LayoutInflater)v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_setting_wait_time, null);
            ImageView img_close = (ImageView)view.findViewById( R.id.img_close);
            final LinearLayout linear_edit = (LinearLayout)view.findViewById( R.id.linear_edit);
            final LinearLayout linear_save = (LinearLayout)view.findViewById( R.id.linear_save);
            TextView txt_head = (TextView)view.findViewById(R.id.txt_head);
            final EditText edt_time_wait = (EditText)view.findViewById( R.id.edt_time_wait);
            final Dialog mBottomSheetDialog = new Dialog(v.getContext(),R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_head.setText(tblSetting.getName());
            edt_time_wait.setText(tblSetting.getValue1());
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();
                }
            });
            linear_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linear_edit.setVisibility(View.GONE);
                    linear_save.setVisibility(View.VISIBLE);
                    edt_time_wait.setEnabled(true);
                    int pos = edt_time_wait.getText().length();
                    edt_time_wait.setSelection(pos);
                }
            });
            linear_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linear_edit.setVisibility(View.VISIBLE);
                    linear_save.setVisibility(View.GONE);
                    edt_time_wait.setEnabled(false);
                    members.get(0).setTime_wait(Integer.parseInt(edt_time_wait.getText().toString()));
                    mView.members.get(0).setTime_wait(Integer.parseInt(edt_time_wait.getText().toString()));
                    mView.callService();
                    mBottomSheetDialog.dismiss();
                    setDataSetting();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
