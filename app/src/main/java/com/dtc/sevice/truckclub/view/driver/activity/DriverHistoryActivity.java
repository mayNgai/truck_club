package com.dtc.sevice.truckclub.view.driver.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.dtc.sevice.truckclub.R;

import java.util.ArrayList;

/**
 * Created by May on 10/9/2017.
 */

public class DriverHistoryActivity extends AppCompatActivity{
    private RadioButton rdoNow, rdoBook;
    private Spinner spinner;
    private EditText edtStart, edtEnd;
    private ImageButton imgStart, imgEnd;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initAll();
    }

    private void initAll() {
        rdoNow = (RadioButton) findViewById(R.id.rdoNow);
        rdoBook = (RadioButton) findViewById(R.id.rdoBook);
        spinner = (Spinner) findViewById(R.id.spinner);
        edtStart = (EditText) findViewById(R.id.edtStart);
        edtEnd = (EditText) findViewById(R.id.edtEnd);
        imgStart = (ImageButton) findViewById(R.id.imgStart);
        imgEnd = (ImageButton) findViewById(R.id.imgEnd);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
