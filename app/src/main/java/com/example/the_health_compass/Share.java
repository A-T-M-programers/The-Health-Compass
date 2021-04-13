package com.example.the_health_compass;

import android.media.Image;

public class Share {
    protected String ID,Manager_ID;
    protected String Phone_Mobile,Phone_Earth,Password,Email,Check_Email;
    protected Image Personal_Image;
    protected boolean Blocking;
    public void InputShare(String sick[],Image Personal_Image,boolean Blocking){
        this.Blocking = Blocking;
        this.Check_Email = sick[3];
        this.Email = sick[1];
        this.ID = sick[0];
        this.Manager_ID = sick[4];
        this.Password = sick[2];
        this.Personal_Image = Personal_Image;
        this.Phone_Earth = sick[5];
        this.Phone_Mobile = sick[6];
    }
}
