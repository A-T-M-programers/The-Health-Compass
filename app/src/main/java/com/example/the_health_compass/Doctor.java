package com.example.the_health_compass;

import java.util.HashMap;

public class Doctor extends Share
{
    String D_Full_Name,D_Location,D_Gender,D_Birthday,D_Able,D_Sectionity;
    public void InputDoctor(HashMap<String,String>Doctor){
        this.D_Full_Name = Doctor.get("D_Full_Name");
        this.D_Birthday = Doctor.get("D_Birthday");
        this.D_Gender = Doctor.get("D_Gender");
        this.D_Able = Doctor.get("D_Able");
        this.D_Sectionity = Doctor.get("D_Sectionity");
    }
    public Doctor(){
        this.D_Full_Name ="";
        this.D_Gender="";
        this.D_Birthday="";
        this.D_Location="";
        this.D_Able = "";
        this.D_Sectionity="";
    }
}
