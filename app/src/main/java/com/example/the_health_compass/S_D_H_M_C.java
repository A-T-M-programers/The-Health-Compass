package com.example.the_health_compass;

import android.media.Image;

public class S_D_H_M_C extends  Name
{
    public  int Manager_ID;
    public   String Location;
    Boolean Blocking;
    public Image image;
    public int getManager_ID()
    {return Manager_ID;}
    public  void setManager_ID(int p)
    {
        Manager_ID=p;
    }
    public  Image getImage()
    {
        return image;
    }
    public  void setImage(Image q)
    {
        image=q;
    }

    public  Boolean getBlocking()
    {
        return Blocking;
    }
    public  void setBlocking(Boolean t)
    {
        Blocking=t;
    }
    public String getLocation() {
        return Location;
    }

    public void setLocation(String o) {
        Location = o;
    }

}
