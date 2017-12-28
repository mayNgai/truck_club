package com.dtc.sevice.truckclub.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.SettingFragmentAdapter;
import com.dtc.sevice.truckclub.fragment.SettingFragmentFirst;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.presenters.SettingPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.TaskController;

import java.util.List;

/**
 * Created by May on 10/31/2017.
 */

public class SettingActivity extends FragmentActivity {
    private SettingFragmentFirst fragmentFirst;
    private static ViewPager pager;
    private int limit = 1;
    private Intent extra;
    public static String authen = "";
    private TaskController taskController;
    public static List<TblMember> members;
    private static ApiService mApiService;
    private static SettingPresenter mSettingPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        intit();
    }

    private void intit() {
        extra = getIntent();
        if (extra != null) {
            if(extra.getStringExtra("authen")!=null)
                authen = extra.getStringExtra("authen");
        }
        ApplicationController.setAppActivity(SettingActivity.this);
        taskController = new TaskController();
        fragmentFirst = new SettingFragmentFirst();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getMember();
        SettingFragmentAdapter adapter = new SettingFragmentAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setOffscreenPageLimit(limit);
        pager.setAdapter(adapter);
    }

    private void getMember(){
        try {
            members = taskController.getMember();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callService(){
        try {
            mApiService = new ApiService();
            mSettingPresenter = new SettingPresenter(SettingActivity.this,mApiService);
            mSettingPresenter.updateSetting();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
