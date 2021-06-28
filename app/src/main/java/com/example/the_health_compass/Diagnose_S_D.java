package com.example.the_health_compass;

public class Diagnose_S_D {
    private String D_ID,TD_ID,TD_Description,TD_Date_Diagnose,TD_Date_Update,Sick_ID;

    public Diagnose_S_D(String[] data){
        this.D_ID = data[0];
        this.TD_ID = data[1];
        this.TD_Description = data[2];
        this.TD_Date_Diagnose = data[3];
        this.TD_Date_Update = data[4];
        this.Sick_ID = data[5];
    }

    public Diagnose_S_D(){
        this.Sick_ID = "";
        this.TD_Date_Update = "";
        this.TD_Date_Diagnose = "";
        this.TD_Description = "";
        this.TD_ID = "";
        this.D_ID = "";
    }

    public String[] GetData(){
        String[] data = new String[6];
        data[0] = this.D_ID;
        data[1] = this.TD_ID;
        data[2] = this.TD_Description;
        data[3] = this.TD_Date_Diagnose;
        data[4] = this.TD_Date_Update;
        data[5] = this.Sick_ID;
        return data;
    }

    public String getD_ID() {
        return D_ID;
    }

    public String getTD_ID() {
        return TD_ID;
    }

    public String getTD_Description() {
        return TD_Description;
    }

    public void setD_ID(String d_ID) {
        D_ID = d_ID;
    }

    public void setTD_Description(String TD_Description) {
        this.TD_Description = TD_Description;
    }

    public void setTD_ID(String TD_ID) {
        this.TD_ID = TD_ID;
    }

    public String getTD_Date_Diagnose() {
        return TD_Date_Diagnose;
    }

    public String getSick_ID() {
        return Sick_ID;
    }

    public String getTD_Date_Update() {
        return TD_Date_Update;
    }

    public void setSick_ID(String sick_ID) {
        Sick_ID = sick_ID;
    }

    public void setTD_Date_Diagnose(String TD_Date_Diagnose) {
        this.TD_Date_Diagnose = TD_Date_Diagnose;
    }

    public void setTD_Date_Update(String TD_Date_Update) {
        this.TD_Date_Update = TD_Date_Update;
    }
}
