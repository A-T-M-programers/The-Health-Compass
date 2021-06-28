package com.example.the_health_compass;

import java.sql.Date;

public class The_Diagnose
{
    private String D_ID;
    private String POB_S_ID ,TD_ID ,TD_Description,TD_Type,POB_ID;
    private String[] IL_ID;
    private String Show_Date;

    public The_Diagnose(){
        this.D_ID = "";
        this.POB_S_ID = "";
        this.TD_ID = "";
        this.TD_Description = "";
        this.TD_Type = "";
        this.POB_ID = "";
        this.Show_Date = "";
    }

    public The_Diagnose(String[] Data,String[] IL){
        this.TD_ID = Data[0];
        this.D_ID = Data[1];
        this.POB_ID = Data[2];
        this.POB_S_ID = Data[3];
        this.Show_Date = Data[4];
        this.IL_ID = IL;
    }

    public String getShow_Date() {
        return Show_Date;
    }

    public String getD_ID() {
        return D_ID;
    }

    public String[] getIL_ID() {
        return IL_ID;
    }

    public String getPOB_ID() {
        return POB_ID;
    }

    public String getPOB_S_ID() {
        return POB_S_ID;
    }

    public String getTD_Description() {
        return TD_Description;
    }

    public String getTD_ID() {
        return TD_ID;
    }

    public String getTD_Type() {
        return TD_Type;
    }

    public void setD_ID(String d_ID) {
        D_ID = d_ID;
    }

    public void setIL_ID(String[] IL_ID) {
        this.IL_ID = IL_ID;
    }

    public void setPOB_ID(String POB_ID) {
        this.POB_ID = POB_ID;
    }

    public void setPOB_S_ID(String POB_S_ID) {
        this.POB_S_ID = POB_S_ID;
    }

    public void setShow_Date(String show_Date) {
        Show_Date = show_Date;
    }

    public void setTD_Description(String TD_Description) {
        this.TD_Description = TD_Description;
    }

    public void setTD_ID(String TD_ID) {
        this.TD_ID = TD_ID;
    }

    public void setTD_Type(String TD_Type) {
        this.TD_Type = TD_Type;
    }

}
