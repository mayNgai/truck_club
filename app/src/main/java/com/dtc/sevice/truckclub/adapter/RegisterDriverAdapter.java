package com.dtc.sevice.truckclub.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dtc.sevice.truckclub.view.driver.fragment.RegisterDriverFragmentFirst;
import com.dtc.sevice.truckclub.view.driver.fragment.RegisterDriverFragmentSecond;
import com.dtc.sevice.truckclub.view.driver.fragment.RegisterDriverFragmentThird;

/**
 * Created by May on 9/21/2017.
 */

public class RegisterDriverAdapter extends FragmentPagerAdapter {
    private final int PAGE_NUM = 3;

    public RegisterDriverAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new RegisterDriverFragmentFirst();
        if(position == 1)
            return new RegisterDriverFragmentSecond();
        if(position == 2)
            return new RegisterDriverFragmentThird();
        return null;
    }
}
