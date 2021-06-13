package com.example.the_health_compass;

import android.media.Image;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Sick extends Share{
    String S_Full_Name,S_Location,S_Gender,S_Birthday,Age;

    public void InPutSick(HashMap<String,String> sick){
        this.S_Full_Name = sick.get("S_Full_Name");
        this.S_Birthday = sick.get("S_Birthday");
        this.S_Gender = sick.get("S_Gender");
        InputAge();
    }
    public Sick(){
        this.S_Full_Name = "";
        this.S_Gender = "";
        this.S_Location = "";
        this.S_Birthday = "";
        this.Age = "";
    }
    public void InputAge(){
        Calendar now = Calendar.getInstance();
        int year = Integer.parseInt(this.S_Birthday.substring(0,4));
        this.Age = String.valueOf(now.get(Calendar.YEAR)-year);
    }
}
