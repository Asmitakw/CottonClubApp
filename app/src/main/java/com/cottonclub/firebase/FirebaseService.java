package com.cottonclub.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.cottonclub.R;
import com.cottonclub.activities.BaseActivity;
import com.cottonclub.activities.LoginActivity;
import com.cottonclub.utilities.AppSession;
import com.cottonclub.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


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
        Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
        Intent intent;
        if (AppSession.getInstance().getLoginStatus(this)) {
            intent = new Intent(this, BaseActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_calendar)
                .setContentTitle(remoteMessage.getNotification().getTitle())
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
