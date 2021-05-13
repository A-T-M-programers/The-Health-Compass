package com.example.the_health_compass;

import android.media.Image;

import java.util.Date;

public class Medical_Device extends Blocking
{
    String 	MD_Sectionity;
    Image MD_Image;

    public Image getMD_Image() {
        return MD_Image;
    }

    public String getMD_Sectionity() {
        return MD_Sectionity;
    }

    public void setMD_Image(Image MD_Image) {
        this.MD_Image = MD_Image;
    }

    public void setMD_Sectionity(String MD_Sectionity) {
        this.MD_Sectionity = MD_Sectionity;
    }
}
