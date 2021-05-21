package com.example.the_health_compass;

import android.media.Image;

import java.util.HashMap;

public class Share {
    protected String ID,Manager_ID;
    protected String Phone_Mobile,Phone_Earth,Password,Email,Check_Email;
    protected Image Personal_Image;
    protected boolean Blocking;
    public void InputShare(HashMap<String,String> sick, boolean Blocking){
        this.Blocking = Blocking;
        this.Check_Email = sick.get("Check_Email");
        this.Email = sick.get("Email");
        this.ID = "1";
        this.Password = sick.get("Password");
        this.Phone_Earth = sick.get("Phone_Earth");
        this.Phone_Mobile = sick.get("Phone_Mobile");
    }
}
