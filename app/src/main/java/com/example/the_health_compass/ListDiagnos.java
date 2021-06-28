package com.example.the_health_compass;

import android.util.ArrayMap;

import java.util.ArrayList;

public class ListDiagnos {
    private String NameSick,AgeSick,PartOfBody,StyleBody,Description_Sick,Diagnos_System,Create_Diagnos,Finishe_Update;
    private ArrayList<String> SyndromeIl;
    private ArrayMap<String,String> Illness;
    public Diagnose_S_D diagnoseSD;

    public ListDiagnos(String[] data,ArrayList<String> Synrom,ArrayMap<String,String> Illnes){
        this.NameSick = data[0];
        this.AgeSick = data[1];
        this.PartOfBody = data[2];
        this.StyleBody = data[3];
        this.Description_Sick = data[4];
        this.Diagnos_System = data[5];
        this.Create_Diagnos = data[6];
        this.Finishe_Update = data[7];
        this.SyndromeIl = Synrom;
        this.Illness = Illnes;
    }
    public ListDiagnos(){
        this.NameSick = "";
        this.AgeSick = "";
        this.StyleBody = "";
        this.PartOfBody = "";
        this.Description_Sick = "";
        this.Diagnos_System = "";
        this.Create_Diagnos = "";
        this.Finishe_Update = "";
        this.Illness = new ArrayMap<>();
    }

    public ListDiagnos(Diagnose_S_D diagnose_s_d){
        this.diagnoseSD = diagnose_s_d;
    }

    public String getCreate_Diagnos() {
        return Create_Diagnos;
    }

    public String getFinishe_Update() {
        return Finishe_Update;
    }

    public void setCreate_Diagnos(String create_Diagnos) {
        Create_Diagnos = create_Diagnos;
    }

    public void setFinishe_Update(String finishe_Update) {
        Finishe_Update = finishe_Update;
    }

    public Diagnose_S_D getDiagnoseSD() {
        return diagnoseSD;
    }

    public String getDescription_Sick() {
        return Description_Sick;
    }

    public String getDiagnos_System() {
        return Diagnos_System;
    }

    public void setDescription_Sick(String description_Sick) {
        Description_Sick = description_Sick;
    }

    public void setDiagnos_System(String diagnos_System) {
        Diagnos_System = diagnos_System;
    }

    public void setDiagnoseSD(Diagnose_S_D diagnoseSD) {
        this.diagnoseSD = diagnoseSD;
    }

    public String getAgeSick() {
        return AgeSick;
    }

    public ArrayMap<String, String> getIllness() {
        return Illness;
    }

    public String getNameSick() {
        return NameSick;
    }

    public String getPartOfBody() {
        return PartOfBody;
    }

    public String getStyleBody() {
        return StyleBody;
    }

    public ArrayList<String> getSyndromeIl() {
        return SyndromeIl;
    }

    public void setAgeSick(String ageSick) {
        AgeSick = ageSick;
    }

    public void setIllness(ArrayMap<String, String> illness) {
        Illness = illness;
    }

    public void setNameSick(String nameSick) {
        NameSick = nameSick;
    }

    public void setPartOfBody(String partOfBody) {
        PartOfBody = partOfBody;
    }

    public void setStyleBody(String styleBody) {
        StyleBody = styleBody;
    }

    public void setSyndromeIl(ArrayList<String> syndromeIl) {
        SyndromeIl = syndromeIl;
    }
}
