package com.example.the_health_compass.SendNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAbOTBuKQ:APA91bFzgEVoT2VZ1dDQhUifSoqMSaBCgqxvY6kIewjAIPBA3uKWCLRTwaqaRVbP6t959V9jB7BUXsiWs6L6WGZThGOn-9oRmB2n6ewmGFc0J8LCrXp23csfj2F773COGraJmi-pH-5d"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
