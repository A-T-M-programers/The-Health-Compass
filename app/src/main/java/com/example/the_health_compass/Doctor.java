package com.example.the_health_compass;

import java.util.Calendar;
import java.util.HashMap;

public class Doctor extends Share
{
    String D_Full_Name,D_Location,D_Gender,D_Birthday,D_Able,D_Specialization,Age;
    public void InputDoctor(HashMap<String,String>Doctor){
        this.D_Full_Name = Doctor.get("D_Full_Name");
        this.D_Birthday = Doctor.get("D_Birthday");
        this.D_Gender = Doctor.get("D_Gender");
        this.D_Able = Doctor.get("D_Able");
        this.D_Specialization = Doctor.get("D_Specialization");
        InputAge();
    }
    public Doctor(){
        this.D_Full_Name ="";
        this.D_Gender="";
        this.D_Birthday="";
        this.D_Location="";
        this.D_Able = "";
        this.D_Specialization="";
    }
    public void InputAge(){
        Calendar now = Calendar.getInstance();
        int year = Integer.parseInt(this.D_Birthday.substring(0,4));
        this.Age = String.valueOf(now.get(Calendar.YEAR)-year);
    }
}
