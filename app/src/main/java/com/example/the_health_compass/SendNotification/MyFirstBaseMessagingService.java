package com.example.the_health_compass.SendNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.the_health_compass.R;
import com.example.the_health_compass.medical_advice_doctor;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirstBaseMessagingService extends FirebaseMessagingService {
    String title,message,token,user;
    private static final int PENDING_INTENT_ID = 9001;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title = remoteMessage.getData().get("Title");
        message = remoteMessage.getData().get("Message");
        token = remoteMessage.getData().get("token");
        user = remoteMessage.getData().get("User");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_app)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent(getApplicationContext(),token,user));
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
    private static PendingIntent contentIntent(Context context, String token,String User1) {
        Intent startActivityIntent = new Intent(context, medical_advice_doctor.class);
        startActivityIntent.putExtra("User",User1+"_ID");
        startActivityIntent.putExtra("Token",token);
        if (User1.equals("Sick")){
            startActivityIntent.putExtra("Type","Doctor");
        }else {
            startActivityIntent.putExtra("Type","Sick");
        }

        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
