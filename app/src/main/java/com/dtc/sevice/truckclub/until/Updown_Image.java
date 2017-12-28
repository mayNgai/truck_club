package com.dtc.sevice.truckclub.until;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Future;


/**
 * Created by admin on 9/20/2017 AD.
 */

public class Updown_Image {
    public String SendImageNode(final Context context, String path)
    {
        File f = new File(path);
        Future uploading = Ion.with(context)
                .load(GlobalVar.url_NodeImage + "/upload")
                .setMultipartFile("image", f)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        try {
                            JSONObject jobj = new JSONObject(result.getResult());
                            Toast.makeText(context, jobj.getString("response"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
        return "OK";
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public Bitmap GetImageUrl(String imggeUrl)
    {
        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(imggeUrl);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
