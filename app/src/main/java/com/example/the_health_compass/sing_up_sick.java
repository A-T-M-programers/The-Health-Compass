package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.util.HashMap;

public class sing_up_sick extends AppCompatActivity {
    HashMap<String,String> sickmap = new HashMap<String,String>();
    Button CreatAccount ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_sick);
    }
}