package com.example.the_health_compass;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

public class Sick extends Share{
    String S_First_Name,S_Last_Name,S_Location,S_Nationality,S_Gender,S_Birthday;
    public void InPutSick(String sick[]){
        this.S_First_Name = sick[0];
        this.S_Birthday = sick[4];
        this.S_Gender = sick[3];
        this.S_Last_Name = sick[1];
        this.S_Location = sick[5];
        this.S_Nationality = sick[2];
    }
}
