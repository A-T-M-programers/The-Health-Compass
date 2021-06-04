package com.example.the_health_compass;

import android.media.Image;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Share {
    protected String ID,Phone_Mobile,Password,Email,Check_Email,Subscription;
    protected Image Personal_Image;
    protected boolean Blocking;
    int id = 0;
    public void InputShare(HashMap<String,String> sick, boolean Blocking){
        this.Blocking = Blocking;
        this.Check_Email = sick.get("Check_Email");
        this.Email = sick.get("Email");
        this.ID = String.valueOf(id);
        this.Password = sick.get("Password");
        this.Phone_Mobile = sick.get("Phone_Mobile");
        id++;
    }
}
