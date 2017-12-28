package com.dtc.sevice.truckclub.until;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class ApplicationController {
    private static ApplicationController instance = null;
    protected static Activity _activity;

    public static ApplicationController getInstance() {
        if (null == instance) {
            instance = new ApplicationController();
        }
        return instance;
    }

    public static Activity getAppActivity() {
        return _activity;

    }

    public static void setAppActivity(Activity a) {
        _activity = a;

    }

    public Bitmap getResizedBitmap(Bitmap bm, double newHeight, double newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;

    }
}
