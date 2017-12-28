package com.dtc.sevice.truckclub.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.view.BaseActivity;

import java.util.List;

/**
 * Created by May on 10/11/2017.
 */

public class TaskListWaitAdapter extends RecyclerView.Adapter<TaskListWaitAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblTask> arrayList;
    private DateController dateController;
    private BaseActivity mView;
    public TaskListWaitAdapter(Context context, List<TblTask> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        dateController = new DateController();
        mView = new BaseActivity();
    }

    @Override
    public void onBindViewHolder(final TaskListWaitAdapter.ViewHolder holder, final int i) {
        holder.txt_date_start.setText(dateController.convertDateFormat2To1(arrayList.get(i).getStart_date()));
        holder.txt_time_start.setText(arrayList.get(i).getStart_date().substring(10,16));
        holder.txt_driver_wait.setText(String.valueOf(arrayList.get(i).getMember().size()));
        holder.txt_start_position.setText(arrayList.get(i).getStart_location());
        holder.txt_des_position.setText(arrayList.get(i).getDest_location());
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogBottom(view,i);
            }
        });

    }

    private void setDialogBottom(final View v,final int pos){
        try {
            LayoutInflater inflater = (LayoutInflater)v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_detail_task_and_info_driver, null);
            ImageView img_back = (ImageView)view.findViewById( R.id.img_back);
            TextView txt_id = (TextView)view.findViewById(R.id.txt_id);
            TextView txt_date_start = (TextView)view.findViewById(R.id.txt_date_start);
            TextView txt_time_start = (TextView)view.findViewById(R.id.txt_time_start);
            TextView txt_date_end = (TextView)view.findViewById(R.id.txt_date_end);
            TextView txt_time_end = (TextView)view.findViewById(R.id.txt_time_end);
            TextView txt_start_position = (TextView)view.findViewById(R.id.txt_start_position);
            TextView txt_des_position = (TextView)view.findViewById(R.id.txt_des_position);
            TextView txt_count_date = (TextView)view.findViewById(R.id.txt_count_date);
            TextView txt_type_car = (TextView)view.findViewById(R.id.txt_type_car);
            TextView txt_count_driver = (TextView)view.findViewById(R.id.txt_count_driver);
            TextView txt_cancel = (TextView)view.findViewById(R.id.txt_cancel);
            RecyclerView recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
            final Dialog mBottomSheetDialog = new Dialog (v.getContext(),R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (false);
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mcontext);
            recycler_view.setLayoutManager(mLayoutManager);
            DriverWaitAcceptAdapter adapter = new DriverWaitAcceptAdapter(mcontext,arrayList.get(pos).getMember(),arrayList.get(pos),arrayList.get(pos).getService_type());
            recycler_view.setAdapter(adapter);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_id.setText(arrayList.get(pos).getId());
            txt_date_start.setText(dateController.convertDateFormat2To1(arrayList.get(pos).getStart_date()));
            txt_time_start.setText(arrayList.get(pos).getStart_date().substring(10,16));
            txt_date_end.setText(dateController.convertDateFormat2To1(arrayList.get(pos).getEnd_date()));
            txt_time_end.setText(arrayList.get(pos).getEnd_date().substring(10,16));
            txt_start_position.setText(arrayList.get(pos).getStart_location());
            txt_des_position.setText(arrayList.get(pos).getDest_location());
            txt_count_date.setText(String.valueOf(arrayList.get(pos).getDate_count()));
            txt_type_car.setText(arrayList.get(pos).getName_group());
            txt_count_driver.setText(String.valueOf(arrayList.get(pos).getMember().size()));
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });
            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                    alertDialogBuilder.setTitle("Warning");
                    alertDialogBuilder.setMessage("คุณต้องการยกเลิกงานใช่ไหม..");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    mView.callCancel(Integer.parseInt(arrayList.get(pos).getId()));
                                    mBottomSheetDialog.dismiss();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public TaskListWaitAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_wait_accept, vGroup, false);
        return new TaskListWaitAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_date_start,txt_time_start,txt_driver_wait,txt_start_position,txt_des_position;
        private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_date_start = (TextView) v.findViewById(R.id.txt_date_start);
            txt_time_start = (TextView) v.findViewById(R.id.txt_time_start);
            txt_driver_wait = (TextView) v.findViewById(R.id.txt_driver_wait);
            txt_start_position = (TextView) v.findViewById(R.id.txt_start_position);
            txt_des_position = (TextView) v.findViewById(R.id.txt_des_position);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
        }
    }
}
