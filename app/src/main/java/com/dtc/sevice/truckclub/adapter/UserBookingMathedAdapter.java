package com.dtc.sevice.truckclub.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.view.user.activity.UserTaskActivity;

import java.util.List;

/**
 * Created by May on 10/18/2017.
 */

public class UserBookingMathedAdapter extends RecyclerView.Adapter<UserBookingMathedAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblTask> arrayList;
    private DateController dateController;
    private DialogController dialogController;
    public UserBookingMathedAdapter(Context context, List<TblTask> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        dateController = new DateController();
        dialogController = new DialogController();
    }
    @Override
    public void onBindViewHolder(final UserBookingMathedAdapter.ViewHolder holder, final int i) {
        holder.linear_list_count.setVisibility(View.GONE);
        holder.linear_title.setVisibility(View.GONE);
        holder.txt_id.setText(arrayList.get(i).getId());
        holder.txt_date_start.setText(dateController.convertDateFormat2To1(arrayList.get(i).getStart_date()));
        holder.txt_time_start.setText(arrayList.get(i).getStart_date().substring(10,16));
        holder.txt_date_end.setText(dateController.convertDateFormat2To1(arrayList.get(i).getEnd_date()));
        holder.txt_time_end.setText(arrayList.get(i).getEnd_date().substring(10,16));
        holder.txt_start_position.setText(arrayList.get(i).getStart_location());
        holder.txt_des_position.setText(arrayList.get(i).getDest_location());
        holder.txt_count_date.setText(String.valueOf(arrayList.get(i).getDate_count()));
        holder.txt_type_car.setText(arrayList.get(i).getName_group());
        holder.txt_status.setText(GlobalVar.checkStatusTask(arrayList.get(i).getTask_status()));
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, UserTaskActivity.class);
                intent.putExtra("select_task",arrayList.get(i));
                mcontext.startActivity(intent);
            }
        });

    }


    @Override
    public UserBookingMathedAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.dialog_detail_task_and_info_driver, vGroup, false);
        return new UserBookingMathedAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_id,txt_date_start,txt_time_start,txt_date_end,txt_time_end,txt_start_position,txt_des_position,txt_count_date,txt_type_car,txt_status;
        private LinearLayout linear_list,linear_list_count,linear_title;

        public ViewHolder(View v) {
            super(v);
            txt_id = (TextView) v.findViewById(R.id.txt_id);
            txt_date_start = (TextView) v.findViewById(R.id.txt_date_start);
            txt_time_start = (TextView) v.findViewById(R.id.txt_time_start);
            txt_date_end = (TextView) v.findViewById(R.id.txt_date_end);
            txt_time_end = (TextView) v.findViewById(R.id.txt_time_end);
            txt_start_position = (TextView) v.findViewById(R.id.txt_start_position);
            txt_des_position = (TextView) v.findViewById(R.id.txt_des_position);
            txt_count_date = (TextView) v.findViewById(R.id.txt_count_date);
            txt_type_car = (TextView) v.findViewById(R.id.txt_type_car);
            txt_status = (TextView) v.findViewById(R.id.txt_status);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
            linear_list_count = (LinearLayout) v.findViewById(R.id.linear_list_count);
            linear_title = (LinearLayout) v.findViewById(R.id.linear_title);
        }
    }

}
