package com.example.the_health_compass;

import java.util.List;

public class POB_Style extends Part_Of_Body {
    private String POB_ID;
    private List<Syndrome_IL> syndrome_ils;
    public POB_Style(String[] Data){
        this.ID = Data[0];
        this.Name = Data[1];
        this.Description = Data[2];
        this.POB_ID = Data[3];
    }

    public POB_Style(){
        this.ID = "";
        this.Name = "";
        this.Description = "";
        this.POB_ID = "";
    }

    public String[] getData(){
        String[] Data = new String[4];
        Data[0] = this.ID;
        Data[1] = this.Name;
        Data[2] = this.Description;
        Data[3] = this.POB_ID;
        return Data;
    }

    public String getPOB_ID() {
        return POB_ID;
    }

    public void setPOB_ID(String POB_ID) {
        this.POB_ID = POB_ID;
    }

    public List<Syndrome_IL> getSyndrome_ils() {
        return syndrome_ils;
    }

    public void setSyndrome_ils(List<Syndrome_IL> syndrome_ils) {
        this.syndrome_ils = syndrome_ils;
    }
}
