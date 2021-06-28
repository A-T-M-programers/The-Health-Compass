package com.example.the_health_compass;

import java.security.PublicKey;

public class Syndrome_IL {
    private String ID,Description,Contain;

    public Syndrome_IL(String[] Data){
        this.ID = Data[0];
        this.Description = Data[2];
        this.Contain = Data[3];
    }
    public Syndrome_IL(){
        this.ID="";
        this.Description="";
        this.Contain="";
    }

    public String[] getData(){
        String[] Data = new String[3];
        Data[0] = this.ID;
        Data[2] = this.Description;
        Data[3] = this.Contain;
        return Data;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContain() {
        return Contain;
    }

    public void setContain(String contain) {
        Contain = contain;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
