package com.dtc.sevice.truckclub.view.driver.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtc.sevice.truckclub.R;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class MeFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public static MeFragment newInstance(){
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        return rootView;
    }
}
