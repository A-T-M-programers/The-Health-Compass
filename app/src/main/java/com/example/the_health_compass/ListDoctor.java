package com.example.the_health_compass;

import android.graphics.Bitmap;

public class ListDoctor {
    private String imageprofile;
    private String name;
    private String ID;
    private String location;

    public ListDoctor(String text1,String ID) {
        //imageprofile = imageResource;
        name = text1;
        this.ID = ID;
        //location = text2;
    }

    public String getID() {
        return ID;
    }

    public String getImageResource() {
        return imageprofile;
    }

    public String getText1() {
        return name;
    }

    public String getText2() {
        return location;
    }
}
