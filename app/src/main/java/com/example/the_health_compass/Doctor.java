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

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getD_Full_Name() {
        return D_Full_Name;
    }

    public String getD_Able() {
        return D_Able;
    }

    public String getD_Location() {
        return D_Location;
    }

    public String getD_Birthday() {
        return D_Birthday;
    }

    public String getD_Gender() {
        return D_Gender;
    }

    public String getD_Specialization() {
        return D_Specialization;
    }

    public void setD_Able(String d_Able) {
        D_Able = d_Able;
    }

    public void setD_Birthday(String d_Birthday) {
        D_Birthday = d_Birthday;
        Calendar now = Calendar.getInstance();
        int year = Integer.parseInt(this.D_Birthday.substring(0,4));
        this.Age = String.valueOf(now.get(Calendar.YEAR)-year);
    }

    public void setD_Full_Name(String d_Full_Name) {
        D_Full_Name = d_Full_Name;
    }

    public void setD_Gender(String d_Gender) {
        D_Gender = d_Gender;
    }

    public void setD_Location(String d_Location) {
        D_Location = d_Location;
    }

    public void setD_Specialization(String d_Specialization) {
        D_Specialization = d_Specialization;
    }

}
