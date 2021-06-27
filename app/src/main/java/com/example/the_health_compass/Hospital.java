package com.example.the_health_compass;

import java.util.HashMap;

public class Hospital  {
    int ID = 0;
   String H_Name , H_Location,H_Phone,H_Description;
   public Hospital() {
       H_Name = "";
       H_Location = "";
       H_Phone = "";
       H_Description= "";
   }
   public void InputHospital(HashMap<String,String> H){
       this.H_Name = H.get("H_Name");
       this.H_Location = H.get("H_Location");
       this.H_Phone = H.get("H_Phone");
       this.H_Description = H.get("H_Description");
       ID++;
   }
}
