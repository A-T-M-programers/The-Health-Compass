package com.example.the_health_compass;

import android.provider.ContactsContract;

import java.util.Date;

public class ListSick {
    private int ID;
    private String Name;
    private String Email;
    private String password;
    private String location;
    private String Subscription;
    private String check_email;
    private boolean blocking;
    private String image;
    private String create_date;
    private String gender;

    public ListSick(int ID, String name, String email, String password, String location, String subscription, String check_email, boolean blocking, String image, String create_date, String gender) {
        this.ID = ID;
        Name = name;
        Email = email;
        this.password = password;
        this.location = location;
        Subscription = subscription;
        this.check_email = check_email;
        this.blocking = blocking;
        this.image = image;
        this.create_date = create_date;
        this.gender = gender;
    }
}
