package com.dtc.sevice.truckclub.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dtc.sevice.truckclub.fragment.SettingFragmentFirst;

/**
 * Created by May on 10/31/2017.
 */

public class SettingFragmentAdapter extends FragmentPagerAdapter {
    private final int PAGE_NUM = 1;

    public SettingFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new SettingFragmentFirst();
        return null;
    }
}
