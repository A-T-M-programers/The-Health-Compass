package com.example.the_health_compass;

import android.media.Image;

import java.util.Date;
import java.util.HashMap;

public class Advertising {
    int A_ID;
    String   A_Type,  A_Content;
    int D_FK_IDe ,M_Fk_ID;
    Date A_Date;
    String A_Image,A_p_ImageA;

    public Date getA_Date() {
        return A_Date;
    }

    public void setA_Date(Date a_Date) {
        A_Date = a_Date;
    }

    public String getA_Image() {
        return A_Image;
    }

    public String getA_p_ImageA() {
        return A_p_ImageA;
    }

    public int getA_ID() {
        return A_ID;
    }

    public int getD_FK_IDe() {
        return D_FK_IDe;
    }

    public int getM_Fk_ID() {
        return M_Fk_ID;
    }

    public String getA_Content() {
        return A_Content;
    }

    public String getA_Type() {
        return A_Type;
    }

    public void setA_Content(String a_Content) {
        A_Content = a_Content;
    }

    public void setA_ID(int a_ID) {
        A_ID = a_ID;
    }

    public void setA_Image(String a_Image) {
        A_Image = a_Image;
    }

    public void setA_p_ImageA(String a_p_ImageA) {
        A_p_ImageA = a_p_ImageA;
    }

    public void setA_Type(String a_Type) {
        A_Type = a_Type;
    }

    public void setD_FK_IDe(int d_FK_IDe) {
        D_FK_IDe = d_FK_IDe;
    }

    public void setM_Fk_ID(int m_Fk_ID) {
        M_Fk_ID = m_Fk_ID;
    }

    public void Input_Advertising(HashMap<String,String> add){
        this.A_Type = add.get("Adivertisement_Type");
        this.A_Content = add.get("Adivertisement_Content");
        this.A_Image = add.get("Adivertisement_Image");
    }

}
