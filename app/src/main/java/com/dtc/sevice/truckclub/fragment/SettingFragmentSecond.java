package com.dtc.sevice.truckclub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dtc.sevice.truckclub.R;

/**
 * Created by May on 10/31/2017.
 */

public class SettingFragmentSecond extends Fragment {
    private View rootView;
    private ImageView img_down;
    private RecyclerView recycler_view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottom_dialog_recycle, container, false);
        init();
        return rootView;
    }

    private void init(){
        img_down = (ImageView)rootView.findViewById(R.id.img_down);
        recycler_view = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        img_down.setVisibility(View.GONE);
    }
}
