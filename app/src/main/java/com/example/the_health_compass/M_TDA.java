package com.example.the_health_compass;

import java.sql.Date;

public class M_TDA extends Total_D
{
    String MP_Contain , MP_Type;
    Date data;

    public Date getData() {
        return data;
    }

    public String getMP_Contain() {
        return MP_Contain;
    }

    public String getMP_Type() {
        return MP_Type;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setMP_Contain(String MP_Contain) {
        this.MP_Contain = MP_Contain;
    }

    public void setMP_Type(String MP_Type) {
        this.MP_Type = MP_Type;
    }
}
