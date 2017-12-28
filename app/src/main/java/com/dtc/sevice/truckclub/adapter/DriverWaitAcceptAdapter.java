package com.dtc.sevice.truckclub.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.view.BaseActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by May on 10/18/2017.
 */

public class DriverWaitAcceptAdapter extends RecyclerView.Adapter<DriverWaitAcceptAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblMember> arrayList;
    private DateController dateController;
    private BaseActivity mView;
    private TblTask tblTask;
    private String service_type;
    public DriverWaitAcceptAdapter(Context context, List<TblMember> _arrayList ,TblTask _tblTask,String _service_type){
        this.arrayList = _arrayList;
        this.mcontext = context;
        this.service_type = _service_type;
        this.tblTask = _tblTask;
        dateController = new DateController();
        mView = new BaseActivity();
    }

    @Override
    public void onBindViewHolder(final DriverWaitAcceptAdapter.ViewHolder holder, final int i) {
        holder.txt_driver_name.setText(arrayList.get(i).getFirst_name());
        holder.txt_driver_surname.setText(arrayList.get(i).getLast_name());
        holder.txt_price.setText(arrayList.get(i).getPrice_offer());
        holder.txt_license.setText(arrayList.get(i).getCar_detail().get(0).getLicense_plate());
        holder.txt_province.setText(arrayList.get(i).getCar_detail().get(0).getProvince());
        holder.txt_type_car.setText(arrayList.get(i).getCar_detail().get(0).getGroup_name());
        holder.txt_date.setText(dateController.convertDateFormat2To1(arrayList.get(i).getDate_offer_create()));
        holder.txt_time.setText(arrayList.get(i).getDate_offer_create().substring(10,16));

        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogBottom(view,i);
            }
        });

    }

    private void setDialogBottom(View v,final int pos){
        try {
            LayoutInflater inflater = (LayoutInflater)v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_driver_detail, null);
            ImageView img_back = (ImageView)view.findViewById( R.id.img_back);
            ImageView img_profile = (ImageView)view.findViewById( R.id.img_profile);
            TextView txt_driver_name = (TextView)view.findViewById(R.id.txt_driver_name);
            TextView txt_driver_surname = (TextView)view.findViewById(R.id.txt_driver_surname);
            TextView txt_license = (TextView)view.findViewById(R.id.txt_license);
            TextView txt_province = (TextView)view.findViewById(R.id.txt_province);
            TextView txt_brand = (TextView)view.findViewById(R.id.txt_brand);
            TextView txt_model = (TextView)view.findViewById(R.id.txt_model);
            TextView txt_price = (TextView)view.findViewById(R.id.txt_price);
            Button btn_accept = (Button)view.findViewById(R.id.btn_accept);
            RecyclerView recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
            final Dialog mBottomSheetDialog = new Dialog (v.getContext(),R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (false);
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mcontext, 4);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.addItemDecoration(new ItemOffsetDecoration(mcontext, R.dimen.item_offset));
            AddImageAdapter adapter = new AddImageAdapter(mcontext,arrayList.get(pos).getCar_detail().get(0).getPicture());
            recycler_view.setAdapter(adapter);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_driver_name.setText(arrayList.get(pos).getFirst_name());
            txt_driver_surname.setText(arrayList.get(pos).getLast_name());
            txt_license.setText(arrayList.get(pos).getCar_detail().get(0).getLicense_plate());
            txt_province.setText(arrayList.get(pos).getCar_detail().get(0).getProvince());
            txt_brand.setText(arrayList.get(pos).getCar_detail().get(0).getCar_brand());
            txt_model.setText(arrayList.get(pos).getCar_detail().get(0).getCar_model());
            txt_price.setText(arrayList.get(pos).getPrice_offer());
            Picasso.with(mcontext).load(GlobalVar.url_up_pic + arrayList.get(pos).getName_pic_path())
                    .placeholder( R.drawable.progress_animation )
                    .fit().centerCrop().error( R.drawable.no_images ).into(img_profile);
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });
            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(service_type.equalsIgnoreCase("Booking")){
                        mView.callServiceUserAccept(Integer.parseInt(tblTask.getId()),arrayList.get(pos),service_type);
                        mBottomSheetDialog.dismiss();
                    }else {
                        UserMainActivity2 mainActivity2 = new UserMainActivity2();
                        mainActivity2.callUpdateTask(tblTask,arrayList.get(pos),3);
                        mBottomSheetDialog.dismiss();
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public DriverWaitAcceptAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_driver_wait, vGroup, false);
        return new DriverWaitAcceptAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_date,txt_time,txt_license,txt_province,txt_driver_name,txt_driver_surname,txt_price,txt_type_car;
        private RatingBar rating_driver;
        private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_date = (TextView) v.findViewById(R.id.txt_date);
            txt_time = (TextView) v.findViewById(R.id.txt_time);
            txt_license = (TextView) v.findViewById(R.id.txt_license);
            txt_province = (TextView) v.findViewById(R.id.txt_province);
            txt_driver_name = (TextView) v.findViewById(R.id.txt_driver_name);
            txt_driver_surname = (TextView) v.findViewById(R.id.txt_driver_surname);
            txt_price = (TextView) v.findViewById(R.id.txt_price);
            txt_type_car = (TextView) v.findViewById(R.id.txt_type_car);
            rating_driver = (RatingBar) v.findViewById(R.id.rating_driver);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
        }
    }
}
