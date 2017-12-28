package com.dtc.sevice.truckclub.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.presenters.LoginFirstPresenter;
import com.dtc.sevice.truckclub.service.ApiService;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class LoginFirstActivity extends AppCompatActivity implements View.OnClickListener{
    private Button UserButton,DriverButton;
    private ApiService mApiService;
    private LoginFirstPresenter mLoginFirstPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_first);
        init();
        mApiService = new ApiService();
        mLoginFirstPresenter = new LoginFirstPresenter(LoginFirstActivity.this,mApiService);
        mLoginFirstPresenter.loadCarGroup();
    }

    private void init() {
        UserButton = (Button)findViewById(R.id.UserButton);
        DriverButton = (Button)findViewById(R.id.DriverButton);
        UserButton.setOnClickListener(this);
        DriverButton.setOnClickListener(this);
        if (GlobalVar.subFullPermission(LoginFirstActivity.this, LoginFirstActivity.this)) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UserButton:
                Intent i = new Intent(LoginFirstActivity.this, LoginSecondActivity.class);
                i.putExtra("authen","User");
                startActivity(i);
                finish();
                break;
            case R.id.DriverButton:
                Intent j = new Intent(LoginFirstActivity.this, LoginSecondActivity.class);
                j.putExtra("authen","Driver");
                startActivity(j);
                finish();
                break;
        }


    }


}
