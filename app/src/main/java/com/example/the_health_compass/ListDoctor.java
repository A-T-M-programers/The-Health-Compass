package com.example.the_health_compass;

import android.graphics.Bitmap;

public class ListDoctor {
    private String imageprofile;
    private String name;
    private String location;

    public ListDoctor(String text1) {
        //imageprofile = imageResource;
        name = text1;
        //location = text2;
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
