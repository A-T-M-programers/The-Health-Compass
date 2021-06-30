package com.example.the_health_compass;

import android.content.Context;
import android.os.Bundle;

public class AppTask {
    public static final String ACTION_SHARE = "clear_all_notifications";
    public static final String ACTION_CLEAR_ALL_NOTIFICATION = "clear_all_notifications2";
    public static final String ACTION_BACKUP_DATABASE = "backup_database";
    public static final String ACTION_SYNC_ROOM_FIREBASE_DATABASE = "sync_room_firebase_database";
    public static final String ACTION_FETCH_API = "fetch_api";

    private AppTask() {
    }

    public static void executeTask(Context context, String action, Bundle extras) {
        if (ACTION_CLEAR_ALL_NOTIFICATION.equals(action)) {
            clearNotifications(context);
        } else if (ACTION_SHARE.equals(action)) {
            shareNotification(context, extras);
        } else if (ACTION_BACKUP_DATABASE.equals(action)) {

        } else if (ACTION_SYNC_ROOM_FIREBASE_DATABASE.equals(action)) {

        } else if (ACTION_FETCH_API.equals(action)) {
            fetchAPI();
        }

    }


    /**
     * Tasks
     */
    private static void clearNotifications(Context context) {
        Notifications.clearNotifications(context);
    }

    private static void shareNotification(Context context, Bundle extras) {
        Notifications.shareNotification(context, extras.getString("TITLE_KEY"), extras.getString("BODY_KEY"));
    }

    private static void fetchAPI() {
        // APIUtils.fetchDataFromAPI(context);
    }

}
