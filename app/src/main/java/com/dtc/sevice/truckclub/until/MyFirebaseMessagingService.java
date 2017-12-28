package com.dtc.sevice.truckclub.until;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.view.LoginFirstActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverBookingActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by May on 10/4/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();

        sendNotification(notification, data);
    }
    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_truck_club);
        boolean success = true;
        //Intent intent ;
        NotificationCompat.Builder notificationBuilder = null;
        Intent i= null;
        try {
            String message = data.get("message");

            if (message != null && !"".equals(message)) {
                if(message.equalsIgnoreCase("log out")){
                    Activity _activity = ApplicationController.getAppActivity();
                    i = new Intent(_activity, LoginFirstActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    _activity.finish();

                }else if(message.equalsIgnoreCase("new_now")||message.equalsIgnoreCase("timeout_driver")||message.equalsIgnoreCase("cancel")) {
                    Activity _activity = ApplicationController.getAppActivity();
                    i = new Intent(_activity, DriverMainActivity2.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    _activity.finish();
                }else if(message.equalsIgnoreCase("new_booking")||message.equalsIgnoreCase("accept")){
                    if(message.equalsIgnoreCase("new_booking")){
                        String start_date = data.get("start_date");
                        if(GlobalVar.checkNotiTaskBooking(start_date)){
                            Activity _activity = ApplicationController.getAppActivity();
                            i = new Intent(_activity, DriverBookingActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            _activity.finish();
                        }else {
                            success = false;
                        }
                    }else if(message.equalsIgnoreCase("accept")){
                        Activity _activity = ApplicationController.getAppActivity();
                        i = new Intent(_activity, DriverBookingActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        _activity.finish();
                    }


                }else if(message.equalsIgnoreCase("offer_now")||message.equalsIgnoreCase("offer_booking")||message.equalsIgnoreCase("wait arrive")||message.equalsIgnoreCase("arrive")
                        ||message.equalsIgnoreCase("done")||message.equalsIgnoreCase("timeout_user")){
                    Activity _activity = ApplicationController.getAppActivity();
                    i = new Intent(_activity, UserMainActivity2.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    _activity.finish();

                }else {
                    Activity _activity = ApplicationController.getAppActivity();
                    i = new Intent(_activity, LoginFirstActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    _activity.finish();
                }

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);

                notificationBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getBody())
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setContentInfo(notification.getTitle())
                        .setLargeIcon(icon)
                        .setColor(Color.RED)
                        .setSmallIcon(R.drawable.ic_truck_marker);
//                URL url = new URL(picture_url);
//                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                notificationBuilder.setStyle(
//                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
//                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(success){
            notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            notificationBuilder.setLights(Color.YELLOW, 1000, 300);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }

    }
}
