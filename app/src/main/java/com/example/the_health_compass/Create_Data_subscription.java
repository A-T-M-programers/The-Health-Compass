package com.example.the_health_compass;

import java.util.Date;

public class Create_Data_subscription extends S_D_H_M_C{
    public Date Create_Date;
    public   String Subscription;
    public Date getCreate_Date()
    {
        return Create_Date;
    }
    public  void  setCreate_Date(Date r)
    {
        Create_Date=r;
    }


    public String getSubscription() {
        return Subscription;
    }

    public void setSubscription(String i) {
        Subscription = i;

    }

}
