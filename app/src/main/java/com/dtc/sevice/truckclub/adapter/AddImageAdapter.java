package com.dtc.sevice.truckclub.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblPicture;
import com.dtc.sevice.truckclub.view.MultiPhotoSelectActivity;
import com.dtc.sevice.truckclub.view.driver.fragment.RegisterDriverFragmentThird;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by may on 9/25/2017.
 */

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolder>{
    private List<TblPicture> arrayList;
    private static Context mcontext;
    private int pos = 0;
    private RegisterDriverFragmentThird fragmentThird;
    public AddImageAdapter(Context context, List<TblPicture> picture) {
        this.arrayList = picture;
        this.mcontext = context;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        holder.checkBox1.setVisibility(View.GONE);
        String url = "file://" + arrayList.get(i).getPath();
//        File f = new File(arrayList.get(i).getPath());
        if(arrayList.get(i).getPath()==null){
            String url2 = GlobalVar.url_up_pic + arrayList.get(i).getName();
            Picasso.with(mcontext)
                    .load(url2)
                    .error(R.drawable.no_images)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.imageView);
        }else if(arrayList.get(i).getPath().length()>0){
            Picasso.with(mcontext)
                    .load(url)
                    .error(R.drawable.no_images)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.imageView);
        }else {
            Picasso.with(mcontext)
                    .load(R.drawable.add_image)
                    .placeholder(R.drawable.add_image)
                    .error(R.drawable.no_images)
                    .into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(arrayList.get(i).getPath()==null){

                }else if(arrayList.get(i).getPath().length()==0){
                    Intent i = new Intent(v.getContext(), MultiPhotoSelectActivity.class);
                    v.getContext().startActivity(i);
                }else {
                    setDialogBottom(v,i);
                }

            }
        });

    }

    private void setDialogBottom(View v,final int pos){
        try {
            LayoutInflater inflater = (LayoutInflater)v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.bottom_sheet, null);
            TextView txt_delete = (TextView)view.findViewById( R.id.txt_delete);
            TextView txt_cancel = (TextView)view.findViewById( R.id.txt_cancel);
            final Dialog mBottomSheetDialog = new Dialog (v.getContext(),R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentThird = new RegisterDriverFragmentThird();
                    fragmentThird.removePicture(pos);
                    Toast.makeText(v.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                    mBottomSheetDialog.dismiss();

                }
            });
            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    mBottomSheetDialog.dismiss();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public AddImageAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.row_multiphoto_item, vGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addAll(List<TblPicture> files) {

        try {
            this.arrayList.clear();
            this.arrayList.addAll(files);

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox1;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageView1);
            checkBox1 = (CheckBox)v.findViewById(R.id.checkBox1);
        }
    }
}
