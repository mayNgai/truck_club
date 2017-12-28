package com.dtc.sevice.truckclub.view.driver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.view.LoginFirstActivity;
import com.dtc.sevice.truckclub.view.driver.fragment.HomeFragment;
import com.dtc.sevice.truckclub.view.driver.fragment.MeFragment;
import com.dtc.sevice.truckclub.view.driver.fragment.ScheduleFragment;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class DriverMainActivity extends AppCompatActivity{
    private Fragment fragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        fragmentManager = getSupportFragmentManager();
        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        fragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_all_task:
                                fragment = HomeFragment.newInstance();
                                break;
                            case R.id.navigation_schedule:
                                fragment = ScheduleFragment.newInstance();
                                break;
                            case R.id.navigation_me:
                                fragment = MeFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, fragment);
                        transaction.commit();
                        return true;
                    }
                });

//Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, HomeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent i = new Intent(DriverMainActivity.this, LoginFirstActivity.class);
//            startActivity(i);
//            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
