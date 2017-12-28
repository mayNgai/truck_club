package com.dtc.sevice.truckclub.view.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.UserBookAdapter;
import com.dtc.sevice.truckclub.view.user.fragment.UserBookFragmentFirst;
import com.dtc.sevice.truckclub.view.user.fragment.UserBookFragmentSecond;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by May on 10/3/2017.
 */

public class UserBookActivity extends FragmentActivity {
    static final Integer READ_EXST = 0x4;
    private static Activity _activity;
    private static ViewPager pager;
    private int limit = 2;
    private UserBookFragmentFirst fragmentFirst;
    private UserBookFragmentSecond fragmentSecond;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        init();
    }

    private void init(){
        _activity = UserBookActivity.this;
        fragmentFirst = new UserBookFragmentFirst();
        fragmentSecond = new UserBookFragmentSecond();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        UserBookAdapter adapter = new UserBookAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setOffscreenPageLimit(limit);
        pager.setAdapter(adapter);
        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
    }
    public void setPre(int i) {
        pager.setCurrentItem(pager.getCurrentItem()- i, true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //backButton();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
