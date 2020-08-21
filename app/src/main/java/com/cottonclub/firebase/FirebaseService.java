package com.cottonclub.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.cottonclub.R;
import com.cottonclub.activities.LoginActivity;
import com.cottonclub.activities.admin.ViewAlterRequestNotificationDetails;
import com.cottonclub.activities.admin.ViewJobCardNotificationDetails;
import com.cottonclub.utilities.AppSession;
import com.cottonclub.utilities.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;


public class FirebaseService extends FirebaseMessagingService {
    private static final String TAG = "Notify";

    public FirebaseService() {
    }

    private static int value = 0;
    String title = "0";
    String type = "0";
    String message = "0";
    Context context;
    private String notificationMessage = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        this.context = this;
        if (AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_KM)
                || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_BB)
                || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_CB)) {

            Intent intent = null;

            if (AppSession.getInstance().getLoginStatus(this)) {
                if (Objects.requireNonNull(Objects.requireNonNull(remoteMessage.getNotification()).getBody()).contains("Job Card")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("position", remoteMessage.getNotification().getTag());
                    intent = new Intent(context, ViewJobCardNotificationDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("extraWithOrder", bundle);
                    AppSession.getInstance().saveIsNotified(context, true);
                }

                else if (Objects.requireNonNull(Objects.requireNonNull(remoteMessage.getNotification()).getBody()).contains("Alter Request")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("position", remoteMessage.getNotification().getTag());
                    intent = new Intent(context, ViewAlterRequestNotificationDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("extraWithOrder", bundle);
                    AppSession.getInstance().saveIsNotified(context, true);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                }

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                String channelId = "Default";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_calendar)
                        .setContentTitle(remoteMessage.getNotification().getTag())
                        .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                }
                notificationMessage = remoteMessage.getNotification().getBody();
                if (AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_KM)
                        || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_BB)
                        || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_CB)) {

                    if (notificationMessage.endsWith(Constants.KIDS_MAGIC)) {
                        manager.notify(0, builder.build());
                    } else if (notificationMessage.endsWith(Constants.BBABY)) {
                        manager.notify(0, builder.build());
                    } else if (notificationMessage.endsWith(Constants.COTTON_BLUE)) {
                        manager.notify(0, builder.build());
                    }
                }
            }

        } else {
            ///Toast.makeText(context,"est",Toast.LENGTH_SHORT).show();
            Log.e("TAG", "No data");
        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }



   /* private void sendNotification(String type, String title, String message) {
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.mipmap.add_button);
        Intent mainIntent;

        //AppMethod.setIntegerPreference(getApplicationContext(),NOTIFICATION_ID,)
        if (AppSession.getInstance().getLoginStatus(this)) {
            mainIntent = new Intent(this, HomeActivity.class);
            mainIntent.putExtra("type", type);
        } else {
            mainIntent = new Intent(this, LoginActivity.class);
            mainIntent.putExtra("type", type);
        }*/


}
