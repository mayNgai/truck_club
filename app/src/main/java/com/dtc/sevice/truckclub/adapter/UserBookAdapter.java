package com.dtc.sevice.truckclub.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dtc.sevice.truckclub.view.user.fragment.UserBookFragmentFirst;
import com.dtc.sevice.truckclub.view.user.fragment.UserBookFragmentSecond;


/**
 * Created by May on 10/3/2017.
 */

public class UserBookAdapter extends FragmentPagerAdapter {
    private final int PAGE_NUM = 2;
    public UserBookAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new UserBookFragmentFirst();
        if(position == 1)
            return new UserBookFragmentSecond();
        return null;
    }
}
