package com.example.the_health_compass;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;

public class Notifications {
    private static final int NOTIFICATION_ID = 9000;
    private static final int PENDING_INTENT_ID = 9001;
    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel_name";
    private static NotificationCompat.Builder mNotificationBuilder ;
    private static NotificationManager mNotificationManager;
    private static boolean isNotificationNew = true;
    private static String User1 = "";

    private Notifications() {}

    public static void showNotification(Context context, String title, String body,String User) {
        User1 = User;
        if (!isNotificationNew) {
            updateNotification(title, body);
            return;
        }
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Awesome Notification Channel";
            String description = "This channel shows only awesome notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(channel);

            channel.setDescription(description);
        }
        mNotificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setGroup(NotificationCompat.CATEGORY_REMINDER)
                .setGroupSummary(true)
                .setSmallIcon(R.mipmap.ic_app)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(contentIntent(context))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .addAction(actionOne(context))
//                .addAction(actionTwo(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mNotificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        }

        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
        isNotificationNew = false;
    }
    public static void updateNotification(String title, String body) {
        mNotificationBuilder
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setOnlyAlertOnce(true)
        ;

        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, medical_advice_doctor.class);
        startActivityIntent.putExtra("User",User1+"_ID");
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

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.mipmap.ic_app);
        return largeIcon;
    }

    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void shareNotification(Context context, String title, String body){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title);
        stringBuilder.append("\n");
        stringBuilder.append(body);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
        context.startActivity(intent.createChooser(intent, "Choose the app you want to share on it"));
    }

    public class TaskService extends IntentService {


        public TaskService() {
            super("TaskService");
        }

        @Override
        protected void onHandleIntent( Intent intent) {
            String action = intent.getAction();
            AppTask.executeTask(this, action, intent.getExtras());

        }
    }



    private static NotificationCompat.Action actionShareNotification(Context context, String title, String body) {
        Intent intent = new Intent(context, NotificationListenerService.class);
        intent.setAction(AppTask.ACTION_SHARE);
        intent.putExtra("TITLE_KEY", title);
        intent.putExtra("BODY_KEY", body);

        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Action action = new NotificationCompat.Action(
                R.mipmap.ic_app,
                context.getString(R.string.share),
                pendingIntent
        );

        return action;
    }

    private static NotificationCompat.Action actionClearNotifications(Context context) {
        Intent intent = new Intent(context, NotificationListenerService.class);
        intent.setAction(AppTask.ACTION_CLEAR_ALL_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Action action = new NotificationCompat.Action(
                R.mipmap.ic_app,
                context.getString(R.string.cancel),
                pendingIntent
        );
        return action;
    }

    public static class TimerWithWorkManager extends Worker {

        public TimerWithWorkManager( Context context, WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            String title = getInputData().getString("Title");
            String body = getInputData().getString("Body");
            String user = getInputData().getString("User");
            String user_id = getInputData().getString("User_ID");
            ArrayList<Diagnose_S_D> arrayList = new ArrayList<>();
            if (user.equals("Sick")){
                arrayList = new DataAccessLayer().getDiagnos_S_D("Sick_ID",user_id,"Doctor");
            }else {
                arrayList = new DataAccessLayer().getDiagnos_S_D("Doctor_ID",user_id,"Sick");
            }
            for (int i = 0 ; i<arrayList.size();i++) {
                Notifications.showNotification(getApplicationContext(),"أستشارة طبية","لديك استشارة طبية قيد الأستجابة يمكنك الرد عليها بالضغط على الأشعار",user);
            }
            return Result.success();
        }
    }
}

