package com.example.the_health_compass;

import android.widget.ImageView;

public class ListHospital {
    private String Image;
    private String Name;
    private String Location;
    public ListHospital(String name,String image,String location ) {
        Name = name;
        Location = location;
        Image = image;
    }
    public String getImage (){return Image;}
    public String getName (){return Name;}
    public String getLocation (){return Location;}
}
